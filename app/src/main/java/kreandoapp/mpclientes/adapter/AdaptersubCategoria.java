package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.ProcesoVenta.listaProductos;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.pojo.SubCategoria;


public class AdaptersubCategoria extends RecyclerView.Adapter<AdaptersubCategoria.CategoryviewHolder>{


    List<SubCategoria> categoryList;
    public Context context;
    detectorInternet internet;

    public AdaptersubCategoria(List<SubCategoria> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;

    }



    @Override
    public CategoryviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category,parent,false);
        CategoryviewHolder holder= new CategoryviewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final CategoryviewHolder holder, final int position) {



        internet = new detectorInternet(context);

        final SubCategoria cateconpos = categoryList.get(position);


        Picasso.with(context).load(cateconpos.getSubImagen()).into(holder.img_categorias, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
                holder.img_categorias.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

            }
        });


              holder.tv_nombre.setText(cateconpos.getSubnombreSub());


             holder.cardView.setOnClickListener(v -> {

                            Intent i = new Intent(v.getContext(), listaProductos.class);
                            i.putExtra("cateId",cateconpos.getIdsub());

                            i.putExtra("catename",cateconpos.getSubnombreSub());
                            i.putExtra("catImage",cateconpos.getSubImagen());
                            i.putExtra("catUrlcambiar",cateconpos.getSubUrlCambiar());
                            i.putExtra("orden",cateconpos.getOrden());
                            v.getContext().startActivity(i);

        });


    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryviewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre;
        CardView cardView;
        ImageView img_categorias;
        ProgressBar progressBar;


        public CategoryviewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            img_categorias = itemView.findViewById(R.id.img_categoria);
            progressBar = itemView.findViewById(R.id.progress_row_cate);
            //mail = itemView.findViewById(R.id.tv_mail);

        }

    }

}