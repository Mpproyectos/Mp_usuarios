package kreandoapp.mpclientes.clientes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
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

import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.AdapterMisPedidos;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.pojo.Pedidos;

public class misPedidos extends AppCompatActivity {

    ProgressBar progress_bar;



    RecyclerView rv_mispedidos;
    detectorInternet internet;
    ArrayList<Pedidos> requests;
    AdapterMisPedidos adapter;
    private LinearLayoutManager mLayoutManager;

    ImageButton botonvolver;
    TextView tv_titulo_toolbar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
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
        setContentView(R.layout.activity_mis_pedidos);
        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Mis pedidos");
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

        progress_bar = findViewById(R.id.progress_bar);

        internet = new detectorInternet(this);



        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv_mispedidos =  findViewById(R.id.recycler_mispedidos);
        rv_mispedidos.setLayoutManager(mLayoutManager);

        // MOSTRAR RECYCLER VIEW

        requests = new ArrayList<>();
        adapter = new AdapterMisPedidos(requests);
        rv_mispedidos.setAdapter(adapter);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("Pedidos").orderByChild("id").equalTo(user.getUid());
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    progress_bar.setVisibility(View.GONE);
                    rv_mispedidos.setVisibility(View.VISIBLE);

                    requests.removeAll(requests);


                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        Pedidos req = snapshot.getValue(Pedidos.class);
                        requests.add(req);


                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(misPedidos.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();
                    finish();
                    Intent i = new Intent(misPedidos.this, home1.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//fin del oncreate!

    @Override
    protected void onResume() {
        super.onResume();
        if(internet.estaConectado()){

        }else{
            finish();
            Toast.makeText(this, "No existe conexion a internet", Toast.LENGTH_SHORT).show();


        }
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
}
