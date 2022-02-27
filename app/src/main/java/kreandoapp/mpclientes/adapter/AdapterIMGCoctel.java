package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.detectorInternet;
import kreandoapp.mpclientes.modoadmin.Coctels.EditarDatosCoctelActivity;
import kreandoapp.mpclientes.pojo.Coctelimg;


public class AdapterIMGCoctel extends RecyclerView.Adapter<AdapterIMGCoctel.coctelviewholderimg>{


    List<Coctelimg> coctellist;
    public Context context;
    detectorInternet internet;

    public AdapterIMGCoctel(List<Coctelimg> coctellist, Context context) {
        this.coctellist = coctellist;
        this.context = context;

    }



    @Override
    public coctelviewholderimg onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_imgcoctel,parent,false);
        coctelviewholderimg holder= new coctelviewholderimg(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final coctelviewholderimg holder, final int position) {



        internet = new detectorInternet(context);

        final Coctelimg coctel = coctellist.get(position);


        Picasso.with(context).load(coctel.getFoto()).into(holder.img_categorias, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
                holder.img_categorias.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }
        });



        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        assert user != null;
        DatabaseReference myRef22 = database111.getReference("modoadmin").child("id");
        myRef22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);

                    if(val.equals(user.getUid())){

                        holder.cardView.setOnClickListener(v -> {

                           // Toast.makeText(context, "Soy admin", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(v.getContext(), EditarDatosCoctelActivity.class);
                            i.putExtra("orden",coctel.getOrden());
                            i.putExtra("imagen",coctel.getFoto());
                            i.putExtra("urlcambiar",coctel.getUrlfotocambiar());
                            i.putExtra("idimg",coctel.getIdcoctelimg());


                             v.getContext().startActivity(i);



                        });

                    }else{

                        Toast.makeText(context, "Soy user", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }


    @Override
    public int getItemCount() {
        return coctellist.size();
    }

    public class coctelviewholderimg extends RecyclerView.ViewHolder{

        TextView tv_nombre;
        CardView cardView;
        ImageView img_categorias;
        ProgressBar progressBar;


        public coctelviewholderimg(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            img_categorias = itemView.findViewById(R.id.img_coctel);
            progressBar = itemView.findViewById(R.id.progress_row_cate);
            //mail = itemView.findViewById(R.id.tv_mail);

        }

    }

}