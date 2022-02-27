package kreandoapp.mpclientes.clientes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.modoadmin.Productos.nuevoProducto;
import kreandoapp.mpclientes.pojo.ModeloCaja;
import kreandoapp.mpclientes.pojo.ModeloCajaFase1;
import kreandoapp.mpclientes.pojo.pojo_productos;

public class CargarFase1Activity extends AppCompatActivity {
    Double latituduser,longituduser;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ImageView img_foto;
    Button seleccione_foto, subirproducto;

    private StorageReference thumbImageRef;

    private DatabaseReference categoriaref;

    Bitmap thumb_bitmap = null;

    private ProgressDialog loadingbar;

    String estado,fecha,hora,idsupervisor,tipo_trabajo,timestamp_inicio;

    public static final int REQUEST_CODE = 101;
    public static final int CAMERA_CODE = 102;

    private Uri imageUri;

    ImageButton botonvolver,btn_editar;
    TextView tv_titulo_toolbar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(CargarFase1Activity.this,home1.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_fase1);

        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Cargar Foto Fase 1");

        botonvolver = findViewById(R.id.btn_volverAtras);
        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),home1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });





        loadingbar = new ProgressDialog(this);




        // Icon click listener

        img_foto = findViewById(R.id.img_foto);
        subirproducto = findViewById(R.id.btn_subirproducto);


        consultarDatosbase();



        categoriaref = FirebaseDatabase.getInstance().getReference().child("Cajas");
        thumbImageRef = FirebaseStorage.getInstance().getReference().child("imagenes_comprimidas");

        seleccione_foto = findViewById(R.id.btn_select_foto);
        seleccione_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ImagePicker.Companion.with(CargarFase1Activity.this)
                        .cameraOnly()

                        .compress(800)
                        .maxResultSize(640,480)
                        .start();
            }

        });


    }//fin del oncreate!!





    private void consultarDatosbase() {
        String id_caja = getIntent().getExtras().getString("id_caja");

        DatabaseReference myRef552 = database.getReference();
        Query query = myRef552.child("Cajas").child(id_caja);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    estado = dataSnapshot.child("estado").getValue(String.class);
                    fecha = dataSnapshot.child("fecha").getValue(String.class);
                    hora = dataSnapshot.child("hora").getValue(String.class);
                    idsupervisor = dataSnapshot.child("id_supervisor").getValue(String.class);
                    tipo_trabajo = dataSnapshot.child("tipo_trabajo").getValue(String.class);

                    seleccione_foto.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        img_foto.setImageURI(uri);


        subirproducto.setVisibility(View.VISIBLE);


            int p = (int) (Math.random() * 25 + 1);int s = (int) (Math.random() * 25 + 1);
            int t = (int) (Math.random() * 25 + 1);int c = (int) (Math.random() * 25 + 1);
            int numero1 = (int) (Math.random() * 1012 + 2111);
            int numero2 = (int) (Math.random() * 1012 + 2111);

            String[] elementos = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "k",
                    "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

            final String aleatorio2 = elementos[p] + elementos[s] +
                    numero1 + elementos[t] + elementos[c] + numero2 + "comprimido.jpg";






            subirproducto.setOnClickListener( v -> {

                loadingbar.setTitle("Subiendo la foto");
                loadingbar.setMessage("Espere por favor, se esta cargando la imagen");
                loadingbar.show();


                final StorageReference ref = thumbImageRef.child(aleatorio2);
                UploadTask uploadTask = ref.putFile(uri);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw Objects.requireNonNull(task.getException());
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            String push = categoriaref.push().getKey();

                            loadingbar.dismiss();
                            //colocando url miniatura en bd..

                            String id_caja = getIntent().getExtras().getString("id_caja");
                            String nombre_caja = getIntent().getExtras().getString("nombre_caja");
                            final Calendar c1 = Calendar.getInstance();

                            final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                            latituduser = Double.valueOf(getIntent().getExtras().getString("latitudUser"));
                            longituduser = Double.valueOf(getIntent().getExtras().getString("longitudUser"));


                            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                            String sn = sh.getString("nombre_supervisor", "");
                            String ids = sh.getString("id_supervisor", "");
                            String idnodo = sh.getString("idnodo", "");


                            Long datetime = System.currentTimeMillis();
                            ModeloCaja caj = new ModeloCaja(

                                    nombre_caja,id_caja,fecha,hora,ids,sn,user.getUid(),"creacion",idnodo,tipo_trabajo,
                                    dateFormat.format(c1.getTime()), timeFormat.format(c1.getTime()),downloadUri.toString(),"imagenes_comprimidas/"+aleatorio2,latituduser.toString(),longituduser.toString(),
                                    "","","","","","",datetime.toString(),"","",0,""

                            );





                            categoriaref.child(id_caja).setValue(caj);


                            loadingbar.dismiss();


                            Intent i = new Intent(CargarFase1Activity.this,Fase1okActivity.class);
                            startActivity(i);

                            finish();








                            //Category cate = new Category(categoria,thumb_downloadUrl,"imagenes_comprimidas/"+aleatorio2);

                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });

            });






    }
}