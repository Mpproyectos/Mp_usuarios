package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.modoadmin.Zonas.EditarZonasActivity;
import kreandoapp.mpclientes.pojo.Zonas;


public class ZonasAdapter extends RecyclerView.Adapter<ZonasAdapter.ZonasViewHolder>{


    List<Zonas> zonasList;
    private Context context;

    public ZonasAdapter(List<Zonas> zonasList, Context context) {
        this.zonasList = zonasList;
       this.context =context;
    }



    @Override
    public ZonasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zonas_list_row,parent,false);
        ZonasViewHolder holder= new ZonasViewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final ZonasViewHolder holder, final int position) {
        final Zonas dem = zonasList.get(position);
        int cant = 20;
        String strOut = dem.getNombre();
        if(strOut.length() < cant){
            holder.tv_nombre.setText(dem.getNombre());
        }else {
            String result = strOut.substring(0, cant) + "...";// count start in 0 and 8 is excluded
            holder.tv_nombre.setText(result);
        }



        holder.tv_orden.setText("Orden:" + String.valueOf(dem.getOrden()));






        holder.btn_editar.setOnClickListener(v -> {

            Intent i = new Intent(v.getContext(), EditarZonasActivity.class);
            i.putExtra("id",dem.getId());
            i.putExtra("nombre",dem.getNombre());
            v.getContext().startActivity(i);


        });




    }


    @Override
    public int getItemCount() {
        return zonasList.size();
    }

    public class ZonasViewHolder extends RecyclerView.ViewHolder{

        TextView tv_orden,tv_nombre;
        CardView card_view;
        Button btn_editar;

        public ZonasViewHolder(View itemView) {
            super(itemView);
        btn_editar = itemView.findViewById(R.id.btn_editar);
        tv_orden = itemView.findViewById(R.id.tv_orden);
        tv_nombre = itemView.findViewById(R.id.tv_nombre);
        card_view = itemView.findViewById(R.id.card_view);



        }

    }

}