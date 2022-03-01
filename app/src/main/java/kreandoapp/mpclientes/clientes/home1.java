package kreandoapp.mpclientes.clientes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import kreandoapp.mpclientes.ContactosRVActivity;
import kreandoapp.mpclientes.Crear_nodo;
import kreandoapp.mpclientes.Notifications.Token;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.AdapterCajas;
import kreandoapp.mpclientes.adapter.AdapterCategoria;
import kreandoapp.mpclientes.adapter.AdapterNodos;
import kreandoapp.mpclientes.adapter.SliderAdapterExample;
import kreandoapp.mpclientes.clientes.ProcesoVenta.DetectandoUbicacionActivity;
import kreandoapp.mpclientes.clientes.ProcesoVenta.DireccionActivity;
import kreandoapp.mpclientes.continuar_nodo;
import kreandoapp.mpclientes.db.database.AppDb;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.loginapp.MainActivity;
import kreandoapp.mpclientes.modoadmin.ModoAdmin;
import kreandoapp.mpclientes.pojo.Categoria;
import kreandoapp.mpclientes.pojo.Coctelpojo;
import kreandoapp.mpclientes.pojo.ModeloCaja;
import kreandoapp.mpclientes.pojo.ModeloLastNodo;
import kreandoapp.mpclientes.pojo.ModeloNodo;
import kreandoapp.mpclientes.pojo.SliderItem;
import kreandoapp.mpclientes.pojo.User;
import kreandoapp.mpclientes.pojo.modelo_prod;
import kreandoapp.mpclientes.pojo.pojo_productos;
import kreandoapp.mpclientes.volley.claseSendVolleyFCM;


