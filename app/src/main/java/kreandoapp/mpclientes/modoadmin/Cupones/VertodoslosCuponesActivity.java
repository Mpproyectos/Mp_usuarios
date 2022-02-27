package kreandoapp.mpclientes.modoadmin.Cupones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.VerCuponesAdapter;
import kreandoapp.mpclientes.pojo.Cupones;

public class VertodoslosCuponesActivity extends AppCompatActivity {
    private Toolbar toolbar;

    RecyclerView rv;

    ArrayList<Cupones> cuponeslist;
    VerCuponesAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ver_all_cupones);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ver todos los cupones.");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv =  findViewById(R.id.rv);
        rv.setLayoutManager(mLayoutManager);

        cuponeslist = new ArrayList<>();
        adapter = new VerCuponesAdapter(cuponeslist,this);
        rv.setAdapter(adapter);

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("Cupones").orderByChild("orden");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    cuponeslist.removeAll(cuponeslist);

                    rv.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        Cupones cup = snapshot.getValue(Cupones.class);
                        cuponeslist.add(cup);

                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(VertodoslosCuponesActivity.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
