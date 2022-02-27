package kreandoapp.mpclientes.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kreandoapp.mpclientes.MessageActivity;

import kreandoapp.mpclientes.R;
import kreandoapp.mpclientes.pojo.User;


public class AdapterUser extends RecyclerView.Adapter<AdapterUser.UserAdapterviewHolder>{


    List<User> userList;
    private Context context;
    private boolean ischat;

    public AdapterUser(List<User> userList,boolean ischat) {
        this.userList = userList;
        this.ischat = ischat;
    }



    @Override
    public UserAdapterviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        UserAdapterviewHolder holder= new UserAdapterviewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final UserAdapterviewHolder holder, final int position) {
        final Context context=holder.profile_image.getContext();
        final User userAll = userList.get(position);
            holder.username.setText(userAll.getUsername());


        Picasso.with(context).load(userAll.getImageUrl()).into(holder.profile_image);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MessageActivity.class);
                    intent.putExtra("userid",userAll.getId());
                    v.getContext().startActivity(intent);
                }
            });


    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserAdapterviewHolder extends RecyclerView.ViewHolder{

            TextView username;
            ImageView img_on,img_off,profile_image;

        public UserAdapterviewHolder(View itemView) {
            super(itemView);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            username = itemView.findViewById(R.id.tv_username);
            profile_image = itemView.findViewById(R.id.profile_image);

        }

    }

}