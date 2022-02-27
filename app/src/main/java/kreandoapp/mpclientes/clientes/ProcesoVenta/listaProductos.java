package kreandoapp.mpclientes.clientes.ProcesoVenta;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.AdapterProductos;
import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.db.database.AppDb;
import kreandoapp.mpclientes.db.entity.Ordenes;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.loginapp.MainActivity;
import kreandoapp.mpclientes.modoadmin.EditarCategoria.EditarsubCategoriaActivity;
import kreandoapp.mpclientes.modoadmin.EditarCategoria.EliminarSubcategoriaActivity;
import kreandoapp.mpclientes.pojo.pojo_productos;
import io.github.inflationx.calligraphy3.CalligraphyConfig;

public class listaProductos extends AppCompatActivity implements AdapterProductos.Acciones{
    detectorInternet internet;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    RecyclerView rv_productos;
    AdapterProductos adapter;
    ArrayList<pojo_productos> productosArrayList;
    ProgressBar progressBar;
    private LinearLayoutManager mLayoutManager;
    Button btn_eliminarcate,btn_editarcategoria;

    ImageButton botonvolver;
    ImageButton editarcate;
    TextView tv_titulo_toolbar;
    //
    TextView tv_total_precio_toolbar,tv_total_count_toolbar;
    Toolbar tb_toolbar_carrito;

