package kreandoapp.mpclientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;

import kreandoapp.mpclientes.adapter.AdapterCajas;
import kreandoapp.mpclientes.adapter.AdapterProductos;
import kreandoapp.mpclientes.clientes.ProcesoVenta.listaProductos;
import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.clientes.pedidoenviadoActivity;
import kreandoapp.mpclientes.pojo.ModeloCaja;
import kreandoapp.mpclientes.pojo.pojo_productos;
import kreandoapp.mpclientes.volley.claseSendVolleyFCM;

public class continuar_nodo extends AppCompatActivity {
    private Toolbar toolbar;
    Button btn_crear_caja;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    RecyclerView rv_cajas;
    AdapterCajas adapter;
    private LinearLayoutManager mLayoutManager;
    ArrayList<ModeloCaja> cajasArrayList;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Button btn_nodoterminado;

    String nombre_nodo_txt;

    DatabaseReference categoriaref;

    int count = 0;
    int caja_ok = 0;

    ImageButton botonvolver,btn_editar;
    TextView tv_titulo_toolbar;
    RelativeLayout rv_rela;

    String tiempo_t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuar_nodo);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        nombre_nodo_txt = sh.getString("nombre_nodo", "");

        categoriaref = FirebaseDatabase.getInstance().getReference().child("Nodos");


        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Continuar Nodo: "+nombre_nodo_txt);

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



        btn_crear_caja = findViewById(R.id.btn_crear_caja);
        btn_crear_caja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),crear_caja.class);

                v.getContext().startActivity(i);

                finish();
            }
        });

        DatabaseReference myRef552 = database.getReference();
        Query query = myRef552.child("LastCaja").child(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String id_lastcaja= dataSnapshot.child("last_id").getValue(String.class);
                    DatabaseReference myRef552 = database.getReference();
                    Query query = myRef552.child("Cajas").child(id_lastcaja);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String estado= dataSnapshot.child("estado").getValue(String.class);

                            if(dataSnapshot.exists()){

                                if(estado.equals("listo")){
                                    btn_crear_caja.setVisibility(View.VISIBLE);

                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(0,0,0,110);
                                    rv_cajas.setLayoutParams(params);

                                }else {
                                    btn_crear_caja.setVisibility(View.GONE);
                                }

                            }else{
                                btn_crear_caja.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    btn_crear_caja.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cargarRv();

        btn_nodoterminado = findViewById(R.id.btn_nodoterminado);
        btn_nodoterminado.setOnClickListener(v ->

          mostrardialog()

        );

    }//fin del oncreate!

    private void notificarsupervisor_nodoend(String idadmin,String nodo) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(idadmin).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_sinfoto("Usuario "+ user.getEmail() +"","necesita autorizar nodo terminado: "+nodo,token,"modoadmin");
                    Toast.makeText(getApplicationContext(), "Si notifique!!", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void mostrardialog() {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(continuar_nodo.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_terminarnodo,null);

        TextView tv_nombre_nodo = view.findViewById(R.id.tv_nombre_nodo);

        tv_nombre_nodo.setText(nombre_nodo_txt);
        Button btn_cancelar = view.findViewById(R.id.btn_cancelar);
        Button btn_actualizar = view.findViewById(R.id.btn_actualizar);


        mbuilder.setCancelable(false);
        mbuilder.setView(view);
        AlertDialog dialog = mbuilder.create();
        dialog.show();

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                String id_supervisor = sh.getString("id_supervisor", "");
                String nombre_nodo = sh.getString("nombre_nodo", "");
                String idnodo = sh.getString("idnodo", "");
                String inicio_nodo_time = sh.getString("inicio_nodo", "");

                notificarsupervisor_nodoend(id_supervisor, nombre_nodo);

                Long datetime = System.currentTimeMillis();

                assert idnodo != null;




                long inicio =Long.parseLong(inicio_nodo_time);

                long segsMilli = 1000;
                long minsMilli = segsMilli * 60;
                long horasMilli = minsMilli * 60;
                long diasMilli = horasMilli * 24;

                Long diferencia = datetime - inicio;


                long diasTranscurridos = diferencia / diasMilli;
                long horasTranscurridos = diferencia / horasMilli;
                long minutosTranscurridos = diferencia / minsMilli;
                long segsTranscurridos = diferencia / segsMilli;


                if(diasTranscurridos == 0){
                    if(horasTranscurridos==0){

                        if(minutosTranscurridos == 0){
                            tiempo_t = segsTranscurridos+"seg";
                        }else{
                            tiempo_t = minutosTranscurridos+"min";
                        }

                    }else{
                        tiempo_t = horasTranscurridos + ":" + minutosTranscurridos+"hs";
                    }

                }else{
                    if(diasTranscurridos==1){
                        tiempo_t = diasTranscurridos +" dia y " + horasTranscurridos+":"+minutosTranscurridos+"hs";
                    }else{
                        tiempo_t = diasTranscurridos +" dias y " + horasTranscurridos+":"+minutosTranscurridos+"hs";
                    }

                }

                categoriaref.child(idnodo).child("tiempo_transcurrido").setValue(tiempo_t);
                categoriaref.child(idnodo).child("estado").setValue("finalizado");
                categoriaref.child(idnodo).child("notificacion2").setValue("si");
                categoriaref.child(idnodo).child("fin_nodo_time").setValue(datetime.toString());
                finish();

                Intent i = new Intent(continuar_nodo.this, pedidoenviadoActivity.class);
                startActivity(i);
            }
        });
    }




    private void cargarRv() {
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv_cajas =  findViewById(R.id.rv_cajas);
        rv_cajas.setLayoutManager(mLayoutManager);

        // MOSTRAR RECYCLER VIEW

        cajasArrayList = new ArrayList<>();
        adapter = new AdapterCajas(cajasArrayList,this);
        rv_cajas.setAdapter(adapter);

        cargarCajas();

    }

    private void cargarCajas() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String idnodo = sh.getString("idnodo", "");

        FirebaseDatabase database22 = FirebaseDatabase.getInstance();
        DatabaseReference myRef33 = database22.getReference();
        Query query4 = myRef33.child("Cajas").orderByChild("id_nodo").equalTo(idnodo);

        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    cajasArrayList.removeAll(cajasArrayList);
                    rv_cajas.setVisibility(View.VISIBLE);



                    for (DataSnapshot snapshot :  dataSnapshot.getChildren()) {

                        ModeloCaja prod = snapshot.getValue(ModeloCaja.class);

                        cajasArrayList.add(prod);

                        count++;

                        if(prod.getEstado().equals("listo")){
                            caja_ok++;
                        }



                    }

                    if(caja_ok == count){
                        btn_nodoterminado.setVisibility(View.VISIBLE);
                    }else{
                        btn_nodoterminado.setVisibility(View.GONE);
                    }

                    adapter.notifyDataSetChanged();


                }else {
                    btn_crear_caja.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "No hay cajas.", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

