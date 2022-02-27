package kreandoapp.mpclientes.modoadmin.Promo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.pojo.pojo_productos;

public class CrearpromoActivity extends AppCompatActivity {
    private Toolbar toolbar;

    Button subirpromo,elegirfoto;
    ImageView img_promo;
    EditText et_nombre_promo,et_descripcion_promo,et_precio;
    private DatabaseReference promoref;

    private StorageReference thumbImageRef;
    private DatabaseReference tutref;


    private  final static int Gallery_pick = 1;
    Bitmap thumb_bitmap = null;

    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crearpromo);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear promo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subirpromo = findViewById(R.id.btn_subirpromo);
        elegirfoto = findViewById(R.id.btn_elige_foto);
        img_promo = findViewById(R.id.img_promo);
        et_nombre_promo = findViewById(R.id.et_nombre_promo);
        et_descripcion_promo = findViewById(R.id.et_descripcion_promo);
        et_precio = findViewById(R.id.et_precio_promo);


        promoref = FirebaseDatabase.getInstance().getReference().child("Promo");
        elegirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_pick);*/

                CropImage.startPickImageActivity(CrearpromoActivity.this);
            }
        });

        thumbImageRef = FirebaseStorage.getInstance().getReference().child("imagenes_comprimidas");


        loadingbar = new ProgressDialog(this);


    }//fin oncreate!!!

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            //NOW CROP IMAGE URI
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)

                    .setRequestedSize(800, 800)
                    .setAspectRatio(2,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {



                Uri resultUri = result.getUri();

                final File thumb_filePathUri = new File(resultUri.getPath());
                Picasso.with(CrearpromoActivity.this).load(thumb_filePathUri).into(img_promo);

                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(90)
                            .compressToBitmap(thumb_filePathUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                final byte[] thumb_byte = byteArrayOutputStream.toByteArray();

                int p = (int) (Math.random() * 25 + 1);
                int s = (int) (Math.random() * 25 + 1);
                int t = (int) (Math.random() * 25 + 1);
                int c = (int) (Math.random() * 25 + 1);
                int numero1 = (int) (Math.random() * 1012 + 2111);
                int numero2 = (int) (Math.random() * 1012 + 2111);

                String[] elementos = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "k",
                        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

                final String aleatorio2 = elementos[p] + elementos[s] + numero1 + elementos[t] + elementos[c] + numero2 + "comprimido.jpg";



                //final StorageReference thumb_filePath = thumbImageRef.child(aleatorio2);
                //UploadTask uploadTask = thumb_filePath.putBytes(thumb_byte);



                subirpromo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String nombre_promo = et_nombre_promo.getText().toString();
                        final String precio = et_precio.getText().toString();
                        final String descripcion = et_descripcion_promo.getText().toString();


                        if(TextUtils.isEmpty(nombre_promo)){
                            et_nombre_promo.setError("Ingresa nombre de categoria..");

                        }else{
                            loadingbar.setTitle("Subiendo la foto");
                            loadingbar.setMessage("Espere por favor, se esta cargando la imagen");
                            loadingbar.show();


                            final StorageReference ref = thumbImageRef.child(aleatorio2);
                            UploadTask uploadTask = ref.putBytes(thumb_byte);


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
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                                        DatabaseReference ref = database.getReference("codigoprod");

                                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                int val = dataSnapshot.getValue(Integer.class);
                                                int codigoprod = val + 1;
                                                ref.setValue(val + 1);

                                                pojo_productos prod = new pojo_productos("cod"+Integer.toString(codigoprod),nombre_promo,downloadUri.toString(),descripcion,precio,"algo","algo","","imagenes_comprimidas/"+aleatorio2,"si",2);
                                                promoref.push().setValue(prod);
                                                Toast.makeText(CrearpromoActivity.this, "Nueva Promo creada.", Toast.LENGTH_SHORT).show();

                                                finish();
                                                Toast.makeText(CrearpromoActivity.this, "Se subio bien", Toast.LENGTH_SHORT).show();
                                                loadingbar.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    } else {
                                        // Handle failures
                                        // ...
                                    }
                                }
                            });

                                    /*Productos prod = new Productos(nombre_promo,thumb_downloadUrl,descripcion,precio,"","imagenes_comprimidas/"+aleatorio2);
                                    promoref.push().setValue(prod);
                                    Toast.makeText(CrearpromoActivity.this, "Nueva Promo creada.", Toast.LENGTH_SHORT).show();

                                    finish();
                                    Toast.makeText(CrearpromoActivity.this, "Se subio bien", Toast.LENGTH_SHORT).show();*/





                                        /*Productos prod = new Productos(nombre_promo,thumb_downloadUrl,descripcion,precio,"","imagenes_comprimidas/"+aleatorio2);
                                        promoref.push().setValue(prod);
                                        Toast.makeText(CrearpromoActivity.this, "Nueva Promo creada.", Toast.LENGTH_SHORT).show();

                                        finish();


                                    }
                                }
                            });

                                    */
                        }


                    }
                });



            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
