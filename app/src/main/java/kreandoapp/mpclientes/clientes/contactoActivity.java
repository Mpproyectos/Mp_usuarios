package kreandoapp.mpclientes.clientes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.modoadmin.EditarcontactoActivity;
import io.github.inflationx.calligraphy3.CalligraphyConfig;

public class contactoActivity extends AppCompatActivity {
    Button shareubicacion,btn_msjwhatsapp;
    TextView tv_telefonoadmin,tv_ubicacion;
    TextView titulo,direccion;
    Button btn_editarcontacto;

    detectorInternet internet;
    ImageButton botonvolver;
    TextView tv_titulo_toolbar;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(this, home1.class);
        this.startActivity(i);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/neufreu.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_contacto);
        titulo = findViewById(R.id.tv_titulo);
        direccion = findViewById(R.id.tv_direccion);
        botonvolver = findViewById(R.id.btn_volverAtras);
        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomtext);
                botonvolver.startAnimation(animation);
                Intent intent = new Intent(v.getContext(),home1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Contacto");

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        assert user != null;
        DatabaseReference myRef22 = database111.getReference("modoadmin").child("id");
        myRef22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);

                    if(val.equals(user.getUid())){
                        btn_editarcontacto.setVisibility(View.VISIBLE);
                    }else{
                        btn_editarcontacto.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_editarcontacto = findViewById(R.id.btn_editarcontacto);
        btn_editarcontacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomtext);
                btn_editarcontacto.startAnimation(animation);
                Intent i = new Intent(getApplicationContext(), EditarcontactoActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("contacto").child("titulo");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    titulo.setText(val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef5 = database2.getReference();
        Query query5 = myRef5.child("contacto").child("direccion");
        query5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    direccion.setText(val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        tv_telefonoadmin =findViewById(R.id.tv_telefonoadmin);
        tv_ubicacion = findViewById(R.id.tv_ubicacion);

        FirebaseDatabase database33 = FirebaseDatabase.getInstance();
        DatabaseReference myRef44 = database33.getReference();
        Query query44 = myRef44.child("modoadmin");
        query44.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String telefono = dataSnapshot.child("telefono").getValue(String.class);
                tv_telefonoadmin.setText(telefono);

                String ubicacion = dataSnapshot.child("ubicacion").getValue(String.class);
                tv_ubicacion.setText(ubicacion);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        shareubicacion = findViewById(R.id.btn_compartirubicacion);
        shareubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomtext);
               shareubicacion.startAnimation(animation);
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String ShareSub = "Esta es mi ubicacion";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tv_ubicacion.getText().toString());
                startActivity(Intent.createChooser(sharingIntent, "Compartir en:"));
            }
        });


        btn_msjwhatsapp = findViewById(R.id.btn_msjwhatsapp);
        btn_msjwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomtext);
                btn_msjwhatsapp.startAnimation(animation);

                String url = "https://wa.me/"+tv_telefonoadmin.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        internet = new detectorInternet(this);


    }// fin oncreate!!!


    @Override
    protected void onResume() {
        super.onResume();
        if(internet.estaConectado()){

        }else{
            finish();
            Toast.makeText(this, "No existe conexion a internet", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                Intent b= new Intent(this, home1.class);
                startActivity(b);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
