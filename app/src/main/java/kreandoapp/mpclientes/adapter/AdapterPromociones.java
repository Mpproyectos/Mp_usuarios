package kreandoapp.mpclientes.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import kreandoapp.mpclientes.APIService;
import kreandoapp.mpclientes.Notifications.Client;
import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.ProcesoVenta.DetectandoUbicacionActivity;
import kreandoapp.mpclientes.modoadmin.Promo.eliminarpromo;
import kreandoapp.mpclientes.pojo.pojo_productos;
import kreandoapp.mpclientes.volley.claseSendVolleyFCM;


public class AdapterPromociones extends RecyclerView.Adapter<AdapterPromociones.PromosviewHolder>{
    MediaPlayer soundagregar;

    List<pojo_productos> orderList;
    Context context;
    private  int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION ;
    APIService apiService;
    private Acciones acciones;
    public AdapterPromociones(List<pojo_productos> orderList,Context context,Acciones act) {
        this.orderList = orderList;
        this.context = context;
        this.acciones = act;

    }



    @Override
    public PromosviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_promociones,parent,false);
        PromosviewHolder holder= new PromosviewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final PromosviewHolder holder, final int position) {

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        final pojo_productos prod = orderList.get(position);
        holder.precio.setText("s/"+prod.getProdPrecio());

        soundagregar = MediaPlayer.create(context,R.raw.enviado_sound);


        Picasso.with(context).load(prod.getProdImagen()).into(holder.imagen, new Callback() {
            @Override
            public void onSuccess() {
                holder.imagen.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });




        holder.description.setText(prod.getProdDescripcion());
        holder.tv_nombre_prod.setText(prod.getProdNombre());

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        assert user != null;
        DatabaseReference myRef3 = database111.getReference("modoadmin").child("id");
        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);

                    if(val.equals(user.getUid())){
                        holder.btn_Notificaratodos.setVisibility(View.VISIBLE);
                       holder.btn_eliminarpromo.setVisibility(View.VISIBLE);
                    }else{
                        holder.btn_eliminarpromo.setVisibility(View.GONE);
                        holder.btn_Notificaratodos.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        holder.btn_agregaralcarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.zoomtext);
                holder.btn_agregaralcarrito.startAnimation(animation);

                int permissionCheck = ContextCompat.checkSelfPermission(v.getContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION);
                if(permissionCheck ==0){


                acciones.AccionAgregar(
                        prod.getProdNombre(),
                        prod.getProdPrecio(),
                        prod.getProdImagen(),
                        prod.getProdId(),
                        holder.numberButton.getNumber(),
                        prod.getCodProd());


                soundagregar.start();
                Intent intent = new Intent(v.getContext(), DetectandoUbicacionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(), "Producto Agregado", Toast.LENGTH_SHORT).show();
                soundagregar.start();
                }else {
                    if (ContextCompat.checkSelfPermission(v.getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions((Activity) v.getContext(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


                    }
                }

            }
        });


        holder.numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                final Double multi = Double.parseDouble(holder.numberButton.getNumber()) * Double.parseDouble(prod.getProdPrecio());
                holder.precio.setText("s/"+String.valueOf(multi));

                holder.btn_agregaralcarrito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int permissionCheck = ContextCompat.checkSelfPermission(v.getContext(),
                                android.Manifest.permission.ACCESS_FINE_LOCATION);
                        if(permissionCheck ==0){


                            acciones.AccionAgregar(
                                    prod.getProdNombre(),
                                    prod.getProdPrecio(),
                                    prod.getProdImagen(),
                                    prod.getProdId(),
                                    holder.numberButton.getNumber(),
                                    prod.getCodProd());

                        Intent intent = new Intent(v.getContext(), DetectandoUbicacionActivity.class);
                        v.getContext().startActivity(intent);
                        Toast.makeText(v.getContext(), "Producto Agregado", Toast.LENGTH_SHORT).show();
                        soundagregar.start();

                        }else {
                            if (ContextCompat.checkSelfPermission(v.getContext(),
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {

                                ActivityCompat.requestPermissions((Activity) v.getContext(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        }
                    }
                });
            }
        });

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database3.getReference();
        Query query4 = myRef4.child("Estadolocal").child("valor");
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("abierto")){
                        holder.btn_agregaralcarrito.setVisibility(View.VISIBLE);
                        holder.btn_infocerrado.setVisibility(View.GONE);
                    }else{
                        holder.btn_agregaralcarrito.setVisibility(View.GONE);
                        holder.btn_infocerrado.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.btn_infocerrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "El negocio se encuentra Cerrado.", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_eliminarpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                final DatabaseReference myRef4 = database3.getReference();
                final Query query= myRef4.child("Promo").orderByChild("prodImagen").equalTo(prod.getProdImagen());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                String key = childSnapshot.getKey();

                                Intent a = new Intent(view.getContext(), eliminarpromo.class);

                                a.putExtra("imagenenproceso",prod.getProdId());
                                a.putExtra("nameprod",prod.getProdNombre());
                                a.putExtra("keyprod",key);


                                view.getContext().startActivity(a);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.btn_Notificaratodos.setOnClickListener(v -> {
            String tipo = "/topics/" + "grupopromo";

            claseSendVolleyFCM clase = new claseSendVolleyFCM();
            clase.volleyfcm_confoto(prod.getProdNombre(),prod.getProdDescripcion(),prod.getProdImagen(),tipo,"promo");
            Toast.makeText(context, "Notificacion enviada!", Toast.LENGTH_SHORT).show();

        });


    }




    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class PromosviewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView tv_nombre_prod, precio, description;
        CardView cardView;
        ElegantNumberButton numberButton;
        Button btn_agregaralcarrito, btn_infocerrado, btn_eliminarpromo;
        ProgressBar progressBar;
        Button btn_Notificaratodos;


        public PromosviewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.tv_description);
            imagen = itemView.findViewById(R.id.img_image);
            btn_infocerrado = itemView.findViewById(R.id.btm_infomodocerrado);
            numberButton = itemView.findViewById(R.id.number_button);
            precio = itemView.findViewById(R.id.tv_precio);
            cardView = itemView.findViewById(R.id.card_view_prod);
            tv_nombre_prod = itemView.findViewById(R.id.tv_nombre_promo);
            btn_agregaralcarrito = itemView.findViewById(R.id.btn_agregaralcarrito_promo);
            progressBar = itemView.findViewById(R.id.progress_bar);
            btn_eliminarpromo = itemView.findViewById(R.id.btn_eliminarpromo);
            btn_Notificaratodos = itemView.findViewById(R.id.btn_Notificaratodos);

        }


    }

public interface Acciones{
    void AccionAgregar(String nombre, String precio, String imagen,String prodidString,String cantidad,String codprod);
    void accionBorrar(int id,int posicion);
}
}