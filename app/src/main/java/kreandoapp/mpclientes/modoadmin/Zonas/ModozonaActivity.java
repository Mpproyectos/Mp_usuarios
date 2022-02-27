package kreandoapp.mpclientes.modoadmin.Zonas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.ZonasAdapter;
import kreandoapp.mpclientes.pojo.Zonas;

public class ModozonaActivity extends AppCompatActivity {

    private Toolbar toolbar;

    RecyclerView rv;
    Button btn_nuevazona;
    ArrayList<Zonas> zonaslist;
    ZonasAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modozona);

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ver Zonas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_nuevazona = findViewById(R.id.btn_nuevazona);
        mLayoutManager = new LinearLayoutManager(this);
        btn_nuevazona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NuevaZonaActivity.class);

                v.getContext().startActivity(i);
            }
        });

        rv =  findViewById(R.id.rv);
        rv.setLayoutManager(mLayoutManager);

        zonaslist = new ArrayList<>();
        adapter = new ZonasAdapter(zonaslist,this);
        rv.setAdapter(adapter);

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("modozona").orderByChild("orden");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    zonaslist.removeAll(zonaslist);

                    rv.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        Zonas cup = snapshot.getValue(Zonas.class);
                        zonaslist.add(cup);

                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(ModozonaActivity.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
