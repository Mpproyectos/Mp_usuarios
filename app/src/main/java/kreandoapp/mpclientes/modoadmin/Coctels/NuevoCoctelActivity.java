package kreandoapp.mpclientes.modoadmin.Coctels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import kreandoapp.mpclientes.pojo.Coctelpojo;

public class NuevoCoctelActivity extends AppCompatActivity {

    private Toolbar toolbar;

    Button btn_elige_foto, btn_subir_categoria;
    ImageView img_categoria;
    EditText et_nombre_categoria;

    private StorageReference TutRefe;
    private StorageReference thumbImageRef;
    private DatabaseReference tutref;
    private DatabaseReference categoriaref;
    private ProgressDialog loadingbar;

    Bitmap thumb_bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_coctel);

        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nuevo Coctel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingbar = new ProgressDialog(this);

        et_nombre_categoria = findViewById(R.id.et_nombre_categoria);

        btn_subir_categoria = findViewById(R.id.btn_subircategoria);
        //init view
        btn_elige_foto = findViewById(R.id.btn_elige_foto);

        btn_elige_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(NuevoCoctelActivity.this);
            }
        });

        thumbImageRef = FirebaseStorage.getInstance().getReference().child("imagenes_comprimidas");
        categoriaref = FirebaseDatabase.getInstance().getReference().child("Coctel");

        img_categoria = findViewById(R.id.img_categoria);



    }//fin del oncreate!!

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            //NOW CROP IMAGE URI
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)

                    .setRequestedSize(640, 480)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        //fin del primero..

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {



                Uri resultUri = result.getUri();

                final File thumb_filePathUri = new File(resultUri.getPath());
                Picasso.with(NuevoCoctelActivity.this).load(thumb_filePathUri).into(img_categoria);
                //thumb_bitmap = new Compressor();

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

                int p = (int) (Math.random() * 25 + 1);int s = (int) (Math.random() * 25 + 1);
                int t = (int) (Math.random() * 25 + 1);int c = (int) (Math.random() * 25 + 1);
                int numero1 = (int) (Math.random() * 1012 + 2111);
                int numero2 = (int) (Math.random() * 1012 + 2111);

                String[] elementos = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "k",
                        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

                final String aleatorio2 = elementos[p] + elementos[s] +
                        numero1 + elementos[t] + elementos[c] + numero2 + "comprimido.jpg";


                //final StorageReference thumb_filePath = thumbImageRef.child(aleatorio2);
                //UploadTask uploadTask = thumb_filePath.putBytes(thumb_byte);


                btn_subir_categoria.setOnClickListener(v -> {
                    final String name_categoria = et_nombre_categoria.getText().toString();


                    if(TextUtils.isEmpty(name_categoria)){
                        Toast.makeText(NuevoCoctelActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();


                    }else{
                        loadingbar.setTitle("Subiendo la foto");
                        loadingbar.setMessage("Espere por favor, se esta cargando la imagen");
                        loadingbar.show();


                        final StorageReference ref = thumbImageRef.child(aleatorio2);
                        UploadTask uploadTask = ref.putBytes(thumb_byte);




                        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                            if (!task.isSuccessful()) {
                                throw Objects.requireNonNull(task.getException());
                            }

                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                String push = categoriaref.push().getKey();
                                loadingbar.dismiss();

                                //colocando url miniatura en bd..

                                Coctelpojo coc = new Coctelpojo(
                                        push,
                                        name_categoria,
                                        downloadUri.toString(),
                                        "imagenes_comprimidas/"+aleatorio2,
                                        2
                                );


                                //Category cate = new Category(categoria,thumb_downloadUrl,"imagenes_comprimidas/"+aleatorio2);
                                categoriaref.child(push).setValue(coc);
                                Toast.makeText(NuevoCoctelActivity.this, "Nuevo Coctel Cargado.", Toast.LENGTH_SHORT).show();


                                finish();

                                loadingbar.dismiss();
                            } else {
                                // Handle failures
                                // ...
                            }
                        });


                    }

                });





            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}