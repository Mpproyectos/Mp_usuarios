package kreandoapp.mpclientes.clientes;

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
import kreandoapp.mpclientes.adapter.AdapterCoctel;
import kreandoapp.mpclientes.modoadmin.EditarCategoria.eliminarcategoria;
import kreandoapp.mpclientes.modoadmin.Coctels.EditarDatosCateCoctelActivity;
import kreandoapp.mpclientes.pojo.Coctelpojo;

public class PreparaCoctelActivity extends AppCompatActivity {

    ArrayList<Coctelpojo> coctelpojoArrayList;
    AdapterCoctel adapter;
    RecyclerView rv_subcategory;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ProgressBar loadrv;

    ImageButton botonvolver;
    TextView tv_titulo_toolbar;
    ImageButton editarcate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepara_coctel);


        loadrv = findViewById(R.id.load_rv);



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
        tv_titulo_toolbar.setText("Cocteles: ");

        rv_subcategory = findViewById(R.id.recycler_menu);
        rv_subcategory.setLayoutManager(new GridLayoutManager(this, 2));

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();





        //Mostrar modo admin

        // MOSTRAR RECYCLER VIEW

        coctelpojoArrayList = new ArrayList<>();
        adapter = new AdapterCoctel(coctelpojoArrayList, this);
        rv_subcategory.setAdapter(adapter);

        // conexion();


        DatabaseReference myRef4 = database.getReference("Coctel");
        Query query4 = myRef4.orderByChild("orden");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    loadrv.setVisibility(View.GONE);
                    rv_subcategory.setVisibility(View.VISIBLE);
                    coctelpojoArrayList.removeAll(coctelpojoArrayList);


                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        Coctelpojo req = snapshot.getValue(Coctelpojo.class);
                        coctelpojoArrayList.add(req);




                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(PreparaCoctelActivity.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }//fin del oncreate!

    private void mostrardialog(String url,String version) {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(PreparaCoctelActivity.this);
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
            Intent i = new Intent(getApplicationContext(), EditarDatosCateCoctelActivity.class);

            //ERROR AQUI
            final int orden = getIntent().getExtras().getInt("orden");
            final String titulo = getIntent().getExtras().getString("titulo");
            final String id = getIntent().getExtras().getString("id");
            final String foto = getIntent().getExtras().getString("foto");
            final String fotourlcambiar = getIntent().getExtras().getString("fotourlcambiar");


            i.putExtra("id",id);
            i.putExtra("titulo",titulo);
            i.putExtra("foto",foto);
            i.putExtra("fotourlcambiar",fotourlcambiar);
            i.putExtra("orden",String.valueOf(orden));
            startActivity(i);
            finish();
        });

        btn_eliminar.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), eliminarcategoria.class);
            final String id = getIntent().getExtras().getString("id");
            final String cate = getIntent().getExtras().getString("catename");
            final String caturlcambiar = getIntent().getExtras().getString("catUrlcambiar");
            i.putExtra("id",id);
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