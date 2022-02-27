package kreandoapp.mpclientes.modoadmin.Envio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.pojo.PrecioEnvio;

public class EditarCalcularEnvioActivity extends AppCompatActivity {
    EditText et_prec1,et_dis1;
    EditText et_prec2,et_dis2;
    EditText et_prec3,et_dis3;
    EditText et_prec4,et_dis4;
    EditText et_prec5,et_dis5;
    EditText et_prec6,et_dis6;
    Button btn_actualizartodo;

    String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_calcular_envio);
        setTitle("Editar valores calcular");
        btn_actualizartodo = findViewById(R.id.btn_actualizartodo);
        et_dis1 = findViewById(R.id.dis1);et_prec1 = findViewById(R.id.prec1);
        et_dis2 = findViewById(R.id.dis2);et_prec2 = findViewById(R.id.prec2);
        et_dis3 = findViewById(R.id.dis3);et_prec3 = findViewById(R.id.prec3);
        et_dis4 = findViewById(R.id.dis4);et_prec4 = findViewById(R.id.prec4);
        et_dis5 = findViewById(R.id.dis5);et_prec5 = findViewById(R.id.prec5);
        et_dis6 = findViewById(R.id.dis6);et_prec6 = findViewById(R.id.prec6);

        btn_actualizartodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                DatabaseReference myRef4 = database3.getReference("precioenvio");

                myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String prec1 = et_prec1.getText().toString();
                            String prec2 = et_prec2.getText().toString();
                            String prec3 = et_prec3.getText().toString();
                            String prec4 = et_prec4.getText().toString();
                            String prec5 = et_prec5.getText().toString();
                            String prec6 = et_prec6.getText().toString();
                            String dis1 = et_dis1.getText().toString();
                            String dis2 = et_dis2.getText().toString();
                            String dis3 = et_dis3.getText().toString();
                            String dis4 = et_dis4.getText().toString();
                            String dis5 = et_dis5.getText().toString();
                            String dis6 = et_dis6.getText().toString();




                            PrecioEnvio prec = new PrecioEnvio(dis1,prec1,dis2,prec2,dis3,prec3,dis4,prec4,dis5,prec5,dis6,prec6,estado);

                            myRef4.setValue(prec);

                            Toast.makeText(EditarCalcularEnvioActivity.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                            finish();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("precioenvio");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    estado = dataSnapshot.child("estado").getValue(String.class);



                String dis1 = dataSnapshot.child("distancia1").getValue(String.class);
                String prec1 = dataSnapshot.child("precio1").getValue(String.class);
                et_dis1.setText(dis1);et_prec1.setText(prec1);

                    String dis2 = dataSnapshot.child("distancia2").getValue(String.class);
                    String prec2 = dataSnapshot.child("precio2").getValue(String.class);
                    et_dis2.setText(dis2);et_prec2.setText(prec2);

                    String dis3 = dataSnapshot.child("distancia3").getValue(String.class);
                    String prec3 = dataSnapshot.child("precio3").getValue(String.class);
                    et_dis3.setText(dis3);et_prec3.setText(prec3);

                    String dis4 = dataSnapshot.child("distancia4").getValue(String.class);
                    String prec4 = dataSnapshot.child("precio4").getValue(String.class);
                    et_dis4.setText(dis4);et_prec4.setText(prec4);

                    String dis5 = dataSnapshot.child("distancia5").getValue(String.class);
                    String prec5 = dataSnapshot.child("precio5").getValue(String.class);
                    et_dis5.setText(dis5);et_prec5.setText(prec5);


                    String dis6 = dataSnapshot.child("distancia6").getValue(String.class);
                    String prec6 = dataSnapshot.child("precio6").getValue(String.class);
                    et_dis6.setText(dis6);et_prec6.setText(prec6);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}