package kreandoapp.mpclientes.clientes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import kreandoapp.mpclientes.adapter.AdapterIMGCoctel;
import kreandoapp.mpclientes.modoadmin.Coctels.EditarDatosCateCoctelActivity;
import kreandoapp.mpclientes.modoadmin.Coctels.EliminarCatecoctelccActivity;
import kreandoapp.mpclientes.pojo.Coctelimg;

public class verImgsCoctelActivity extends AppCompatActivity {

    ImageButton botonvolver;
    ImageButton editarcate;
    TextView tv_titulo_toolbar;

    RecyclerView rv_productos;
    AdapterIMGCoctel adapter;
    ArrayList<Coctelimg> coctelimgArrayList;
    ProgressBar progressBar;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_imgs_coctel);
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
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        });



        final String n = getIntent().getExtras().getString("titulo");
        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Coctel:"+ n);

        final String id = getIntent().getExtras().getString("id");

        Toast.makeText(this, "id", Toast.LENGTH_SHORT).show();

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(true);
        rv_productos =  findViewById(R.id.rv);
        rv_productos.setLayoutManager(mLayoutManager);

        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

        // MOSTRAR RECYCLER VIEW

        coctelimgArrayList = new ArrayList<>();
        adapter = new AdapterIMGCoctel(coctelimgArrayList,this);
        rv_productos.setAdapter(adapter);

        FirebaseDatabase database22 = FirebaseDatabase.getInstance();
        DatabaseReference myRef33 = database22.getReference("imgcoctel");

        Query query4 = myRef33.orderByChild("orden");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                    coctelimgArrayList.removeAll(coctelimgArrayList);
                    rv_productos.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {


                        Coctelimg prod = snapshot.getValue(Coctelimg.class);



                        if(prod.getIdcatefoto().equals(id)){
                            coctelimgArrayList.add(prod);

                        }





                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(verImgsCoctelActivity.this, "No hay productos en esta Categoria.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }//fin del oncreate!

    private void mostrardialog(String url,String version) {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(verImgsCoctelActivity.this);
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
            final String foto = getIntent().getExtras().getString("miniatura");
            final String fotourlcambiar = getIntent().getExtras().getString("urlcambiar");


            i.putExtra("id",id);
            i.putExtra("titulo",titulo);
            i.putExtra("foto",foto);
            i.putExtra("fotourlcambiar",fotourlcambiar);
            i.putExtra("orden",orden);
            startActivity(i);
            finish();
        });

        btn_eliminar.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), EliminarCatecoctelccActivity.class);
            final String id = getIntent().getExtras().getString("id");
            final String cate = getIntent().getExtras().getString("titulo");
            final String caturlcambiar = getIntent().getExtras().getString("urlcambiar");
            i.putExtra("id",id);
            i.putExtra("cate",cate);
            i.putExtra("urlimg",caturlcambiar);

            startActivity(i);
            finish();
        });
    }
}