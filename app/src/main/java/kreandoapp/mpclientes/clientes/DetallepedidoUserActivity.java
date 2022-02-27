package kreandoapp.mpclientes.clientes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.adapter.DetalleproductosUserAdapter;
import kreandoapp.mpclientes.db.entity.Ordenes;
import io.github.inflationx.calligraphy3.CalligraphyConfig;

public class DetallepedidoUserActivity extends AppCompatActivity {

    private Toolbar toolbar;

    RecyclerView rv_productos;
    DetalleproductosUserAdapter adapter;
    ArrayList<Ordenes> productoslist;
    ProgressBar progressBar;
    private LinearLayoutManager mLayoutManager;
    TextView tv_fecha;
    TextView tv_envio;
    TextView tv_pedido;
    TextView tv_total;
    TextView txt_total;
    TextView tv_pago;
    TextView tv_zona_envio;
    LinearLayout LL_total;
    LinearLayout LL_envio;
    LinearLayout LL_zona;

    TextView cancelacon;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/neufreu.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_detallepedido_user);

        toolbar = findViewById(R.id.tb_toolbar);
        progressBar = findViewById(R.id.progress_Bar);
        tv_fecha = findViewById(R.id.tv_fecha);
        tv_envio= findViewById(R.id.tv_envio);
        LL_total = findViewById(R.id.LL_total);
        LL_envio = findViewById(R.id.LL_envio);
        tv_pedido = findViewById(R.id.tv_pedido);
        tv_total = findViewById(R.id.tv_total);
        txt_total = findViewById(R.id.txt_total);
        tv_pago = findViewById(R.id.tv_pago);
        tv_zona_envio = findViewById(R.id.tv_zona);
        LL_zona = findViewById(R.id.LL_total5);

        cancelacon = findViewById(R.id.tv_cancela);

        final String id = getIntent().getExtras().getString("id");
        final String numerodepedido = getIntent().getExtras().getString("numerodepedido");
        final String total = getIntent().getExtras().getString("total");
        final String envio = getIntent().getExtras().getString("envio");
        final String fecha = getIntent().getExtras().getString("fecha");
        final String hora = getIntent().getExtras().getString("hora");
        final String retirar = getIntent().getExtras().getString("retirar");
        final String pago = getIntent().getExtras().getString("pago");
        final String txt_zona_envio = getIntent().getExtras().getString("zonaenvio");
        final String txt_cancelacon = getIntent().getExtras().getString("cancelacon");

        cancelacon.setText("s/" +txt_cancelacon);

        if(txt_zona_envio.equals("sinzona")){
            LL_zona.setVisibility(View.GONE);
        }else {
            tv_zona_envio.setText(txt_zona_envio);

        }
        tv_pago.setText(pago);

        if(!envio.equals("0")){

            tv_total.setText("Envio:" + "s/ "+String.valueOf(envio) +" Total con envio: s/ " + String.valueOf(total));
        }else{
            txt_total.setText("Total: s/ ");
            tv_total.setText(String.valueOf(total));
        }

        if(retirar.equals("retirar")){
            tv_pedido.setText("Retira por sucursal.");
        }else {
            tv_pedido.setText("Envio a domicilio.");
        }


        tv_fecha.setText(fecha + " "+ hora);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pedido nº:" +numerodepedido);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv_productos =  findViewById(R.id.rv);
        rv_productos.setLayoutManager(mLayoutManager);

        // MOSTRAR RECYCLER VIEW

        productoslist = new ArrayList<>();
        adapter = new DetalleproductosUserAdapter(productoslist,this);
        rv_productos.setAdapter(adapter);

        FirebaseDatabase database22 = FirebaseDatabase.getInstance();
        DatabaseReference myRef33 = database22.getReference();
        Query query4 = myRef33.child("Pedidos").child(id).child("ordenes");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                    LL_total.setVisibility(View.VISIBLE);
                    productoslist.removeAll(productoslist);
                    rv_productos.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        Ordenes prod = snapshot.getValue(Ordenes.class);

                        productoslist.add(prod);




                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(DetallepedidoUserActivity.this, "No hay productos en esta Categoria.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }//fin del oncreate!
}