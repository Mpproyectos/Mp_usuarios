package kreandoapp.mpclientes.clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.pojo.ModeloCaja;
import kreandoapp.mpclientes.pojo.ModeloNodo;

public class DetalleNodoActivity extends AppCompatActivity {

    ImageButton botonvolver,btn_editar;
    TextView tv_titulo_toolbar;
    Integer count_cajas = 0;
    Integer puntos_cajas = 0;
    Integer cto_cajas = 0;
    Integer md_cajas = 0;
    TextView tv_cajas,tv_puntos,tv_cto,tv_md;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_nodo);

        final String nombre_nodo = getIntent().getExtras().getString("nombre_nodo");


        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Detalle nodo: "+nombre_nodo);

        tv_cajas = findViewById(R.id.tv_cajas);
        tv_puntos = findViewById(R.id.tv_puntos);
        tv_cto = findViewById(R.id.tv_cto);
        tv_md = findViewById(R.id.tv_md);

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

        consultarDatos();


    }//fin del oncreate!!

    private void consultarDatos() {
        final String id_nodo = getIntent().getExtras().getString("id_nodo");
        DatabaseReference myRef552 = database.getReference();
        Query query = myRef552.child("Cajas").orderByChild("id_nodo").equalTo(id_nodo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot :  dataSnapshot.getChildren()) {

                    ModeloCaja c = snapshot.getValue(ModeloCaja.class);

                    count_cajas++;
                    puntos_cajas += c.getPuntos();

                    if(c.getTipo_trabajo().equals("CTO")){
                        cto_cajas ++;
                    }else{
                        md_cajas++;
                    }

                }

                tv_cajas.setText(count_cajas.toString());
                tv_puntos.setText(puntos_cajas.toString());
                tv_cto.setText(cto_cajas.toString());
                tv_md.setText(md_cajas.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}