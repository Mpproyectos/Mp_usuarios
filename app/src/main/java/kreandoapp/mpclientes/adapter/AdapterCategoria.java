package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.ProcesoVenta.ListaSubcategoriasActivity;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.pojo.Categoria;


public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.CategoryviewHolder>{


    List<Categoria> categoryList;
    public Context context;
    detectorInternet internet;

    public AdapterCategoria(List<Categoria> categoryList,Context context) {
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

        final Categoria cateconpos = categoryList.get(position);


        Picasso.with(context).load(cateconpos.getCatImage()).into(holder.img_categorias, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
                holder.img_categorias.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

            }
        });


              holder.tv_nombre.setText(cateconpos.getCatNombre());


             holder.cardView.setOnClickListener(v -> {

                            /*Intent i = new Intent(v.getContext(), listaProductos.class);
                            i.putExtra("cateId",cateconpos.getCatId());
                            i.putExtra("catename",cateconpos.getCatNombre());
                            i.putExtra("catImage",cateconpos.getCatImage());
                            i.putExtra("catUrlcambiar",cateconpos.getCatUrlCambiarImagen());
                            i.putExtra("orden",cateconpos.getOrden());
                            v.getContext().startActivity(i);*/
                            Intent i = new Intent(v.getContext(), ListaSubcategoriasActivity.class);
                            i.putExtra("idcate",cateconpos.getCatId());
                            i.putExtra("catename",cateconpos.getCatNombre());


                 i.putExtra("catImage",cateconpos.getCatImage());
                 i.putExtra("catUrlcambiar",cateconpos.getCatUrlCambiarImagen());
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