package kreandoapp.mpclientes.modoadmin.Zonas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

public class EditarZonasActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView tv_zona;
    EditText et_orden,et_nombre,et_valor;
    Button btn_guardar,btn_eliminar_zona;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    CardView card_valor,card_orden;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_zonas);
        btn_eliminar_zona = findViewById(R.id.btn_eliminar_zona);
        String id = getIntent().getExtras().getString("id");
        String nombre = getIntent().getExtras().getString("nombre");

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar demora: " + id );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        card_orden = findViewById(R.id.card_orden);
        card_valor = findViewById(R.id.card_valor);
        if(id.equals("id21") || id.equals("id27")){
            card_valor.setVisibility(View.GONE);
            card_orden.setVisibility(View.GONE);
            btn_eliminar_zona.setVisibility(View.GONE);
        }else {
            card_valor.setVisibility(View.VISIBLE);
            card_orden.setVisibility(View.VISIBLE);
            btn_eliminar_zona.setVisibility(View.VISIBLE);

        }
        tv_zona = findViewById(R.id.tv_zona);

        et_nombre = findViewById(R.id.et_nombre);
        et_valor = findViewById(R.id.et_valor);
        et_orden = findViewById(R.id.et_orden);

        btn_guardar = findViewById(R.id.btn_guardar);
        DatabaseReference myRef4 = database.getReference("modozona").child(id);
        myRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    int orden = dataSnapshot.child("orden").getValue(Integer.class);
                    int valor = dataSnapshot.child("valor").getValue(Integer.class);

                    et_nombre.setText(nombre);
                    et_orden.setText(String.valueOf(orden));
                    et_valor.setText(String.valueOf(valor));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_eliminar_zona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogo();
            }
        });



        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreTxt = et_nombre.getText().toString();
                String ordenTxt = et_orden.getText().toString();
                String valorTxt = et_valor.getText().toString();

                if(TextUtils.isEmpty(ordenTxt) && TextUtils.isEmpty(nombreTxt) && TextUtils.isEmpty(valorTxt)){
                    Toast.makeText(EditarZonasActivity.this, "Complete los datos..", Toast.LENGTH_SHORT).show();
                }else {
                    int valor =  Integer.parseInt(ordenTxt);
                    if(valor == 0){
                        Toast.makeText(EditarZonasActivity.this, "Coloca otro numero diferente a 0 (a zero)", Toast.LENGTH_LONG).show();

                    }else {
                        String id = getIntent().getExtras().getString("id");

                        DatabaseReference nombre = database.getReference("modozona").child(id).child("nombre");
                        nombre.setValue(nombreTxt);

                        DatabaseReference orden = database.getReference("modozona").child(id).child("orden");
                        orden.setValue(Integer.parseInt(ordenTxt));

                        DatabaseReference valor1 = database.getReference("modozona").child(id).child("valor");
                        valor1.setValue(Integer.parseInt(valorTxt));

                        Toast.makeText(EditarZonasActivity.this, "Datos actualizados!", Toast.LENGTH_SHORT).show();

                        finish();
                    }




                }


            }
        });




    }//fin del oncreate!

    private void mostrarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarZonasActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_eliminar_zona, null);

        builder.setView(view);

        String nombre = getIntent().getExtras().getString("nombre");
        String id = getIntent().getExtras().getString("id");

        TextView tv_zona = view.findViewById(R.id.tv_zona);
        tv_zona.setText(nombre);

        final AlertDialog dialog = builder.create();
        dialog.show();


        Button btnCancel = view.findViewById(R.id.btn_cancelar);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnconfirmar = view.findViewById(R.id.btn_notificar);
        btnconfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef4 = database3.getReference("modozona").child(id);
                    myRef4.removeValue();
                    Toast.makeText(EditarZonasActivity.this, "Zona eliminada con exito!", Toast.LENGTH_SHORT).show();
                    finish();

                    dialog.dismiss();
                }



        });
    }
}