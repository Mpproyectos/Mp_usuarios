package kreandoapp.mpclientes.adapter;

import android.content.Context;
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
import kreandoapp.mpclientes.db.entity.Ordenes;


public class DetalleproductosUserAdapter extends RecyclerView.Adapter<DetalleproductosUserAdapter.requestpedidorequestviewHolder>{


    List<Ordenes> order2List;
    private Context context;

    public DetalleproductosUserAdapter(List<Ordenes> order2List, Context context) {
        this.order2List = order2List;
        this.context = context;

    }



    @Override
    public requestpedidorequestviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detallepedidouser,parent,false);
        requestpedidorequestviewHolder holder= new requestpedidorequestviewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final requestpedidorequestviewHolder holder, final int position) {

        final Ordenes ord = order2List.get(position);
        holder.tv_cantidad.setText("x"+ord.getCantidad());
        holder.tv_producto.setText(ord.getDetalle());
        if(ord.getCantidad().equals("1")){
            holder.tv_subtotal.setText("s/ "+ord.getPrecio());
        }else{
            Double price = (Double.parseDouble(ord.getPrecio()))*Double.parseDouble(ord.getCantidad());
            holder.tv_subtotal.setText("s/ "+ String.valueOf(price));
        }
        Picasso.with(context).load(ord.getImagen()).into(holder.img_prod_detalle, new Callback() {
            @Override
            public void onSuccess() {
                holder.img_prod_detalle.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });




    }


    @Override
    public int getItemCount() {
        return order2List.size();
    }

    public class requestpedidorequestviewHolder extends RecyclerView.ViewHolder{

        TextView tv_cantidad,tv_producto,tv_subtotal;
        CardView card_view_prod;
        ImageView img_prod_detalle;
        ProgressBar progressBar;

        public requestpedidorequestviewHolder(View itemView) {
            super(itemView);
            tv_cantidad = itemView.findViewById(R.id.tv_cantidad);
            tv_producto = itemView.findViewById(R.id.tv_nombre_producto);
            tv_subtotal = itemView.findViewById(R.id.tv_subtotal);
            card_view_prod = itemView.findViewById(R.id.card_view_prod);
            img_prod_detalle = itemView.findViewById(R.id.img_prod_detalle);
            progressBar = itemView.findViewById(R.id.progress_bar);

        }

    }

}