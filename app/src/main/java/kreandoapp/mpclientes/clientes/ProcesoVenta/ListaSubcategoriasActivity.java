package kreandoapp.mpclientes.clientes.ProcesoVenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.AdaptersubCategoria;
import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.modoadmin.EditarCategoria.editarCategoriaActivity;
import kreandoapp.mpclientes.modoadmin.EditarCategoria.eliminarcategoria;
import kreandoapp.mpclientes.pojo.SubCategoria;

public class ListaSubcategoriasActivity extends AppCompatActivity {

    ArrayList<SubCategoria> subcategoryArrayList;
    AdaptersubCategoria adapter;
    RecyclerView rv_subcategory;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ProgressBar loadrv;

    ImageButton botonvolver;
    TextView tv_titulo_toolbar;
    ImageButton editarcate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_subcategorias);

        loadrv = findViewById(R.id.load_rv);
        final String cateIdkey = getIntent().getExtras().getString("idcate");
        final String cate = getIntent().getExtras().getString("catename");

        final String catimagen = getIntent().getExtras().getString("catImage");
        final String caturlcambiar = getIntent().getExtras().getString("catUrlcambiar");
        final int orden = getIntent().getExtras().getInt("orden");

        botonvolver = findViewById(R.id.btn_volverAtras);
        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),home1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        editarcate = findViewById(R.id.btn_editar);
        editarcate.setOnClickListener(v -> {
            mostrardialog("12","1.1");

        });

        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Categoría: " + cate);

        rv_subcategory = findViewById(R.id.recycler_menu);
        rv_subcategory.setLayoutManager(new GridLayoutManager(this, 2));

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

                       editarcate.setVisibility(View.VISIBLE);

                    }else{

                        editarcate.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //Mostrar modo admin

        // MOSTRAR RECYCLER VIEW

        subcategoryArrayList = new ArrayList<>();
        adapter = new AdaptersubCategoria(subcategoryArrayList, this);
        rv_subcategory.setAdapter(adapter);

        // conexion();


        DatabaseReference myRef4 = database.getReference("SubCategoria");
        Query query4 = myRef4.orderByChild("orden");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    loadrv.setVisibility(View.GONE);
                    rv_subcategory.setVisibility(View.VISIBLE);
                    subcategoryArrayList.removeAll(subcategoryArrayList);


                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        SubCategoria req = snapshot.getValue(SubCategoria.class);

                        if(req.getIdcate().equals(cateIdkey)){
                            subcategoryArrayList.add(req);
                        }



                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListaSubcategoriasActivity.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }//fin del oncreate!

    private void mostrardialog(String url,String version) {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(ListaSubcategoriasActivity.this);
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
            Intent i = new Intent(getApplicationContext(), editarCategoriaActivity.class);
            final String cateIdkey = getIntent().getExtras().getString("idcate");
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
            Intent i = new Intent(getApplicationContext(), eliminarcategoria.class);
            final String cateIdkey = getIntent().getExtras().getString("idcate");
            final String cate = getIntent().getExtras().getString("catename");
            final String caturlcambiar = getIntent().getExtras().getString("catUrlcambiar");
            i.putExtra("id",cateIdkey);
            i.putExtra("cate",cate);
            i.putExtra("urlimg",caturlcambiar);

            startActivity(i);
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home1, menu);
        return true;
    }


}