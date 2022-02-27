package kreandoapp.mpclientes.modoadmin.Cupones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bitvale.switcher.SwitcherX;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.pojo.Cupones;

public class crear_cupon_admin extends AppCompatActivity {
    TextView tv_cupon_generado;
    Button btn_generate,btn_crear_cuppon;
    EditText et_monto,et_condicion,et_porcentaje;
    String numAleatorio;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Toolbar toolbar;
    SwitcherX modo_cupon,switcher_modo_porc;
    TextView tv_modo,tv_modo_pagooporc;
    String modo_cupon_dato ="sicancel";

    CardView card_condicion,card_monto,card_porcentaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cupon_admin);

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear nuevo cupon");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        modo_cupon = findViewById(R.id.switcher_modo_cupon);
        switcher_modo_porc = findViewById(R.id.switcher_modo_porc);
        tv_modo = findViewById(R.id.tv_modo);
        tv_modo_pagooporc = findViewById(R.id.tv_modo_pagooporc);

        card_condicion = findViewById(R.id.card_condicion);
        card_monto = findViewById(R.id.card_monto_desc);
        card_porcentaje = findViewById(R.id.card_porcentaje);

        switcher_modo_porc.setOnCheckedChangeListener(checked ->{
                    if(checked){

                        tv_modo_pagooporc.setText("Por monto");
                        card_condicion.setVisibility(View.VISIBLE);
                        card_monto.setVisibility(View.VISIBLE);
                        card_porcentaje.setVisibility(View.GONE);
                        et_porcentaje.setText("");
                    }else {

                        card_porcentaje.setVisibility(View.VISIBLE);
                        card_condicion.setVisibility(View.GONE);
                        card_monto.setVisibility(View.GONE);
                        tv_modo_pagooporc.setText("Por Porcentaje");

                    }
                    return null;
                }
        );
        modo_cupon.setOnCheckedChangeListener(checked ->{
               if(checked){
                   tv_modo.setText("Cupon Autocancelable");
                   modo_cupon_dato ="sicancel";
               }else {
                   tv_modo.setText("Cupon No cancelable");
                   modo_cupon_dato ="nocancel";
               }
                   return null;
            }
        );



        btn_crear_cuppon = findViewById(R.id.btn_crear_cuppon);
        tv_cupon_generado = findViewById(R.id.tv_cupon_generado);
        btn_generate = findViewById(R.id.btn_generate);
        btn_generate.setOnClickListener(v -> {
            tv_cupon_generado.setText(damecuponaleatorio());
        });
        et_monto = findViewById(R.id.et_monto);
        et_condicion = findViewById(R.id.et_condicion);
        et_porcentaje=findViewById(R.id.et_porcentaje);
        btn_crear_cuppon.setOnClickListener(v -> {
            String porce = et_porcentaje.getText().toString();
            if(TextUtils.isEmpty(porce)){
                modoMonto();
            }else {
                modoPorce();
            }



        });
        numAleatorio = damecuponaleatorio();
        tv_cupon_generado.setText(numAleatorio);

    }//fin del oncreate!!

    private void modoMonto() {
        DatabaseReference myRefcup4 = database.getReference("Cupones").child(numAleatorio);
        myRefcup4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(crear_cupon_admin.this, "Ya existe este cupon, presiona New-Cupon para generar uno nuevo.", Toast.LENGTH_LONG).show();
                }else {
                    String montoString = et_monto.getText().toString();
                    String condicionString = et_condicion.getText().toString();

                    if(TextUtils.isEmpty(montoString) && TextUtils.isEmpty(condicionString)){
                        Toast.makeText(crear_cupon_admin.this, "Completa monto y condicion!", Toast.LENGTH_SHORT).show();
                    }else {
                        DatabaseReference myRef4 = database.getReference("ordencup");
                        myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int valor = dataSnapshot.getValue(Integer.class);
                                myRef4.setValue(valor+1);
                                final Calendar c = Calendar.getInstance();
                                final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                                int monto = Integer.parseInt(et_monto.getText().toString());
                                int condi = Integer.parseInt(et_condicion.getText().toString());

                                Cupones cup = new Cupones(
                                        0,
                                        condi,
                                        "activo",
                                        numAleatorio,
                                        monto,
                                        "no",
                                        modo_cupon_dato,
                                        0,
                                        valor,
                                        dateFormat.format(c.getTime()),
                                        timeFormat.format(c.getTime()));
                                myRefcup4.setValue(cup);
                                finish();
                                Toast.makeText(crear_cupon_admin.this, "Nuevo cupon generado!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void modoPorce() {
        DatabaseReference myRefcup4 = database.getReference("Cupones").child(numAleatorio);
        myRefcup4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(crear_cupon_admin.this, "Ya existe este cupon, presiona New-Cupon para generar uno nuevo.", Toast.LENGTH_LONG).show();
                }else {


                        DatabaseReference myRef4 = database.getReference("ordencup");
                        myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int valor = dataSnapshot.getValue(Integer.class);
                                myRef4.setValue(valor+1);
                                final Calendar c = Calendar.getInstance();
                                final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


                                int porce = Integer.parseInt(et_porcentaje.getText().toString());
                                Cupones cup = new Cupones(
                                        porce ,
                                        0,
                                        "activo",
                                        numAleatorio,
                                        0,
                                        "no",
                                        modo_cupon_dato,
                                        0,
                                        valor,
                                        dateFormat.format(c.getTime()),
                                        timeFormat.format(c.getTime()));
                                myRefcup4.setValue(cup);
                                finish();
                                Toast.makeText(crear_cupon_admin.this, "Nuevo cupon generado!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private String damecuponaleatorio() {
        int a = (int) (Math.random() * 22 + 1);
        int b = (int) (Math.random() * 20 + 2);
        int c = (int) (Math.random() * 15 + 3);
        int d = (int) (Math.random() * 10 + 3);
        int numero1 = (int) (Math.random() * 10 + 1);
        int numero2 = (int) (Math.random() * 8 + 2);
        int numero3 = (int) (Math.random() * 4 + 2);
        int numero4 = (int) (Math.random() * 1 + 2);
        String[] elementos = {"A", "B", "C", "D", "E", "F", "G", "H", "S", "K"
                , "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
                ,"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};




        final String aleatorio =
                elementos[a]+
                        elementos[b]+
                        elementos[c]+
                        elementos[d] +
                        numero1+numero2+numero3+numero4;
        return aleatorio;
    }
}