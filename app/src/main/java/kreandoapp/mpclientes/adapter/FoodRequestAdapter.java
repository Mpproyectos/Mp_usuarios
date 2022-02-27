package kreandoapp.mpclientes.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.db.entity.Ordenes;


public class FoodRequestAdapter extends RecyclerView.Adapter<FoodRequestAdapter.requestpedidorequestviewHolder>{


    List<Ordenes> order2List;
    private Context context;

    public FoodRequestAdapter(List<Ordenes> order2List) {
        this.order2List = order2List;

    }



    @Override
    public requestpedidorequestviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.verpedidorow,parent,false);
        requestpedidorequestviewHolder holder= new requestpedidorequestviewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final requestpedidorequestviewHolder holder, final int position) {
        //final Context context=holder.imagen.getContext();



        final Ordenes ord = order2List.get(position);
        holder.tv_cantidad.setText("X"+ord.getCantidad());
        holder.tv_producto.setText(ord.getDetalle());
        if(ord.getCantidad().equals("1")){
            holder.tv_subtotal.setText("s/"+ord.getPrecio());
        }else{
            Double price = Double.parseDouble(ord.getPrecio())* (Double.parseDouble(ord.getCantidad()));
            holder.tv_subtotal.setText("s/"+ String.valueOf(price));
        }


        holder.tv_code.setText("("+ord.getCodprod()+")");



    }


    @Override
    public int getItemCount() {
        return order2List.size();
    }

    public class requestpedidorequestviewHolder extends RecyclerView.ViewHolder{

        TextView tv_cantidad,tv_producto,tv_subtotal,tv_code;
        CardView card_view_prod;



        public requestpedidorequestviewHolder(View itemView) {
            super(itemView);
            tv_cantidad = itemView.findViewById(R.id.tv_cantidad);
            tv_producto = itemView.findViewById(R.id.tv_nombre_producto);
            tv_subtotal = itemView.findViewById(R.id.tv_subtotal);
            tv_code = itemView.findViewById(R.id.tv_cod);
            card_view_prod = itemView.findViewById(R.id.card_view_prod);


        }

    }

}