public class home1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
    //checando estado login.----
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    ///------fin login
    Animation fromsmall;

    LinearLayout linearload;



    ImageButton btn_modoadmin;
    detectorInternet internet;
    ProgressBar loadrv;
    TextView tv_count;

    TelephonyManager manager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();



    String nombreTxt, direccionTxt, telefonoTxt;
    private FusedLocationProviderClient fusedLocationClient;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    int version;
    FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();


    SliderView imageSlider;

    CardView card_slider;

    ArrayList<SliderItem> lista_slider;
    Button btn;

    Button btn_crearNodo,btn_continuarNodo,btn_error_nodo;

    private DatabaseReference categoriaref;
    private DatabaseReference lastnodoref;
    String resultado_estado;
    Boolean lectura_estado = false;
    TextView tv_mensaje;
    CardView card_texto;


    RecyclerView rv_nodos;
    AdapterNodos adapter2;
    private LinearLayoutManager mLayoutManager;
    ArrayList<ModeloNodo> nodosArrayList;

    String tiempo_t;
    String nombre_supervisor_home;
    String id_supervisor_home;
    String nombre_nodo_home;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String currentUserID = user.getUid();

            // Use currentUserID
        } else {
            gologing();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/neufreu.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_home1);

        categoriaref = FirebaseDatabase.getInstance().getReference().child("Nodos");
        lastnodoref = FirebaseDatabase.getInstance().getReference().child("LastNodo");

        tv_mensaje = findViewById(R.id.tv_mensaje);
        card_texto = findViewById(R.id.card_texto);

        btn_crearNodo = findViewById(R.id.btn_crearnodo);
        btn_crearNodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Crear_nodo.class);
                startActivity(i);
                finish();
                
            }
        });

        linearload = findViewById(R.id.linearload);
        
        //probandofechas();
        
        cargarNodos();
        btn_continuarNodo = findViewById(R.id.btn_continuarnodo);
        btn_error_nodo = findViewById(R.id.btn_error_nodo);

        DatabaseReference myRef552 = database.getReference();
        Query query = myRef552.child("LastNodo").child(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String id_last= dataSnapshot.child("last_id").getValue(String.class);



                    DatabaseReference myRef552 = database.getReference();
                    assert id_last != null;
                    Query query = myRef552.child("Nodos").child(id_last);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){
                                rv_nodos.setVisibility(View.VISIBLE);
                                linearload.setVisibility(View.GONE);
                                //---------
                                String estado = dataSnapshot.child("estado").getValue(String.class);
                                String notificacion1= dataSnapshot.child("notificacion1").getValue(String.class);
                                String notificacion2= dataSnapshot.child("notificacion2").getValue(String.class);
                                String autorizacion1= dataSnapshot.child("autorizacion1").getValue(String.class);
                                String autorizacion2= dataSnapshot.child("autorizacion2").getValue(String.class);
                                String nombre_nodo= dataSnapshot.child("nombreNodo").getValue(String.class);
                                String nombre_supervisor= dataSnapshot.child("nombre_supervisor").getValue(String.class);
                                String id_supervisor= dataSnapshot.child("id_supervisor").getValue(String.class);
                                String idnodo= dataSnapshot.child("idnodo").getValue(String.class);
                                String inicio_nodo= dataSnapshot.child("inicio_nodo_time").getValue(String.class);

                                nombre_supervisor_home = nombre_supervisor;
                                id_supervisor_home = id_supervisor;
                                nombre_nodo_home = nombre_nodo;

                                if(notificacion1.equals("si") && autorizacion1.equals("no") && estado.equals("trabajando")){

                                    card_texto.setVisibility(View.VISIBLE);
                                    tv_mensaje.setText("Esperando autorización de supervisor por creación Nodo: "+nombre_nodo);
                                    btn_continuarNodo.setVisibility(View.GONE);
                                    btn_error_nodo.setVisibility(View.VISIBLE);
                                }
                                if(notificacion1.equals("si") && autorizacion1.equals("ok") && estado.equals("trabajando")){
                                    btn_continuarNodo.setText("Continuar nodo: " + nombre_nodo);
                                    btn_continuarNodo.setVisibility(View.VISIBLE);
                                    card_texto.setVisibility(View.GONE);
                                    btn_error_nodo.setVisibility(View.GONE);

                                    btn_continuarNodo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent i = new Intent(getApplicationContext(), continuar_nodo.class);

                                            v.getContext().startActivity(i);



                                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);


                                            SharedPreferences.Editor myEdit = sharedPreferences.edit();


                                            myEdit.putString("idnodo", idnodo);
                                            myEdit.putString("nombre_nodo", nombre_nodo);
                                            myEdit.putString("id_supervisor", id_supervisor);
                                            myEdit.putString("nombre_supervisor", nombre_supervisor);
                                            myEdit.putString("nombre_supervisor", nombre_supervisor);
                                            myEdit.putString("inicio_nodo", inicio_nodo);


                                            myEdit.commit();
                                        }
                                    });
                                }

                                if(notificacion2.equals("si") && autorizacion2.equals("no") && estado.equals("finalizado") ){
                                    card_texto.setVisibility(View.VISIBLE);
                                    tv_mensaje.setText("Esperando autorización de supervisor por Nodo finalizado");
                                    btn_continuarNodo.setVisibility(View.GONE);
                                    btn_error_nodo.setVisibility(View.GONE);
                                }

                                if(notificacion2.equals("si") && autorizacion2.equals("ok") && estado.equals("finalizado") ){
                                    card_texto.setVisibility(View.GONE);
                                    btn_crearNodo.setVisibility(View.VISIBLE);
                                    tv_mensaje.setVisibility(View.GONE);
                                    btn_continuarNodo.setVisibility(View.GONE);
                                    btn_error_nodo.setVisibility(View.GONE);
                                }
                                //---------
                            }else{
                                card_texto.setVisibility(View.GONE);
                                btn_crearNodo.setVisibility(View.VISIBLE);
                                tv_mensaje.setVisibility(View.GONE);
                                btn_error_nodo.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else {
                    btn_crearNodo.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_error_nodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrardialog_error_nodo();
            }
        });





        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            nombreTxt = parametros.getString("nombre");
            telefonoTxt = parametros.getString("telefono");
            direccionTxt = parametros.getString("direccion");
            userunico();
        }else {
            userface();
        }


        //Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();

        tv_count = findViewById(R.id.textview_count);


        FirebaseMessaging.getInstance().subscribeToTopic("grupopromo")
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        Toast.makeText(home1.this, "suscrito al tema grupo promo", Toast.LENGTH_SHORT).show();
                    }


                });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fromsmall = AnimationUtils.loadAnimation(this, R.anim.fromsmall);

        FirebaseMessaging.getInstance().subscribeToTopic("grupopromo");

        loadrv = findViewById(R.id.load_rv);


        datoidUnico();

        //datosobligatorioapp();
        botonadmin();
        updateToken(FirebaseInstanceId.getInstance().getToken());




        //fin creando user unico


        //actualizar tokem


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FirebaseMessaging.getInstance().subscribeToTopic("grupopromo").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Toast.makeText(home1.this, "Suscrito al tema", Toast.LENGTH_SHORT).show();
            }
        });



        internet = new detectorInternet(this);

        btn_modoadmin = findViewById(R.id.btn_modoadmin);

        btn_modoadmin.setOnClickListener(v -> {

            if (internet.estaConectado()) {
                Intent i1 = new Intent(home1.this, ModoAdmin.class);
                startActivity(i1);
            } else {
                Toast.makeText(this, "No existe conexion a internet", Toast.LENGTH_SHORT).show();
            }


        });




        





        damecount();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        TextView nav_user = hView.findViewById(R.id.tv_nombre_completo);
        assert user != null;
        nav_user.setText(user.getDisplayName());

        TextView tv_mail = hView.findViewById(R.id.tv_mail);
        tv_mail.setText(user.getEmail());

        CircleImageView img_profile = hView.findViewById(R.id.img_profile);
        Picasso.with(this).load(user.getPhotoUrl()).into(img_profile);



        navigationView.setNavigationItemSelectedListener(this);

        cargarNodos();

    }//fin del oncreate!

    private void probandofechas()  {

        String myDate1 = "2022/10/28 07:00:45";
        String myDate2 = "2022/10/29 23:15:45";
//creates a formatter that parses the date in the given format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        Date date2 = null;
        try {
            date = sdf.parse(myDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = sdf.parse(myDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long timeInMillis1= date.getTime();
        long timeInMillis2 = date2.getTime();





        long segsMilli = 1000;
        long minsMilli = segsMilli * 60;
        long horasMilli = minsMilli * 60;
        long diasMilli = horasMilli * 24;

        Long diferencia = timeInMillis2 - timeInMillis1;


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

                int minutos = (int) (  minutosTranscurridos - horasTranscurridos * 60  );

                tiempo_t = horasTranscurridos + ":" + minutos+"hs";


            }

        }else{

            if(diasTranscurridos==1){
                int minutos = (int) (  minutosTranscurridos - horasTranscurridos * 60 );
                int horas = (int) (  horasTranscurridos - (diasTranscurridos * 24) );
                tiempo_t = diasTranscurridos +" dia y " + horas +":"+ minutos+"hs" ;
            }else{
                tiempo_t = diasTranscurridos +" dias y " + horasTranscurridos+":"+minutosTranscurridos+"hs";
            }
        }

        Toast.makeText(getApplicationContext(), tiempo_t, Toast.LENGTH_SHORT).show();
    }


    private void cargarNodos() {

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv_nodos =  findViewById(R.id.rv_nodos);
        rv_nodos.setLayoutManager(mLayoutManager);

        // MOSTRAR RECYCLER VIEW

        nodosArrayList = new ArrayList<>();
        adapter2 = new AdapterNodos(nodosArrayList,this);
        rv_nodos.setAdapter(adapter2);

        cargarRV();
    }

    private void cargarRV() {

        FirebaseDatabase database22 = FirebaseDatabase.getInstance();
        DatabaseReference myRef33 = database22.getReference();
        Query query4 = myRef33.child("Nodos").orderByChild("id_usuario").equalTo(user.getUid());

        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    nodosArrayList.removeAll(nodosArrayList);
                    rv_nodos.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot :  dataSnapshot.getChildren()) {

                        ModeloNodo prod = snapshot.getValue(ModeloNodo.class);

                        if(prod.getAutorizacion2().equals("ok")){
                            nodosArrayList.add(prod);
                        }




                    }



                    adapter2.notifyDataSetChanged();


                }else {
                    Toast.makeText(getApplicationContext(), "No hay Nodos.", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void pasatodoadouble() {
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database2.getReference("Productos");

       myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   double doble = Double.parseDouble("3.50");
                   modelo_prod req = snapshot.getValue(modelo_prod.class);
                   myRef3.child(req.getProdId()).child("prodPrecio").setValue("3.50");

               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    private void notificarsupervisor_error_nodo(String idsuper,String nodo) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(idsuper).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_sinfoto("Usuario "+ user.getEmail() +"","necesita cancelar el nodo: "+nodo,token,"modoadmin");
                    Toast.makeText(getApplicationContext(), "Supervisor notificado", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void userface() {
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database2.getReference();
        Query query2 = myRef3.child("Users");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                   // Toast.makeText(home1.this, user.getEmail(), Toast.LENGTH_SHORT).show();

                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef3 = database2.getReference();
                    Query query2 = myRef3.child("Users").child(user.getUid());
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                            }else {
                                FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                assert user != null;
                                User uu = new User(user.getUid(),user.getDisplayName(),String.valueOf(user.getPhotoUrl()),
                                        "","",1,user.getEmail(),"","","");
                                DatabaseReference myRef3 = database2.getReference("Users").child(user.getUid());
                                myRef3.setValue(uu);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                    assert user != null;
                    User uu = new User(user.getUid(),user.getDisplayName(),String.valueOf(user.getPhotoUrl()),
                            "","",1,user.getEmail(),"","","");
                    DatabaseReference myRef3 = database2.getReference("Users").child(user.getUid());
                    myRef3.setValue(uu);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void mostrardialog(String url,String version) {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(home1.this);
        View view = getLayoutInflater().inflate(R.layout.dialogupdate,null);

        TextView tv_version = view.findViewById(R.id.tv_version);
        Button btn_cancelar = view.findViewById(R.id.btn_cancelar);
        Button btn_actualizar = view.findViewById(R.id.btn_actualizar);
        tv_version.setText(version);

        mbuilder.setCancelable(false);
        mbuilder.setView(view);
        AlertDialog dialog = mbuilder.create();
        dialog.show();

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent abrirurl = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
              startActivity(abrirurl);
            }
        });
    }

    private void datoidUnico() {
        String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        DatabaseReference myRef4 = database.getReference("idTelefono").child(m_androidId);
        myRef4.child(m_androidId).child("id").setValue(m_androidId);

    }


    private void botonadmin() {
        assert user != null;
        DatabaseReference myRef22 = database.getReference("modoadmin").child("id");
        myRef22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String val = dataSnapshot.getValue(String.class);

                    if (val.equals(user.getUid())) {

                        btn_modoadmin.setVisibility(View.VISIBLE);

                    } else {
                        //btn_modoadmin.setVisibility(View.VISIBLE);
                        btn_modoadmin.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void datosobligatorioapp() {
        DatabaseReference myRef33 = database.getReference();
        assert user != null;
        Query query23 = myRef33.child("Users").child(user.getUid()).child("mitelefono");
        query23.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if (dataSnapshot.exists()) {
                    assert val != null;
                    if (val.equals("")) {
                        Intent i = new Intent(home1.this, misdatosobligatorios.class);
                        startActivity(i);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void userunico() {

        DatabaseReference myRef3 = database.getReference();
        Query query2 = myRef3.child("Users").child(user.getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    assert user != null;
                    User uu = new User(user.getUid(),nombreTxt,"default",direccionTxt,telefonoTxt,1,user.getEmail(),"","","");
                    DatabaseReference myRef3 = database.getReference("Users").child(user.getUid());
                    myRef3.setValue(uu);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nombreTxt)

                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(home1.this, "Nombre actualizado", Toast.LENGTH_SHORT).show();
                                }
                            });

                }else {

                        String nombre = dataSnapshot.child("username").getValue(String.class);
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nombre)

                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        //Toast.makeText(home1.this, "Nombre actualizado", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void damecount() {
        int count = AppDb.getAppDb(getApplicationContext()).ordenesDao().dameTodoCount();
        tv_count.setText(Integer.toString(count));

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(internet.estaConectado()){

            PackageInfo packageInfo;

            try {
                packageInfo = this.getPackageManager().getPackageInfo(getPackageName(),0);
                version = packageInfo.versionCode;


            }catch (Exception e){
                e.printStackTrace();
            }

            remoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(true).build());
            HashMap<String,Object> update = new HashMap<>();
            Task<Void> fetch = remoteConfig.fetch(0);
            fetch.addOnSuccessListener(this,Avoid ->{
                remoteConfig.activateFetched();
                version(version);
            });

        }else{

            Toast.makeText(this, "No existe conexion a internet", Toast.LENGTH_SHORT).show();
        }
        damecount();


        //tv_count.setText(new Database(this).getCountCart());
    }

    private void version(int version) {
        int nueva = (int) remoteConfig.getLong("versioncode");
        String web = remoteConfig.getString("weburl");
        String versionname = remoteConfig.getString("versionname");

        if(nueva > version){
            mostrardialog(web,versionname);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cerrar_sesion) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            gologing();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mi_direccion) {


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    //finish();
                    //Intent i = new Intent(home1.this, misdatos.class);
                    //startActivity(i);
                    Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
                }
            }, 200);

        }/* else if (id == R.id.cart) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    Intent i = new Intent(home1.this, DetectandoUbicacionActivity.class);
                    startActivity(i);
                }
            }, 200);


        } else if (id == R.id.mispedidos) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    Intent i = new Intent(home1.this, misPedidos.class);
                    startActivity(i);
                }
            }, 200);
        } else if (id == R.id.mispromos) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    Intent i = new Intent(home1.this, promociones.class);
                    startActivity(i);
                }
            }, 200);

        } else if (id == R.id.contacto)  {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    Intent i = new Intent(home1.this, ContactosRVActivity.class);
                    startActivity(i);
                }
            }, 200);


        }
    else if (id == R.id.preparaciones)  {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                Intent i = new Intent(home1.this, PreparaCoctelActivity.class);
                startActivity(i);
            }
        }, 200);


    }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(user2.getUid()).setValue(token1);
    }

    private void gologing() {
        Intent i = new Intent(home1.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void mostrardialog_error_nodo() {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(home1.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_error_en_nodo,null);

        TextView tv_nombre_nodo = view.findViewById(R.id.tv_nombre_nodo);
        TextView tv_texto = view.findViewById(R.id.tv_texto_dialog);

        tv_texto.setText("Seguro quieres Notificar al supervisor "+ nombre_supervisor_home +
                " por el nodo: "+ nombre_nodo_home+" creado por error?");

        tv_nombre_nodo.setText(nombre_nodo_home);
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
                notificarsupervisor_error_nodo(id_supervisor_home,nombre_nodo_home);
                dialog.dismiss();
                //Notificar a supervisor X
            }
        });
    }



}








