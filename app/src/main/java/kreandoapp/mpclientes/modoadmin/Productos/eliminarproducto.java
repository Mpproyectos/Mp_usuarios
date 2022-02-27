package kreandoapp.mpclientes.modoadmin.Productos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.home1;

public class eliminarproducto extends AppCompatActivity {

    Button btnsi,btnno;
    TextView tv_catename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarproducto);

        btnsi = findViewById(R.id.btnsi);
        btnno = findViewById(R.id.btnno);

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), home1.class);
                startActivity(i);
            }
        });

        tv_catename = findViewById(R.id.tv_catename);

        final String img = getIntent().getExtras().getString("imagenenproceso");
        final String key = getIntent().getExtras().getString("keyprod");
        final String name = getIntent().getExtras().getString("nameprod");
        tv_catename.setText(name);

        btnsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StorageReference storageReference =
                        FirebaseStorage.getInstance().getReference().child(img);

                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "se borro bien", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //Toast.makeText(getApplicationContext(), "ups. un error al subir.", Toast.LENGTH_SHORT).show();
                    }
                });

                FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                final DatabaseReference myRef4 = database3.getReference("Productos").child(key);
                myRef4.removeValue();

                Toast.makeText(eliminarproducto.this, "Producto eliminado", Toast.LENGTH_SHORT).show();

                finish();

                Intent i = new Intent(getApplicationContext(), home1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

    }//fin del oncreate!!

}
