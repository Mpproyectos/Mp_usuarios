package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.modoadmin.detallePedido;
import kreandoapp.mpclientes.pojo.Pedidos;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.newpedidorequestviewHolder>{


    List<Pedidos> requestList;
    private Context context;

    public RequestAdapter(List<Pedidos> requestList) {
        this.requestList = requestList;

    }



    @Override
    public newpedidorequestviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newreq_row,parent,false);
        newpedidorequestviewHolder holder= new newpedidorequestviewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final newpedidorequestviewHolder holder, final int position) {
        //final Context context=holder.imagen.getContext();


        final Pedidos r = requestList.get(position);
        if(r.getNameprod() != null){
            String string = r.getNameprod();
            String[] parts = string.split(" ");
            String part1 = parts[0]; // Primer nombre---
            holder.tv_nombre.setText(part1);
        }





        holder.npedido.setText("Ped. Nº"+r.getPedido());


        holder.hora.setText(r.getHora());

        final Calendar c = Calendar.getInstance();

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String fechaactual = dateFormat.format(c.getTime());

        String fechapedido= r.getFecha();

        if(fechaactual.equals(fechapedido)){
            holder.fecha.setText("Hoy");
        }else {
            holder.fecha.setText(r.getFecha());
        }


        if(r.getVisto().equals("si")){
            holder.visto.setVisibility(View.VISIBLE);
            holder.nuevo.setVisibility(View.GONE);
        }else {
            holder.nuevo.setVisibility(View.VISIBLE);
            holder.visto.setVisibility(View.GONE);
        }


        if(r.getEstado().equals("enviado")){
            holder.enviado.setVisibility(View.VISIBLE);
        }else {
            holder.enviado.setVisibility(View.GONE);
        }
        if(r.getEstado().equals("cancelado")){
            holder.cancelado.setVisibility(View.VISIBLE);
            holder.visto.setVisibility(View.GONE);
        }else{
            holder.cancelado.setVisibility(View.GONE);
        }

        if(r.getEstado().equals("canceladouser")){
            holder.cancelado.setVisibility(View.VISIBLE);
        }else {

        }
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database2.getReference();
        Query query2 = myRef3.child("Pedidos").child(r.getIdrequest()).child("EncuestaSatis").child("visto");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if(dataSnapshot.exists()){
                   if(val.equals("no")){
                       holder.img_msj_negro.setVisibility(View.VISIBLE);
                       holder.img_msj_verde.setVisibility(View.GONE);
                   }else {
                       holder.img_msj_verde.setVisibility(View.VISIBLE);
                       holder.img_msj_negro.setVisibility(View.GONE);
                   }
                }else {
                    holder.img_msj_negro.setVisibility(View.GONE);
                    holder.img_msj_verde.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                DatabaseReference myRef3 = database2.getReference();
                Query query2 = myRef3.child("Pedidos").orderByChild("keykey").equalTo(r.getKeykey());
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            String keyreq = childSnapshot.getKey();
                            Intent intent = new Intent(v.getContext(), detallePedido.class);
                            intent.putExtra("total",r.getTotal());
                            intent.putExtra("estado",r.getEstado());
                            intent.putExtra("direccion",r.getDireccion());
                            intent.putExtra("keyreqlist",keyreq);
                            intent.putExtra("idReceiver",r.getId());
                            intent.putExtra("userAnotificar",r.getNameprod());
                            intent.putExtra("ubicacion",r.getUbicacion());
                            intent.putExtra("mitelefono",r.getTelefono());
                            intent.putExtra("nombre",r.getNameprod());
                            intent.putExtra("envio",r.getEnvio());
                            intent.putExtra("visto",r.getVisto());
                            intent.putExtra("modoenvio",r.getRetirasucursal());
                            intent.putExtra("modopago",r.getPagocontarjeta());
                            intent.putExtra("cancelacon",r.getCancelacon());


                            intent.putExtra("numerodepedido",r.getPedido());
                            intent.putExtra("cuponutilizado",r.getCuponutilizado());
                            intent.putExtra("promoutilizada",r.getPromoutilizada());
                            intent.putExtra("montocupon",r.getMonto_cupon());

                            v.getContext().startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });
    }


    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class newpedidorequestviewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre,fecha,hora,npedido;
        CardView cardView;
        ImageView enviado,nuevo,cancelado,visto,reserva;
        ImageView img_msj_negro,img_msj_verde;


        public newpedidorequestviewHolder(View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            fecha = itemView.findViewById(R.id.tv_fecha_req);
            hora = itemView.findViewById(R.id.tv_hora_req);
            enviado = itemView.findViewById(R.id.img_send);
            nuevo = itemView.findViewById(R.id.img_new);
            cancelado = itemView.findViewById(R.id.img_cancels);
            cardView = itemView.findViewById(R.id.card_view_prod);
            visto = itemView.findViewById(R.id.img_visto);
            reserva = itemView.findViewById(R.id.img_reserva);
            npedido = itemView.findViewById(R.id.tv_npedido);
            img_msj_negro = itemView.findViewById(R.id.img_msj_negro);
            img_msj_verde = itemView.findViewById(R.id.img_msj_verde);

           // tv_nombre_prod = itemView.findViewById(R.id.tv_nombre_producto);

            //mail = itemView.findViewById(R.id.tv_mail);

        }

    }

}