package kreandoapp.mpclientes.modoadmin.Cupones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
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

public class verDetalleCupActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    TextView tv_estado;
    Button btn_save , btn_desac,btn_activo,btn_eliminar,btn_copiar;
    EditText et_condicion,et_monto,et_porcentaje;
    TextView tv_contador,tv_modo_cancel,tv_modo_cupon;
    CardView card_descuento,card_condicion,card_porcentaje;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_cup);
        tv_estado = findViewById(R.id.tv_estado);
        btn_save = findViewById(R.id.btn_save);
        btn_activo = findViewById(R.id.btn_activo);
        btn_desac = findViewById(R.id.btn_desac);
        et_condicion = findViewById(R.id.et_condicion);
        et_monto = findViewById(R.id.et_monto);
        et_porcentaje = findViewById(R.id.et_porcentaje);
        tv_contador = findViewById(R.id.tv_contador);
        btn_eliminar =findViewById(R.id.btn_eliminar);
        tv_modo_cancel = findViewById(R.id.tv_modo_cancel);
        tv_modo_cupon = findViewById(R.id.tv_modo_cupon);
        card_condicion = findViewById(R.id.card_condicion);
        card_descuento = findViewById(R.id.card_descuento);
        card_porcentaje = findViewById(R.id.card_porcentaje);
        btn_copiar = findViewById(R.id.btn_copiar);



        final String id = getIntent().getExtras().getString("id");

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cupon nº "+ id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_copiar.setOnClickListener(v -> {
            ClipboardManager myClipboard = myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData myClip;
            myClip = ClipData.newPlainText("text", id);
            myClipboard.setPrimaryClip(myClip);
            Toast.makeText(this, "Cupon copiado!", Toast.LENGTH_SHORT).show();
        });

        DatabaseReference myRef4 = database.getReference("Cupones").child(id);
        myRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String estado = dataSnapshot.child("estado").getValue(String.class);
                    int condicion = dataSnapshot.child("condicion").getValue(Integer.class);
                    int monto = dataSnapshot.child("monto").getValue(Integer.class);
                    int count = dataSnapshot.child("count_uso").getValue(Integer.class);
                    int porce = dataSnapshot.child("porcentaje").getValue(Integer.class);
                    String modo = dataSnapshot.child("modo").getValue(String.class);



                    if(modo.equals("sicancel")){
                        tv_modo_cancel.setText("Modo autocancelable");
                    }else {
                        tv_modo_cancel.setText("Modo no cancelable");
                    }
                    if(porce >0){
                        tv_modo_cupon.setText("PORCENTAJE");
                        card_condicion.setVisibility(View.GONE);
                        card_descuento.setVisibility(View.GONE);
                        card_porcentaje.setVisibility(View.VISIBLE);


                    }else {

                        tv_modo_cupon.setText("MONTO");
                        card_condicion.setVisibility(View.VISIBLE);
                        card_descuento.setVisibility(View.VISIBLE);
                        card_porcentaje.setVisibility(View.GONE);

                    }


                    tv_estado.setText(estado);
                    et_condicion.setText(String.valueOf(condicion));
                    et_monto.setText(String.valueOf(monto));
                    tv_contador.setText(String.valueOf(count));
                    et_porcentaje.setText(String.valueOf(porce));

                    if(estado.equals("desactivado")){
                        btn_eliminar.setVisibility(View.VISIBLE);
                    }else {
                        btn_eliminar.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_activo.setOnClickListener(v -> {
            DatabaseReference myRef5 = database.getReference("Cupones").child(id).child("estado");
            myRef5.setValue("activo");
            Toast.makeText(this, "Estado activo!", Toast.LENGTH_SHORT).show();
        });
        btn_desac.setOnClickListener(v -> {
            DatabaseReference myRef6 = database.getReference("Cupones").child(id).child("estado");
            myRef6.setValue("desactivado");
            Toast.makeText(this, "Estado desactivado!", Toast.LENGTH_SHORT).show();
        });

        btn_eliminar.setOnClickListener(v -> {
            DatabaseReference myRef5 = database.getReference("Cupones").child(id);
            myRef5.removeValue();
            Toast.makeText(v.getContext(), "Cupon Eliminado!", Toast.LENGTH_SHORT).show();
            finish();
        });
        btn_save.setOnClickListener(v -> {
            String porceDato = et_porcentaje.getText().toString();
            DatabaseReference porce = database.getReference("Cupones").child(id).child("porcentaje");
            porce.setValue(Integer.parseInt(porceDato));

            String descDato = et_monto.getText().toString();
            DatabaseReference desc = database.getReference("Cupones").child(id).child("monto");
            desc.setValue(Integer.parseInt(descDato));

            String condiDato = et_condicion.getText().toString();
            DatabaseReference condi = database.getReference("Cupones").child(id).child("condicion");
            condi.setValue(Integer.parseInt(condiDato));

            Toast.makeText(this, "Datos actualizados!", Toast.LENGTH_LONG).show();

            finish();
        });

    }//fin del oncreate!
}