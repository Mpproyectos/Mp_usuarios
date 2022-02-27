package kreandoapp.mpclientes.modoadmin.modoGPS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import kreandoapp.mpclientes.R;

public class DetectGpsactualadminActivity extends AppCompatActivity {

    int total_envio;

    Double latitudAdmindetect,longitudAdmindetect;
    String latitud_db_v,longitud_db_v;

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_LOCATION_CODE = 2;

    private TextView textLatLong;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_gpsactualadmin);

        textLatLong = findViewById(R.id.textLatLong);


        ubiygps();



    }//fin del oncreate!

    private void ubiygps() {
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    DetectGpsactualadminActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE
            );
        } else {
            if(gpsActived()){
                getCurrentLocation();
            }else {
                MostrarAlertaNOgps();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permiso denegado!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LOCATION_CODE && gpsActived()){
            getCurrentLocation();
        }else {
            MostrarAlertaNOgps();
        }
    }

    private void getCurrentLocation() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.getFusedLocationProviderClient(DetectGpsactualadminActivity.this).
                requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(DetectGpsactualadminActivity.this)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() >0){
                            int latestLocationIndex = locationResult.getLocations().size()-1;
                            latitudAdmindetect = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            longitudAdmindetect = locationResult.getLocations().get(latestLocationIndex).getLongitude();

                            DatabaseReference myRef3 = database.getReference();
                            Query query2 = myRef3.child("modoadmin");
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {


                                        latitud_db_v = dataSnapshot.child("latitud").getValue(String.class);
                                        longitud_db_v = dataSnapshot.child("longitud").getValue(String.class);

                                        vamosamodogps();

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });





                        }

                    }
                }, Looper.getMainLooper());
    }



    private boolean gpsActived(){
        boolean isActive= false;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            isActive = true;
        }

        return isActive;
    }




    private void vamosamodogps() {
        Intent i = new Intent(DetectGpsactualadminActivity.this, ModoGpsEdicionActivity.class);
        i.putExtra("latitudAdmin",Double.toString(latitudAdmindetect));
        i.putExtra("longitudAdmin",Double.toString(longitudAdmindetect));
        i.putExtra("latitud_db",latitud_db_v);
        i.putExtra("longitud_db",longitud_db_v);



        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void MostrarAlertaNOgps(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Por favor activa tu ubicación para continuar")
                .setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),REQUEST_LOCATION_CODE);
                    }
                }).create().show();
    }




}