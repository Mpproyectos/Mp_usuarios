package kreandoapp.mpclientes.clientes.ProcesoVenta;

import android.Manifest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;


import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.disposables.Disposable;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.AdapterCart;
import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.db.database.AppDb;
import kreandoapp.mpclientes.db.entity.Ordenes;
import kreandoapp.mpclientes.detectorInternet;


import kreandoapp.mpclientes.retrofit.RetrofitApi;
import kreandoapp.mpclientes.retrofit.RetrofitClient;


import retrofit2.Retrofit;
import io.github.inflationx.calligraphy3.CalligraphyConfig;

public class Carrito extends AppCompatActivity implements AdapterCart.Acciones {
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION ;
    TextView tv_id_admin;
    TextView text_ubicacion;
    TextView tv_telefono;
    TextView tv_direccion;
    AdapterCart adapter;
    private FusedLocationProviderClient fusedLocationClient;

    FirebaseUser fuser;
    TextView txtTotalPrice;

    Boolean modo = false;

    List<kreandoapp.mpclientes.pojo.Order> cart = new ArrayList<>();
    detectorInternet internet;

    private int minutos, hora, dia, mes, anio;

    String soloretirar = "";

    String opcion;
    String estadopago;
    TextView tv_mail;
    TextView tv_password;

    TextView tv_mitelefono;
    TextView tv_midireccion;
    TextView tv_datos;
    TextView tv_cabezera;
    TextView tv_cabezera_retira;
    TextView tv_countmail;
    TextView tv_total;
    TextView tv_envio;
    String adminlat, adminlon;
    String mail, pass, telefono_Admin, direccion_admin, ubicacion_admin,id_admin;
    TextView tv_info_total, tv_signo_peso;
    CardView card_precio_envio;
    public int total_delete = 0;
    String total_retirar;

    //Toolbar--............
    ImageButton botonvolver;
    TextView tv_titulo_toolbar;
    /// fin del toolbar----
    Button btn_add_more;

    Button btn_continuar;
    CardView ll_total2;

    private Retrofit retrofit;
    private RetrofitApi retrofitApi;
    private Disposable disposable;

    private JSONObject jsonCard;
    private static final String PUBLIC_KEY = "TEST-bddf87a4-3d9f-476d-9543-5e519b941ec2"; //reemplazar por su public key

    ProgressBar progres_precio;
    ProgressBar progress_total;

    CardView ll_descuento;

    boolean cardsist_envio = false;


    String telefonomp;
    String direccionmp;

    //Total contador....
    TextView tv_total_pedido,tv_total_pedido_desc;
    TextView tv_txt_total;

    String estadolocal;

    private Ordenes ordenes;
    List<Ordenes> ordenesList;
    List<Ordenes> ordenesListRoom;

    Double total = 0.0;
    Double total_de_pedido;
    String totalCondesc;
    String descuento_cupon;
    int descuento_porce;
    Double total_bd;
    int total_envio;
    int valor_envio_zona;

    String valor_rango;

    TextView tv_costo_envio;

    TextView tv_txt_total_desc;

    String cupon_utilizado;
    String modoCupon_utilizado;
    String promo_utilizada;
    Double latituduser,longituduser;

    TextView tv_titulo_desc,tv_condiciones;
    EditText et_dato_cupon;
    Button btn_consultar_cupon;
    LinearLayout ll_cupon_felicidades;


    TextView tv_linea1,tv_linea2;

    CardView card_envio;

    TextView tv_txt_costo;
    CardView cardmodozona;
    CardView card_modocupon;
    TextView tv_retirar_sucursal_cardzona;
    Boolean modo_zona = false;
    String seleccionado;
    String zonaenvio = "sinzona";
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(this, home1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
    }



    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
         ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/neufreu.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        setContentView(R.layout.activity_cart);
        cardmodozona = findViewById(R.id.cardmodozona);
        card_modocupon = findViewById(R.id.check_cupon);

        tv_retirar_sucursal_cardzona = findViewById(R.id.tv_retirar_sucursal_cardzona);

