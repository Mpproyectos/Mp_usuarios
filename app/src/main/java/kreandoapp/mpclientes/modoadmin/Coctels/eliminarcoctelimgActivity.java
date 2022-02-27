package kreandoapp.mpclientes.modoadmin.Coctels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class eliminarcoctelimgActivity extends AppCompatActivity {


    Button btnsi,btnno;
    TextView tv_catename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarcoctelimg);

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

        final String id = getIntent().getExtras().getString("id");


        final String urlimg = getIntent().getExtras().getString("urlimg");
        //tv_catename.setText(cate);

        Toast.makeText(this, urlimg, Toast.LENGTH_SHORT).show();

        btnsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = getIntent().getExtras().getString("id");
                Toast.makeText(eliminarcoctelimgActivity.this, id, Toast.LENGTH_SHORT).show();

                String aborrar = urlimg;
                Toast.makeText(eliminarcoctelimgActivity.this, aborrar, Toast.LENGTH_SHORT).show();
                StorageReference storageReference =
                        FirebaseStorage.getInstance().getReference().child(aborrar);

                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "se borro bien", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "No se pudo borrar. error!!!", Toast.LENGTH_SHORT).show();
                    }
                });

                FirebaseDatabase database2 = FirebaseDatabase.getInstance();

                DatabaseReference myRef4 = database2.getReference("imgcoctel").child(id);
                myRef4.removeValue();
               finish();


            }




        });

    }//fin del oncreate!!

}