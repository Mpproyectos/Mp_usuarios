package kreandoapp.mpclientes.clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.enviarmail.EnviarMail;
import kreandoapp.mpclientes.pojo.EncuestaSatis;
import kreandoapp.mpclientes.volley.claseSendVolleyFCM;

public class satisActivity extends AppCompatActivity {
    private Toolbar toolbar;

    EditText et_mensaje;
    TextView tv_titulo;
    Button btn_enviar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RadioGroup radioGroup;
    RadioButton radioButton;

    String mail;
    String pass;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goHome();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Encuesta satisfacción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioGroup = findViewById(R.id.Radio_group);

        damemodoadmin();


        et_mensaje = findViewById(R.id.et_mensaje);
        tv_titulo = findViewById(R.id.tv_titulo);
        btn_enviar = findViewById(R.id.btn_enviar);
        String first = getIntent().getExtras().getString("first");
        String id = getIntent().getExtras().getString("idadmin");
        String npedido = getIntent().getExtras().getString("nped");



        Toast.makeText(this, first, Toast.LENGTH_SHORT).show();

        tv_titulo.setText("¡Hola " + npedido+"!");

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


                String idreq = getIntent().getExtras().getString("idreq");
                if(TextUtils.isEmpty(et_mensaje.getText().toString())){
                    Toast.makeText(satisActivity.this, "Escriba un mensaje...", Toast.LENGTH_SHORT).show();
                }else {

                    int radioId = radioGroup.getCheckedRadioButtonId();

                    radioButton =findViewById(radioId);



                    DatabaseReference refEncuesta = database.getReference("Pedidos");
                    EncuestaSatis encu = new EncuestaSatis(
                            et_mensaje.getText().toString(),
                            radioButton.getText().toString(),
                            idreq,
                            dateFormat.format(c.getTime()),
                            timeFormat.format(c.getTime()),
                            "no"

                    );
                    refEncuesta.child(idreq).child("EncuestaSatis").setValue(encu);
                    // nuevaEncuesta(id,"");
                    finish();
                    goHome();

                    String npedido = getIntent().getExtras().getString("nped");

                    EnviarMail sendmail = new EnviarMail();
                    sendmail.MailTo_admin_entregado(mail,pass,et_mensaje.getText().toString(),first,npedido,radioButton.getText().toString());

                    Toast.makeText(satisActivity.this, "Encuesta enviada!", Toast.LENGTH_SHORT).show();
                }


            }
        });





    }//fin del oncreate!


    private void nuevaEncuesta(String id,String m) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(id).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String first = getIntent().getExtras().getString("first");
                String nped = getIntent().getExtras().getString("nped");
                String token = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_sinfoto("¡Nueva encuesta de pedido!",first+ " "+"Encuesta ped. nº"+nped,token,"modoadmin");

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void damemodoadmin() {

        DatabaseReference myRef3 = database.getReference();
        Query query2 = myRef3.child("modoadmin");
        query2.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    mail = dataSnapshot.child("mail").getValue(String.class);
                    pass = dataSnapshot.child("password").getValue(String.class);


                    //Toast.makeText(Carrito.this, mail, Toast.LENGTH_SHORT).show();



                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void goHome() {
        Intent i = new Intent(satisActivity.this, home1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}