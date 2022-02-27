package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.ProcesoVenta.Carrito;
import kreandoapp.mpclientes.pojo.Order;


public class AdapterFotos extends RecyclerView.Adapter<AdapterFotos.FotosviewHolder>{
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    List<Order> orderList;
    Context context;
    Carrito cart;



    public AdapterFotos(List<Order> orderList, Carrito cart, Context context) {
        this.orderList = orderList;
        this.cart = cart;
        this.context = context;
    }


    @Override
    public FotosviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(cart).inflate(R.layout.row_cart,parent,false);
        FotosviewHolder holder= new FotosviewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final FotosviewHolder holder, final int position) {

        final Order ord = orderList.get(position);
        Picasso.with(context).load(ord.getImage()).into(holder.img_cart, new Callback() {
            @Override
            public void onSuccess() {
                holder.img_cart.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });




    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class FotosviewHolder extends RecyclerView.ViewHolder{

        public TextView txt_cart_name,txt_price,tv_cantidades;
        public ElegantNumberButton btn_cantidad;
        ImageButton btn_delete;
        ImageView img_cart;
        ProgressBar progressBar;


        public FotosviewHolder(View itemView) {
            super(itemView);
            txt_cart_name = itemView.findViewById(R.id.tv_nombre_producto);
            txt_price = itemView.findViewById(R.id.cart_item_Price);
            tv_cantidades = itemView.findViewById(R.id.tv_cantidades);
            btn_delete = itemView.findViewById(R.id.cart_item_count);
            img_cart = itemView.findViewById(R.id.img_cart);
            progressBar = itemView.findViewById(R.id.progress_bar);

        }

    }

}