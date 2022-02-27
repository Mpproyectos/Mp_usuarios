package kreandoapp.mpclientes.modoadmin;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
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

public class modoestado extends AppCompatActivity {
    Button btn_abierto,btn_cerrado;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modoestado);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Estado del negocio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_abierto = findViewById(R.id.btn_negocio_abierto);
        btn_cerrado = findViewById(R.id.btn_negocio_cerrado);

        btn_abierto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAcerrado();
            }
        });
        btn_cerrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarA_abierto();
            }
        });

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("Estadolocal").child("valor");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("abierto")){
                        btn_abierto.setVisibility(View.VISIBLE);
                        btn_cerrado.setVisibility(View.GONE);
                    }else{
                        btn_abierto.setVisibility(View.GONE);
                        btn_cerrado.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }//fin del oncreate

    private void cambiarAcerrado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(modoestado.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_abierto, null);

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
                DatabaseReference myRef4 = database3.getReference().child("Estadolocal").child("valor");
                myRef4.setValue("cerrado");
                Toast.makeText(modoestado.this, "Se cambio a modo Cerrado ", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
    private void cambiarA_abierto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(modoestado.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_cerrado, null);

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
                DatabaseReference myRef4 = database3.getReference().child("Estadolocal").child("valor");
                myRef4.setValue("abierto");
                Toast.makeText(modoestado.this, "Se cambio a modo Abierto ", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
}
