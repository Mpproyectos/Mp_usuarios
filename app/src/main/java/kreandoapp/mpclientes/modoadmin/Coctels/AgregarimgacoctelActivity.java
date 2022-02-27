package kreandoapp.mpclientes.modoadmin.Coctels;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.pojo.Coctelimg;

public class AgregarimgacoctelActivity extends AppCompatActivity {
    private Toolbar toolbar;
    EditText et_nombre,et_descripcion,et_precio;
    TextView tv_cate_elegida;
    ImageView img_foto;
    Button seleccione_foto, subirproducto;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private StorageReference thumbImageRef;

    private DatabaseReference categoriaref;


    Bitmap thumb_bitmap = null;

    private ProgressDialog loadingbar;

    String catesele;
    String subcatesele;

    Spinner s1,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarimgacoctel);


        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Añadir imagen coctel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingbar = new ProgressDialog(this);


        categoriaref = FirebaseDatabase.getInstance().getReference().child("imgcoctel");
        thumbImageRef = FirebaseStorage.getInstance().getReference().child("imagenes_comprimidas");

        s1 = findViewById(R.id.id_spinner);
        s2 = findViewById(R.id.id_spinner2);
        et_nombre = findViewById(R.id.et_nombre_producto);
        et_descripcion = findViewById(R.id.et_descripcion_producto);
        et_precio = findViewById(R.id.et_precio_producto);
        seleccione_foto = findViewById(R.id.btn_select_foto);
        subirproducto = findViewById(R.id.btn_subirproducto);
        tv_cate_elegida = findViewById(R.id.tv_cate_elegida);
        img_foto = findViewById(R.id.img_foto);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference r1 = database.getReference("Coctel");
        r1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("titulo").getValue(String.class);
                    areas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(AgregarimgacoctelActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                s1.setAdapter(areasAdapter);
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getSelectedItem().toString();


                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        DatabaseReference r2 = database.getReference();
                        Query q2 = r2.child("Coctel").orderByChild("titulo").equalTo(item);
                        q2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String catekey = childSnapshot.getKey();
                                    tv_cate_elegida.setText(catekey);
                                    catesele = catekey;
                                    Toast.makeText(AgregarimgacoctelActivity.this, catesele, Toast.LENGTH_SHORT).show();



                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        seleccione_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_pick);*/

                CropImage.startPickImageActivity(AgregarimgacoctelActivity.this);
            }
        });

    }//fin oncreate



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            //NOW CROP IMAGE URI
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)

                    .setRequestedSize(1024, 1024)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        //fin del primero..

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {



                Uri resultUri = result.getUri();

                final File thumb_filePathUri = new File(resultUri.getPath());
                Picasso.with(AgregarimgacoctelActivity.this).load(thumb_filePathUri).into(img_foto);
                //thumb_bitmap = new Compressor();

                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxWidth(1024)
                            .setMaxHeight(1024)
                            .setQuality(50)
                            .compressToBitmap(thumb_filePathUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
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



                subirproducto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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

                                        String push = categoriaref.push().getKey();

                                        loadingbar.dismiss();
                                        //colocando url miniatura en bd..

                                                Coctelimg cocimg = new Coctelimg(
                                                        push,
                                                        downloadUri.toString(),
                                                        "imagenes_comprimidas/"+aleatorio2,
                                                        catesele,
                                                        2
                                                );



                                                categoriaref.child(push).setValue(cocimg);
                                                Toast.makeText(AgregarimgacoctelActivity.this, "Nueva Imagen coctel Cargada.", Toast.LENGTH_SHORT).show();


                                                finish();

                                                loadingbar.dismiss();





                                        //Category cate = new Category(categoria,thumb_downloadUrl,"imagenes_comprimidas/"+aleatorio2);

                                    } else {
                                        // Handle failures
                                        // ...
                                    }
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