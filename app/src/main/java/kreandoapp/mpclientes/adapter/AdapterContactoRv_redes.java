package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.pojo.redes_pojo;


public class AdapterContactoRv_redes extends RecyclerView.Adapter<AdapterContactoRv_redes.ContactosRedesviewHolder>{

    detectorInternet internet;
    List<redes_pojo> redesList;
    public Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Acciones acciones;


    public AdapterContactoRv_redes(List<redes_pojo> redesList, Context context) {
        this.redesList = redesList;

        this.context = context;

    }


    @Override
    public ContactosRedesviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_redes,parent,false);
        ContactosRedesviewHolder holder= new ContactosRedesviewHolder(v);

        return  holder;

    }

    @Override
    public void onBindViewHolder(final ContactosRedesviewHolder holder, final int position) {


        internet = new detectorInternet(context);

        final redes_pojo cont = redesList.get(position);

        holder.card_view_what.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = cont.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                v.getContext().startActivity(i);
            }
        });



        

        Picasso.with(context).load(cont.getImagen()).into(holder.imagen, new Callback() {
            @Override
            public void onSuccess() {

                holder.imagen.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

           }

        });




    }//fin del onvinbinholder
    private void animacionadd(){

    }
    @Override
    public int getItemCount() {
        return redesList.size();
    }

    public class ContactosRedesviewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        CardView card_view_what;


        public ContactosRedesviewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.img_image);
            card_view_what = itemView.findViewById(R.id.card_view);


        }

    }
    public interface Acciones{
        void AccionAgregar(String nombre, String precio, String imagen,String prodidString,String cantidad,String codprod,String notaprod,String regalopromo);
        void accionBorrar(int id,int posicion);
    }

}