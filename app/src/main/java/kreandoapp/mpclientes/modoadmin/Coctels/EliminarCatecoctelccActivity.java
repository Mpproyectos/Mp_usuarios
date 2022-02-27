package kreandoapp.mpclientes.modoadmin.Coctels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.home1;

public class EliminarCatecoctelccActivity extends AppCompatActivity {

    Button btnsi,btnno;
    TextView tv_catename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_catecoctelcc);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
        final String cate = getIntent().getExtras().getString("cate");
        final String urlimg = getIntent().getExtras().getString("urlimg");

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        tv_catename.setText(cate);

        btnsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                DatabaseReference myRef3 = database2.getReference();
                Query query2 = myRef3.child("imgcoctel").orderByChild("idcatefoto").equalTo(id);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){



                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                                String key = childSnapshot.getKey();

                                FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                DatabaseReference myRef3 = database2.getReference("imgcoctel").child(key).child("urlfotocambiar");
                                myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String val = dataSnapshot.getValue(String.class);
                                        StorageReference storageReference =
                                                FirebaseStorage.getInstance().getReference().child(val);

                                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Toast.makeText(getApplicationContext(), "se borro bien", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {

                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                Toast.makeText(getApplicationContext(), "ups. un error al subir.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                FirebaseDatabase database22 = FirebaseDatabase.getInstance();
                                DatabaseReference myRef4 = database22.getReference("imgcoctel").child(key);
                                myRef4.removeValue();

                                FirebaseDatabase database255 = FirebaseDatabase.getInstance();
                                DatabaseReference myRef555 = database255.getReference("Coctel").child(id).child("urlcambiar");
                                myRef555.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String val = dataSnapshot.getValue(String.class);
                                        if(dataSnapshot.exists()){
                                            StorageReference storageReference =
                                                    FirebaseStorage.getInstance().getReference().child(val);

                                            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getApplicationContext(), "se borro bien", Toast.LENGTH_SHORT).show();

                                                    FirebaseDatabase database23 = FirebaseDatabase.getInstance();
                                                    DatabaseReference myRef5 = database23.getReference("Coctel").child(id);
                                                    myRef5.removeValue();



                                                    Toast.makeText(EliminarCatecoctelccActivity.this, "Coctel Eliminado", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {

                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    Toast.makeText(getApplicationContext(), "ups. un error al subir.", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });








                            }
                            finish();
                            Intent i = new Intent(getApplicationContext(), home1.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);

                        }else {
                            final String urlimg = getIntent().getExtras().getString("urlimg");

                            StorageReference storageReference =
                                    FirebaseStorage.getInstance().getReference().child(urlimg);

                            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "se borro bien", Toast.LENGTH_SHORT).show();

                                    FirebaseDatabase database23 = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef5 = database23.getReference("Coctel").child(id);
                                    myRef5.removeValue();



                                    Toast.makeText(EliminarCatecoctelccActivity.this, "Coctel Eliminado", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {

                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(getApplicationContext(), "ups. un error .", Toast.LENGTH_SHORT).show();
                                }
                            });

                            FirebaseDatabase database23 = FirebaseDatabase.getInstance();
                            DatabaseReference myRef5 = database23.getReference("Coctel").child(id);
                            myRef5.removeValue();
                            finish();
                            Toast.makeText(EliminarCatecoctelccActivity.this, "Coctel Eliminado!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }//fin del oncreate!!

}