package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.modoadmin.Cupones.verDetalleCupActivity;
import kreandoapp.mpclientes.pojo.Cupones;


public class VerCuponesAdapter extends RecyclerView.Adapter<VerCuponesAdapter.VerCuponesViewHolder>{


    List<Cupones> cuponesList;
    private Context context;

    public VerCuponesAdapter(List<Cupones> cuponesList,Context context) {
        this.cuponesList = cuponesList;
       this.context =context;
    }



    @Override
    public VerCuponesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cupones_list_row,parent,false);
        VerCuponesViewHolder holder= new VerCuponesViewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final VerCuponesViewHolder holder, final int position) {
        //final Context context=holder.imagen.getContext();

        final Cupones cup = cuponesList.get(position);
        holder.tv_ncupon.setText("Cup.Nº"+String.valueOf(cup.getOrden()));
        holder.tv_codigo.setText(cup.getId());
        holder.tv_fecha.setText(cup.getFecha());
        holder.tv_hora.setText(cup.getHora());

       holder.card_view.setOnClickListener(v -> {
            Intent i =new Intent(context, verDetalleCupActivity.class);
            i.putExtra("id",cup.getId());
            v.getContext().startActivity(i);
       });



    }


    @Override
    public int getItemCount() {
        return cuponesList.size();
    }

    public class VerCuponesViewHolder extends RecyclerView.ViewHolder{

        TextView tv_ncupon,tv_codigo,tv_fecha,tv_hora;
        CardView card_view;

        public VerCuponesViewHolder(View itemView) {
            super(itemView);
        tv_ncupon = itemView.findViewById(R.id.tv_ncupon);
        tv_codigo = itemView.findViewById(R.id.tv_codigo);
        tv_fecha = itemView.findViewById(R.id.tv_fecha_cup);
        tv_hora = itemView.findViewById(R.id.tv_hora_cup);
        card_view = itemView.findViewById(R.id.card_view);
           // tv_nombre_prod = itemView.findViewById(R.id.tv_nombre_producto);

            //mail = itemView.findViewById(R.id.tv_mail);

        }

    }

}