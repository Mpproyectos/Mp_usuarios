package kreandoapp.mpclientes;

import android.content.Intent;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kreandoapp.mpclientes.Notifications.Client;
import kreandoapp.mpclientes.Notifications.Data;
import kreandoapp.mpclientes.Notifications.MyResponse;
import kreandoapp.mpclientes.Notifications.Sender;
import kreandoapp.mpclientes.Notifications.Token;
import kreandoapp.mpclientes.adapter.MessageAdapter;
import kreandoapp.mpclientes.pojo.Chat;
import kreandoapp.mpclientes.pojo.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {
    CircleImageView profileimage;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    String userid ;
    MessageAdapter messageAdapter;

    List<Chat> mchat;
    RecyclerView recyclerView;

    Intent intent;

    APIService apiService;

    boolean notify = false;

    ImageView img_on, img_of;
    MediaPlayer enviado_sound;
    MediaPlayer visto_sound;

    TextView txt_enlinea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_on = findViewById(R.id.img_on);
        img_of = findViewById(R.id.img_off);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        enviado_sound = MediaPlayer.create(this,R.raw.enviado_sound);
        visto_sound = MediaPlayer.create(this,R.raw.visto_sound);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        profileimage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send=findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        txt_enlinea = findViewById(R.id.txt_enlinea);




        //Visto al mensaje...



        intent = getIntent();

        final String userid = intent.getStringExtra("userid");
        //Toast.makeText(this, userid, Toast.LENGTH_SHORT).show();




        FirebaseDatabase database44 = FirebaseDatabase.getInstance();
        DatabaseReference myRef44 = database44.getReference();
        Query query44= myRef44.child("Users").child(userid).child("status");
        query44.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    if(val.equals("online")){

                        btn_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                notify = false;
                                String msg = text_send.getText().toString();
                                if(!msg.equals("")){

                                    visto_sound.start();
                                    sendMessage2(fuser.getUid(),userid,msg);



                                }else{
                                    Toast.makeText(MessageActivity.this, "You cant send empty message", Toast.LENGTH_SHORT).show();
                                }
                                text_send.setText("");
                            }
                        });
                        img_on.setVisibility(View.VISIBLE);
                        img_of.setVisibility(View.GONE);
                        txt_enlinea.setVisibility(View.VISIBLE);
                    }else{
                        btn_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                notify = true;
                                String msg = text_send.getText().toString();
                                if(!msg.equals("")){

                                    enviado_sound.start();
                                    sendMessage(fuser.getUid(),userid,msg);

                                }else{
                                    Toast.makeText(MessageActivity.this, "You cant send empty message", Toast.LENGTH_SHORT).show();
                                }
                                text_send.setText("");
                            }
                        });
                        img_on.setVisibility(View.GONE);
                        img_of.setVisibility(View.VISIBLE);
                        txt_enlinea.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(getApplicationContext(), fuser.getUid(), Toast.LENGTH_SHORT).show();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                username.setText(user.getUsername());

                    Picasso.with(getApplicationContext()).load(user.getImageUrl()).into(profileimage);


                readMesagges(fuser.getUid(),userid,user.getImageUrl());
                //dalevistoalmensaje();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private  void sendMessage2(String sender, final String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        hashMap.put("visto","si");
        hashMap.put("img_profile",String.valueOf(fuser.getPhotoUrl()));



        reference.child("Chats").push().setValue(hashMap);


    }
    private  void sendMessage(String sender, final String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        hashMap.put("visto","no");
        hashMap.put("img_profile",String.valueOf(fuser.getPhotoUrl()));

        reference.child("Chats").push().setValue(hashMap);

        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if(notify){
                    sendNotification(receiver,user.getUsername(),msg);
                }

                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //inicio send noti
    private void sendNotification(String receiver, final String username, final String message) {

        final String xx = intent.getStringExtra("userid");

        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, username + ": " + message, "new Mensaje", xx);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200) {

                                        assert response.body() != null;
                                        if (response.body().success != 1) {
                                            Toast.makeText(MessageActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    //fin send noti

    private void readMesagges(final String myid,final String userid,final String imageurl){



        mchat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myid)&& chat.getSender().equals(userid)||
                            chat.getReceiver().equals(userid)&& chat.getSender().equals(myid)){
                        mchat.add(chat);

                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this,mchat,imageurl);

                    recyclerView.setAdapter(messageAdapter);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void dalevistoalmensaje() {
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database2.getReference();
        Query query2 = myRef3.child("Chats").orderByChild("visto").equalTo("no");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        final String key = childSnapshot.getKey();
                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                        DatabaseReference myRef3 = database2.getReference();
                        Query query2 = myRef3.child("Chats").child(key).child("receiver");
                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String val = dataSnapshot.getValue(String.class);
                                if(fuser.getUid().equals(val)){

                                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef3 = database2.getReference("Chats").child(key).child("visto");
                                   myRef3.setValue("si");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private  void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status",status);
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");

    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
        finish();

    }




}
