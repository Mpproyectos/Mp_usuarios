package kreandoapp.mpclientes.modoadmin.modoGPS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.home1;

public class ModoGpsEdicionActivity extends AppCompatActivity {

    private Toolbar toolbar;

    Double latitudAdmin_detect,longitudAdmin_detect;

    String latitud_db,longitud_db;
    TextView tv_latitud_detect,tv_longitud_detect;

    Button btn_actualizar_corde_detec,btn_actualizar_manualmente;

    EditText et_latitud,et_longitud;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(this, home1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_gps_edicion);

        et_latitud = findViewById(R.id.et_latitud);
        et_longitud = findViewById(R.id.et_longitud);

        btn_actualizar_corde_detec = findViewById(R.id.btn_actualizar_corde_detec);
        btn_actualizar_corde_detec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCorde();
            }
        });

        btn_actualizar_manualmente = findViewById(R.id.btn_actualizar_manualmente);
        btn_actualizar_manualmente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCorde_manual();
            }
        });

        latitudAdmin_detect = Double.valueOf(getIntent().getExtras().getString("latitudAdmin"));
        longitudAdmin_detect = Double.valueOf(getIntent().getExtras().getString("longitudAdmin"));


        latitud_db = getIntent().getExtras().getString("latitud_db");
        longitud_db = getIntent().getExtras().getString("longitud_db");

        if(latitud_db.equals(String.valueOf(latitudAdmin_detect)) &&
                longitud_db.equals(String.valueOf(longitudAdmin_detect))){
            Toast.makeText(this, "Son iguales", Toast.LENGTH_SHORT).show();
        }else {
            btn_actualizar_corde_detec.setVisibility(View.VISIBLE);
        }

        tv_latitud_detect = findViewById(R.id.latitudDetect);
        tv_longitud_detect = findViewById(R.id.longitudDetect);


        tv_latitud_detect.setText(String.valueOf(latitudAdmin_detect));
        tv_longitud_detect.setText(String.valueOf(longitudAdmin_detect));






        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Modo gps admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }//fin del oncreate!!


    private void actualizarCorde() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModoGpsEdicionActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_actualizarcorde_row, null);

        builder.setView(view);


        final AlertDialog dialog = builder.create();
        dialog.show();

        Button btn_No = view.findViewById(R.id.btn_no);
        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_si = view.findViewById(R.id.btn_si);
        btn_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                DatabaseReference myRef4 = database3.getReference().child("modoadmin");
                myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String lat = dataSnapshot.child("latitud").getValue(String.class);
                            String lon = dataSnapshot.child("longitud").getValue(String.class);

                            myRef4.child("latitud").setValue(latitudAdmin_detect.toString());
                            myRef4.child("longitud").setValue(longitudAdmin_detect.toString());



                            final DatabaseReference myRef45 = database3.getReference("modoadmin").child("ubicacion");
                            myRef45.setValue("http://maps.google.com/maps?daddr="+latitudAdmin_detect.toString()+","+longitudAdmin_detect.toString());

                            Toast.makeText(ModoGpsEdicionActivity.this, "Se actualizaron las cordenadas!! ", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });
    }

    private void actualizarCorde_manual() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModoGpsEdicionActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_actualizarcorde_manual_row, null);

        builder.setView(view);


        final AlertDialog dialog = builder.create();
        dialog.show();

        Button btn_No = view.findViewById(R.id.btn_no);
        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_si = view.findViewById(R.id.btn_si);
        btn_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et_txt_latitud = et_latitud.getText().toString();
                String et_txt_longitud = et_longitud.getText().toString();

                if(TextUtils.isEmpty(et_txt_latitud) && TextUtils.isEmpty(et_txt_longitud)){
                    Toast.makeText(ModoGpsEdicionActivity.this, "Complete los campos.", Toast.LENGTH_SHORT).show();
                }else {



                    FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef4 = database3.getReference().child("modoadmin");
                    myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                //String lat = dataSnapshot.child("latitud").getValue(String.class);
                                //String lon = dataSnapshot.child("longitud").getValue(String.class);



                                myRef4.child("latitud").setValue(et_latitud.getText().toString());
                                myRef4.child("longitud").setValue(et_longitud.getText().toString());

                                final DatabaseReference myRef45 = database3.getReference("modoadmin").child("ubicacion");
                                myRef45.setValue("http://maps.google.com/maps?daddr="+et_latitud.getText().toString()+","+et_longitud.getText().toString());

                                Toast.makeText(ModoGpsEdicionActivity.this, "Se actualizaron las cordenadas manualmente!! ", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
    }
}