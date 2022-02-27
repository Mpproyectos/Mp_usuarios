package kreandoapp.mpclientes.modoadmin.EditarCategoria;

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

public class EditarsubCategoriaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    EditText et_nombre,et_orden;
    ImageView img_image;
    ProgressBar progress_bar;
    Button btn_actualizardatos,btn_actualizarfotos;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarsub_categoria);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar subcategoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_actualizardatos= findViewById(R.id.btn_actualizardatos);
        btn_actualizarfotos = findViewById(R.id.btn_actualizarfotos);

        progress_bar = findViewById(R.id.progress_bar);
        img_image = findViewById(R.id.img_image);

        String nombre = getIntent().getExtras().getString("cate");


        final String urlimagen = getIntent().getExtras().getString("cateurlImage");

        final String key = getIntent().getExtras().getString("id");
        final String orden = getIntent().getExtras().getString("orden");

        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();

        et_nombre = findViewById(R.id.et_nombre);
        et_nombre.setText(nombre);



        et_orden = findViewById(R.id.et_orden);
        et_orden.setText(orden);

        btn_actualizardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final DatabaseReference myRef6 = database.getReference("SubCategoria").child(key).child("subnombreSub");
                myRef6.setValue(et_nombre.getText().toString());


                final DatabaseReference myRef7 = database.getReference("SubCategoria").child(key).child("orden");

                myRef7.setValue(Integer.parseInt(et_orden.getText().toString()));


                finish();
                Toast.makeText(EditarsubCategoriaActivity.this, "SubCategoria editada!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), home1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        btn_actualizarfotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String key = getIntent().getExtras().getString("id");
                Intent i = new Intent(getApplicationContext(), EditarsubCategoriaimgActivity.class);
                i.putExtra("key",key);
                i.putExtra("urlenproceso",urlimagen);
                startActivity(i);
                finish();
            }
        });








    }//fin del oncreate!
}
