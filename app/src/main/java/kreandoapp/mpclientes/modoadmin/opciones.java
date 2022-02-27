package kreandoapp.mpclientes.modoadmin;

import android.content.Context;
import android.content.pm.ActivityInfo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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
import io.github.inflationx.calligraphy3.CalligraphyConfig;

public class opciones extends AppCompatActivity {
    private Toolbar toolbar;
    Button deliveryoff,deliveryon,reservaoff,reservaon;

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
        setContentView(R.layout.activity_opciones);

        deliveryoff = findViewById(R.id.btn_delivery_off);
        deliveryon = findViewById(R.id.btn_delivery_on);

        reservaoff = findViewById(R.id.btn_reservaoff);
        reservaon = findViewById(R.id.btn_reservaon);


        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("opciones").child("delivery");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("si")){
                        deliveryon.setVisibility(View.VISIBLE);
                        deliveryoff.setVisibility(View.GONE);
                    }else {
                        deliveryon.setVisibility(View.GONE);
                        deliveryoff.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase database33 = FirebaseDatabase.getInstance();
        DatabaseReference myRef44 = database33.getReference();
        Query query44 = myRef44.child("opciones").child("reserva");
        query44.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("si")){
                        reservaon.setVisibility(View.VISIBLE);
                        reservaoff.setVisibility(View.GONE);
                    }else {
                        reservaon.setVisibility(View.GONE);
                        reservaoff.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        deliveryoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database33 = FirebaseDatabase.getInstance();
                DatabaseReference myRef44 = database33.getReference("opciones").child("delivery");
                myRef44.setValue("si");
            }
        });
        deliveryon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database33 = FirebaseDatabase.getInstance();
                DatabaseReference myRef44 = database33.getReference("opciones").child("delivery");
                myRef44.setValue("no");
            }
        });

        reservaoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database33 = FirebaseDatabase.getInstance();
                DatabaseReference myRef44 = database33.getReference("opciones").child("reserva");
                myRef44.setValue("si");
            }
        });
        reservaon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database33 = FirebaseDatabase.getInstance();
                DatabaseReference myRef44 = database33.getReference("opciones").child("reserva");
                myRef44.setValue("no");
            }
        });



        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Opciones Delivery Reserva");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
