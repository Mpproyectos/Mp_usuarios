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
import kreandoapp.mpclientes.pojo.Contacto_pojo;


public class AdapterContactoRv extends RecyclerView.Adapter<AdapterContactoRv.ContactosviewHolder>{

    detectorInternet internet;
    List<Contacto_pojo> contactoList;
    public Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Acciones acciones;


    public AdapterContactoRv(List<Contacto_pojo> contactoList, Context context) {
        this.contactoList = contactoList;

        this.context = context;

    }


    @Override
    public ContactosviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_2,parent,false);
        ContactosviewHolder holder= new ContactosviewHolder(v);

        return  holder;

    }



    @Override
    public void onBindViewHolder(final ContactosviewHolder holder, final int position) {


        internet = new detectorInternet(context);

        final Contacto_pojo cont = contactoList.get(position);

        holder.card_view_what.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wa.me/"+cont.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                v.getContext().startActivity(i);
            }
        });



        Picasso.with(context).load(cont.getFoto()).into(holder.imagen, new Callback() {
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
        return contactoList.size();
    }

    public class ContactosviewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        CardView card_view_what;

        public ContactosviewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.img_image);
            card_view_what = itemView.findViewById(R.id.card_view_what);


        }

    }
    public interface Acciones{
        void AccionAgregar(String nombre, String precio, String imagen,String prodidString,String cantidad,String codprod,String notaprod,String regalopromo);
        void accionBorrar(int id,int posicion);
    }

}