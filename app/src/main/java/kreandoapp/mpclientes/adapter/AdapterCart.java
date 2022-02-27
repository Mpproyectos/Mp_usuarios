package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.media.MediaPlayer;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.db.entity.Ordenes;


public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ProductosviewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    MediaPlayer soundborrar;
    List<Ordenes> orderList;
    Context context;

    private Acciones acciones;

    public AdapterCart(List<Ordenes> orderList,Acciones act, Context context) {
        this.orderList = orderList;
        this.acciones = act;
        this.context = context;
    }


    @Override
    public ProductosviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);
        ProductosviewHolder holder = new ProductosviewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ProductosviewHolder holder, final int position) {

        final Ordenes ord = orderList.get(position);
        Picasso.with(context).load(ord.getImagen()).into(holder.img_cart, new Callback() {
            @Override
            public void onSuccess() {
                holder.img_cart.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });







        holder.tv_cantidades.setText("x"+ord.getCantidad());

        Locale locale = new Locale("es","PE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        Double price = Double.parseDouble(ord.getCantidad() ) * Double.parseDouble(ord.getPrecio());
        holder.txt_price.setText(fmt.format(price));//SUB TOTAL RV...
       holder.txt_cart_name.setText(ord.getDetalle() + "("+ord.getCodprod() + ")");
       


       soundborrar = MediaPlayer.create(context,R.raw.borrado_sound);

       holder.btn_delete.setOnClickListener(view -> {

           acciones.accionBorrar(ord.getId(),position);

           Snackbar.make(view, "Producto borrado.", Snackbar.LENGTH_LONG)
                   .show();


           //NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

           // int suma = cart.total_delete + total;


           //cart.txtTotalPrice.setText(Integer.toString(suma)); //total...!!

        /*   int v2 = new Database(getApplicationContext()).getCountCart();
           if(v2==0){

                cart.txtTotalPrice.setText("0");
           }
           Snackbar.make(view, "Producto borrado.", Snackbar.LENGTH_LONG)
                   .show();*/



       });
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ProductosviewHolder extends RecyclerView.ViewHolder{

        public TextView txt_cart_name,txt_price,tv_cantidades;
        public ElegantNumberButton btn_cantidad;
        ImageButton btn_delete;
        ImageView img_cart;
        ProgressBar progressBar;


        public ProductosviewHolder(View itemView) {
            super(itemView);
            txt_cart_name = itemView.findViewById(R.id.tv_nombre_producto);
            txt_price = itemView.findViewById(R.id.cart_item_Price);
            tv_cantidades = itemView.findViewById(R.id.tv_cantidades);

            btn_delete = itemView.findViewById(R.id.cart_item_count);
            img_cart = itemView.findViewById(R.id.img_cart);
            progressBar = itemView.findViewById(R.id.progress_bar);

        }

    }
    public interface Acciones{
        void AccionAgregar(String id, String titulo, String precio);
        void accionBorrar(int id,int posicion);
    }

}