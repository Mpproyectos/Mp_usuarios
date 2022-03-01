package kreandoapp.mpclientes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Random;

import kreandoapp.mpclientes.clientes.home1;
import kreandoapp.mpclientes.clientes.misPedidos;
import kreandoapp.mpclientes.clientes.promociones;
import kreandoapp.mpclientes.clientes.satisActivity;
import kreandoapp.mpclientes.modoadmin.ModoAdmin;

public class FcmMessaginService extends FirebaseMessagingService {
    private NotificationManagerCompat notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        super.onMessageReceived(remoteMessage);


            if(remoteMessage.getData().size()==7){
                final String t, d, f,ir_a,id,n,idreq;
                t = remoteMessage.getData().get("titulo");
                d = remoteMessage.getData().get("detalle");
                id = remoteMessage.getData().get("idadmin");
                n = remoteMessage.getData().get("npedido");
                idreq = remoteMessage.getData().get("idreq");
                f = remoteMessage.getData().get("firstName");
                ir_a = remoteMessage.getData().get("ir_a");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    mayor_a_oreo_entregado(t,d,id,n,f,idreq,ir_a);
                }else {
                    menor_a_oreo_entregado(t,d,id,n,f,idreq,ir_a);
                }

            }
         else if (remoteMessage.getData().size() == 4) {

             final String t, d, f,ir_a;
             t = remoteMessage.getData().get("titulo");
             d = remoteMessage.getData().get("detalle");
             f = remoteMessage.getData().get("fotourl");
             ir_a = remoteMessage.getData().get("ir_a");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    mayor_a_oreo_confoto(t,d,f,ir_a);
                }else {
                    menor_a_oreo_confoto(t,d,f,ir_a);
                }


         }
         else if(remoteMessage.getData().size() == 3){
             final String t, d,ir_a;
             t = remoteMessage.getData().get("titulo");
             d = remoteMessage.getData().get("detalle");
             ir_a = remoteMessage.getData().get("ir_a");

             if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                 mayor_a_oreo_sinfoto(t,d,ir_a);
             }else {
                 menor_a_oreo_sinfoto(t,d,ir_a);
             }
         }

    }

    private void mayor_a_oreo_sinfoto(String t, String d,String ir_a) {
        if(ir_a.equals("promo")){
            String id = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
                nc.setDescription("Notificacion FCM");
                nc.setShowBadge(true);
                assert nm != null;
                nm.createNotificationChannel(nc);
            }

            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(t)
                    .setTicker("Nueva notificacion")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(d)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(d))
                    .setContentIntent(irapromociones())
                    .setContentInfo("nuevo");

            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());
        }
        if(ir_a.equals("mispedidos")){
            String id = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
                nc.setDescription("Notificacion FCM");
                nc.setShowBadge(true);
                assert nm != null;
                nm.createNotificationChannel(nc);
            }

            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(t)
                    .setTicker("Nueva notificacion")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(d)

                    .setStyle(new NotificationCompat.BigTextStyle().bigText(d))
                    .setContentIntent(iramispedidos())
                    .setContentInfo("nuevo");

            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());
        }
        if(ir_a.equals("modoadmin")){
            String id = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
                nc.setDescription("Notificacion FCM");
                nc.setShowBadge(true);
                assert nm != null;
                nm.createNotificationChannel(nc);
            }

            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(t)
                    .setTicker("Nueva notificacion")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(d)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(d))
                    .setContentIntent(iramodoadmin())
                    .setContentInfo("nuevo");

            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());
        }

    }
    private void mayor_a_oreo_confoto(String t, String d, String f,String ir_a) {
        if(ir_a.equals("promo")){
            String id = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
                nc.setDescription("Notificacion FCM");
                nc.setShowBadge(true);
                assert nm != null;
                nm.createNotificationChannel(nc);
            }
            try {
                Bitmap img_foto = Picasso.with(getApplicationContext()).load(f).get();
                builder.setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(t)
                        .setTicker("Nueva notificacion")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(img_foto).bigLargeIcon(null))
                        .setContentText(d)
                        .setContentIntent(irapromociones())
                        .setContentInfo("nuevo");
            } catch (IOException e) {
                e.printStackTrace();
            }


            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());

        }
        if(ir_a.equals("mispedidos")){
            String id = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
                nc.setDescription("Notificacion FCM");
                nc.setShowBadge(true);
                assert nm != null;
                nm.createNotificationChannel(nc);
            }
            try {
                Bitmap img_foto = Picasso.with(getApplicationContext()).load(f).get();
                builder.setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(t)
                        .setTicker("Nueva notificacion")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(img_foto).bigLargeIcon(null))
                        .setContentText(d)
                        .setContentIntent(iramispedidos())
                        .setContentInfo("nuevo");
            } catch (IOException e) {
                e.printStackTrace();
            }


            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());

        }
        if(ir_a.equals("modoadmin")){
            String id = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
                nc.setDescription("Notificacion FCM");
                nc.setShowBadge(true);
                assert nm != null;
                nm.createNotificationChannel(nc);
            }
            try {
                Bitmap img_foto = Picasso.with(getApplicationContext()).load(f).get();
                builder.setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(t)
                        .setTicker("Nueva notificacion")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(img_foto).bigLargeIcon(null))
                        .setContentText(d)
                        .setContentIntent(iramodoadmin())
                        .setContentInfo("nuevo");
            } catch (IOException e) {
                e.printStackTrace();
            }


            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());

        }

    }

    private void menor_a_oreo_sinfoto(String t,String d,String ir_a) {
        if(ir_a.equals("promo")){
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String id = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(t)
                    .setTicker("Nueva notificacion")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(d)
                    .setSound(sounduri)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(d))
                    .setContentIntent(irapromociones())
                    .setContentInfo("nuevo");

            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());
        }
        if(ir_a.equals("modoadmin")){
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String id = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(t)
                    .setTicker("Nueva notificacion")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(d)
                    .setSound(sounduri)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(d))
                    .setContentIntent(iramodoadmin())
                    .setContentInfo("nuevo");

            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());
        }
        if(ir_a.equals("mispedidos")){
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String id = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(t)
                    .setTicker("Nueva notificacion")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(d)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(d))
                    .setSound(sounduri)
                    .setContentIntent(iramispedidos())
                    .setContentInfo("nuevo");

            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());
        }


    }
    private void menor_a_oreo_confoto(String t,String d,String f,String ir_a) {
        if(ir_a.equals("promo")){
            try {
                Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Bitmap img_foto = Picasso.with(getApplicationContext()).load(f).get();
                String id = "message";
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
                builder.setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(t)
                        .setTicker("Nueva notificacion")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(img_foto).bigLargeIcon(null))
                        .setContentText(d)
                        .setSound(sounduri)
                        .setContentIntent(irapromociones())
                        .setContentInfo("nuevo");

                Random random = new Random();
                int idNotify = random.nextInt(8000);

                assert nm != null;
                nm.notify(idNotify,builder.build());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(ir_a.equals("mispedidos")){
            try {
                Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Bitmap img_foto = Picasso.with(getApplicationContext()).load(f).get();
                String id = "message";
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
                builder.setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(t)
                        .setTicker("Nueva notificacion")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(img_foto).bigLargeIcon(null))
                        .setContentText(d)
                        .setSound(sounduri)
                        .setContentIntent(iramispedidos())
                        .setContentInfo("nuevo");

                Random random = new Random();
                int idNotify = random.nextInt(8000);

                assert nm != null;
                nm.notify(idNotify,builder.build());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(ir_a.equals("modoadmin")){
            try {
                Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Bitmap img_foto = Picasso.with(getApplicationContext()).load(f).get();
                String id = "message";
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
                builder.setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(t)
                        .setTicker("Nueva notificacion")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(img_foto).bigLargeIcon(null))
                        .setContentText(d)
                        .setSound(sounduri)
                        .setContentIntent(iramodoadmin())
                        .setContentInfo("nuevo");

                Random random = new Random();
                int idNotify = random.nextInt(8000);

                assert nm != null;
                nm.notify(idNotify,builder.build());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    private void mayor_a_oreo_entregado(String t, String d,String id,String f,String n,String idreq,String ir_a) {


        if(ir_a.equals("modosatis")){
            String ids = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,ids);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
                nc.setDescription("Notificacion FCM");
                nc.setShowBadge(true);
                assert nm != null;
                nm.createNotificationChannel(nc);
            }
            Intent intent1 = new Intent(this,satisActivity.class);
            intent1.putExtra("idadmin",id);
            intent1.putExtra("first",f);
            intent1.putExtra("nped",n);
            intent1.putExtra("idreq",idreq);
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(
                    this,0,intent1,PendingIntent.FLAG_ONE_SHOT
            );

            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(t)
                    .setTicker("Nueva notificacion")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(d)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(d))
                    .setContentIntent(pendingIntent1)
                    .setContentInfo("nuevo");

            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());
        }
    }
    private void menor_a_oreo_entregado(String t, String d,String id,String f,String n,String idreq,String ir_a) {

        if(ir_a.equals("modosatis")){

            Intent intent1 = new Intent(this,satisActivity.class);
            intent1.putExtra("idadmin",id);
            intent1.putExtra("first",f);
            intent1.putExtra("nped",n);
            intent1.putExtra("idreq",idreq);
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(
                    this,0,intent1,PendingIntent.FLAG_ONE_SHOT
            );
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String ids = "message";
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,ids);
            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(t)
                    .setTicker("Nueva notificacion")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(d)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(d))
                    .setSound(sounduri)
                    .setContentIntent(pendingIntent1)
                    .setContentInfo("nuevo");

            Random random = new Random();
            int idNotify = random.nextInt(8000);

            assert nm != null;
            nm.notify(idNotify,builder.build());
        }

    }

    public PendingIntent irapromociones () {
                Intent nf = new Intent(getApplicationContext(), promociones.class);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }
    public PendingIntent iramodoadmin () {
        Intent nf = new Intent(getApplicationContext(), home1.class);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }
    public PendingIntent iramispedidos () {
        Intent nf = new Intent(getApplicationContext(), misPedidos.class);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }
    public PendingIntent iraSatis (String id,String f,String n) {
        Intent nf = new Intent(getApplicationContext(), satisActivity.class);
        nf.putExtra("idadmin",id);
        nf.putExtra("first",f);
        nf.putExtra("nped",n);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }






}
