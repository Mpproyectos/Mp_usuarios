package kreandoapp.mpclientes.clientes.ProcesoVenta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bitvale.switcher.SwitcherX;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.PedidoRetirarActivity;
import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.clientes.pedidoenviadoActivity;
import kreandoapp.mpclientes.db.database.AppDb;
import kreandoapp.mpclientes.db.entity.Ordenes;
import kreandoapp.mpclientes.enviarmail.EnviarMail;
import kreandoapp.mpclientes.models.ExcludedPaymentMethod;
import kreandoapp.mpclientes.models.Item;
import kreandoapp.mpclientes.models.PagoDetalles;
import kreandoapp.mpclientes.models.Payer;
import kreandoapp.mpclientes.models.PaymentMethods;
import kreandoapp.mpclientes.models.ResponsePago;
import kreandoapp.mpclientes.pojo.Pedidos;
import kreandoapp.mpclientes.pojo.TrabajoxDia;
import kreandoapp.mpclientes.retrofit.RetrofitApi;
import kreandoapp.mpclientes.retrofit.RetrofitClient;
import kreandoapp.mpclientes.volley.claseSendVolleyFCM;
import retrofit2.Retrofit;
import io.github.inflationx.calligraphy3.CalligraphyConfig;

public class DireccionActivity extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener{
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference pedidosref,trabajoxdiaref;
    //TOOLBAR COOL
    ImageButton botonvolver;
    TextView tv_titulo_toolbar;
    //-------

    //DIRECCION Y TELEFONO..
    EditText et_direccion,et_telefono;
    //----
    //SWICHER FORMA DE ENVIO---
    SwitcherX swich_envio,swich_retiro;
    String estadoenvio = "delivery";
    String estadopago = "efectivo";
    SwitcherX swich_efectivo,swich_tarjeta;
    TextView tv_efectivo, tv_tarjeta;
    TextView tv_envio, tv_retirar;

    Button btn_continuar;

    private JSONObject jsonCard;
    private static final String PUBLIC_KEY = "APP_USR-63c5cd31-87b9-4e10-bd17-250b0abbf9ce"; //reemplazar por su public key
    private Disposable disposable;
    private Retrofit retrofit;
    private RetrofitApi retrofitApi;


    int total_int,envio_int;
    int monto_a_pagar;
    String totalcondescuento;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    EditText et_cancelarcon;
    String txt_cancelacon = "-";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(DireccionActivity.this, home1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    //DATOS PARA EL MAIL.
    TextView tv_datos,tv_cabezera;

    //TELEFONO MAIL.---

    String telefono,direccion;
    TextView tv_envio_txt,tv_total_envio,tv_total_direccion;

    String promo_utilizada;
    String descuento_cupon;
    String cupon_utilizado;
    String modo_cuponutilizado;
    String total_envio;

    Double total_d;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
         ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/neufreu.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_direccion);
        tv_datos = findViewById(R.id.tv_datos_d);
        tv_cabezera = findViewById(R.id.tv_cabezera_d);
        //DATOS DEL MAIL---->



        pedidosref = database.getReference("Pedidos");

        request = Volley.newRequestQueue(this);

        et_cancelarcon = findViewById(R.id.et_a_cancelar);

