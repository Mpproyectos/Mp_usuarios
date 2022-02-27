package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.clientes.DetallepedidoUserActivity;
import kreandoapp.mpclientes.pojo.Pedidos;


public class AdapterMisPedidos extends RecyclerView.Adapter<AdapterMisPedidos.MispedidosviewHolder>{


    List<Pedidos> requestList;
    private Context context;

    public AdapterMisPedidos(List<Pedidos> requestList) {
        this.requestList = requestList;

    }



    @Override
    public MispedidosviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mispedidosrow,parent,false);
        MispedidosviewHolder holder= new MispedidosviewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final MispedidosviewHolder holder, final int position) {
        //final Context context=holder.imagen.getContext();
        final Pedidos req = requestList.get(position);

       holder.tv_npedido.setText("Pedido nº: "+req.getPedido());

        holder.tv_fechayhora.setText(req.getFecha() + " "+ req.getHora());

        holder.tv_estado.setText(req.getEstado());

        holder.btn_detallepedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(v.getContext(), DetallepedidoUserActivity.class);
                intent.putExtra("id",req.getIdrequest());
                intent.putExtra("numerodepedido",req.getPedido());
                intent.putExtra("total",req.getTotal());
                intent.putExtra("envio",req.getEnvio());

                intent.putExtra("fecha",req.getFecha());
                intent.putExtra("hora",req.getHora());
                intent.putExtra("retirar",req.getRetirasucursal());
                intent.putExtra("zonaenvio",req.getZonaEnvio());
                intent.putExtra("pago",req.getPagocontarjeta());
                intent.putExtra("pago",req.getPagocontarjeta());
                intent.putExtra("cancelacon",req.getCancelacon());


                v.getContext().startActivity(intent);
            }
        });

        /*holder.tv_pedidoenviadon.setText("Pedido nº: "+req.getPedido()+ " Enviado!");
        holder.tv_pedidocancelad.setText("Pedido nº: "+req.getPedido()+ " Cancelado!");

        holder.hora.setText(req.getHora());

        final Calendar c = Calendar.getInstance();

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String fechaactual = dateFormat.format(c.getTime());

        String fechapedido= req.getFecha();

        if(fechaactual.equals(fechapedido) && !req.getEstado().equals("enviado") ) {
            holder.fecha.setText("Hoy");
            holder.btn_cancelar.setVisibility(View.VISIBLE);
        }else {
            holder.fecha.setText(req.getFecha());
            holder.btn_cancelar.setVisibility(View.GONE);
        }

        if(req.getEstado().equals("canceladouser")){
            holder.img_cancelado.setVisibility(View.VISIBLE);
            holder.btn_cancelar.setVisibility(View.GONE);
        }

        if(req.getEstado().equals("enviado")){
            holder.img_enviado.setVisibility(View.VISIBLE);
            holder.img_cancelado.setVisibility(View.GONE);
            holder.demoraestimada.setText(req.getDemora());
            holder.demoraestimada.setVisibility(View.VISIBLE);
            holder.LinearDemora.setVisibility(View.VISIBLE);

        }
        if(req.getEstado().equals("cancelado")){
            holder.img_enviado.setVisibility(View.GONE);
            holder.img_cancelado.setVisibility(View.VISIBLE);
            holder.motivocancelacion.setVisibility(View.VISIBLE);
            holder.motivocancelacion.setText(req.getMotivocancelacion());
            holder.LinearCancelacion.setVisibility(View.VISIBLE);
        }
        if(req.getVisto().equals("si")){
            holder.visto.setVisibility(View.VISIBLE);
        }else {
            holder.visto.setVisibility(View.GONE);
        }

        holder.btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                final DatabaseReference myRef4 = database3.getReference();
                final Query query= myRef4.child("Requests").orderByChild("keykey").equalTo(req.getKeykey());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                String key = childSnapshot.getKey();
                                Intent i = new Intent(view.getContext(), cancelarPedidoActivity.class);
                                i.putExtra("idkey",key);
                                i.putExtra("idid",req.getPedido());
                                view.getContext().startActivity(i);

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        });
*/

    }


    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class MispedidosviewHolder extends RecyclerView.ViewHolder{

        TextView tv_npedido,precio,fecha,hora,motivocancelacion,demoraestimada;
        ImageView img_enviado,img_cancelado,visto;
        LinearLayout LinearDemora,LinearCancelacion;
        TextView tv_pedidoenviadon,tv_pedidocancelad;
        Button btn_cancelar;
        TextView tv_fechayhora;
        TextView tv_estado;
        Button btn_detallepedido;


        public MispedidosviewHolder(View itemView) {
            super(itemView);

            tv_fechayhora = itemView.findViewById(R.id.tv_fechayhora);

            fecha = itemView.findViewById(R.id.tv_fecha);
            hora = itemView.findViewById(R.id.tv_hora);
            img_enviado = itemView.findViewById(R.id.img_send);

            visto =itemView.findViewById(R.id.img_visto);

            demoraestimada = itemView.findViewById(R.id.tv_demoraestimada);
            //mail = itemView.findViewById(R.id.tv_mail);
            LinearDemora = itemView.findViewById(R.id.Linear_demora);

            tv_npedido = itemView.findViewById(R.id.tv_npedido);
            tv_pedidoenviadon = itemView.findViewById(R.id.tv_pedidoenviadon);

            btn_cancelar = itemView.findViewById(R.id.btn_cancelar);
            tv_estado = itemView.findViewById(R.id.tv_estado);
            btn_detallepedido  = itemView.findViewById(R.id.btn_verdetalle);
        }

    }

}