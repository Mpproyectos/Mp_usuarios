package kreandoapp.mpclientes.modoadmin;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import kreandoapp.mpclientes.APIService;
import kreandoapp.mpclientes.Notifications.Client;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.FoodRequestAdapter;
import kreandoapp.mpclientes.db.entity.Ordenes;
import kreandoapp.mpclientes.volley.claseSendVolleyFCM;

public class detallePedido extends AppCompatActivity {


    private DatabaseReference ref;

    RecyclerView rv;
    TextView total, direccion;
    ArrayList<Ordenes> orderArrayList;
    FoodRequestAdapter adapter;
    Button btn_cancelar,bnt_pedidoenviado,btn_ubicacion,btn_enviar_whatsapp;
    TextView tvfechareserva,tvhorareserva;
    private Toolbar toolbar;
    private LinearLayoutManager mLayoutManager;
    APIService apiService;
    FirebaseUser fuser;
    boolean notify = false;
    ProgressBar progress_bar;

    LinearLayout dire,fech,hor;

    TextView tv_telefono;
    Button btn_llamar;
    LinearLayout LL_envio;
    TextView tv_envio;
    TextView tv_estado,tv_txt_envio,tv_modoenvio,tv_modopago,tv_cancelacon;


    String txt_demora = "Demora 30 min.";

    TextView tv_promo_utilizada,tv_cupon_utilizado,tv_monto_cupon;

