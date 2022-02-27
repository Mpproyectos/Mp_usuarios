package kreandoapp.mpclientes.clientes.ProcesoVenta;

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
import android.location.Location;
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

import java.text.DecimalFormat;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.CargarFase1Activity;
import kreandoapp.mpclientes.clientes.CargarFase2Activity;

public class DetectandoUbicacion2 extends AppCompatActivity {

    int total_envio;

    Double latitudUser,longitudeUser;

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_LOCATION_CODE = 2;

    private TextView textLatLong;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detectando_ubicacion2);
        textLatLong = findViewById(R.id.textLatLong);


        ubiygps();



    }//fin del oncreate!


    private void ubiygps() {
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    DetectandoUbicacion2.this,
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
        LocationServices.getFusedLocationProviderClient(DetectandoUbicacion2.this).
                requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(DetectandoUbicacion2.this)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() >0){
                            int latestLocationIndex = locationResult.getLocations().size()-1;
                            latitudUser = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            longitudeUser = locationResult.getLocations().get(latestLocationIndex).getLongitude();


                            vamosacarrito();


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
    private void damemodoadmin(Double lat,Double lon) {

        DatabaseReference myRef3 = database.getReference();
        Query query2 = myRef3.child("modoadmin");
        query2.addValueEventListener(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String adminlat = dataSnapshot.child("latitud").getValue(String.class);
                    String  adminlon = dataSnapshot.child("longitud").getValue(String.class);


                    Location user = new Location("");
                    user.setLatitude(lat);
                    user.setLongitude(lon);

                    Location admin = new Location("");
                    admin.setLatitude(Double.parseDouble(adminlat));
                    admin.setLongitude(Double.parseDouble(adminlon));

                    float distance = user.distanceTo(admin) / 1000;
                    DecimalFormat df = new DecimalFormat("#.0");

                    String distancia = df.format(distance);
                    String str2 = distancia.replaceAll(",", ".");
                    Double val = Double.valueOf(str2);

                    // modoprecioenvio(val);




                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /*private void modoprecioenvio(Double val) {

        DatabaseReference myRefdistancia = database.getReference("precioenvio");
        myRefdistancia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {




                    String dis1 = dataSnapshot.child("distancia1").getValue(String.class);
                    String dis2 = dataSnapshot.child("distancia2").getValue(String.class);
                    String dis3 = dataSnapshot.child("distancia3").getValue(String.class);
                    String dis4 = dataSnapshot.child("distancia4").getValue(String.class);
                    String dis5 = dataSnapshot.child("distancia5").getValue(String.class);
                    String dis6 = dataSnapshot.child("distancia6").getValue(String.class);

                    String precio1 = dataSnapshot.child("precio1").getValue(String.class);
                    String precio2 = dataSnapshot.child("precio2").getValue(String.class);
                    String precio3 = dataSnapshot.child("precio3").getValue(String.class);
                    String precio4 = dataSnapshot.child("precio4").getValue(String.class);
                    String precio5 = dataSnapshot.child("precio5").getValue(String.class);
                    String precio6 = dataSnapshot.child("precio6").getValue(String.class);

                    //String ttotal = total_bd;

                    //countpedidos



                    if (!dis1.equals("") && val < Double.valueOf(dis1)) {
                        int prec1 = Integer.parseInt(precio1);
                        if (prec1 == 0) {
                            total_envio = Integer.parseInt(precio1);
                            vamosacarrito(total_envio,"d");
                        }else {
                            total_envio = Integer.parseInt(precio1);
                            vamosacarrito(total_envio,"d");
                        }


                    }else if (!dis2.equals("") && val < Double.valueOf(dis2)) {
                        int prec2 = Integer.parseInt(precio2);
                        if (prec2 == 0) {
                            total_envio = Integer.parseInt(precio2);
                            vamosacarrito(total_envio,"d");
                        }else {
                            total_envio = Integer.parseInt(precio2);
                            vamosacarrito(total_envio,"d");
                        }

                    }else if (!dis3.equals("") && val < Double.valueOf(dis3)) {
                        int prec3 = Integer.parseInt(precio3);
                        if (prec3 == 0) {
                            total_envio = Integer.parseInt(precio2);
                            vamosacarrito(total_envio,"d");
                        }else {
                            total_envio = Integer.parseInt(precio3);
                            vamosacarrito(total_envio,"d");
                        }
                    }else if (!dis4.equals("") && val < Double.valueOf(dis4)) {
                        int prec4 = Integer.parseInt(precio4);
                        if (prec4 == 0) {
                            total_envio = Integer.parseInt(precio4);
                            vamosacarrito(total_envio,"d");
                        }else {
                            total_envio = Integer.parseInt(precio4);
                            vamosacarrito(total_envio,"d");
                        }
                    }else if (!dis5.equals("") && val < Double.valueOf(dis5)) {
                        int prec5 = Integer.parseInt(precio5);
                        if (prec5 == 0) {
                            total_envio = Integer.parseInt(precio5);
                            vamosacarrito(total_envio,"d");
                        }else {
                            total_envio = Integer.parseInt(precio5);
                            vamosacarrito(total_envio,"d");
                        }
                    }else if (!dis6.equals("") && val < Double.valueOf(dis6)) {
                        int prec6 = Integer.parseInt(precio6);
                        if (prec6 == 0) {
                            total_envio = Integer.parseInt(precio6);
                            vamosacarrito(total_envio,"d");
                        }else {
                            total_envio = Integer.parseInt(precio6);
                            vamosacarrito(total_envio,"d");
                        }
                    }else {
                        vamosacarrito(0,"f");
                    }


                }else {
                    Toast.makeText(DetectandoUbicacionActivity.this, "No existe-", Toast.LENGTH_SHORT).show();

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    private void vamosacarrito() {

        String id_caja = getIntent().getExtras().getString("id_caja");
        String nombre_caja = getIntent().getExtras().getString("nombre_caja");


        Intent i = new Intent(DetectandoUbicacion2.this, CargarFase2Activity.class);
        i.putExtra("latitudUser",Double.toString(latitudUser));
        i.putExtra("longitudUser",Double.toString(longitudeUser));
        i.putExtra("nombre_caja" , nombre_caja);
        i.putExtra("id_caja",id_caja);


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