package kreandoapp.mpclientes.clientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import kreandoapp.mpclientes.R;

public class Fase1okActivity extends AppCompatActivity {

    Button btncls;
    LinearLayout mykonten,overbox;
    ImageView locicon;
    Animation fromsmall,fromnothing,forloci,togo;
    MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fase1ok);


        sound = MediaPlayer.create(this,R.raw.arpeggio);
        final Vibrator vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);

        btncls = findViewById(R.id.btncls);


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
        sound.start();
        vibrator.vibrate(300);




        btncls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(view.getContext(),home1.class);
                view.getContext().startActivity(i);

               /* overbox.startAnimation(togo);
                mykonten.startAnimation(togo);
                locicon.startAnimation(togo);
                locicon.setVisibility(View.GONE);
                ViewCompat.animate(mykonten).setStartDelay(1000).alpha(0).start();
                ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();*/
            }
        });
    }
}
