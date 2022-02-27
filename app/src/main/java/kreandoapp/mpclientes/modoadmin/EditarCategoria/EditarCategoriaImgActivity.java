package kreandoapp.mpclientes.modoadmin.EditarCategoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import kreandoapp.mpclientes.R;

public class EditarCategoriaImgActivity extends AppCompatActivity {
    private Toolbar toolbar;

    ProgressBar progress_bar;
    ImageView img_foto;
    Button seleccione_foto, subirproducto;

    private StorageReference TutRefe;
    private StorageReference thumbImageRef;
    private DatabaseReference tutref;
    private DatabaseReference categoriaref;

    private final static int Gallery_pick = 1;
    Bitmap thumb_bitmap = null;

    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editarimagen);


        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar Imagen de categoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingbar = new ProgressDialog(this);

        progress_bar = findViewById(R.id.progress_bar);

        tutref = FirebaseDatabase.getInstance().getReference().child("tutorial");
        categoriaref = FirebaseDatabase.getInstance().getReference().child("Categoria");
        thumbImageRef = FirebaseStorage.getInstance().getReference().child("imagenes_comprimidas");



        seleccione_foto = findViewById(R.id.btn_select_foto);
        subirproducto = findViewById(R.id.btn_subirproducto);

        img_foto = findViewById(R.id.img_foto);

        String key = getIntent().getExtras().getString("key");

        String urlenproceso = getIntent().getExtras().getString("urlenproceso");
        Toast.makeText(this, urlenproceso, Toast.LENGTH_SHORT).show();

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database2.getReference("Categoria").child(key).child("catImage");
        myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    Picasso.with(getApplicationContext()).load(val).into(img_foto, new Callback() {
                        @Override
                        public void onSuccess() {
                            img_foto.setVisibility(View.VISIBLE);
                            progress_bar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        seleccione_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.startPickImageActivity(EditarCategoriaImgActivity.this);
            }
        });


    }//fin oncreate!!

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

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {


                Uri resultUri = result.getUri();

                final File thumb_filePathUri = new File(resultUri.getPath());
                Picasso.with(EditarCategoriaImgActivity.this).load(thumb_filePathUri).into(img_foto);

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

                subirproducto.setOnClickListener(v -> {



                    loadingbar.setTitle("Subiendo la foto");
                    loadingbar.setMessage("Espere por favor, se esta cargando la imagen");
                    loadingbar.show();


                    final StorageReference ref = thumbImageRef.child(aleatorio2);
                    UploadTask uploadTask = ref.putBytes(thumb_byte);



                    ///
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



                                String key = getIntent().getExtras().getString("key");
                                String urlenproceso = getIntent().getExtras().getString("urlenproceso");



                                FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                                final DatabaseReference myRef4 = database3.getReference("Categoria").child(key).child("catImage");
                                myRef4.setValue(downloadUri.toString());

                                FirebaseDatabase database5 = FirebaseDatabase.getInstance();
                                final DatabaseReference myRef5 = database5.getReference("Categoria").child(key).child("catUrlCambiarImagen");
                                myRef5.setValue("imagenes_comprimidas/"+aleatorio2);

                                String url= urlenproceso;


                                StorageReference storageReference =   FirebaseStorage.getInstance().getReference().child(url);

                                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(EditarCategoriaImgActivity.this, "se borro bien", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {

                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(EditarCategoriaImgActivity.this, "ups. un error al subir.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                finish();

                                Toast.makeText(getApplicationContext(), "Imagen Actualizada Cargado.", Toast.LENGTH_SHORT).show();





                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });
                    ///



                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }

        }
    }
}