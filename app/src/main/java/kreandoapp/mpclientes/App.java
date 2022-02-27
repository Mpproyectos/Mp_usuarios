package kreandoapp.mpclientes;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.loginapp.MainActivity;

import static android.content.ContentValues.TAG;

public class App extends Application {


    @Override
    public void onCreate() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        super.onCreate();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in, send to mainmenu


            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            Intent i = new Intent(App.this, home1.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        } else {
            Intent i = new Intent(App.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

    }


}