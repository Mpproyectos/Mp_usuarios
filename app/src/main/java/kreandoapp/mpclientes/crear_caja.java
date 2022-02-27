package kreandoapp.mpclientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
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

import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.modoadmin.Productos.nuevoProducto;
import kreandoapp.mpclientes.pojo.ModeloCaja;
import kreandoapp.mpclientes.pojo.ModeloLastCaja;
import kreandoapp.mpclientes.pojo.ModeloLastNodo;
import kreandoapp.mpclientes.pojo.ModeloNodo;

public class crear_caja extends AppCompatActivity {


    Button btn_crear_caja;
    EditText et_nombre_caja;

    private DatabaseReference cajaref;
    private DatabaseReference lastcajaref;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Spinner s1,s2;
    String catesele;

    ImageButton botonvolver,btn_editar;
    TextView tv_titulo_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_caja);

        cajaref = FirebaseDatabase.getInstance().getReference().child("Cajas");
        lastcajaref = FirebaseDatabase.getInstance().getReference().child("LastCaja");

        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Crear nueva caja");

        botonvolver = findViewById(R.id.btn_volverAtras);
        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), home1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        et_nombre_caja = findViewById(R.id.et_nombre_caja);
        btn_crear_caja = findViewById(R.id.btn_crear_caja);
        btn_crear_caja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_nombre_caja.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "El nombre caja esta vacio", Toast.LENGTH_LONG).show();

                }else{

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference r1 = database.getReference();
                    Query query = r1.child("Cajas").orderByChild("nombre_caja").equalTo(et_nombre_caja.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()){
                                //metodo cargar
                                cargarCaja();
                            }else{
                                Toast.makeText(getApplicationContext(), "Esta Caja ya existe.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });

        s1 = findViewById(R.id.id_spinner);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference r1 = database.getReference("tipo_colocacion");
        r1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("nombre").getValue(String.class);

                    areas.add(areaName);

                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(crear_caja.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                s1.setAdapter(areasAdapter);
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        catesele = parent.getSelectedItem().toString();



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



    }//fin del oncreate!

    private void cargarCaja() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String sn = sh.getString("nombre_supervisor", "");
        String ids = sh.getString("id_supervisor", "");
        String idnodo = sh.getString("idnodo", "");

        final Calendar c1 = Calendar.getInstance();

        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");



        String push = cajaref.push().getKey();
        ModeloCaja caj = new ModeloCaja(
                et_nombre_caja.getText().toString(),push,dateFormat.format(c1.getTime()),timeFormat.format(c1.getTime()),ids,sn,user.getUid(),"creacion",idnodo,catesele,
                "","","","","","",
                "","","","","","","","","",0,""
        );
        ModeloLastCaja last = new ModeloLastCaja(push,user.getUid());

        cajaref.child(push).setValue(caj);
        lastcajaref.child(user.getUid()).setValue(last);

        finish();
    }

}