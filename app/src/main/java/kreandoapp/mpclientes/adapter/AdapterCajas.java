package kreandoapp.mpclientes.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import kreandoapp.mpclientes.clientes.ProcesoVenta.DetectandoUbicacion2;
import kreandoapp.mpclientes.clientes.ProcesoVenta.DetectandoUbicacionActivity;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.modoadmin.Productos.editarproductoActivity;
import kreandoapp.mpclientes.modoadmin.Productos.eliminarproducto;
import kreandoapp.mpclientes.pojo.ModeloCaja;
import kreandoapp.mpclientes.pojo.pojo_productos;


public class AdapterCajas extends RecyclerView.Adapter<AdapterCajas.CajasviewHolder>{
    MediaPlayer soundagregar;
    detectorInternet internet;
    List<ModeloCaja> cajalist;
    public Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();



    private  int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION ;

    public AdapterCajas(List<ModeloCaja> cajalist, Context context) {
        this.cajalist = cajalist;
        this.context = context;

    }


    @Override
    public CajasviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_caja,parent,false);
        CajasviewHolder holder= new CajasviewHolder(v);




        return  holder;

    }

    @Override
    public void onBindViewHolder(final CajasviewHolder holder, final int position) {



        internet = new detectorInternet(context);

        final ModeloCaja caj = cajalist.get(position);

        holder.tv_nombre.setText("Caja: " + caj.getNombre_caja());
        holder.tipo.setText(caj.getTipo_trabajo());

        if(caj.getFase1_fecha().equals("")){
            holder.btncargarF1.setVisibility(View.VISIBLE);
        }else{
            holder.btncargarF1.setVisibility(View.GONE);
            if(caj.getFase2_fecha().equals("")){
                holder.btncargarF2.setVisibility(View.VISIBLE);
            }else{
                holder.btncargarF2.setVisibility(View.GONE);
                if(caj.getEstado().equals("listo")){
                    holder.btncompletado.setVisibility(View.VISIBLE);
                }
            }
        }


        holder.btncargarF1.setOnClickListener(v -> {

           Intent i = new Intent(v.getContext(), DetectandoUbicacionActivity.class);
            i.putExtra("id_caja",caj.getId_caja());
            i.putExtra("nombre_caja",caj.getNombre_caja());
           v.getContext().startActivity(i);

        });

        holder.btncargarF2.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), DetectandoUbicacion2.class);
            i.putExtra("id_caja",caj.getId_caja());
            i.putExtra("nombre_caja",caj.getNombre_caja());
            v.getContext().startActivity(i);
        });


    }//fin del onvinbinholder
    private void animacionadd(){

    }
    @Override
    public int getItemCount() {
        return cajalist.size();
    }

    public class CajasviewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre,tipo;
        Button btncargarF1;
        Button btncargarF2;
        Button btncompletado;




        public CajasviewHolder(View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.nombrecaja);
            btncargarF1 = itemView.findViewById(R.id.btn_cargarF1);
            btncargarF2 = itemView.findViewById(R.id.btn_cargarF2);
            btncompletado = itemView.findViewById(R.id.btn_completado);
            tipo = itemView.findViewById(R.id.tipo);


        }

    }


}