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
import kreandoapp.mpclientes.modoadmin.Demoras.EditarDemoraActivity;
import kreandoapp.mpclientes.pojo.Demoras;


public class DemorasAdapter extends RecyclerView.Adapter<DemorasAdapter.DemorasViewHolder>{


    List<Demoras> demorasList;
    private Context context;

    public DemorasAdapter(List<Demoras> demorasList, Context context) {
        this.demorasList = demorasList;
       this.context =context;
    }



    @Override
    public DemorasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.demoras_list_row,parent,false);
        DemorasViewHolder holder= new DemorasViewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final DemorasViewHolder holder, final int position) {
        //final Context context=holder.imagen.getContext();

        final Demoras dem = demorasList.get(position);
        holder.tv_orden.setText("Orden:" + String.valueOf(dem.getOrden()));
        holder.tv_demora.setText("Demora:" + dem.getDemora());

        holder.btn_editar.setOnClickListener(v -> {

            Intent i = new Intent(v.getContext(), EditarDemoraActivity.class);
            i.putExtra("id",dem.getId());
            i.putExtra("demora",dem.getDemora());
            v.getContext().startActivity(i);


        });




    }


    @Override
    public int getItemCount() {
        return demorasList.size();
    }

    public class DemorasViewHolder extends RecyclerView.ViewHolder{

        TextView tv_orden,tv_demora;
        CardView card_view;
        Button btn_editar;

        public DemorasViewHolder(View itemView) {
            super(itemView);
        btn_editar = itemView.findViewById(R.id.btn_editar);
        tv_orden = itemView.findViewById(R.id.tv_orden);
        tv_demora = itemView.findViewById(R.id.tv_demora);
        card_view = itemView.findViewById(R.id.card_view);

           // tv_nombre_prod = itemView.findViewById(R.id.tv_nombre_producto);

            //mail = itemView.findViewById(R.id.tv_mail);

        }

    }

}