    Double total = 0.0;



    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }
    @Override


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String currentUserID = user.getUid();

            // Use currentUserID
        }else {
            gologing();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/neufreu.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_lista_productos);

        progressBar =findViewById(R.id.progress_bar);

        //Boton animar...
        tv_total_precio_toolbar = findViewById(R.id.tv_total_toolbar);
        tv_total_count_toolbar = findViewById(R.id.tv_total_count_toolbar);
        //aqui animar---
        tb_toolbar_carrito = findViewById(R.id.tb_toolbar_carrito);



        dametotal();

        tb_toolbar_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internet.estaConectado()){
                    finish();
                    Intent intent = new Intent(v.getContext(), DetectandoUbicacionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }else {
                    Snackbar snackbar = Snackbar.make(v, "No existe conexion a internet", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(true);
        rv_productos =  findViewById(R.id.recycler_listaproductos);
        rv_productos.setLayoutManager(mLayoutManager);

        // MOSTRAR RECYCLER VIEW

        productosArrayList = new ArrayList<>();
        adapter = new AdapterProductos(productosArrayList,this,this);
        rv_productos.setAdapter(adapter);

        final String cateIdkey = getIntent().getExtras().getString("cateId");
        final String cate = getIntent().getExtras().getString("catename");
        final String catimagen = getIntent().getExtras().getString("catImage");
        final String caturlcambiar = getIntent().getExtras().getString("catUrlcambiar");
        final int orden = getIntent().getExtras().getInt("orden");

        //declarando ids


        //Toast.makeText(this, cateIdkey, Toast.LENGTH_SHORT).show();







        internet = new detectorInternet(this);

        botonvolver = findViewById(R.id.btn_volverAtras);
        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });
        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Sub categoría: " + cate);


        editarcate = findViewById(R.id.btn_editar);
        editarcate.setOnClickListener(v -> {
            mostrardialog();

        });

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        assert user != null;
        DatabaseReference myRef22 = database111.getReference("modoadmin").child("id");
        myRef22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);

                    if(val.equals(user.getUid())){

                       loadadmin();

                    }else{

                       loaduser();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }//fin oncreate
    private void mostrardialog() {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(listaProductos.this);
        View view = getLayoutInflater().inflate(R.layout.dialogeditarcate,null);


        Button btn_editar = view.findViewById(R.id.btn_editar);
        Button btn_eliminar = view.findViewById(R.id.btn_eliminar);
        Button btn_salir = view.findViewById(R.id.btn_salir);


        mbuilder.setCancelable(false);
        mbuilder.setView(view);
        AlertDialog dialog = mbuilder.create();
        dialog.show();

        btn_salir.setOnClickListener(v ->
                dialog.dismiss()
        );

        btn_editar.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), EditarsubCategoriaActivity.class);
            final String cateIdkey = getIntent().getExtras().getString("cateId");

            final String cate = getIntent().getExtras().getString("catename");

            final String catimagen = getIntent().getExtras().getString("catImage");
            final String caturlcambiar = getIntent().getExtras().getString("catUrlcambiar");
            final int orden = getIntent().getExtras().getInt("orden");

            i.putExtra("id",cateIdkey);
            i.putExtra("cate",cate);
            i.putExtra("cateImage",catimagen);
            i.putExtra("cateurlImage",caturlcambiar);
            i.putExtra("orden",String.valueOf(orden));
            startActivity(i);
            finish();
        });

        btn_eliminar.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), EliminarSubcategoriaActivity.class);
            final String cateIdkey = getIntent().getExtras().getString("cateId");
            final String cate = getIntent().getExtras().getString("catename");
            final String caturlcambiar = getIntent().getExtras().getString("catUrlcambiar");
            i.putExtra("id",cateIdkey);
            i.putExtra("cate",cate);
            i.putExtra("urlimg",caturlcambiar);

            startActivity(i);
            finish();
        });
    }

    private void dametotal() {
        int count = AppDb.getAppDb(getApplicationContext()).ordenesDao().dameTodoCount();
        if(count>0){
            tb_toolbar_carrito.setVisibility(View.VISIBLE);
            List<Ordenes> lista = AppDb.getAppDb(getApplicationContext()).ordenesDao().dametodaslasOrdenes();
            tv_total_count_toolbar.setText(String.valueOf(count));
            total =0.0;
            for (Ordenes ord : lista){


                Double precio = Double.parseDouble(ord.getPrecio());
                Double cant = Double.parseDouble(ord.getCantidad());
                total += precio * cant;

                tv_total_precio_toolbar.setText("s/ " +total);
                animaciontotal();
                animacioncount();
            }

        }
    }

    private void animaciontotal(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.precio_total_anim);
        tv_total_precio_toolbar.startAnimation(animation);
    }
    private void animacioncount(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.count_total_anim);
        tv_total_count_toolbar.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(internet.estaConectado()){

        }else{
            finish();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No existe conexion a internet", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void loaduser() {

        editarcate.setVisibility(View.GONE);
        String cateIdkey = getIntent().getExtras().getString("cateId");
        int orden = getIntent().getExtras().getInt("orden");

        FirebaseDatabase database22 = FirebaseDatabase.getInstance();
        DatabaseReference myRef33 = database22.getReference("Productos");
       // Query query4 = myRef33.child("Productos").orderByChild("prodCategoriaID").equalTo(cateIdkey);
        Query query4 = myRef33.orderByChild("orden");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                    productosArrayList.removeAll(productosArrayList);
                    rv_productos.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {


                        pojo_productos prod = snapshot.getValue(pojo_productos.class);


                        if(prod.getProdStock().equals("si") && prod.getProdCategoriaID().equals(cateIdkey)){
                            productosArrayList.add(prod);
                            rv_productos.getLayoutManager().scrollToPosition(0);
                        }


                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(listaProductos.this, "No hay productos en esta Categoria.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void loadadmin() {
        editarcate.setVisibility(View.VISIBLE);

        String cateIdkey = getIntent().getExtras().getString("cateId");

        int orden = getIntent().getExtras().getInt("orden");

        FirebaseDatabase database22 = FirebaseDatabase.getInstance();
        DatabaseReference myRef33 = database22.getReference("Productos");
        Query query4 = myRef33.orderByChild("orden");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                    productosArrayList.removeAll(productosArrayList);
                    rv_productos.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                        pojo_productos prod = snapshot.getValue(pojo_productos.class);

                        if(prod.getProdCategoriaID().equals(cateIdkey)){
                            productosArrayList.add(prod);
                            rv_productos.getLayoutManager().scrollToPosition(0);


                        }




                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(listaProductos.this, "No hay productos en esta Categoria.", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                Intent b= new Intent(this, home1.class);
                startActivity(b);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    //INTERFACE AL ADAPTER.


    @Override
    public void AccionAgregar(String nombre,String detalle, String precio, String imagen,String prodId, String cantidad,String codprod) {
        Ordenes ord = new Ordenes();
        ord.setNombre(nombre);
        ord.setDetalle(detalle);
        ord.setPrecio(precio);
        ord.setImagen(imagen);
        ord.setIdproducto(prodId);
        ord.setCantidad(cantidad);
        ord.setCodprod(codprod);

        AppDb.getAppDb(getApplicationContext()).ordenesDao().insertarOrdenes(ord);

        dametotal();

    }

    @Override
    public void accionBorrar(int id, int posicion) {

    }
    private void gologing() {
        Intent i = new Intent(listaProductos.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}