        et_cancelarcon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String result = s.toString().replaceAll(" ", "");
                String result2 = s.toString().replaceAll("", "");
                if (!s.toString().equals(result)) {
                    et_cancelarcon.setText(result);
                    et_cancelarcon.setSelection(result.length());
                    // alert the user
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final String envio = getIntent().getExtras().getString("envio");
        final String total = getIntent().getExtras().getString("total");
        final String promo_utilizadaD = getIntent().getExtras().getString("promo_utilizada");
        final String descuento_cuponD = getIntent().getExtras().getString("descuento_cupon");
        final String cupon_utilizadoD = getIntent().getExtras().getString("cupon_utilizado");
        final String modo_cuponutilizadoD = getIntent().getExtras().getString("modo_cuponutilizado");
        final String zona_envio = getIntent().getExtras().getString("zona_envio");

        final String id_admin = getIntent().getExtras().getString("id_admin");



        Toast.makeText(this, id_admin, Toast.LENGTH_SHORT).show();


        final String mail_admin = getIntent().getExtras().getString("mailadmin");
        //Toast.makeText(this, mail_admin, Toast.LENGTH_SHORT).show();

        total_envio = String.valueOf(envio);

        if(promo_utilizadaD != null){

            if(promo_utilizadaD.equals("promo_usuario_nuevo")){
                cupon_utilizado="promo_usuario_nuevo";
                descuento_cupon=descuento_cuponD;
                promo_utilizada = promo_utilizadaD;
                modo_cuponutilizado = "sin_modo";
                String totalConvertido = total;

                totalcondescuento =  String.valueOf(totalConvertido);
            }else {
                cupon_utilizado = cupon_utilizadoD;
                descuento_cupon= descuento_cuponD;
                promo_utilizada = promo_utilizadaD;
                modo_cuponutilizado = modo_cuponutilizadoD;
                String totalConvertido = (total);

                totalcondescuento = String.valueOf(totalConvertido);
            }


        }else {
            cupon_utilizado ="sin_cupon";
            descuento_cupon ="sin_descuento";
            promo_utilizada = "sin_promocion";
            totalcondescuento = String.valueOf(total);

            //Toast.makeText(this, "Ninguna Promo", Toast.LENGTH_SHORT).show();

        }




       // total_int = Integer.parseInt(total);

        monto_a_pagar = total_int;

        tv_envio_txt = findViewById(R.id.tv_envio_txt);
        tv_total_envio = findViewById(R.id.tv_total_envio);
        tv_total_direccion = findViewById(R.id.tv_total_direccion);

        if(Integer.parseInt(envio) > 0){
            tv_total_envio.setText(String.valueOf(envio));
            tv_total_direccion.setText(String.valueOf(totalcondescuento));
        }else {
            tv_total_direccion.setText(String.valueOf(total));
            tv_total_envio.setVisibility(View.GONE);
            tv_envio_txt.setVisibility(View.GONE);
        }



        retrofit = RetrofitClient.getInstance();
        retrofitApi = retrofit.create(RetrofitApi.class);


        botonvolver = findViewById(R.id.btn_volverAtras);



        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i = new Intent(DireccionActivity.this, home1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Dirección y envio: ");

        et_direccion = findViewById(R.id.et_direccion_d);
        animaciondireccion();
        et_telefono = findViewById(R.id.et_telefono_d);
        dameDireccionyTelefono();
        animaciontelefono();



        DatosPedido();
        dameswichestadoenvio();
        damemodopago();
        Cabezera();
        btn_continuar = findViewById(R.id.btn_continuar);
        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomtext);
                btn_continuar.startAnimation(animation);
                    if (estadopago.equals("tarjeta")) {
                        generarPago();
                        Toast.makeText(DireccionActivity.this, "Pagar con tarjeta", Toast.LENGTH_SHORT).show();
                    } else {

                        EnviarRegistrosAfirebaseymail(
                                et_direccion.getText().toString(),
                                et_telefono.getText().toString(),
                                "El cliente paga en el domicilio");


                        Toast.makeText(DireccionActivity.this, "Enviar Pedido", Toast.LENGTH_SHORT).show();

                    }

            }
        });



    }//fin del oncreate!

    private void enviarNotificacionalAdmin(String idadmin) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(idadmin).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_sinfoto("Nuevo Pedido","El cliente: "+user.getDisplayName()+" envio un pedido.",token,"modoadmin");
                    Toast.makeText(DireccionActivity.this, "Si notifique!!", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void generarPago() {


        List<Item> list = new ArrayList<>();//lista con datos de la venta
        Item item = new Item();
        item.setUnitPrice(Double.valueOf(totalcondescuento)); //precio unitario de producto
        //item.setUnitPrice(160.0); //precio unitario de producto

        item.setTitle("Pedido Disbos"); //titulo de la venta del producto
        item.setQuantity(1); //cantidad de productos a vender
        item.setDescription("descripcion del producto"); //descripcion del producto
        item.setCurrencyId("PEN"); //moneda de pais del precio


        list.add(item); //agregamos los detalles a la lista


        //codigo nuevo----------------------------------------

        List<ExcludedPaymentMethod> listexc = new ArrayList<>();
        ExcludedPaymentMethod exc = new ExcludedPaymentMethod();

        exc.setId("ticket");
        listexc.add(exc);


        PaymentMethods paymet = new PaymentMethods();
        paymet.setExcludedPaymentMethods(listexc);

        //codigo nuevo----------------------------------------

        Payer payer2 = new Payer(); //objeto con los datos de usuario comprador
        payer2.setEmail(user.getEmail()); //email del usuario comprador


        PagoDetalles pagoDetalles = new PagoDetalles(); //generamos el objeto para enviar a mercado pago
        pagoDetalles.setPayer(payer2);
        pagoDetalles.setExcludedPaymentMethod(exc);

        
        pagoDetalles.setItems(list);





        //generamos el id de la compra


        disposable = retrofitApi.obtenerDatosPago(pagoDetalles).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponsePago>() {
                    @Override
                    public void onNext(ResponsePago responsePago) {
                        //una vez generada el id de compra lo mandamos a mercado pago y se genera la ventana de tarjetas
                        new MercadoPagoCheckout.Builder(PUBLIC_KEY, responsePago.getId())

                                .build()

                                .startPayment(DireccionActivity.this, 12);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("aca", "error = " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {


                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //resultado de la compra
        if (requestCode == 12) {


            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                final Payment payment = (Payment) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT);//payment almacena los datos de la compra

                if (payment.getPaymentStatus().equals("approved")) {
                    
                    EnviarRegistrosAfirebaseymail(
                            et_direccion.getText().toString(),
                            et_telefono.getText().toString(),
                            "El pedido se encuentra pago con tarjeta");

                    Toast.makeText(this, "Pedido enviado!", Toast.LENGTH_SHORT).show();




                } else {
                    //compra no aprobada

                }
            } else if (resultCode == RESULT_CANCELED) {

                if (data != null && data.getExtras() != null
                        && data.getExtras().containsKey(MercadoPagoCheckout.EXTRA_ERROR)) {
                    //error en algun paso de mercado pago
                    final MercadoPagoError mercadoPagoError =
                            (MercadoPagoError) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR);
                    //el objeto mercadoPagoError contiene todos los datos de porque no se genero la venta

                } else {
                    //compra cancelada

                }
            }


        }


    }
    private void dameswichestadoenvio() {
        final String soloretirar = getIntent().getExtras().getString("soloretirar");

        tv_envio = findViewById(R.id.tv_txt_envio);
        tv_retirar = findViewById(R.id.tv_txt_retirar);
        swich_envio = findViewById(R.id.switcher_envio);
        swich_retiro = findViewById(R.id.switcher_retiro);



        if(soloretirar.equals("si")){
            estadoenvio = "retirar";

            swich_envio.setVisibility(View.GONE);
           tv_envio.setVisibility(View.GONE);
          swich_retiro.setChecked(true,false);
          swich_retiro.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  swich_retiro.setChecked(true,false);

              }
          });
        }

        swich_envio.setOnCheckedChangeListener(checked -> {
            if(checked){
                final String envio = getIntent().getExtras().getString("envio");
                if(Integer.parseInt(envio) >0){
                    final String total = getIntent().getExtras().getString("total");
                    //total_int = totalcondescuento;
                    totalcondescuento = (total);
                    tv_total_direccion.setText(String.valueOf(total));
                    tv_total_envio.setVisibility(View.VISIBLE);
                    tv_envio_txt.setVisibility(View.VISIBLE);
                    total_envio=String.valueOf(envio);
                    Toast.makeText(this, String.valueOf(totalcondescuento), Toast.LENGTH_SHORT).show();
                }else {
                    tv_total_direccion.setText(String.valueOf(total_int));

                }
                estadoenvio = "delivery";
                tv_envio.setTextColor(Color.parseColor("#03938d"));
                tv_retirar.setTextColor(Color.parseColor("#808080"));
                swich_envio.setChecked(true,true);
                swich_retiro.setChecked(false,true);

            }else {
                tv_retirar.setTextColor(Color.parseColor("#03938d"));
                tv_envio.setTextColor(Color.parseColor("#808080"));
                swich_envio.setChecked(false,true);
                swich_retiro.setChecked(true,true);
            }
            return null;}
        );

        swich_retiro.setOnCheckedChangeListener(checked -> {
            if(checked){
                final String envio = getIntent().getExtras().getString("envio");
                if(Integer.parseInt(envio) >0){
                    final String total = getIntent().getExtras().getString("total");

                    //total_int = Integer.parseInt(total) - Integer.parseInt(envio);
                    total_d =  Double.parseDouble(total)  - Integer.parseInt(envio);

                    totalcondescuento = String.valueOf(total_d);

                    tv_total_direccion.setText(String.valueOf(total_d));
                    tv_total_envio.setVisibility(View.GONE);
                    tv_envio_txt.setVisibility(View.GONE);
                    total_envio="0";

                }else {
                    final String envio2 = getIntent().getExtras().getString("envio");
                    final String total = getIntent().getExtras().getString("total");
                    int total_sin_envio = Integer.parseInt(total) - Integer.parseInt(envio2);
                    totalcondescuento = String.valueOf(total_sin_envio);

                    tv_total_direccion.setText(String.valueOf(totalcondescuento));


                }
                estadoenvio = "retirar";
                tv_retirar.setTextColor(Color.parseColor("#03938d"));
                tv_envio.setTextColor(Color.parseColor("#808080"));

                swich_retiro.setChecked(true,true);
                swich_envio.setChecked(false,true);


            }else {
                tv_envio.setTextColor(Color.parseColor("#03938d"));
                tv_retirar.setTextColor(Color.parseColor("#808080"));
                swich_retiro.setChecked(false,true);
                swich_envio.setChecked(true,true);
            }
            return null;}
        );
    }
    private void damemodopago() {
        tv_efectivo = findViewById(R.id.tv_efectivo);
        tv_tarjeta = findViewById(R.id.tv_tarjeta);

        swich_efectivo = findViewById(R.id.switcher_efectivo);
        swich_tarjeta = findViewById(R.id.switcher_tarjeta);


        swich_efectivo.setOnCheckedChangeListener(checked -> {
            if(checked){
                et_cancelarcon.setVisibility(View.VISIBLE);
                btn_continuar.setText("Enviar pedido");
                estadopago = "efectivo";
                tv_efectivo.setTextColor(Color.parseColor("#03938d"));
                tv_tarjeta.setTextColor(Color.parseColor("#808080"));
                swich_efectivo.setChecked(true,true);
                swich_tarjeta.setChecked(false,true);

            }else {
                tv_tarjeta.setTextColor(Color.parseColor("#03938d"));
                tv_efectivo.setTextColor(Color.parseColor("#808080"));
                swich_efectivo.setChecked(false,true);
                swich_tarjeta.setChecked(true,true);
            }
            return null;}
        );

        swich_tarjeta.setOnCheckedChangeListener(checked -> {
            if(checked){
                et_cancelarcon.setVisibility(View.GONE);
                estadopago = "tarjeta";
                btn_continuar.setText("Pagar con Tarjeta y enviar pedido");
                tv_tarjeta.setTextColor(Color.parseColor("#ff6600"));
                tv_efectivo.setTextColor(Color.parseColor("#808080"));

                swich_tarjeta.setChecked(true,true);
                swich_efectivo.setChecked(false,true);


            }else {
                tv_efectivo.setTextColor(Color.parseColor("#03938d"));
                tv_tarjeta.setTextColor(Color.parseColor("#808080"));
                swich_tarjeta.setChecked(false,true);
                swich_efectivo.setChecked(true,true);
            }
            return null;}
        );
    }

    private void dameDireccionyTelefono() {
        final DatabaseReference myRef4 = database.getReference();
        Query query4 = myRef4.child("Users").child(user.getUid());
        query4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String mitele = dataSnapshot.child("mitelefono").getValue(String.class);
                    et_telefono.setText(mitele);

                    String midire = dataSnapshot.child("midireccion").getValue(String.class);
                    et_direccion.setText(midire);

                    btn_continuar.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void actualizaDireccionyTelefono() {
        final DatabaseReference myRef4 = database.getReference("Users").child(user.getUid());
            myRef4.child("mitelefono").setValue(et_telefono.getText().toString());
            myRef4.child("midireccion").setValue(et_direccion.getText().toString());


    }
    private void animaciontelefono(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.precio_total_anim);
        et_telefono.startAnimation(animation);
    }
    private void animaciondireccion(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.precio_total_anim);
        et_direccion.startAnimation(animation);
    }

    private void DatosPedido(){
        List<Ordenes> ordenes = AppDb.getAppDb(getApplicationContext()).ordenesDao().dametodaslasOrdenes();
        for (Ordenes ord : ordenes){
            Double precio = Double.parseDouble(ord.getPrecio());
            Double cant = Double.parseDouble(ord.getCantidad());

            Double subtotal = precio * cant;

            /*tv_datos.append(
                    "<tr style='background: white'> <td style='padding: 5px'></td><td style='padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px'>x" + ord.getCantidad() + " " + ord.getNombre() +"<b>("+ ord.getCodprod()+ ")</b></td>" +
                    "<td style='padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center'>$" + subtotal + "</td>" +
                    "<td style='padding: 5px;background: white'></td></tr>");*/
            tv_datos.append("<tr style='background: white'>"+
                            "<td style='padding: 5px'></td>"+
                            "<td style='padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px'>x"
                            +ord.getCantidad()+" "+ord.getDetalle()+"<b>("+ord.getCodprod()+")</b></td>"+
                            "<td style='padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center'><b>s/</b>"+subtotal+"</td>"+
                            "<td style='padding: 5px;background: white'></td>"+
                            "</tr>");

        }
    }
    @SuppressLint("SetTextI18n")
    private void Cabezera(){
        tv_cabezera.setText("" +

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: #f8dfcd; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Nombre cliente:</b>" + user.getDisplayName() + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: #f8dfcd;padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Telefono:</b>" + et_telefono.getText().toString() + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: #f8dfcd;padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Dirección:</b>" + et_direccion.getText().toString() + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: #f8dfcd;padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Mail:</b>" + user.getEmail() + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"

        );
    }
    private void EnviarRegistrosAfirebaseymail(String direccion,String telefono,String pago){

        List<Ordenes> lista = AppDb.getAppDb(getApplicationContext()).ordenesDao().dametodaslasOrdenes();
        final String url_c = getIntent().getExtras().getString("url_ubicacion_cliente");
        final String envio = getIntent().getExtras().getString("envio");
        final String total = getIntent().getExtras().getString("total");
        final String telefono_admin = getIntent().getExtras().getString("telefono_admin");
        final String direccion_admin = getIntent().getExtras().getString("direccion_admin");
        final String ubicacion_admin = getIntent().getExtras().getString("ubicacion_admin");
        final String id_admin = getIntent().getExtras().getString("id_admin");
        final String mail_admin = getIntent().getExtras().getString("mailadmin");
        final String pass_admin = getIntent().getExtras().getString("passadmin");
        final String zona_envio = getIntent().getExtras().getString("zona_envio");

        //Enviando mensaje pedido y msj admin
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Calendar c = Calendar.getInstance();
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        txt_cancelacon = et_cancelarcon.getText().toString();


        trabajoxdia();

        if(modo_cuponutilizado!=null){

            if(modo_cuponutilizado.equals("sicancel")){
                confimandousocupon(cupon_utilizado);
            }else if(modo_cuponutilizado.equals("nocancel")){
                countcupon(cupon_utilizado);
                desactivar_cupon_solo_yo(cupon_utilizado);
            }
            else {
                Toast.makeText(this, "Modo user new", Toast.LENGTH_SHORT).show();
            }
        }


        countuser();

        //countpedidos



        DatabaseReference ref = database.getReference("countmail");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                int count = Integer.parseInt(val);
                ref.setValue(String.valueOf(count+1));
                String push = ref.push().getKey();

                Pedidos ped = new Pedidos(
                        estadoenvio,
                        push,
                        val,
                        user.getDisplayName(),
                        direccion,
                        totalcondescuento,
                        "En preparación",
                        dateFormat.format(c.getTime()),
                        timeFormat.format(c.getTime()),
                        "",
                        "",
                        user.getUid(),
                        url_c,
                        telefono,
                        val+timeFormat.format(c.getTime()),
                        "no",
                        total_envio,
                        pago,
                        cupon_utilizado,
                        promo_utilizada,
                        descuento_cupon,
                        zona_envio,
                        txt_cancelacon,
                        lista

                );



                EnviarMail sendmail = new EnviarMail();

                sendmail.MailTo_admin(
                        val,
                        estadoenvio,
                        pago,
                        Integer.parseInt(total_envio),
                        tv_datos.getText().toString(),
                        url_c,
                        String.valueOf(totalcondescuento),
                        user.getDisplayName(),
                        user.getEmail(),
                        telefono,
                        direccion,
                        mail_admin,
                        pass_admin,
                        promo_utilizada,
                        descuento_cupon,
                        cupon_utilizado,zona_envio,
                        txt_cancelacon

                        );

               sendmail.MailTo_usuario(
                       val,
                       estadoenvio,
                       total_envio,
                       tv_datos.getText().toString(),
                       String.valueOf(totalcondescuento),
                       user.getDisplayName(),
                       user.getEmail(),telefono_admin,
                       direccion_admin,ubicacion_admin,
                       pago,mail_admin,
                       pass_admin,
                       promo_utilizada,
                       descuento_cupon,
                       cupon_utilizado,zona_envio
               );




                actualizaDireccionyTelefono();
                pedidosref.child(push).setValue(ped);
                enviarNotificacionalAdmin(id_admin);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(estadoenvio.equals("delivery")){
            Intent intent = new Intent(DireccionActivity.this, pedidoenviadoActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(DireccionActivity.this, PedidoRetirarActivity.class);
            startActivity(intent);
        }

        //final String destinatarioadmin = tv_id_admin.getText().toString();

        AppDb.getAppDb(getApplicationContext()).ordenesDao().borrarTodasLasOrdenes();
        //enviarNotificacionalAdmin(destinatarioadmin);

        finish();
        //fin de enviando pedido y msj





    }

    private void desactivar_cupon_solo_yo(String idcupon) {
        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        DatabaseReference myRef22 = database111.getReference("CupNocanceluso").child(idcupon);
       myRef22.child(user.getUid()).setValue("cupon_usado");

    }



    private void countcupon(String id) {
        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        assert user != null;
        DatabaseReference myRef22 = database111.getReference("Cupones").child(id).child("count_uso");
        myRef22.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int valor = dataSnapshot.getValue(Integer.class);
                myRef22.setValue(valor+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void countuser() {
        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        assert user != null;
        DatabaseReference myRef22 = database111.getReference("Users").child(user.getUid()).child("countpedidos");
        myRef22.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = dataSnapshot.getValue(Integer.class);
                myRef22.setValue(count+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void confimandousocupon(String id) {
        if(id != null){
            FirebaseDatabase database111 = FirebaseDatabase.getInstance();
            assert user != null;
            DatabaseReference myRef22 = database111.getReference("Cupones").child(id);
            myRef22.child("estado").setValue("desactivado");
            myRef22.child("utilizado").setValue("si");
        }


    }

    private void trabajoxdia() {

        final Calendar c = Calendar.getInstance();
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        final SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");

        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        assert user != null;
        DatabaseReference myRef22 = database111.getReference("trabajoxDia").child(dateFormat.format(c.getTime()));
        myRef22.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    String Fconguion = dateFormat.format(c.getTime());
                    String FconParen = dateFormat2.format(c.getTime());
                    String Hconparent = timeFormat.format(c.getTime());
                    String estado = "<span class='badge badge-pill badge-danger'>sin pagar</span>";
                    TrabajoxDia tra = new TrabajoxDia(FconParen,estado,Fconguion,Hconparent);
                    myRef22.setValue(tra);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {



    }
}