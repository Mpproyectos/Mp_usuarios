package kreandoapp.mpclientes.modoadmin.Zonas;

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
import kreandoapp.mpclientes.pojo.Zonas;

public class NuevaZonaActivity extends AppCompatActivity {

    private Toolbar toolbar;

    EditText et_nombre,et_orden,et_valor;
    Button btn_guardar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_zona);


        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear nueva Zona");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_nombre = findViewById(R.id.et_nombre);
        et_orden = findViewById(R.id.et_orden);
        et_valor = findViewById(R.id.et_valor);

        btn_guardar = findViewById(R.id.btn_guardar);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_orden.getText().toString()) && TextUtils.isEmpty(et_nombre.getText().toString()) && TextUtils.isEmpty(et_valor.getText().toString())){
                    Toast.makeText(NuevaZonaActivity.this, "Complete todos los datos...", Toast.LENGTH_SHORT).show();
                }else {
                    String ordenTxt = et_orden.getText().toString();
                    int valor =  Integer.parseInt(ordenTxt);
                    if(valor == 0){
                        Toast.makeText(NuevaZonaActivity.this, "Coloca otro numero diferente a 0 (a zero)", Toast.LENGTH_LONG).show();

                    }else {
                        DatabaseReference ref = database.getReference("modozona");
                        String push = ref.push().getKey();
                        Zonas dem = new Zonas(push,Integer.parseInt(et_orden.getText().toString()),Integer.parseInt(et_valor.getText().toString()),et_nombre.getText().toString());
                        ref.child(push).setValue(dem);
                        Toast.makeText(NuevaZonaActivity.this, "Nueva Zona Creada", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                }

            }
        });




    }//fin del oncreate!
}