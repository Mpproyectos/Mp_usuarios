package kreandoapp.mpclientes.modoadmin.Demoras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.pojo.Demoras;

public class CrearDemoraActivity extends AppCompatActivity {
    private Toolbar toolbar;


    EditText et_demora,et_orden;
    Button btn_guardar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_demora);

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear nueva Demora");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_demora = findViewById(R.id.et_demora);
        et_orden = findViewById(R.id.et_orden);
        btn_guardar = findViewById(R.id.btn_guardar);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_orden.getText().toString()) && TextUtils.isEmpty(et_demora.getText().toString())){
                    Toast.makeText(CrearDemoraActivity.this, "Complete todos los datos...", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference ref = database.getReference("Demoras");
                    String push = ref.push().getKey();
                    Demoras dem = new Demoras(push,Integer.parseInt(et_orden.getText().toString()),et_demora.getText().toString());
                    ref.child(push).setValue(dem);
                    Toast.makeText(CrearDemoraActivity.this, "Nueva demora Creada", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });




    }//fin del oncreate!
}