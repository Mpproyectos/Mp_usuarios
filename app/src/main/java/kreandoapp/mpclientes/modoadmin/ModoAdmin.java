package kreandoapp.mpclientes.modoadmin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.modoadmin.Coctels.AgregarimgacoctelActivity;
import kreandoapp.mpclientes.modoadmin.Coctels.NuevoCoctelActivity;
import kreandoapp.mpclientes.modoadmin.Cupones.VertodoslosCuponesActivity;
import kreandoapp.mpclientes.modoadmin.Cupones.crear_cupon_admin;
import kreandoapp.mpclientes.modoadmin.Demoras.EditarDemorasActivity;
import kreandoapp.mpclientes.modoadmin.EditarCategoria.NuevaCategoriaActivity;
import kreandoapp.mpclientes.modoadmin.Envio.EditarCalcularEnvioActivity;
import kreandoapp.mpclientes.modoadmin.Envio.ModoCalcularenvioActivity;
import kreandoapp.mpclientes.modoadmin.Productos.NuevoSubCategoriaActivity;
import kreandoapp.mpclientes.modoadmin.Productos.nuevoProducto;
import kreandoapp.mpclientes.modoadmin.Promo.CrearpromoActivity;
import kreandoapp.mpclientes.modoadmin.Promo.PromoPrimerasComprasActivity;
import kreandoapp.mpclientes.modoadmin.Zonas.ModozonaActivity;
import kreandoapp.mpclientes.modoadmin.modoGPS.DetectGpsactualadminActivity;

public class ModoAdmin extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i= new Intent(this, home1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
    }
    detectorInternet internet;
    private Toolbar toolbar;
    Button btn_editarCategoriaProducto;
    Button btn_nuevaCategoria,btn_nuevoProducto,
            btn_verestadopedidos,btn_estadolocal,
            btn_crearpromo,btn_crearcupon,btn_Promo_primeras_c,
            btn_ver_cupones,btn_editar_demora,btn_nuevo_subcategoria,btn_modoGps;

    Button btn_estado_modo_envio,btn_editar_valores_envio,btn_modozona,btn_NuevoCoctel,btn_agregarimgAcoctel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_admin);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Modo Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        internet = new detectorInternet(this);

        btn_nuevaCategoria = findViewById(R.id.btn_nuevaCategoria);
        btn_nuevaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, NuevaCategoriaActivity.class);
                startActivity(i);
            }
        });

        btn_nuevoProducto = findViewById(R.id.btn_nuevoProducto);
        btn_nuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, nuevoProducto.class);
                startActivity(i);
            }
        });
        btn_verestadopedidos = findViewById(R.id.btn_verestadopedidos);
        btn_verestadopedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, todoslospedido.class);
                startActivity(i);
            }
        });
        btn_estadolocal = findViewById(R.id.btn_estadolocal);
        btn_estadolocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, modoestado.class);
                startActivity(i);
            }
        });



        btn_crearpromo = findViewById(R.id.btn_crearpromo);
        btn_crearpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, CrearpromoActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });


        btn_crearcupon = findViewById(R.id.btn_crearCupon);
        btn_crearcupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, crear_cupon_admin.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        btn_Promo_primeras_c = findViewById(R.id.btn_Promo_primeras_c);
        btn_Promo_primeras_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, PromoPrimerasComprasActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        btn_ver_cupones = findViewById(R.id.btn_ver_cupones);
        btn_ver_cupones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, VertodoslosCuponesActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        btn_editar_demora = findViewById(R.id.btn_editar_demora);
        btn_editar_demora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, EditarDemorasActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        btn_estado_modo_envio = findViewById(R.id.btn_estado_modo_envio);
        btn_estado_modo_envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, ModoCalcularenvioActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        btn_modozona = findViewById(R.id.btn_modozona);
        btn_modozona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, ModozonaActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        btn_nuevo_subcategoria = findViewById(R.id.btn_subcategoria);
        btn_nuevo_subcategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, NuevoSubCategoriaActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        btn_NuevoCoctel = findViewById(R.id.btn_NuevoCoctel);
        btn_NuevoCoctel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, NuevoCoctelActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        btn_agregarimgAcoctel = findViewById(R.id.   btn_agregarimgAcoctel);
        btn_agregarimgAcoctel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, AgregarimgacoctelActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        btn_modoGps = findViewById(R.id.btn_modoGps);
        btn_modoGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, DetectGpsactualadminActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        btn_editar_valores_envio = findViewById(R.id.btn_editar_modo_envio);
        btn_editar_valores_envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModoAdmin.this, EditarCalcularEnvioActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });


    }//fin oncreate



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
}
