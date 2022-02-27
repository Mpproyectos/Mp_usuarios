package kreandoapp.mpclientes.modoadmin.Demoras;

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
import kreandoapp.mpclientes.adapter.DemorasAdapter;
import kreandoapp.mpclientes.pojo.Demoras;

public class EditarDemorasActivity extends AppCompatActivity {
    private Toolbar toolbar;

    RecyclerView rv;
    Button btn_nuevademora;
    ArrayList<Demoras> demoraslist;
    DemorasAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_demoras);

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ver demoras");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_nuevademora = findViewById(R.id.btn_nuevademora);
        mLayoutManager = new LinearLayoutManager(this);
        btn_nuevademora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CrearDemoraActivity.class);
                v.getContext().startActivity(i);
            }
        });

        rv =  findViewById(R.id.rv);
        rv.setLayoutManager(mLayoutManager);

        demoraslist = new ArrayList<>();
        adapter = new DemorasAdapter(demoraslist,this);
        rv.setAdapter(adapter);

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("Demoras").orderByChild("orden");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    demoraslist.removeAll(demoraslist);

                    rv.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        Demoras cup = snapshot.getValue(Demoras.class);
                        demoraslist.add(cup);

                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(EditarDemorasActivity.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
