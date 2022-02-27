package kreandoapp.mpclientes.modoadmin.Productos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
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

public class editarproductoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    EditText et_tituloprod,et_detalleprod,et_precioprod,et_orden;
    ImageView img_image;
    ProgressBar progress_bar;
    Button btn_actualizardatos,btn_actualizarfotos;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarproducto);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar Producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_actualizardatos= findViewById(R.id.btn_actualizardatos);
        btn_actualizarfotos = findViewById(R.id.btn_actualizarfotos);

        progress_bar = findViewById(R.id.progress_bar);
        img_image = findViewById(R.id.img_image);

        String titulo = getIntent().getExtras().getString("tituloprod");
        String detalle = getIntent().getExtras().getString("detalleprod");
        String imagen = getIntent().getExtras().getString("imagenprod");
        final String urlimagen = getIntent().getExtras().getString("imagenenproceso");
        String precio = getIntent().getExtras().getString("precioprod");
        final String key = getIntent().getExtras().getString("keyprod");
        final int orden = getIntent().getExtras().getInt("orden");

        et_tituloprod = findViewById(R.id.et_tituloprod);
        et_tituloprod.setText(titulo);

        et_detalleprod = findViewById(R.id.et_detalleprod);
        et_detalleprod.setText(detalle);

        et_precioprod = findViewById(R.id.et_precioprod);
        et_precioprod.setText(precio);

        et_orden = findViewById(R.id.et_orden);
        et_orden.setText(String.valueOf(orden));

        btn_actualizardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference myRef4 = database.getReference("Productos").child(key).child("prodNombre");
                myRef4.setValue(et_tituloprod.getText().toString());


                final DatabaseReference myRef5 = database.getReference("Productos").child(key).child("prodDescripcion");
                myRef5.setValue(et_detalleprod.getText().toString());


                final DatabaseReference myRef6 = database.getReference("Productos").child(key).child("prodPrecio");
                myRef6.setValue(et_precioprod.getText().toString());


                final DatabaseReference myRef7 = database.getReference("Productos").child(key).child("orden");
                int orden = Integer.parseInt(et_orden.getText().toString());
                myRef7.setValue(orden);


                finish();
                Toast.makeText(editarproductoActivity.this, "Producto editado!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), home1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        btn_actualizarfotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String key = getIntent().getExtras().getString("keyprod");
                Intent i = new Intent(getApplicationContext(), editarimagenActivity.class);
                i.putExtra("key",key);
                i.putExtra("urlenproceso",urlimagen);
                startActivity(i);
                finish();
            }
        });








    }//fin del oncreate!
}
