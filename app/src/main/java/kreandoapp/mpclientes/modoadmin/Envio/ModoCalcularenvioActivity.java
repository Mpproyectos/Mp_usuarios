package kreandoapp.mpclientes.modoadmin.Envio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bitvale.switcher.SwitcherX;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kreandoapp.mpclientes.R;

public class ModoCalcularenvioActivity extends AppCompatActivity {
    Button btn_abierto,btn_cerrado;
    private Toolbar toolbar;
    SwitcherX switcher_modo_distancia,switcher_modo_zona,switcher_modo_cupon;
    TextView tv_modo_distancia,tv_modo_zona,tv_modo_cupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_calcularenvio);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Estado modo envio y cupones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switcher_modo_distancia = findViewById(R.id.switcher_modo_distancia);
        switcher_modo_zona = findViewById(R.id.switcher_modo_zona);
        switcher_modo_cupon = findViewById(R.id.switcher_modo_cupon);

        tv_modo_distancia = findViewById(R.id.tv_modo_distancia);
        tv_modo_zona = findViewById(R.id.tv_modo_zona);
        tv_modo_cupon = findViewById(R.id.tv_modo_cupon);





        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference("precioenvio").child("estado");
        myRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("activo")){
                        switcher_modo_distancia.setChecked(true,false);
                        tv_modo_distancia.setText("Modo distancia Activo");
                    }else{
                        switcher_modo_distancia.setChecked(false,false);
                        tv_modo_distancia.setText("Modo distancia Desactivado");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference myRef5 = database3.getReference("estadoModozona").child("estado");
        myRef5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("activo")){
                        switcher_modo_zona.setChecked(true,false);
                        tv_modo_zona.setText("Modo zona Activo");
                    }else{
                        switcher_modo_zona.setChecked(false,false);
                        tv_modo_zona.setText("Modo zona Desactivado");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference myRef55 = database3.getReference("estadomodocupon").child("estado");
        myRef55.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("activo")){
                        switcher_modo_cupon.setChecked(true,false);
                        tv_modo_cupon.setText("Modo cupon Activo");
                    }else{
                        switcher_modo_cupon.setChecked(false,false);
                        tv_modo_cupon.setText("Modo cupon Desactivado");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        switcher_modo_zona.setOnCheckedChangeListener(checked ->{
                    if(checked){
                        switcher_modo_zona.setChecked(true,true);
                        switcher_modo_distancia.setChecked(false,true);
                        tv_modo_zona.setText("Modo zona Activado");
                        DatabaseReference myRef6 = database3.getReference("estadoModozona").child("estado");
                        myRef6.setValue("activo");

                        tv_modo_distancia.setText("Modo distancia Desactivado");
                        DatabaseReference myRef7 = database3.getReference("precioenvio").child("estado");
                        myRef7.setValue("desact");

                    }else {
                        tv_modo_zona.setText("Modo zona Desactivado");
                        DatabaseReference myRef6 = database3.getReference("estadoModozona").child("estado");
                        myRef6.setValue("desact");

                        tv_modo_distancia.setText("Modo distancia Activado");
                        DatabaseReference myRef8 = database3.getReference("precioenvio").child("estado");
                        myRef8.setValue("activo");
                    }
                    return null;
                }
        );
        switcher_modo_distancia.setOnCheckedChangeListener(checked ->{
                    if(checked){
                        switcher_modo_zona.setChecked(false,true);
                        switcher_modo_distancia.setChecked(true,true);
                        tv_modo_distancia.setText("Modo distancia Activado");
                        DatabaseReference myRef6 = database3.getReference("precioenvio").child("estado");
                        myRef6.setValue("activo");

                        tv_modo_zona.setText("Modo zona Desactivado");
                        DatabaseReference myRef7 = database3.getReference("estadoModozona").child("estado");
                        myRef7.setValue("desact");
                    }else {
                        tv_modo_distancia.setText("Modo distancia Desactivado");
                        DatabaseReference myRef6 = database3.getReference("precioenvio").child("estado");
                        myRef6.setValue("desact");

                        tv_modo_zona.setText("Modo zona Activado");
                        DatabaseReference myRef7 = database3.getReference("estadoModozona").child("estado");
                        myRef7.setValue("activo");
                    }
                    return null;
                }

        );

        switcher_modo_cupon.setOnCheckedChangeListener(checked ->{
                    if(checked){
                        switcher_modo_cupon.setChecked(true,true);

                        tv_modo_cupon.setText("Modo cupon Activado");
                        DatabaseReference myRef6 = database3.getReference("estadomodocupon").child("estado");
                        myRef6.setValue("activo");



                    }else {
                        tv_modo_cupon.setText("Modo cupon Desactivado");
                        DatabaseReference myRef6 = database3.getReference("estadomodocupon").child("estado");
                        myRef6.setValue("desact");


                    }
                    return null;
                }
        );

    }//fin del oncreate

    private void cambiarAdesactivado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModoCalcularenvioActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_activado_modo_envio, null);

        builder.setView(view);


        final AlertDialog dialog = builder.create();
        dialog.show();

        Button btn_No = view.findViewById(R.id.btn_no);
        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_si = view.findViewById(R.id.btn_si);
        btn_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                DatabaseReference myRef4 = database3.getReference().child("precioenvio").child("estado");
                myRef4.setValue("desac");
                Toast.makeText(ModoCalcularenvioActivity.this, "Se cambio a modo Desactivado", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
    private void cambiarA_activo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModoCalcularenvioActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_desactivado_modo_envio, null);

        builder.setView(view);


        final AlertDialog dialog = builder.create();
        dialog.show();

        Button btn_No = view.findViewById(R.id.btn_no);
        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_si = view.findViewById(R.id.btn_si);
        btn_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                DatabaseReference myRef4 = database3.getReference().child("precioenvio").child("estado");
                myRef4.setValue("activo");
                Toast.makeText(ModoCalcularenvioActivity.this, "Se cambio a modo activo ", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
}
