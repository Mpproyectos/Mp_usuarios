package kreandoapp.mpclientes.clientes;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.AdapterPromociones;
import kreandoapp.mpclientes.db.database.AppDb;
import kreandoapp.mpclientes.db.entity.Ordenes;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.pojo.pojo_productos;
import io.github.inflationx.calligraphy3.CalligraphyConfig;


public class promociones extends AppCompatActivity implements AdapterPromociones.Acciones {

    ImageButton botonvolver;
    TextView tv_titulo_toolbar;
    RecyclerView rv_promo;
    detectorInternet internet;
    ArrayList<pojo_productos> orderArrayList;
    AdapterPromociones adapter;
    private LinearLayoutManager mLayoutManager;
    ProgressBar progressBar;

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
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, home1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
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
        setContentView(R.layout.activity_promociones);


        final Calendar c = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");


        internet = new detectorInternet(this);
        botonvolver = findViewById(R.id.btn_volverAtras);
        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomtext);
                botonvolver.startAnimation(animation);

                Intent intent = new Intent(v.getContext(),home1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Promociones");


        progressBar = findViewById(R.id.progress_bar);


        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv_promo =  findViewById(R.id.rv_promo);
        rv_promo.setLayoutManager(mLayoutManager);

// MOSTRAR RECYCLER VIEW

        orderArrayList = new ArrayList<>();
        adapter = new AdapterPromociones(orderArrayList,this,this);
        rv_promo.setAdapter(adapter);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("Promo");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    progressBar.setVisibility(View.GONE);
                    rv_promo.setVisibility(View.VISIBLE);
                    orderArrayList.removeAll(orderArrayList);


                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        pojo_productos req = snapshot.getValue(pojo_productos.class);
                        orderArrayList.add(req);


                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(promociones.this, "Ups, No existen promociones!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(promociones.this, home1.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//fin oncreate!

    @Override
    protected void onResume() {
        super.onResume();
        if(internet.estaConectado()){

        }else{

            Toast.makeText(this, "No existe conexion a internet", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent i = new Intent(this,home1.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void AccionAgregar(String nombre, String precio, String imagen,String prodId, String cantidad,String codprod) {

        Ordenes ord = new Ordenes();
        ord.setNombre(nombre);
        ord.setPrecio(precio);
        ord.setImagen(imagen);
        ord.setIdproducto(prodId);
        ord.setCantidad(cantidad);
        ord.setCodprod(codprod);

        AppDb.getAppDb(getApplicationContext()).ordenesDao().insertarOrdenes(ord);
    }

    @Override
    public void accionBorrar(int id, int posicion) {

    }
}
