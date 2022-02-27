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

public class EditarDatosCoctelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    EditText et_orden;
    ImageView img_image;
    ProgressBar progress_bar;
    Button btn_actualizardatos,btn_actualizarfotos,btn_eliminarimagenCoctelimg;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos_coctel);

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar datos Coctel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_actualizardatos= findViewById(R.id.btn_actualizardatos);
        btn_actualizarfotos = findViewById(R.id.btn_actualizarfotos);
        btn_eliminarimagenCoctelimg = findViewById(R.id.btn_eliminarimagenCoctelimg);

        progress_bar = findViewById(R.id.progress_bar);
        img_image = findViewById(R.id.img_image);



        String imagen = getIntent().getExtras().getString("imagen");
        final String urlimagen = getIntent().getExtras().getString("urlcambiar");

        final String idimg = getIntent().getExtras().getString("idimg");
        final int orden = getIntent().getExtras().getInt("orden");
        Toast.makeText(this, urlimagen, Toast.LENGTH_SHORT).show();


        et_orden = findViewById(R.id.et_orden);
        et_orden.setText(String.valueOf(orden));

        btn_actualizardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                final DatabaseReference myRef7 = database.getReference("imgcoctel").child(idimg).child("orden");
                int orden = Integer.parseInt(et_orden.getText().toString());
                myRef7.setValue(orden);


                finish();
                Toast.makeText(EditarDatosCoctelActivity.this, "Datos Coctel editado!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), home1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        btn_actualizarfotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idimg = getIntent().getExtras().getString("idimg");


                Intent i = new Intent(getApplicationContext(),EditarimgcoctelActivity.class);
                i.putExtra("key",idimg);
                i.putExtra("urlenproceso",urlimagen);
                startActivity(i);
                finish();
            }
        });

        btn_eliminarimagenCoctelimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String urlimagen = getIntent().getExtras().getString("urlcambiar");
                String idid = getIntent().getExtras().getString("idimg");
                String url = getIntent().getExtras().getString("id");

                Intent i = new Intent(getApplicationContext(),eliminarcoctelimgActivity.class);
                i.putExtra("id",idid);
                i.putExtra("urlimg",urlimagen);

                startActivity(i);
                finish();
            }
        });








    }//fin del oncreate!
}
