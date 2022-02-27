package kreandoapp.mpclientes.clientes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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
import io.github.inflationx.calligraphy3.CalligraphyConfig;

public class misdatos extends AppCompatActivity {


    EditText et_ultimadireccion,et_telefono;
    Button btn_actualizardire;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/neufreu.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_midireccion);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        internet = new detectorInternet(this);

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
        tv_titulo_toolbar.setText("Mis datos");

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("Users").child(user.getUid());
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String d = dataSnapshot.child("midireccion").getValue(String.class);
                    String t = dataSnapshot.child("mitelefono").getValue(String.class);

                    et_ultimadireccion.setText(d);
                    et_telefono.setText(t);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        et_telefono = findViewById(R.id.et_telefono);

        et_ultimadireccion = findViewById(R.id.et_ultimadireccion);

        btn_actualizardire = findViewById(R.id.btn_actualizardire);
        btn_actualizardire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomtext);
                btn_actualizardire.startAnimation(animation);

                if(TextUtils.isEmpty(et_ultimadireccion.getText().toString()) || TextUtils.isEmpty(et_telefono.getText().toString())){
                    et_ultimadireccion.setError("Completa los datos por favor...");
                }else{

                    FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef4 = database3.getReference("Users").child(user.getUid()).child("midireccion");
                    myRef4.setValue(et_ultimadireccion.getText().toString());


                    FirebaseDatabase database34 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef5 = database34.getReference("Users").child(user.getUid()).child("mitelefono");
                    myRef5.setValue(et_telefono.getText().toString());


                    Toast.makeText(misdatos.this, "Datos Actualizada", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(getApplicationContext(),home1.class);
                    startActivity(i);
                }
            }
        });





    }//fin oncreate!

    @Override
    protected void onResume() {
        super.onResume();
        if(internet.estaConectado()){

        }else{

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
