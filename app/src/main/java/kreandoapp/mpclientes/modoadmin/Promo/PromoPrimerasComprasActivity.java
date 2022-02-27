package kreandoapp.mpclientes.modoadmin.Promo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.pojo.Promo1;

public class PromoPrimerasComprasActivity extends AppCompatActivity {
    EditText et_condicion,et_descuentos,et_maxdesc,et_porcentaje;
    TextView tv_estado;
    Button btn_activo,btn_desac,btn_actualizar_datos;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Toolbar toolbar;

    int porcentaje;
    int condicion;
    int descuento;
    int maxdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_primeras_compras);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Promo primeras compras");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_condicion = findViewById(R.id.et_condicion);
        et_descuentos = findViewById(R.id.et_descuentos);
        et_porcentaje = findViewById(R.id.et_porcentajee);
        et_maxdesc = findViewById(R.id.et_maxdesc);
        tv_estado = findViewById(R.id.tv_estado);
        btn_activo = findViewById(R.id.btn_activo);
        btn_desac = findViewById(R.id.btn_desact);

        btn_actualizar_datos = findViewById(R.id.btn_actualizar_datos);
        btn_actualizar_datos.setOnClickListener(v -> {

            String cond = et_condicion.getText().toString();
            String desc = et_descuentos.getText().toString();
            String max = et_maxdesc.getText().toString();
            String estado = tv_estado.getText().toString();
            String porce = et_porcentaje.getText().toString();

            if(porce.equals("")){
                porcentaje = 0;
            }else {
                porcentaje = Integer.parseInt(porce);
            }

            if(cond.equals("")){
                condicion = 0;
            }else {
                condicion = Integer.parseInt(cond);
            }

            if(desc.equals("")){
                descuento = 0;
            }else {
                descuento = Integer.parseInt(desc);
            }

            if(max.equals("")){
                maxdesc = 0;
            }else {
                maxdesc = Integer.parseInt(max);
            }
            DatabaseReference myRef5 = database.getReference("promo1");
            Promo1 prom = new Promo1(
                    condicion,
                    descuento,
                    maxdesc,
                    estado,
                    porcentaje);

            myRef5.setValue(prom);


            Toast.makeText(this, "Datos actualizados!", Toast.LENGTH_LONG).show();

            finish();

        });

        btn_activo.setOnClickListener(v -> {
            DatabaseReference myRef4 = database.getReference("promo1").child("estado");
            myRef4.setValue("activo");
            Toast.makeText(this, "Estado activo!", Toast.LENGTH_SHORT).show();
        });

        btn_desac.setOnClickListener(v -> {
            DatabaseReference myRef4 = database.getReference("promo1").child("estado");
            myRef4.setValue("desactivado");
            Toast.makeText(this, "Estado desactivado!", Toast.LENGTH_SHORT).show();
        });
        DatabaseReference myRef4 = database.getReference("promo1");
        myRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){


                int condicion = dataSnapshot.child("condicion").getValue(Integer.class);
                int descuento = dataSnapshot.child("descuento").getValue(Integer.class);
                int maxdesc = dataSnapshot.child("maxdesc").getValue(Integer.class);
                    String estado = dataSnapshot.child("estado").getValue(String.class);
                    int porce = dataSnapshot.child("porcentaje").getValue(Integer.class);

                et_condicion.setText(String.valueOf(condicion));
                et_descuentos.setText(String.valueOf(descuento));
                et_maxdesc.setText(String.valueOf(maxdesc));
                et_porcentaje.setText(String.valueOf(porce));
                tv_estado.setText(estado);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }//fin del oncreate!
}