package kreandoapp.mpclientes.viewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kreandoapp.mpclientes.Interface.ItemClickListener;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_cart_name,txt_price;
    public ImageView img_cart_count;

    public RelativeLayout view_background;
    public LinearLayout view_foreground;

    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView) {
        super(itemView);
        //txt_cart_name = itemView.findViewById(R.id.cart_item_name);
        //txt_price = itemView.findViewById(R.id.cart_item_Price);
       // img_cart_count = itemView.findViewById(R.id.cart_item_count);
       // view_background = itemView.findViewById(R.id.view_background);
        //view_foreground = itemView.findViewById(R.id.view_foreground);


    }

    @Override
    public void onClick(View view) {

    }
}
