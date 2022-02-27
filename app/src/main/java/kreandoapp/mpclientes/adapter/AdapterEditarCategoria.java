package kreandoapp.mpclientes.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.pojo.Categoria;


public class AdapterEditarCategoria extends RecyclerView.Adapter<AdapterEditarCategoria.EditarCategoriaviewHolder>{


    List<Categoria> categoryList;
    private Context context;

    public AdapterEditarCategoria(List<Categoria> categoryList) {
        this.categoryList = categoryList;

    }



    @Override
    public EditarCategoriaviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_editar_category,parent,false);
        EditarCategoriaviewHolder holder= new EditarCategoriaviewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final EditarCategoriaviewHolder holder, final int position) {
        //final Context context=holder.imagen.getContext();


    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class EditarCategoriaviewHolder extends RecyclerView.ViewHolder{




        public EditarCategoriaviewHolder(View itemView) {
            super(itemView);


        }

    }

}