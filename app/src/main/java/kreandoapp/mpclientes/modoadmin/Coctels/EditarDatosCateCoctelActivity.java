package kreandoapp.mpclientes.modoadmin.Coctels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.home1;

public class EditarDatosCateCoctelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    EditText et_titulo,et_orden;
    ImageView img_image;
    ProgressBar progress_bar;
    Button btn_actualizardatos,btn_actualizarfotos;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos_cate_coctel);

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar datos Coctel cate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_actualizardatos= findViewById(R.id.btn_actualizardatos);
        btn_actualizarfotos = findViewById(R.id.btn_actualizarfotos);

        progress_bar = findViewById(R.id.progress_bar);
        img_image = findViewById(R.id.img_image);




        final String titulo = getIntent().getExtras().getString("titulo");

        String fotourlcambiar = getIntent().getExtras().getString("fotourlcambiar");
        final String id = getIntent().getExtras().getString("id");
        final int orden = getIntent().getExtras().getInt("orden");



        et_orden = findViewById(R.id.et_orden);
        et_orden.setText(String.valueOf(orden));

        et_titulo = findViewById(R.id.et_titulo);
        et_titulo.setText(titulo);

        btn_actualizardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final DatabaseReference myRef7 = database.getReference("Coctel").child(id).child("orden");
                int orden = Integer.parseInt(et_orden.getText().toString());
                myRef7.setValue(orden);

                final DatabaseReference myRef8 = database.getReference("Coctel").child(id).child("titulo");
                String titulo = et_titulo.getText().toString();
                myRef8.setValue(titulo);


                finish();
                Toast.makeText(EditarDatosCateCoctelActivity.this, "Datos Coctel cate editado!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), home1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        btn_actualizarfotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idimg = getIntent().getExtras().getString("idimg");


                Intent i = new Intent(getApplicationContext(),EditarimgCoctelcateActivity.class);
                i.putExtra("key",id);
                i.putExtra("urlenproceso",fotourlcambiar);
                startActivity(i);
                finish();
            }
        });








    }//fin del oncreate!
}
