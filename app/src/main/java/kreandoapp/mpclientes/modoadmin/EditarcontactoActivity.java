package kreandoapp.mpclientes.modoadmin;

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

public class EditarcontactoActivity extends AppCompatActivity {

    EditText et_titulo,et_direccion,et_telefono;
    Button btn_actualizardatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarcontacto);
        et_titulo = findViewById(R.id.et_titulo);
        et_direccion = findViewById(R.id.et_direccion);
        btn_actualizardatos = findViewById(R.id.btn_actualizardatos);
        et_telefono = findViewById(R.id.et_telefono);

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("contacto");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String titulo = dataSnapshot.child("titulo").getValue(String.class);
                    String direccion = dataSnapshot.child("direccion").getValue(String.class);
                    et_titulo.setText(titulo);
                    et_direccion.setText(direccion);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase database33 = FirebaseDatabase.getInstance();
        DatabaseReference myRef43 = database33.getReference();
        Query query43 = myRef43.child("modoadmin").child("telefono");
        query43.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    et_telefono.setText(val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_actualizardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                final DatabaseReference myRef4 = database3.getReference("contacto").child("titulo");
                myRef4.setValue(et_titulo.getText().toString());

                FirebaseDatabase database33 = FirebaseDatabase.getInstance();
                final DatabaseReference myRef44 = database33.getReference("contacto").child("direccion");
                myRef44.setValue(et_direccion.getText().toString());

                FirebaseDatabase database34 = FirebaseDatabase.getInstance();
                final DatabaseReference myRef45 = database33.getReference("modoadmin").child("telefono");
                myRef45.setValue(et_telefono.getText().toString());

                finish();
                Toast.makeText(EditarcontactoActivity.this, "Datos Actualizados!", Toast.LENGTH_SHORT).show();

            }
        });




    }
}
