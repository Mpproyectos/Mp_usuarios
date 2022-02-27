package kreandoapp.mpclientes.clientes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Vibrator;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import kreandoapp.mpclientes.APIService;
import kreandoapp.mpclientes.Notifications.Client;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.volley.claseSendVolleyFCM;

public class cancelarPedidoActivity extends AppCompatActivity {
    Button btnsi,btnno;
    LinearLayout mykonten,overbox;
    ImageView locicon;
    Animation fromsmall,fromnothing,forloci,togo;
    MediaPlayer sound;
    TextView tv_pedido;
    FirebaseUser fuser;
    APIService apiService;

    TextView tv_id_admin;
    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_pedido);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        sound = MediaPlayer.create(this,R.raw.arpeggio);
        final Vibrator vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);

        btnsi = findViewById(R.id.btnsi);
        btnno = findViewById(R.id.btnno);

        tv_id_admin = findViewById(R.id.tv_id_admin);

        dameidadmin();

        String numerodepedido = getIntent().getExtras().getString("idid");
        final String key = getIntent().getExtras().getString("idkey");

        tv_pedido = findViewById(R.id.tv_pedido);
        tv_pedido.setText(numerodepedido);

        mykonten = findViewById(R.id.mykonten);
        overbox = findViewById(R.id.overbox);

        locicon = findViewById(R.id.locicon);

        fromsmall = AnimationUtils.loadAnimation(this,R.anim.fromsmall);
        fromnothing = AnimationUtils.loadAnimation(this,R.anim.fromnothing);
        forloci = AnimationUtils.loadAnimation(this,R.anim.forloci );
        togo = AnimationUtils.loadAnimation(this,R.anim.togo );

        mykonten.setAlpha(0);
        overbox.setAlpha(0);
        locicon.setVisibility(View.GONE);



        locicon.setVisibility(View.VISIBLE);
        locicon.startAnimation(forloci);

        overbox.setAlpha(1);
        overbox.startAnimation(fromnothing);

        mykonten.setAlpha(1);
        mykonten.startAnimation(fromsmall);

        vibrator.vibrate(300);




        btnsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                notify = true;
                final String destinatarioadmin = tv_id_admin.getText().toString();



                FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                DatabaseReference myRef3 = database2.getReference("Requests").child(key);
                myRef3.child("estado").setValue("canceladouser");

                pedidoCancelado(tv_id_admin.getText().toString(),"");

                Intent i = new Intent(view.getContext(),home1.class);
                view.getContext().startActivity(i);
                Toast.makeText(cancelarPedidoActivity.this, "Pedido cancelado!", Toast.LENGTH_SHORT).show();
                vibrator.vibrate(300);

            }
        });

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(view.getContext(),home1.class);
                view.getContext().startActivity(i);

            }
        });
    }//fin del oncreate!!



    private  void dameidadmin(){
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database2.getReference();
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
    private void pedidoCancelado(String id,String m) {

        Toast.makeText(this, "Cliente notificado!", Toast.LENGTH_SHORT).show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(id).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_sinfoto("Pedido Cancelado","El usuario cancelo el pedido",token,"modoadmin");
                    Toast.makeText(cancelarPedidoActivity.this, token, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
