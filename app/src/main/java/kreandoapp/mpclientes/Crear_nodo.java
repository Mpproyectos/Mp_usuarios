package kreandoapp.mpclientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kreandoapp.mpclientes.clientes.ProcesoVenta.DireccionActivity;
import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.modoadmin.Productos.nuevoProducto;
import kreandoapp.mpclientes.pojo.ModeloLastNodo;
import kreandoapp.mpclientes.pojo.ModeloNodo;
import kreandoapp.mpclientes.volley.claseSendVolleyFCM;

public class Crear_nodo extends AppCompatActivity {

    private Toolbar toolbar;
    Spinner s1;

    String idsele,nombresuper;
    Button btn_crear_nodo;

    private DatabaseReference categoriaref;
    private DatabaseReference lastnodoref;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    EditText et_nombre_nodo;

    ImageButton botonvolver,btn_editar;
    TextView tv_titulo_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nodo);

        et_nombre_nodo = findViewById(R.id.et_nombre_nodo);

        categoriaref = FirebaseDatabase.getInstance().getReference().child("Nodos");
        lastnodoref = FirebaseDatabase.getInstance().getReference().child("LastNodo");


        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Crear nuevo nodo");

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


        s1 = findViewById(R.id.id_spinner);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference r1 = database.getReference("supervisores");
        r1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("nombre").getValue(String.class);

                    areas.add(areaName);

                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(Crear_nodo.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                s1.setAdapter(areasAdapter);
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getSelectedItem().toString();
                        nombresuper = item;

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference r2 = database.getReference();
                        Query q2 = r2.child("supervisores").orderByChild("nombre").equalTo(item);
                        q2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String catekey = childSnapshot.getKey();
                                    idsele =  catekey;

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

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

        btn_crear_nodo = findViewById(R.id.btn_crear_nodo);
        btn_crear_nodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_nombre_nodo.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Nombre Nodo esta vacio", Toast.LENGTH_SHORT).show();
                }else{

                    //TODO: no repetir el Nombrenodo...

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference r1 = database.getReference();
                    Query query = r1.child("Nodos").orderByChild("nombreNodo").equalTo(et_nombre_nodo.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(!dataSnapshot.exists()){
                                cargarNodo(et_nombre_nodo.getText().toString());
                            }else{
                                Toast.makeText(getApplicationContext(), "Este nodo ya existe.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }


            }
        });

    }//fin del oncreate!

    private void cargarNodo(String dato) {
        final Calendar c1 = Calendar.getInstance();

        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Long datetime = System.currentTimeMillis();

        String push = categoriaref.push().getKey();
        ModeloNodo nod = new ModeloNodo(user.getEmail(),dato,dateFormat.format(c1.getTime()),timeFormat.format(c1.getTime()),"","",
                "trabajando",user.getUid(),idsele,nombresuper,"no","no","si","no",push,"no",datetime.toString(),"","");
        ModeloLastNodo last = new ModeloLastNodo(push,user.getUid());

        categoriaref.child(push).setValue(nod);


        lastnodoref.child(user.getUid()).setValue(last);




        notificarsupervisor(idsele,et_nombre_nodo.getText().toString());


        Intent intent = new Intent(getApplicationContext(), home1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void notificarsupervisor(String idadmin,String nodo) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(idadmin).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_sinfoto("Usuario "+ user.getEmail() +"","necesita autorizar nuevo nodo: "+nodo,token,"modoadmin");
                    

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}