        final Spinner s1 = findViewById(R.id.id_spinner);
        DatabaseReference r2 = database.getReference("estadoModozona").child("estado");
        r2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if (val.equals("activo")) {
                    modo_zona=true;

                    cardmodozona.setVisibility(View.VISIBLE);
                    DatabaseReference r1 = database.getReference("modozona");
                    Query query = r1.orderByChild("orden");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            final List<String> areas = new ArrayList<String>();
                            for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                                String areaName = areaSnapshot.child("nombre").getValue(String.class);
                                int valor = areaSnapshot.child("valor").getValue(Integer.class);

                                if(valor ==1){
                                    areas.add(areaName);
                                }else {
                                    areas.add(areaName + " | valor s/" +(String.valueOf(valor)));


                                }




                            }



                            ArrayAdapter<String> areasAdapter = new ArrayAdapter<>(Carrito.this, android.R.layout.simple_spinner_item, areas);
                            areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s1.setAdapter(areasAdapter);
                            s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String item = parent.getSelectedItem().toString();
                                    if(item.equals("Selecciona una zona")){
                                        soloretirar="si";
                                        total_envio= 0;
                                        dametotal();
                                        seleccionado="nada";
                                    }else if(item.equals("Retirar sucursal")){
                                        seleccionado="retirar";
                                        s1.setVisibility(View.GONE);
                                        tv_retirar_sucursal_cardzona.setVisibility(View.VISIBLE);
                                        tv_retirar_sucursal_cardzona.setText("Retirar por sucursal");
                                        soloretirar="si";
                                        total_envio= 0;
                                       zonaenvio = "retirar_domicilio";
                                        dametotal();

                                    }else{
                                        String s = item;
                                        String s2 = s.substring(s.indexOf("/")+1);
                                        s2.trim();

                                       zonaenvio = s;
                                        total_envio = Integer.parseInt(s2);
                                        soloretirar="no";
                                        dametotal();
                                        seleccionado="valor";

                                        s1.setVisibility(View.GONE);
                                        tv_retirar_sucursal_cardzona.setVisibility(View.VISIBLE);
                                        tv_retirar_sucursal_cardzona.setText(item);
                                    }


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
                }else {
                    modo_zona=false;
                    cardmodozona.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference r24 = database.getReference("estadomodocupon").child("estado");
        r24.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if(val.equals("activo")){
                    card_modocupon.setVisibility(View.VISIBLE);
                }else {
                    card_modocupon.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        tv_txt_total_desc = findViewById(R.id.tv_txt_total_desc);
        tv_costo_envio = findViewById(R.id.tv_costo_envio);
        tv_txt_costo = findViewById(R.id.tv_txt_costo);

        card_envio = findViewById(R.id.card_envio_monto);

        total_envio = getIntent().getExtras().getInt("valor_envio");
        valor_rango = getIntent().getExtras().getString("valor_rango");

        if(total_envio >0){
            if(valor_rango.equals("d")){
                soloretirar="no";
                card_envio.setVisibility(View.VISIBLE);
                tv_txt_costo.setText("Costo de envio:");
                tv_costo_envio.setText("$"+total_envio);
            }else if(valor_rango.equals("f")){
                soloretirar="si";
                card_envio.setVisibility(View.VISIBLE);
                tv_costo_envio.setText("Sin envio, Solo retiro sucursal.");
            }

        }else {
            if(valor_rango.equals("n")){
                soloretirar="si";
                card_envio.setVisibility(View.GONE);
            }else if(valor_rango.equals("d")){
                soloretirar="no";
                card_envio.setVisibility(View.VISIBLE);
                tv_costo_envio.setText("Envio gratis!");
            }else if(valor_rango.equals("f")){
                card_envio.setVisibility(View.VISIBLE);
                soloretirar="si";
                tv_costo_envio.setText("Sin envio, Solo retiro sucursal.");

            }


        }


        ll_descuento = findViewById(R.id.ll_descuento);
        tv_titulo_desc = findViewById(R.id.tv_titulo_desc);
        tv_condiciones = findViewById(R.id.tv_condiciones);
        et_dato_cupon = findViewById(R.id.et_dato_cupon);
        ll_cupon_felicidades = findViewById(R.id.ll_cupon_felicidades);
        btn_consultar_cupon = findViewById(R.id.btn_consultar_cupon);
        tv_linea1 = findViewById(R.id.tv_linea1);
        tv_linea2 = findViewById(R.id.tv_linea2);
        tv_total_pedido_desc = findViewById(R.id.tv_total_pedido_desc);


        ll_total2 = findViewById(R.id.ll_total2);
        btn_consultar_cupon.setOnClickListener(v -> {
                String dato= et_dato_cupon.getText().toString();

                if(modo_zona){
                    if(seleccionado.equals("valor")){
                        if(TextUtils.isEmpty(dato)){
                            Toast.makeText(this, "Coloque algun codigo de cupon..", Toast.LENGTH_SHORT).show();
                        }else {

                            DatabaseReference myRef4 = database.getReference("Cupones").child(dato);
                            myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        String estado = dataSnapshot.child("estado").getValue(String.class);
                                        String id = dataSnapshot.child("id").getValue(String.class);
                                        String modoCupon = dataSnapshot.child("modo").getValue(String.class);
                                        int condicionCupon = dataSnapshot.child("condicion").getValue(Integer.class);
                                        int monto = dataSnapshot.child("monto").getValue(Integer.class);
                                        int porce = dataSnapshot.child("porcentaje").getValue(Integer.class);

                                        et_dato_cupon.setText("");
                                        if(estado.equals("activo")){
                                            if(porce > 0 ){
                                                DatabaseReference myRef4 = database.getReference("CupNocanceluso").child(id).child(user.getUid());
                                                myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if(dataSnapshot.exists()){
                                                            Toast.makeText(Carrito.this, "Cupon no disponible.", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Toast.makeText(Carrito.this, "Cupon porcentaje--p" + String.valueOf(porce), Toast.LENGTH_SHORT).show();
                                                            descuento_porce = porce;
                                                            // descuento_cupon=0;
                                                            et_dato_cupon.setVisibility(View.GONE);
                                                            btn_consultar_cupon.setVisibility(View.GONE);
                                                            ll_cupon_felicidades.setVisibility(View.VISIBLE);
                                                            promo_utilizada="promo_cupon";
                                                            cupon_utilizado = id;
                                                            modoCupon_utilizado = modoCupon;
                                                            String string = user.getDisplayName();
                                                            String[] parts = string.split(" ");
                                                            String part1 = parts[0]; // Primer nombre---
                                                            tv_linea1.setText("Felicidades "+ part1+" has obtenido un cupon");
                                                            tv_linea2.setText("con descuento del "+String.valueOf(descuento_porce)+"% en esta compra!");

                                                            dameCuponconDescporcentaje(porce);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                            }else {
                                                if(total_bd > condicionCupon){
                                                    DatabaseReference myRef4 = database.getReference("CupNocanceluso").child(id).child(user.getUid());
                                                    myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()){
                                                                Toast.makeText(Carrito.this, "Cupon no disponible,ya utilizado.", Toast.LENGTH_SHORT).show();
                                                            }else {
                                                                et_dato_cupon.setVisibility(View.GONE);
                                                                btn_consultar_cupon.setVisibility(View.GONE);
                                                                ll_cupon_felicidades.setVisibility(View.VISIBLE);
                                                                cupon_utilizado = id;
                                                                descuento_cupon = String.valueOf(monto);
                                                                promo_utilizada="promo_cupon";
                                                                modoCupon_utilizado = modoCupon;
                                                                String string = user.getDisplayName();
                                                                String[] parts = string.split(" ");
                                                                String part1 = parts[0]; // Primer nombre---
                                                                tv_linea1.setText("Felicidades "+ part1+" has obtenido un cupon");
                                                                tv_linea2.setText("con descuento de $"+descuento_cupon +" en esta compra!");


                                                                dameCuponconDescMonto(monto);
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });


                                                }else {
                                                    Toast.makeText(Carrito.this, "Condición cupon: pedido mayor a " + String.valueOf(condicionCupon) , Toast.LENGTH_LONG).show();

                                                }
                                            }


                                        }else {
                                            et_dato_cupon.setText("");
                                            Snackbar.make(v, "Cupon no disponible", Snackbar.LENGTH_LONG).show();
                                        }

                                    }else {
                                        et_dato_cupon.setText("");
                                        Snackbar.make(v, "Este cupon no existe", Snackbar.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    if(seleccionado.equals("retirar")){
                        if(TextUtils.isEmpty(dato)){
                            Toast.makeText(this, "Coloque algun codigo de cupon..", Toast.LENGTH_SHORT).show();
                        }else {

                            DatabaseReference myRef4 = database.getReference("Cupones").child(dato);
                            myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        String estado = dataSnapshot.child("estado").getValue(String.class);
                                        String id = dataSnapshot.child("id").getValue(String.class);
                                        String modoCupon = dataSnapshot.child("modo").getValue(String.class);
                                        int condicionCupon = dataSnapshot.child("condicion").getValue(Integer.class);
                                        int monto = dataSnapshot.child("monto").getValue(Integer.class);
                                        int porce = dataSnapshot.child("porcentaje").getValue(Integer.class);

                                        et_dato_cupon.setText("");
                                        if(estado.equals("activo")){
                                            if(porce > 0 ){
                                                DatabaseReference myRef4 = database.getReference("CupNocanceluso").child(id).child(user.getUid());
                                                myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if(dataSnapshot.exists()){
                                                            Toast.makeText(Carrito.this, "Cupon no disponible.", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Toast.makeText(Carrito.this, "Cupon porcentaje--p" + String.valueOf(porce), Toast.LENGTH_SHORT).show();
                                                            descuento_porce = porce;
                                                            // descuento_cupon=0;
                                                            et_dato_cupon.setVisibility(View.GONE);
                                                            btn_consultar_cupon.setVisibility(View.GONE);
                                                            ll_cupon_felicidades.setVisibility(View.VISIBLE);
                                                            promo_utilizada="promo_cupon";
                                                            cupon_utilizado = id;
                                                            modoCupon_utilizado = modoCupon;
                                                            String string = user.getDisplayName();
                                                            String[] parts = string.split(" ");
                                                            String part1 = parts[0]; // Primer nombre---
                                                            tv_linea1.setText("Felicidades "+ part1+" has obtenido un cupon");
                                                            tv_linea2.setText("con descuento del "+String.valueOf(descuento_porce)+"% en esta compra!");

                                                            dameCuponconDescporcentaje(porce);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                            }else {
                                                if(total_bd > condicionCupon){
                                                    DatabaseReference myRef4 = database.getReference("CupNocanceluso").child(id).child(user.getUid());
                                                    myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()){
                                                                Toast.makeText(Carrito.this, "Cupon no disponible.", Toast.LENGTH_SHORT).show();
                                                            }else {
                                                                et_dato_cupon.setVisibility(View.GONE);
                                                                btn_consultar_cupon.setVisibility(View.GONE);
                                                                ll_cupon_felicidades.setVisibility(View.VISIBLE);
                                                                cupon_utilizado = id;
                                                                descuento_cupon = String.valueOf(monto);
                                                                promo_utilizada="promo_cupon";
                                                                modoCupon_utilizado = modoCupon;
                                                                String string = user.getDisplayName();
                                                                String[] parts = string.split(" ");
                                                                String part1 = parts[0]; // Primer nombre---
                                                                tv_linea1.setText("Felicidades "+ part1+" has obtenido un cupon");
                                                                tv_linea2.setText("con descuento de $"+descuento_cupon +" en esta compra!");


                                                                dameCuponconDescMonto(monto);
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });


                                                }else {
                                                    Toast.makeText(Carrito.this, "Condición cupon: pedido mayor a " + String.valueOf(condicionCupon) , Toast.LENGTH_LONG).show();

                                                }
                                            }


                                        }else {
                                            et_dato_cupon.setText("");
                                            Snackbar.make(v, "Cupon no disponible", Snackbar.LENGTH_LONG).show();
                                        }

                                    }else {
                                        et_dato_cupon.setText("");
                                        Snackbar.make(v, "Este cupon no existe", Snackbar.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                    if(seleccionado.equals("nada")){
                        Toast.makeText(this, "seleccione una zona o retirar por sucursal", Toast.LENGTH_LONG).show();
                    }

                }


        });



        tv_total_pedido = findViewById(R.id.tv_total_pedido);
        tv_txt_total = findViewById(R.id.tv_txt_total);
        text_ubicacion = findViewById(R.id.tv_textubicacionurl);
        latituduser = Double.valueOf(getIntent().getExtras().getString("latitudUser"));
        longituduser = Double.valueOf(getIntent().getExtras().getString("longitudUser"));

        String ubicacion = "http://maps.google.com/maps?daddr="+latituduser+","+longituduser;

        text_ubicacion.setText(ubicacion);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Estadolocal").child("valor");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if(val.equals("abierto")){
                    estadolocal = "abierto";

                    btn_continuar.setVisibility(View.VISIBLE);
                }else {
                    btn_continuar.setVisibility(View.VISIBLE);
                    btn_continuar.setText("Negocio en modo cerrado");
                    estadolocal = "cerrado";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        dametotal();

        btn_continuar = findViewById(R.id.btn_continuar);
        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internet.estaConectado()){
                    if (estadolocal.equals("abierto")) {

                        if(zonaenvio.equals("sinzona")){
                            Toast.makeText(Carrito.this, "Seleccione una zona de envio", Toast.LENGTH_LONG).show();
                        }else {



                            Intent intent = new Intent(v.getContext(), DireccionActivity.class);
                            intent.putExtra("cupon_utilizado",cupon_utilizado);
                            intent.putExtra("promo_utilizada",promo_utilizada);
                            intent.putExtra("modo_cuponutilizado",modoCupon_utilizado);
                            intent.putExtra("descuento_cupon",String.valueOf(descuento_cupon));
                            intent.putExtra("soloretirar", soloretirar);
                            intent.putExtra("envio", String.valueOf(total_envio));
                            intent.putExtra("total", String.valueOf(total_de_pedido));
                            intent.putExtra("url_ubicacion_cliente", text_ubicacion.getText().toString());
                            intent.putExtra("telefono_admin", telefono_Admin);
                            intent.putExtra("id_admin", id_admin);
                            intent.putExtra("mailadmin", mail);
                            intent.putExtra("passadmin", pass);
                            intent.putExtra("direccion_admin", direccion_admin);
                            intent.putExtra("ubicacion_admin", ubicacion_admin);
                            intent.putExtra("zona_envio", zonaenvio);


                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    }else {
                        btn_continuar.setText("Negocio en modo cerrado");
                        Toast.makeText(Carrito.this, "El negocio se encuentra cerrado.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar snackbar = Snackbar.make(v, "No existe conexion a internet", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }




            }

        });
        retrofit = RetrofitClient.getInstance();
        retrofitApi = retrofit.create(RetrofitApi.class);
        progres_precio = findViewById(R.id.progres_precio);
        progress_total = findViewById(R.id.progress_total);



        database = FirebaseDatabase.getInstance();

        tv_mail = findViewById(R.id.tv_mail);
        tv_password = findViewById(R.id.tv_password);

        tv_mitelefono = findViewById(R.id.tv_mitelefono);
        tv_midireccion = findViewById(R.id.tv_midireccion);
        tv_datos = findViewById(R.id.tv_datos);
        tv_cabezera = findViewById(R.id.tv_cabezera);
        tv_cabezera_retira = findViewById(R.id.tv_cabezera_retira);
        tv_countmail = findViewById(R.id.tv_countmail);
        tv_total = findViewById(R.id.tv_total);
        tv_envio = findViewById(R.id.tv_envio);
        tv_info_total = findViewById(R.id.tv_info_total);
        Boolean checkstock = false;

        int total = 0;
        //VIENDO OPCIONESS RESERVA!!



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
        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Carrito de compra");
        btn_add_more = findViewById(R.id.btn_add_more);
        btn_add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),home1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });



        //VIENDO OPCIONESS RESERVA!!



//VIENDO OPCIONESS!!





        internet = new detectorInternet(this);
        dameidadmin();



        fuser = FirebaseAuth.getInstance().getCurrentUser();
        tv_id_admin = findViewById(R.id.tv_id_admin);
        tv_telefono = findViewById(R.id.tv_telefono);

        tv_direccion = findViewById(R.id.tv_direccion);
        card_precio_envio = findViewById(R.id.card_precio_envio);



        //firebase
        database = FirebaseDatabase.getInstance();


        //init
        recyclerView = findViewById(R.id.rv_listCart);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        ordenes = new Ordenes();
        ordenesList = new ArrayList<>();

        checkstock();



        txtTotalPrice = findViewById(R.id.total);

        int count = AppDb.getAppDb(getApplicationContext()).ordenesDao().dameTodoCount();
        if(count ==0){
            Intent intent = new Intent(getApplicationContext(),home1.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish();
            Toast.makeText(this, "El carrito esta vacio.", Toast.LENGTH_SHORT).show();
        }else {
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION);
            if(permissionCheck ==0){

                damemodoadmin();
            }else {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
            }

        }

        for (Ordenes ord : ordenesList){


            Double precio = Double.parseDouble(ord.getPrecio());
            int cant = Integer.parseInt(ord.getCantidad());
            //total += precio * cant;
            //txtTotalPrice.setText(String.valueOf(total));
            //txtTotalPrice.setVisibility(View.VISIBLE);
            Double subtotal = precio * cant;

            tv_datos.append(" <tr style='background: white'> <td style='padding: 5px'></td><td style='padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px'>x" + ord.getCantidad() + " " + ord.getNombre() + "</td>" +
                    "<td style='padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center'>$" + subtotal + "</td>" +
                    "<td style='padding: 5px;background: white'></td></tr>");

        }






    }//fin oncreate!

    private void dameCuponconDescMonto(int monto) {
        ll_descuento.setVisibility(View.GONE);
        List<Ordenes> lista = AppDb.getAppDb(getApplicationContext()).ordenesDao().dametodaslasOrdenes();

        total =0.0;
        for (Ordenes ord : lista) {

            Locale locale = new Locale("es", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            Double precio = Double.parseDouble(ord.getPrecio());
            int cant = Integer.parseInt(ord.getCantidad());
            total += precio * cant;

            if(total_envio>0){
                tv_txt_total_desc.setText("Total con desc. + envio:");
            }else {
                tv_txt_total_desc.setText("Total con desc:");
            }

            total_de_pedido = total - monto+total_envio;
            tv_txt_total.setText("Antes:");
            ll_total2.setVisibility(View.VISIBLE);
            String text = "<strike><font color=\'#757575\'>" + String.valueOf(fmt.format(total+total_envio)) + "</font></strike>";
            tv_total_pedido_desc.setText(String.valueOf(fmt.format(total - monto + total_envio)));
            tv_total_pedido.setText(Html.fromHtml(text));
            descuento_cupon = String.valueOf(monto);

        }
    }

    private void dameCuponconDescporcentaje(int porce) {
        ll_descuento.setVisibility(View.GONE);
        List<Ordenes> lista = AppDb.getAppDb(getApplicationContext()).ordenesDao().dametodaslasOrdenes();

        total =0.0;

        for (Ordenes ord : lista) {

            if(total_envio>0){
                tv_txt_total_desc.setText("Total con desc. + envio:");
            }else {
                tv_txt_total_desc.setText("Total con desc:");
            }

            Locale locale = new Locale("es", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            Double precio = Double.parseDouble(ord.getPrecio());
            Double cant = Double.parseDouble(ord.getCantidad());
            total += precio * cant;

            Double descuento = (porce * total / 100 );
            total_de_pedido = total-descuento+total_envio;
            tv_txt_total.setText("Antes:");
            ll_total2.setVisibility(View.VISIBLE);
            String text = "<strike><font color=\'#757575\'>"+String.valueOf(fmt.format(total+total_envio))+"</font></strike>";
            tv_total_pedido_desc.setText(String.valueOf(fmt.format(total-descuento+total_envio)));
            tv_total_pedido.setText(Html.fromHtml(text));
            descuento_cupon = String.valueOf(descuento);
        }





    }

    private void checkstock() {
        ordenesList = AppDb.getAppDb(getApplicationContext()).ordenesDao().dametodaslasOrdenes();
        for(Ordenes ordlist : ordenesList){
            DatabaseReference ref_prod = database.getReference("Productos").child(ordlist.getIdproducto()).child("prodStock");
            ref_prod.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String val = dataSnapshot.getValue(String.class);
                    if(dataSnapshot.exists()){
                        assert val != null;
                        if(val.equals("no")) {
                            AppDb.getAppDb(getApplicationContext()).ordenesDao().borrarxidString(ordlist.getIdproducto());
                            checkstock();
                            dametotal();
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        adapter = new AdapterCart(ordenesList, Carrito.this, Carrito.this);
        recyclerView.setAdapter(adapter);
    }



    private void dametotal() {

            List<Ordenes> lista = AppDb.getAppDb(getApplicationContext()).ordenesDao().dametodaslasOrdenes();

            total =0.0;
            total_bd= 0.0;
            for (Ordenes ord : lista){

                Locale locale = new Locale("es","PE");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                Double precio = Double.parseDouble(ord.getPrecio());
                Double cant =Double.parseDouble(ord.getCantidad());
                total += precio * cant;




                    DatabaseReference myRef4 = database.getReference("Users").child(user.getUid()).child("countpedidos");
                    myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int val = dataSnapshot.getValue(Integer.class);

                            DatabaseReference myRef4 = database.getReference("promo1");
                            myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        int maxdes = dataSnapshot.child("maxdesc").getValue(Integer.class);
                                        String estado = dataSnapshot.child("estado").getValue(String.class);
                                        int descuento = dataSnapshot.child("descuento").getValue(Integer.class);
                                        int condicion = dataSnapshot.child("condicion").getValue(Integer.class);
                                        int porce = dataSnapshot.child("porcentaje").getValue(Integer.class);

                                        if(estado.equals("activo") ){
                                            if(porce >0){
                                                if( val <= maxdes){
                                                    promo_utilizada="promo_usuario_nuevo";

                                                    if(total_envio>0){
                                                        tv_txt_total_desc.setText("Total con desc. + envio:");
                                                    }else {
                                                        tv_txt_total_desc.setText("Total con desc:");
                                                    }

                                                    ll_descuento.setVisibility(View.VISIBLE);
                                                    tv_titulo_desc.setText("Descuento primeras " + maxdes + " compras!");
                                                    tv_condiciones.setText("Felicidades has obtenido un descuento de: "+porce+"% en tus primeros " + maxdes + " compras");

                                                    tv_total_pedido.setText(String.valueOf(fmt.format(total)));
                                                    tv_txt_total.setText("Antes:");
                                                    Double descuentoporc = (porce * total / 100 );
                                                    Double condesc = total - descuentoporc;
                                                    total_de_pedido = condesc;
                                                    descuento_cupon = String.valueOf(descuentoporc);
                                                    modoCupon_utilizado="promo_usuario_nuevo";
                                                    total_bd = condesc+total_envio;
                                                    tv_total_pedido.setVisibility(View.VISIBLE);
                                                    tv_total_pedido_desc.setText(String.valueOf(fmt.format(condesc+total_envio)));
                                                    String text = "<strike><font color=\'#757575\'>"+String.valueOf(fmt.format(total+total_envio))+"</font></strike>";
                                                    tv_total_pedido.setText(Html.fromHtml(text));
                                                    ll_total2.setVisibility(View.VISIBLE);

                                                    animaciontotal();

                                                }



                                            }else {
                                                if(total > condicion && val <= maxdes){
                                                    if(total_envio>0){
                                                        tv_txt_total_desc.setText("Total con desc. + envio:");
                                                    }else {
                                                        tv_txt_total_desc.setText("Total con desc:");
                                                    }


                                                    modoCupon_utilizado="promo_usuario_nuevo";
                                                    promo_utilizada="promo_usuario_nuevo";
                                                    Double condesc = total - descuento;

                                                    ll_descuento.setVisibility(View.VISIBLE);
                                                    tv_titulo_desc.setText("Descuento primeras " + maxdes + " compras!");
                                                    tv_condiciones.setText("Condicion monto mayor a $"+condicion + " descuento de $"+descuento);
                                                    tv_total_pedido.setVisibility(View.VISIBLE);

                                                    tv_txt_total.setText("Antes:");
                                                    total_de_pedido = condesc+total_envio;
                                                    descuento_cupon = String.valueOf(descuento);
                                                    total_bd = total;


                                                    tv_total_pedido_desc.setText(String.valueOf(fmt.format(condesc+total_envio)));
                                                    String text = "<strike><font color=\'#757575\'>"+String.valueOf(fmt.format(total+total_envio))+"</font></strike>";
                                                    tv_total_pedido.setText(Html.fromHtml(text));
                                                    ll_total2.setVisibility(View.VISIBLE);

                                                    animaciontotal();

                                                }else {
                                                    if(total_envio>0){
                                                        tv_txt_total.setText("Total + envio:");
                                                    }else {
                                                        tv_txt_total.setText("Total:");
                                                    }



                                                    tv_total_pedido.setVisibility(View.VISIBLE);
                                                    ll_total2.setVisibility(View.GONE);
                                                    ll_descuento.setVisibility(View.GONE);
                                                    ll_cupon_felicidades.setVisibility(View.GONE);

                                                    tv_total_pedido.setText(String.valueOf(fmt.format(total+total_envio)));
                                                    total_de_pedido = total+total_envio;
                                                    total_bd = total+total_envio;

                                                    animaciontotal();
                                                }
                                            }



                                        }else {
                                            if(total_envio > 0){
                                                tv_txt_total.setText("Total + envio:");
                                            }else {
                                                tv_txt_total.setText("Total:");
                                            }



                                           tv_total_pedido.setVisibility(View.VISIBLE);

                                            ll_total2.setVisibility(View.GONE);
                                            ll_descuento.setVisibility(View.GONE);
                                            ll_cupon_felicidades.setVisibility(View.GONE);

                                            tv_total_pedido.setText(String.valueOf(fmt.format(total+total_envio)));
                                            total_de_pedido = total+total_envio;
                                            total_bd = total+total_envio;
                                            animaciontotal();
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });









        }

    }
    private void animaciontotal(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.precio_total_anim);
        tv_total_pedido.startAnimation(animation);
    }



    private void damemodoadmin() {

        DatabaseReference myRef3 = database.getReference();
        Query query2 = myRef3.child("modoadmin");
        query2.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    id_admin = dataSnapshot.child("id").getValue(String.class);
                    ubicacion_admin = dataSnapshot.child("ubicacion").getValue(String.class);
                    direccion_admin = dataSnapshot.child("direccion").getValue(String.class);
                    telefono_Admin = dataSnapshot.child("telefono").getValue(String.class);
                    mail = dataSnapshot.child("mail").getValue(String.class);
                    pass = dataSnapshot.child("password").getValue(String.class);
                    adminlat = dataSnapshot.child("latitud").getValue(String.class);
                    adminlon = dataSnapshot.child("longitud").getValue(String.class);

                    //Toast.makeText(Carrito.this, mail, Toast.LENGTH_SHORT).show();



                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }




    private  void dameidadmin(){

        DatabaseReference myRef3 = database.getReference();
        Query query2 = myRef3.child("modoadmin").child("id");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = dataSnapshot.getValue(String.class);
                tv_id_admin.setText(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                Intent b= new Intent(this, home1.class);
                startActivity(b);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void AccionAgregar(String id, String titulo, String precio) {

    }

    @Override
    public void accionBorrar(int id,int p) {

        Ordenes ordenes = new Ordenes();
        ordenes.setId(id);
        AppDb.getAppDb(getApplicationContext()).ordenesDao().borrarxid(ordenes);
        ordenesList.remove(p);
        recyclerView.removeViewAt(p);
        adapter.notifyItemRemoved(p);
        adapter.notifyItemRangeChanged(p,ordenesList.size());

        et_dato_cupon.setVisibility(View.VISIBLE);
        btn_consultar_cupon.setVisibility(View.VISIBLE);
        ll_cupon_felicidades.setVisibility(View.GONE);
        ll_total2.setVisibility(View.GONE);
        tv_total_pedido.setVisibility(View.GONE);


       dametotal();
        
        int count = AppDb.getAppDb(getApplicationContext()).ordenesDao().dameTodoCount();
        if(count == 0){
            finish();
            Intent intent = new Intent(this,home1.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

    }



    private class SendMail extends AsyncTask<Message,String,String> {



        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return  "success";

            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }

        }


    }

}