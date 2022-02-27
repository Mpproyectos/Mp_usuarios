package kreandoapp.mpclientes.modoadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kreandoapp.mpclientes.R;

public class encuestasPedidosActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView fechayhora,tv_mensaje,tv_estado;
    TextView tv_nombre;
    Button btn_clickwhatsapp;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuestas_pedidos);

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mensajes encuesta:");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fechayhora = findViewById(R.id.tv_fechayhora);
        tv_mensaje = findViewById(R.id.tv_mensaje);
        tv_estado = findViewById(R.id.tv_estado);
        tv_nombre = findViewById(R.id.tv_nombre);



        final String mensaje = getIntent().getExtras().getString("mensaje");
        final String entregado = getIntent().getExtras().getString("entregado");
        final String fecha = getIntent().getExtras().getString("fecha");
        final String hora = getIntent().getExtras().getString("hora");
        final String nombre = getIntent().getExtras().getString("nombre");
        final String tel = getIntent().getExtras().getString("telefono");
        final String id = getIntent().getExtras().getString("idped");

        DatabaseReference myRef3 = database.getReference("Pedidos").child(id).child("EncuestaSatis");
        myRef3.child("visto").setValue("si");

        fechayhora.setText(fecha+ " " + hora);
        tv_mensaje.setText(mensaje);
        tv_estado.setText(entregado);
        tv_nombre.setText(nombre);

        btn_clickwhatsapp = findViewById(R.id.btn_clickwhatsapp);
        btn_clickwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wa.me/51"+tel;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }//fin del oncreate!
}