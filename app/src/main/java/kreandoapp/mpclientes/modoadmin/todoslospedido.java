package kreandoapp.mpclientes.modoadmin;

import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.RequestAdapter;
import kreandoapp.mpclientes.pojo.Pedidos;

public class todoslospedido extends AppCompatActivity {
    RecyclerView rv;

    ArrayList<Pedidos> requestlist;
    RequestAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpedido);
        // MOSTRAR RECYCLER VIEW
        Toolbar toolbar;
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Todos los pedidos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress_bar = findViewById(R.id.progress_bar);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv =  findViewById(R.id.recycler_req);
        rv.setLayoutManager(mLayoutManager);

        requestlist = new ArrayList<>();
        adapter = new RequestAdapter(requestlist);
        rv.setAdapter(adapter);

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("Pedidos");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    requestlist.removeAll(requestlist);
                    progress_bar.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        Pedidos req = snapshot.getValue(Pedidos.class);
                        requestlist.add(req);

                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(todoslospedido.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
