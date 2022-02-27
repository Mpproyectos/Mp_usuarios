package kreandoapp.mpclientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kreandoapp.mpclientes.adapter.AdapterContactoRv;
import kreandoapp.mpclientes.adapter.AdapterContactoRv_redes;
import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.pojo.Contacto_pojo;
import kreandoapp.mpclientes.pojo.redes_pojo;

public class ContactosRVActivity extends AppCompatActivity {
    Button shareubicacion,btn_msjwhatsapp;
    RecyclerView rv_contacto_rv;
    AdapterContactoRv adapter;
    ArrayList<Contacto_pojo> contactoArrayList;
    private LinearLayoutManager mLayoutManager;


    ArrayList<redes_pojo> redesPojoArrayList;
    AdapterContactoRv_redes adapter2;
    RecyclerView rv_redes;

    ImageButton botonvolver;
    TextView tv_titulo_toolbar;

    String ubicacion;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), home1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos_r_v);
        dato();

        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Contacto");
        botonvolver = findViewById(R.id.btn_volverAtras);


        FirebaseDatabase database33 = FirebaseDatabase.getInstance();
        DatabaseReference myRef44 = database33.getReference();
        Query query44 = myRef44.child("modoadmin");
        query44.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String telefono = dataSnapshot.child("telefono").getValue(String.class);
               // tv_telefonoadmin.setText(telefono);

                String ubicacion2 = dataSnapshot.child("ubicacion").getValue(String.class);
               ubicacion = ubicacion2;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        shareubicacion = findViewById(R.id.btn_compartirubicacion);
        shareubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomtext);
                shareubicacion.startAnimation(animation);
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String ShareSub = "Esta es mi ubicacion";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, ubicacion);
                startActivity(Intent.createChooser(sharingIntent, "Compartir en:"));
            }
        });



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

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(true);
        rv_contacto_rv =  findViewById(R.id.rv_contacto);
        rv_contacto_rv.setLayoutManager(mLayoutManager);


        contactoArrayList = new ArrayList<>();
        adapter = new AdapterContactoRv(contactoArrayList,this);
        rv_contacto_rv.setAdapter(adapter);

        cargar_contacto_rv();






    }//fin del oncreate!!

    private void dato() {
        rv_redes = findViewById(R.id.rv_redes);
        rv_redes.setLayoutManager(new GridLayoutManager(this, 2));
        redesPojoArrayList = new ArrayList<>();
        adapter2 = new AdapterContactoRv_redes(redesPojoArrayList, this);
        rv_redes.setAdapter(adapter2);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef555 = database.getReference("contactorv_redes");
        Query query45 = myRef555.orderByChild("orden");
        query45.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    redesPojoArrayList.removeAll(redesPojoArrayList);



                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {



                        redes_pojo req = snapshot.getValue(redes_pojo.class);
                        redesPojoArrayList.add(req);


                    }

                    adapter.notifyDataSetChanged();
                } else {
                    // Toast.makeText(home1.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void cargar_contacto_rv() {
        FirebaseDatabase database22 = FirebaseDatabase.getInstance();
        DatabaseReference myRef33 = database22.getReference("contactorv");

        myRef33.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                    contactoArrayList.removeAll(contactoArrayList);


                    for (DataSnapshot snapshot :  dataSnapshot.getChildren()) {


                        Contacto_pojo prod = snapshot.getValue(Contacto_pojo.class);



                        contactoArrayList.add(prod);




                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(ContactosRVActivity.this, "No hay productos en esta Categoria.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}