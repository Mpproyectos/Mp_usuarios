package kreandoapp.mpclientes.modoadmin.Demoras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class EditarDemoraActivity extends AppCompatActivity {
    private Toolbar toolbar;

    EditText et_demora,et_orden;
    Button btn_guardar,btn_eliminar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_demora);

        btn_eliminar = findViewById(R.id.btn_eliminar_demora);
        String id = getIntent().getExtras().getString("id");

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar demora: " + id );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogo();
            }
        });

        et_demora = findViewById(R.id.et_demora);
        et_orden = findViewById(R.id.et_orden);
        btn_guardar = findViewById(R.id.btn_guardar);
        DatabaseReference myRef4 = database.getReference("Demoras").child(id);
        myRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String demora = dataSnapshot.child("demora").getValue(String.class);
                    int orden = dataSnapshot.child("orden").getValue(Integer.class);

                    et_demora.setText(demora);
                    et_orden.setText(String.valueOf(orden));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String demoraTxt = et_demora.getText().toString();
                String ordenTxt = et_orden.getText().toString();

                if(TextUtils.isEmpty(ordenTxt) && TextUtils.isEmpty(demoraTxt)){
                    Toast.makeText(EditarDemoraActivity.this, "Complete los datos..", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference demora = database.getReference("Demoras").child(id).child("demora");
                    demora.setValue(demoraTxt);

                    DatabaseReference porce = database.getReference("Demoras").child(id).child("orden");
                    porce.setValue(Integer.parseInt(ordenTxt));

                    Toast.makeText(EditarDemoraActivity.this, "Datos actualizados!", Toast.LENGTH_SHORT).show();

                    finish();
                }


            }
        });




    }//fin del oncreate!
    private void mostrarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarDemoraActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_eliminar_demora, null);

        builder.setView(view);

        String demora = getIntent().getExtras().getString("demora");
        String id = getIntent().getExtras().getString("id");

        TextView tv_zona = view.findViewById(R.id.tv_zona);
        tv_zona.setText(demora);

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
                DatabaseReference myRef4 = database3.getReference("Demoras").child(id);
                myRef4.removeValue();
                Toast.makeText(EditarDemoraActivity.this, "Demora eliminada con exito!", Toast.LENGTH_SHORT).show();
                finish();

                dialog.dismiss();
            }



        });
    }
}