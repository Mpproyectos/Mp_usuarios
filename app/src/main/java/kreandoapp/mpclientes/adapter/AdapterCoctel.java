package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.verImgsCoctelActivity;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.pojo.Coctelpojo;


public class AdapterCoctel extends RecyclerView.Adapter<AdapterCoctel.cotectelViewHolder>{


    List<Coctelpojo> coctellist;
    public Context context;
    detectorInternet internet;

    public AdapterCoctel(List<Coctelpojo> coctellist, Context context) {
        this.coctellist = coctellist;
        this.context = context;

    }



    @Override
    public cotectelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category,parent,false);
        cotectelViewHolder holder= new cotectelViewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final cotectelViewHolder holder, final int position) {



        internet = new detectorInternet(context);

        final Coctelpojo coctel = coctellist.get(position);


        Picasso.with(context).load(coctel.getMiniatura()).into(holder.img_categorias, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
                holder.img_categorias.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }
        });


              holder.tv_nombre.setText(coctel.getTitulo());


             holder.cardView.setOnClickListener(v -> {

                Intent i = new Intent(v.getContext(), verImgsCoctelActivity.class);
                i.putExtra("id",coctel.getIdpush());
                 i.putExtra("titulo",coctel.getTitulo());
                 i.putExtra("miniatura",coctel.getMiniatura());
                 i.putExtra("urlcambiar",coctel.getUrlcambiar());
                 i.putExtra("orden",coctel.getOrden());
                 v.getContext().startActivity(i);

                            /*Intent i = new Intent(v.getContext(), listaProductos.class);
                            i.putExtra("cateId",cateconpos.getIdsub());

                            i.putExtra("catename",cateconpos.getSubnombreSub());
                            i.putExtra("catImage",cateconpos.getSubImagen());
                            i.putExtra("catUrlcambiar",cateconpos.getSubUrlCambiar());
                            i.putExtra("orden",cateconpos.getOrden());*/
                            //v.getContext().startActivity(i);

        });


    }


    @Override
    public int getItemCount() {
        return coctellist.size();
    }

    public class cotectelViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre;
        CardView cardView;
        ImageView img_categorias;
        ProgressBar progressBar;


        public cotectelViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            img_categorias = itemView.findViewById(R.id.img_categoria);
            progressBar = itemView.findViewById(R.id.progress_row_cate);
            //mail = itemView.findViewById(R.id.tv_mail);

        }

    }

}