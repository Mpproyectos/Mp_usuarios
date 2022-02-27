package kreandoapp.mpclientes.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.modoadmin.Productos.editarproductoActivity;
import kreandoapp.mpclientes.modoadmin.Productos.eliminarproducto;
import kreandoapp.mpclientes.pojo.pojo_productos;


public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ProductosviewHolder>{
    MediaPlayer soundagregar;
    detectorInternet internet;
    List<pojo_productos> productosList;
    public Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Acciones acciones;


    private  int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION ;

    public AdapterProductos(List<pojo_productos> productosList, Acciones act, Context context) {
        this.productosList = productosList;
        this.acciones =  act;
        this.context = context;

    }


    @Override
    public ProductosviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prod,parent,false);
        ProductosviewHolder holder= new ProductosviewHolder(v);

        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "fonts/font3.ttf");
        holder.description.setTypeface(typeface);

        return  holder;

    }

    @Override
    public void onBindViewHolder(final ProductosviewHolder holder, final int position) {



        internet = new detectorInternet(context);

        final pojo_productos prod = productosList.get(position);
        holder.precio.setText("s/" +prod.getProdPrecio());



        soundagregar = MediaPlayer.create(context,R.raw.enviado_sound);


        Picasso.with(context).load(prod.getProdImagen()).into(holder.imagen, new Callback() {
            @Override
            public void onSuccess() {
                holder.progress_bar.setVisibility(View.GONE);
                holder.imagen.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError() {

            }
        });


        holder.description.setText(prod.getProdDescripcion());
        holder.tv_nombre_prod.setText(prod.getProdNombre());

        holder.btn_agregaralcarrito.setOnClickListener(v -> {

            if(internet.estaConectado()){
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.zoomtext);
                holder.btn_agregaralcarrito.startAnimation(animation);

                int permissionCheck = ContextCompat.checkSelfPermission(v.getContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION);
                if(permissionCheck ==0){

                    acciones.AccionAgregar(
                            prod.getProdNombre(),
                            prod.getProdDescripcion(),
                            prod.getProdPrecio(),
                            prod.getProdImagen(),
                            prod.getProdId(),
                            holder.numberButton.getNumber(),
                            prod.getCodProd());


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

            }else {
                Snackbar snackbar = Snackbar.make(v, "No existe conexion a internet", Snackbar.LENGTH_LONG);
                snackbar.show();
            }






        });


       holder.numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
           @Override
           public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
            final Double multi = Double.parseDouble(holder.numberButton.getNumber()) * Double.parseDouble(prod.getProdPrecio());
               holder.precio.setText("s/ "+String.valueOf(multi));

               holder.btn_agregaralcarrito.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Animation animation = AnimationUtils.loadAnimation(context,R.anim.zoomtext);
                       holder.btn_agregaralcarrito.startAnimation(animation);

                       internet = new detectorInternet(context);
                       int permissionCheck = ContextCompat.checkSelfPermission(v.getContext(),
                               android.Manifest.permission.ACCESS_FINE_LOCATION);
                       if(permissionCheck ==0){
                           acciones.AccionAgregar(
                                   prod.getProdNombre(),
                                    prod.getProdDescripcion(),
                                   prod.getProdPrecio(),
                                   prod.getProdImagen(),
                                   prod.getProdId(),
                                   holder.numberButton.getNumber(),
                                   prod.getCodProd()
                           );


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





        holder.btn_infocerrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "El negocio se encuentra Cerrado.", Toast.LENGTH_SHORT).show();
            }
        });






        if(prod.getProdStock().equals("si")){
            holder.btn_enstock.setVisibility(View.VISIBLE);
            holder.btn_sinstock.setVisibility(View.GONE);
        }else {
            holder.btn_sinstock.setVisibility(View.VISIBLE);
            holder.btn_enstock.setVisibility(View.GONE);
        }
        holder.btn_enstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final DatabaseReference myRef4 = database.getReference("Productos").child(prod.getProdId());

                myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       myRef4.child("prodStock").setValue("no");


                       Toast.makeText(view.getContext(), "Producto sin stock", Toast.LENGTH_SHORT).show();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        holder.btn_sinstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final DatabaseReference myRef4 = database.getReference("Productos").child(prod.getProdId());

                myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        myRef4.child("prodStock").setValue("si");

                        Toast.makeText(view.getContext(), "Producto en stock", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        assert user != null;
        DatabaseReference myRef22 = database.getReference("modoadmin").child("id");
        myRef22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);

                    if(val.equals(user.getUid())){
                    holder.btn_eliminar.setVisibility(View.VISIBLE);
                    holder.btn_editar.setVisibility(View.VISIBLE);
                    }else{
                        holder.btn_eliminar.setVisibility(View.GONE);
                        holder.btn_editar.setVisibility(View.GONE);
                        holder.btn_enstock.setVisibility(View.GONE);
                        holder.btn_sinstock.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.btn_editar.setOnClickListener(view -> {

                            Intent a = new Intent(view.getContext(), editarproductoActivity.class);
                            a.putExtra("tituloprod",prod.getProdNombre());
                            a.putExtra("detalleprod",prod.getProdDescripcion());
                            a.putExtra("imagenprod",prod.getProdImagen());
                            a.putExtra("imagenenproceso",prod.getProdUrlenproceso());
                            a.putExtra("precioprod",prod.getProdPrecio());
                            a.putExtra("keyprod",prod.getProdId());
                            a.putExtra("orden",prod.getOrden());

                            view.getContext().startActivity(a);



        });

                         holder.btn_eliminar.setOnClickListener(view -> {


                            Intent a = new Intent(view.getContext(), eliminarproducto.class);

                            a.putExtra("imagenenproceso",prod.getProdUrlenproceso());
                            a.putExtra("nameprod",prod.getProdNombre());
                            a.putExtra("keyprod",prod.getProdId());


                            view.getContext().startActivity(a);

        });


    }//fin del onvinbinholder
    private void animacionadd(){

    }
    @Override
    public int getItemCount() {
        return productosList.size();
    }

    public class ProductosviewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView tv_nombre_prod,precio,description;
        CardView cardView;
        ElegantNumberButton numberButton;
        Button btn_agregaralcarrito,btn_infocerrado,btn_enstock,btn_sinstock,btn_editar,btn_eliminar;
        ProgressBar progress_bar;



        public ProductosviewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.tv_description);
            imagen = itemView.findViewById(R.id.img_image);
            btn_infocerrado = itemView.findViewById(R.id.btm_infomodocerrado);
            numberButton = itemView.findViewById(R.id.number_button);
            precio = itemView.findViewById(R.id.tv_precio);
            cardView = itemView.findViewById(R.id.card_view_prod);
            tv_nombre_prod = itemView.findViewById(R.id.tv_nombre_producto);
            btn_agregaralcarrito = itemView.findViewById(R.id.btn_agregaralcarrito);

            btn_enstock = itemView.findViewById(R.id.btn_enstock);
            btn_sinstock = itemView.findViewById(R.id.btn_sinstock);

            btn_editar = itemView.findViewById(R.id.btn_editar);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            btn_eliminar = itemView.findViewById(R.id.btn_eliminar);


        }

    }
    public interface Acciones{
        void AccionAgregar(String nombre,String detalle, String precio, String imagen,String prodidString,String cantidad,String codprod);
        void accionBorrar(int id,int posicion);
    }

}