package kreandoapp.mpclientes.modoadmin;

import android.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import kreandoapp.mpclientes.R;

public class modomail extends AppCompatActivity {
    Button btn_activado,btn_desactivado;
    Button btn_activadocancelar,btn_desactivadocancelar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modomail);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Modo mail < Notificaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_activado = findViewById(R.id.btn_modoactivado);
        btn_desactivado = findViewById(R.id.btn_mododesactivado);
        btn_activadocancelar = findViewById(R.id.btn_modoactivadocancelar);
        btn_desactivadocancelar = findViewById(R.id.btn_mododesactivadocancelar);

        btn_activadocancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAdesactivadocancelar();
            }
        });
        btn_desactivadocancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarA_activadocancelar();
            }
        });

        btn_activado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAdesactivado();
            }
        });
        btn_desactivado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarA_activado();
            }
        });

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("modomail").child("valor");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("activado")){
                        btn_activado.setVisibility(View.VISIBLE);
                        btn_desactivado.setVisibility(View.GONE);
                    }else{
                        btn_activado.setVisibility(View.GONE);
                        btn_desactivado.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase database33 = FirebaseDatabase.getInstance();
        DatabaseReference myRef5 = database33.getReference();
        Query query5 = myRef5.child("modomailreserva").child("valor");
        query5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("activado")){
                        btn_activadocancelar.setVisibility(View.VISIBLE);
                        btn_desactivadocancelar.setVisibility(View.GONE);
                    }else{
                        btn_activadocancelar.setVisibility(View.GONE);
                        btn_desactivadocancelar.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }//fin del oncreate

    private void cambiarAdesactivado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(modomail.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_activado, null);

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
                DatabaseReference myRef4 = database3.getReference().child("modomail").child("valor");
                myRef4.setValue("desactivado");
                Toast.makeText(modomail.this, "Se cambio a modo desactivado ", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
    private void cambiarA_activado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(modomail.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_desactivado, null);

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
                DatabaseReference myRef4 = database3.getReference().child("modomail").child("valor");
                myRef4.setValue("activado");
                Toast.makeText(modomail.this, "Se cambio a modo Activado ", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }

    private void cambiarAdesactivadocancelar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(modomail.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_activado_cancelar, null);

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
                DatabaseReference myRef4 = database3.getReference().child("modomailreserva").child("valor");
                myRef4.setValue("desactivado");
                Toast.makeText(modomail.this, "Se cambio a modo desactivado ", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
    private void cambiarA_activadocancelar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(modomail.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_desactivado_cancelar, null);

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
                DatabaseReference myRef4 = database3.getReference().child("modomailreserva").child("valor");
                myRef4.setValue("activado");
                Toast.makeText(modomail.this, "Se cambio a modo activado ", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
}

