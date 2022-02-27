package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.DetalleNodoActivity;
import kreandoapp.mpclientes.clientes.ProcesoVenta.DetectandoUbicacion2;
import kreandoapp.mpclientes.clientes.ProcesoVenta.DetectandoUbicacionActivity;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.pojo.ModeloCaja;
import kreandoapp.mpclientes.pojo.ModeloNodo;


public class AdapterNodos extends RecyclerView.Adapter<AdapterNodos.NodosviewHolder>{
    MediaPlayer soundagregar;
    detectorInternet internet;
    List<ModeloNodo> nodolist;
    public Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();



    private  int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION ;

    public AdapterNodos(List<ModeloNodo>nodolist, Context context) {
        this.nodolist = nodolist;
        this.context = context;

    }


    @Override
    public NodosviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nodo,parent,false);
        NodosviewHolder holder= new NodosviewHolder(v);




        return  holder;

    }

    @Override
    public void onBindViewHolder(final NodosviewHolder holder, final int position) {



        internet = new detectorInternet(context);

        final ModeloNodo nod = nodolist.get(position);

        holder.tv_nombre.setText( nod.getNombreNodo());
        holder.tv_fecha.setText(nod.getFechaNodo());

        holder.btn_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DetalleNodoActivity.class);
                i.putExtra("id_nodo",nod.getIdnodo());
                i.putExtra("nombre_nodo",nod.getNombreNodo());
                v.getContext().startActivity(i);
            }
        });




       /* if(nod.getAutorizacion2().equals("ok")){

            holder.btncompletado.setVisibility(View.VISIBLE);
        }else{

            holder.btncompletado.setVisibility(View.GONE);
        }*/

       /* if(caj.getFase1_fecha().equals("")){
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
        });*/


    }//fin del onvinbinholder
    private void animacionadd(){

    }
    @Override
    public int getItemCount() {
        return nodolist.size();
    }

    public class NodosviewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre,tv_fecha;
        Button btn_proceso;

        ImageButton btn_detalle;




        public NodosviewHolder(View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.nombrenodo);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);


            btn_detalle = itemView.findViewById(R.id.btn_detalle);


        }

    }


}