    Button btn_pedido_entregado;
    Button btn_mensaje_encuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalle_pedido);

        LL_envio = findViewById(R.id.LL_envio);
        tv_envio = findViewById(R.id.tv_envio);
        tv_estado = findViewById(R.id.tv_estado);

        dire = findViewById(R.id.linear_direccion);
        fech = findViewById(R.id.linear_fecha);
        hor = findViewById(R.id.linear_hora);
        tv_txt_envio = findViewById(R.id.tv_txt_envio);
        btn_llamar = findViewById(R.id.btn_llamar);
        tv_telefono = findViewById(R.id.tv_telefono);

        tv_promo_utilizada = findViewById(R.id.tv_promo_utilizada);
        tv_cupon_utilizado = findViewById(R.id.tv_cupon_utilizado);
        tv_monto_cupon = findViewById(R.id.tv_monto_cupon);
        btn_mensaje_encuesta = findViewById(R.id.btn_mensaje_encuesta);


        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        ref = FirebaseDatabase.getInstance().getReference().child("Pedidos");
        progress_bar = findViewById(R.id.progress_bar);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);

        tv_cancelacon = findViewById(R.id.tv_cancelacon);


        total = findViewById(R.id.tv_precio_total);
        direccion = findViewById(R.id.tv_direccion_envio);
        btn_cancelar = findViewById(R.id.btn_cancelar);
        bnt_pedidoenviado = findViewById(R.id.btn_pedidoenviado);
        btn_ubicacion = findViewById(R.id.btn_ubicacion);
        btn_enviar_whatsapp = findViewById(R.id.btn_enviarwhatsapp);

        tvfechareserva = findViewById(R.id.tv_fechareserva);
        tvhorareserva = findViewById(R.id.tv_horareserva);
        btn_pedido_entregado = findViewById(R.id.btn_pedido_entregado);
        tv_modoenvio = findViewById(R.id.tv_modoenvio);
        tv_modopago = findViewById(R.id.tv_modopago);

        final String idreq = getIntent().getExtras().getString("keyreqlist");

        final String total2 = getIntent().getExtras().getString("total");
        final String direccion2 = getIntent().getExtras().getString("direccion");
        final String ubi = getIntent().getExtras().getString("ubicacion");
        final String tel = getIntent().getExtras().getString("mitelefono");
        final String horareserva = getIntent().getExtras().getString("horareserva");
        final String fechareserva = getIntent().getExtras().getString("fechareserva");
        final String estado = getIntent().getExtras().getString("estado");
        final String numerodepedido = getIntent().getExtras().getString("numerodepedido");
        final int envio = getIntent().getExtras().getInt("envio");
        final String visto = getIntent().getExtras().getString("visto");
        final String modoenvio = getIntent().getExtras().getString("modoenvio");
        final String modopago = getIntent().getExtras().getString("modopago");

        final String cuponutilizado = getIntent().getExtras().getString("cuponutilizado");
        final String promoutilizada = getIntent().getExtras().getString("promoutilizada");
        final String montocupon = getIntent().getExtras().getString("montocupon");
        final String nombre = getIntent().getExtras().getString("nombre");
        final String cancelacon = getIntent().getExtras().getString("cancelacon");


        tv_cancelacon.setText("s/"+cancelacon);




        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database2.getReference();
        Query query2 = myRef3.child("Pedidos").child(idreq).child("EncuestaSatis");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String entregado = dataSnapshot.child("entregado").getValue(String.class);
                    String mensaje = dataSnapshot.child("mensaje").getValue(String.class);
                    String fecha = dataSnapshot.child("fecha").getValue(String.class);
                    String hora = dataSnapshot.child("hora").getValue(String.class);
                    btn_mensaje_encuesta.setVisibility(View.VISIBLE);
                    btn_mensaje_encuesta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent enc = new Intent(detallePedido.this, encuestasPedidosActivity.class);
                            enc.putExtra("idped",idreq);
                            enc.putExtra("entregado",entregado);
                            enc.putExtra("mensaje",mensaje);
                            enc.putExtra("fecha",fecha);
                            enc.putExtra("hora",hora);
                            enc.putExtra("nombre",nombre);
                            enc.putExtra("telefono",tel);

                            startActivity(enc);
                        }
                    });

                }else {
                    btn_mensaje_encuesta.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        tv_modopago.setText(modopago);

        tv_promo_utilizada.setText(promoutilizada);
        tv_cupon_utilizado.setText(cuponutilizado);
        tv_monto_cupon.setText(montocupon);

        btn_pedido_entregado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoPedidoEntregado();
            }
        });


        tv_estado.setText(estado);
        if(modoenvio.equals("delivery")){
            tv_modoenvio.setText("Enviar a domicilio del cliente");
        }else {
            tv_modoenvio.setText("el cliente retira por sucursal");
        }


        if(envio > 0){
            tv_txt_envio.setText("Total + envio");
            LL_envio.setVisibility(View.VISIBLE);
            tv_envio.setText("s/"+envio);


        }

        getSupportActionBar().setTitle("Detalle del pedido nº "+ numerodepedido);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(estado.equals("cancelado") || estado.equals("canceladouser")){
            
        }
        tv_telefono.setText(tel);


        btn_llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + tel)));
            }
        });

        tvfechareserva.setText(fechareserva);
        tvhorareserva.setText(horareserva);

        if(direccion2.equals("")){
            dire.setVisibility(View.GONE);
        }else{
            dire.setVisibility(View.VISIBLE);
        }




        ref.child(idreq).child("visto").setValue("si");

        if(!visto.equals("si")){
            ref.child(idreq).child("estado").setValue("En revision");
        }


        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String ShareSub = "Esta es mi ubicacion";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, ubi);
                startActivity(Intent.createChooser(sharingIntent, "Compartir en:"));
            }
        });

        btn_enviar_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wa.me/+51"+tel;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



        total.setText("s/"+ total2);
        direccion.setText(direccion2);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mostrarDialogoCancelar();
            }
        });
        bnt_pedidoenviado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mostrarDialogoconfirmar();
            }
        });


        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv = findViewById(R.id.rv_detallepedido);
        rv.setLayoutManager(mLayoutManager);

        orderArrayList = new ArrayList<>();
        adapter = new FoodRequestAdapter(orderArrayList);
        rv.setAdapter(adapter);

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("Pedidos").child(idreq).child("ordenes");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    orderArrayList.removeAll(orderArrayList);
                    progress_bar.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        Ordenes ord = snapshot.getValue(Ordenes.class);
                        orderArrayList.add(ord);


                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(detallePedido.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void mostrarDialogoPedidoEntregado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(detallePedido.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_pedenviado, null);

        builder.setView(view);

        //TODO BOTONES POR DEFECTO
        /**
         builder.setView(inflater.inflate(R.layout.dialog_personalizado,null))
         .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(getApplicationContext(),"Conectando...",Toast.LENGTH_SHORT).show();
        }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
        }
        });
         */
        final String idreq = getIntent().getExtras().getString("keyreqlist");
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText et_motivo_cancel = view.findViewById(R.id.et_motivo_cancel);
        Button btnCancel = view.findViewById(R.id.btn_cancelar);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnconfirmar = view.findViewById(R.id.btn_confirmar2);
        btnconfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef4 = database3.getReference("Pedidos").child(idreq).child("estado");
                    myRef4.setValue("entregado");



                    fuser = FirebaseAuth.getInstance().getCurrentUser();
                    final String id = getIntent().getExtras().getString("idReceiver");

                    pedidoEntregado(id,"");
                    finish();

                    dialog.dismiss();



            }
        });
    }
    private void mostrarDialogoCancelar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(detallePedido.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_cancelar, null);

        builder.setView(view);

        //TODO BOTONES POR DEFECTO
        /**
         builder.setView(inflater.inflate(R.layout.dialog_personalizado,null))
         .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(getApplicationContext(),"Conectando...",Toast.LENGTH_SHORT).show();
        }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
        }
        });
         */
        final String idreq = getIntent().getExtras().getString("keyreqlist");
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText et_motivo_cancel = view.findViewById(R.id.et_motivo_cancel);
        Button btnCancel = view.findViewById(R.id.btn_cancelar);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
            Button btnconfirmar = view.findViewById(R.id.btn_notificar);
        btnconfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notify = true;
                    if(TextUtils.isEmpty(et_motivo_cancel.getText().toString())) {
                        et_motivo_cancel.setError("Escribe un motivo de cancelación.");
                    }else {
                        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                        DatabaseReference myRef4 = database3.getReference("Pedidos").child(idreq).child("estado");
                        myRef4.setValue("cancelado");
                        DatabaseReference myRef5 = database3.getReference("Pedidos").child(idreq).child("motivocancelacion");
                        myRef5.setValue(et_motivo_cancel.getText().toString());

                        Toast.makeText(detallePedido.this, "Cliente Notificado", Toast.LENGTH_SHORT).show();
                        fuser = FirebaseAuth.getInstance().getCurrentUser();
                        final String id = getIntent().getExtras().getString("idReceiver");
                        pedidoCancelado(id,et_motivo_cancel.getText().toString());

                        finish();

                        dialog.dismiss();
                    }

                    //dialog.dismiss();
                }
            });
        }
    private void mostrarDialogoconfirmar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(detallePedido.this);

        LayoutInflater inflater = getLayoutInflater();
        final String idreq = getIntent().getExtras().getString("keyreqlist");
        final View view = inflater.inflate(R.layout.dialog_productoenviado, null);

        builder.setView(view);



        final AlertDialog dialog = builder.create();
        dialog.show();

        final Spinner s1 = view.findViewById(R.id.id_spinner);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference r1 = database.getReference("Demoras");
        Query query = r1.orderByChild("orden");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("demora").getValue(String.class);
                    areas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(detallePedido.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s1.setAdapter(areasAdapter);
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getSelectedItem().toString();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        txt_demora = item;

                        DatabaseReference r2 = database.getReference();
                        Query q2 = r2.child("Demoras").orderByChild("demora").equalTo(item);
                        q2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String catekey = childSnapshot.getKey();


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

        Button btnCancel = view.findViewById(R.id.btn_cancelar);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



       /* final TextView tv_demora = view.findViewById(R.id.tv_demora);
        tv_demora.setText("demora 30 min.");
        RadioGroup radioGroup = view.findViewById(R.id.radioGroupx);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.opcion_15min){
                   txt_demora = "demora 15 min.";
                }
                if(checkedId == R.id.opcion_30min){
                    txt_demora = "demora 30 min.";
                }
                if(checkedId == R.id.opcion_45min){
                    txt_demora = "demora 45 min.";
                }



            }


        });*/

        Button btn_enviado = view.findViewById(R.id.btn_enviado);
        btn_enviado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String idreq = getIntent().getExtras().getString("keyreqlist");



                FirebaseDatabase database4 = FirebaseDatabase.getInstance();
                DatabaseReference myRef5 = database4.getReference("Pedidos").child(idreq).child("demora");
                myRef5.setValue(txt_demora);
                fuser = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database22 = FirebaseDatabase.getInstance();
                final Calendar c = Calendar.getInstance();

                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                DatabaseReference myRef22 = database22.getReference("Pedidos").child(idreq).child("terminado");
                myRef22.setValue(dateFormat.format(c.getTime()));

                final String iduser = getIntent().getExtras().getString("idReceiver");

                pedidoEnviado(iduser,txt_demora,idreq);

                finish();
                dialog.dismiss();


            }
        });
    }

    private void pedidoEnviado(String id,String d,String idreq) {

        Toast.makeText(this, "Cliente notificado!", Toast.LENGTH_SHORT).show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(id).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_sinfoto("Pedido enviado","Tu pedido llegara en: "+d,token,"mispedidos");
                   
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef5 = database3.getReference("Pedidos").child(idreq);
        myRef5.child("estado").setValue("Pedido enviado.");




    }
    private void pedidoCancelado(String id,String m) {

        Toast.makeText(this, "Cliente notificado!", Toast.LENGTH_SHORT).show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(id).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_sinfoto("Pedido Cancelado","Motivo: "+m,token,"mispedidos");



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void pedidoEntregado(String id,String m) {
        final String numerodepedido = getIntent().getExtras().getString("numerodepedido");
        final String nombre = getIntent().getExtras().getString("nombre");
        final String idreq = getIntent().getExtras().getString("keyreqlist");
        String string = nombre;
        String[] parts = string.split(" ");
        String part1 = parts[0]; // Primer nombre---

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(id).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_ped_entregado(
                            "¡Pedido entregado!",
                            "Presiona para dejar un comentario. Disbos",
                            token,
                            fuser.getUid(),
                            part1,
                            numerodepedido,
                            idreq,
                            "modosatis"
                           );

                        finish();
                    Toast.makeText(detallePedido.this, "Pedido entregado!", Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    }

