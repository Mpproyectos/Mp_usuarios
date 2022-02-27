package kreandoapp.mpclientes.enviarmail;

import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EnviarMail {
    String html_descuento ="";
    String html_zona_envio ="";

    String color1="#0e3457";
    String color2="#3b88cf";
    String color3="#3b88cf";
    String nombreapp = "Disbos 1.0";
    String iconocarrito="https://firebasestorage.googleapis.com/v0/b/uber-78b4b.appspot.com/o/carrito%20blanco.png?alt=media&token=45941cb4-e1fe-45a2-bdcf-5b13801f652d";
    String iconook="https://firebasestorage.googleapis.com/v0/b/uber-78b4b.appspot.com/o/OK.png?alt=media&token=304928a8-1ea8-4542-bf49-105d4784814a";


    public void MailTo_admin_entregado(String mail_admin,String pass_admin,String dato, String npedido,String nombreUser,String estado){
        final String sEmail = mail_admin;
        final String sPassword = pass_admin;



        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        //initialialice session

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail,sPassword);
            }
        });

        try {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //initialice email content

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mail_admin));
            message.setSubject("Encuesta Ped. Nº "+npedido +"-Admin");
            message.setHeader("X-Priority", "High") ;
            message.setContent("", "text/html; charset=utf-8");
            // private String formatomail_satisfaccion(String dato,String nombre,String npedido) {
            message.setContent(formatomail_satisfaccion(dato, nombreUser, npedido,estado), "text/html; charset=utf-8");

            new SendMail().execute(message);


        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
    private String formatomail_satisfaccion(String dato,String nombre,String npedido,String estado) {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




        String todo = "<body style=\"background: #eeeeee \">\n" +
                "   <table width='95%' style=\"margin:  0 auto;border-collapse: collapse\" >\n" +
                "      <tr style=\"background: "+color1+";color: white;font-weight: bold ;font-family: Arial, Helvetica, sans-serif;font-size: 16px\" >\n" +
                "         <td style=\"padding: 3px\"></td>\n" +
                "          \n" +
                "          <td  colspan=\"2\" style=\"padding: 18px;text-align:center\">"+nombreapp+
                "          <img src="+iconocarrito+" width=\"24px\" height=\"24px\"></td>\n" +
                "          <td style=\"padding: 3px\"></td>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;background: white\"><img src="+iconook+" width=\"60px\" height=\"60px\"></td>\n" +
                "                \n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;font-family: Arial, Helvetica, sans-serif;font-size: 16px;font-weight: bolder;color: #333333;background: white;padding-bottom: 16px\">¡Nuevo encuesta de "+nombre+"!</td>\n" +
                "          </tr>\n" +
                "          \n" +
                "                         \n" +
                "          \n" +
                "        \n" +
                "          \n" +
                "          \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Pedido nº:</td>\n" +
                "               <td style=\"padding: 10px;color: white;background:"+color3+";  font-family: Arial, Helvetica, sans-serif;font-size: 20px;font-weight: bold;text-align: center\">"+npedido+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "            <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\"><u>Pedido del cliente:</u></td>\n" +
                "               <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"></td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +


                "            <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Pedido entregado: <b>"+estado+"</b></td>\n" +
                "               <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"></td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +


                "          \n" +
                "            <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">"+dato+"</td>\n" +
                "               <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"></td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +


                "   </table>\n" +
                "</body>";

        return todo;
    }
    public void MailTo_admin(String count,String opcion,String pago,int envio, String datos ,
                             String url_ubicacion,String total,String nombre,String mail,
                             String telefono,String direccion,String mail_admin,
                             String pass_admin,String promo_utilizado,String descuento_cupon,
                             String cupon_utilizado,String zona_envio,String cancela_con){
        final String sEmail = mail_admin;
        final String sPassword = pass_admin;


        int envio_pedido = (envio);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        //initialialice session

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail,sPassword);
            }
        });

        try {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //initialice email content
            //String nombreapp = "Disbos 1.0";
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sEmail));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(mail_admin));
            message.setSubject("Pedido "+nombreapp+" Nº "+count +"-Admin");
            message.setHeader("X-Priority", "High") ;
            message.setContent("", "text/html; charset=utf-8");
            if(opcion.equals("delivery")){
                if(envio_pedido > 0 ){
            message.setContent(formatomail_admin_conenvio(envio_pedido,count,pago,datos,url_ubicacion,total,
                    nombre,mail,telefono,direccion,promo_utilizado,descuento_cupon,cupon_utilizado,zona_envio,cancela_con), "text/html; charset=utf-8");

                }else {
                    message.setContent(formatomail_admin_sinenvio(count,pago,datos,url_ubicacion,total,
                            nombre,mail,telefono,direccion,promo_utilizado,descuento_cupon,cupon_utilizado,cancela_con), "text/html; charset=utf-8");
                }
            }else {
                message.setContent(formatomail_admin_retirar(count,pago,datos,url_ubicacion,total,
                        nombre,mail,telefono,direccion,promo_utilizado,descuento_cupon,cupon_utilizado,cancela_con), "text/html; charset=utf-8");
            }

            new SendMail().execute(message);


        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    public void MailTo_usuario(String count,String opcion,String envio, String datos ,
                             String total,String nombre,String mail,String telefono_Admin,String direccion_admin,
                               String ubicacion_admin,String pago,String mail_admin,String pass_admin,
                               String promo_utilizado,String descuento_cupon,String cupon_utilizado,String zona_envio
                             ){
        final String sEmail = mail_admin;
        final String sPassword = pass_admin;


        String envio_pedido = String.valueOf(envio);
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        //initialialice session

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail,sPassword);
            }
        });

        try {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //initialice email content
            //String nombreapp = "Disbos 1.0";
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject("Pedido "+nombreapp+" Nº "+count );
            message.setHeader("X-Priority", "High") ;
            message.setContent("", "text/html; charset=utf-8");
            if(opcion.equals("delivery")){
                if(!envio_pedido.equals(0.0) ){
                    message.setContent(formatomail_conprecio_envio_cliente_delivery(nombre,count,envio_pedido,datos,
                            total,telefono_Admin,pago,promo_utilizado,descuento_cupon,cupon_utilizado,zona_envio), "text/html; charset=utf-8");
                }else {
                    message.setContent(formatomail_sinprecio_envio_cliente_delivery(nombre,count,envio_pedido,datos,
                            total,telefono_Admin,pago,promo_utilizado,descuento_cupon,cupon_utilizado), "text/html; charset=utf-8");

                }
            }else {
                message.setContent(formatomail_retira_sinpago_deenvio(nombre,count,envio_pedido,datos,total,
                        telefono_Admin,direccion_admin,ubicacion_admin,pago,promo_utilizado,descuento_cupon,cupon_utilizado), "text/html; charset=utf-8");

            }

            new SendMail().execute(message);


        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    private String formatomail_conprecio_envio_cliente_delivery(String nombre_user, String count, String precio_envio,
                                                                String datos,String total,String telefono_admin,String pago,
                                                                String promo_utilizado,String descuento_cupon,String cupon_utilizado,String zona_envio) {


        if(!zona_envio.equals("sinzona")){
            html_zona_envio =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Zona envio:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"><b> "+zona_envio+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }else {
            html_zona_envio =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Envio:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"><b> $"+(precio_envio)+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }

        if(!descuento_cupon.equals("sin_descuento")){


            html_descuento =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Descuento utilizado:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\">Cupon:<b>"+cupon_utilizado+" </b>($"+descuento_cupon+")"+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }

        String todo = "<body style='background: #eeeeee'>" +
                "   <table width='95%' style='margin:  0 auto;border-collapse: collapse'>" +
                "      <tr style='background: "+color1+";color: white;font-weight: bold ;font-family: Arial, Helvetica, sans-serif;font-size: 16px'>" +
                "         <td style='padding: 3px'></td>" +
                "          <td  colspan='2' style='padding: 18px;text-align:center'>"+nombreapp+
                "          <img src='"+iconocarrito+"' width='24px' height='24px'></td>" +
                "          <td style='padding: 3px'></td>" +
                "          <tr>" +
                "              <td colspan='4' style='text-align: center;background: white'><img src='"+iconook+"' width='100px' height='100px'></td>" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;font-family: Arial, Helvetica, sans-serif;font-size: 22px;font-weight: bolder;color: #333333;background: white;padding-bottom: 20px\">¡Gracias ,"+nombre_user+"!</td>\n" +
                "          </tr>\n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px\">Tu pedido se está preparando.</td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px\">Podés comunicarte con nosotros al: </td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "           <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px\"><a href='tel:+"+telefono_admin+"'>+"+telefono_admin+"</a></td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          \n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px \">Tambien Podés enviarnos un WhatsApp: </td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 20px \"><a href=\"https://wa.me/+"+telefono_admin+"\">Click para enviar Whatsapp</a></td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Numero de orden:</td>\n" +
                "               <td style=\"padding: 10px;color: white;background:"+color3+";  font-family: Arial, Helvetica, sans-serif;font-size: 20px;font-weight: bold;text-align: center\">"+count+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "            <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\"><u>Tu pedido:</u></td>\n" +
                "               <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"></td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +

                "            "+datos+
                                html_descuento+
                            html_zona_envio+

                "         \n" +
                "              \n" +
                "              <tr style=\"background: white\">\n" +
                "                 <td></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td></td>\n" +
                "              </tr>\n" +
                "             <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 14px;font-weight: bold\">TOTAL:</td>\n" +
                "               <td style=\"color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 14px;text-align: center;font-weight: bold\">s/"+total+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +

               " </tr>\n" +
        "             <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 14px;font-weight: bold\">PAGO DEL PEDIDO:</td>\n" +
                "               <td style=\"color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 14px;text-align: center;font-weight: bold\">"+pago+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          <tr style=\"background: white\">\n" +
                "                 <td style=\"padding: 5px\"></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td style=\"padding: 5px\"></td>\n" +
                "              </tr>\n" +
                "          \n" +
                "          \n" +
                "   </table>";

        return todo;
    }
    private String formatomail_sinprecio_envio_cliente_delivery(String nombre_user, String count, String precio_envio,
                                                                String datos,String total,String telefono_admin,String pago,
                                                                String promo_utilizado,String descuento_cupon,String cupon_utilizado) {


        if(!descuento_cupon.equals("sin_descuento")){
            html_descuento =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Descuento utilizado:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\">Cupon:<b>"+cupon_utilizado+" </b>($"+descuento_cupon+")"+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }

        String todo = "<body style='background: #eeeeee'>" +
                "   <table width='95%' style='margin:  0 auto;border-collapse: collapse'>" +
                "      <tr style='background: "+color1+";color: white;font-weight: bold ;font-family: Arial, Helvetica, sans-serif;font-size: 16px'>" +
                "         <td style='padding: 3px'></td>" +
                "          <td  colspan='2' style='padding: 18px;text-align:center'>"+nombreapp+
                "          <img src='"+iconocarrito+"' width='24px' height='24px'></td>" +
                "          <td style='padding: 3px'></td>" +
                "          <tr>" +
                "              <td colspan='4' style='text-align: center;background: white'><img src='"+iconook+"' width='100px' height='100px'></td>" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;font-family: Arial, Helvetica, sans-serif;font-size: 22px;font-weight: bolder;color: #333333;background: white;padding-bottom: 20px\">¡Gracias ,"+nombre_user+"!</td>\n" +
                "          </tr>\n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px\">Tu pedido se está preparando.</td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px\">Podés comunicarte con nosotros al: </td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "           <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px\"><a href='tel:+"+telefono_admin+"'>+"+telefono_admin+"</a></td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          \n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px \">Tambien Podés enviarnos un WhatsApp: </td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 20px \"><a href=\"https://wa.me/+"+telefono_admin+"\">Click para enviar Whatsapp</a></td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Numero de orden:</td>\n" +
                "               <td style=\"padding: 10px;color: white;background:"+color3+";  font-family: Arial, Helvetica, sans-serif;font-size: 20px;font-weight: bold;text-align: center\">"+count+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "            <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\"><u>Tu pedido:</u></td>\n" +
                "               <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"></td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +

                "            "+datos+
                        html_descuento+


                "              \n" +
                "              <tr style=\"background: white\">\n" +
                "                 <td></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td></td>\n" +
                "              </tr>\n" +
                "             <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 14px;font-weight: bold\">TOTAL:</td>\n" +
                "               <td style=\"color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 14px;text-align: center;font-weight: bold\">s/"+total+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                " </tr>\n" +
                "             <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 14px;font-weight: bold\">PAGO DEL PEDIDO:</td>\n" +
                "               <td style=\"color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 14px;text-align: center;font-weight: bold\">"+pago+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +

                "          <tr style=\"background: white\">\n" +
                "                 <td style=\"padding: 5px\"></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td style=\"padding: 5px\"></td>\n" +
                "              </tr>\n" +
                "          \n" +
                "          \n" +
                "   </table>";

        return todo;
    }
    private String formatomail_retira_sinpago_deenvio(String nombre_user, String count, String precio_envio,
                                                      String datos,String total,String telefono_admin,String direccion_admin,
                                                      String ubicacion_admin,String pago,
                                                      String promo_utilizado,String descuento_cupon,String cupon_utilizado) {


        if(!descuento_cupon.equals("sin_descuento")){
            html_descuento =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Descuento utilizado:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\">Cupon:<b>"+cupon_utilizado+" </b>($"+descuento_cupon+")"+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }
        String todo = "<body style='background: #eeeeee'>" +
                "   <table width='95%' style='margin:  0 auto;border-collapse: collapse'>" +
                "      <tr style='background: "+color1+";color: white;font-weight: bold ;font-family: Arial, Helvetica, sans-serif;font-size: 16px'>" +
                "         <td style='padding: 3px'></td>" +
                "          <td  colspan='2' style='padding: 18px;text-align:center'>"+nombreapp+
                "          <img src='"+iconocarrito+"' width='24px' height='24px'></td>" +
                "          <td style='padding: 3px'></td>" +
                "          <tr>" +
                "              <td colspan='4' style='text-align: center;background: white'><img src='"+iconook+"' width='100px' height='100px'></td>" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;font-family: Arial, Helvetica, sans-serif;font-size: 22px;font-weight: bolder;color: #333333;background: white;padding-bottom: 20px\">¡Gracias ,"+nombre_user+"!</td>\n" +
                "          </tr>\n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px\">Tu pedido se está preparando.</td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px\">Podés comunicarte con nosotros al: </td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "           <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px\"><a href='tel:+"+telefono_admin+"'>+"+telefono_admin+"</a></td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          \n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 8px \">Tambien Podés enviarnos un WhatsApp: </td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          <tr style=\"background: white;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">\n" +
                "              <td></td>\n" +
                "              <td colspan=\"2\" style=\"padding-bottom: 20px \"><a href=\"https://wa.me/"+telefono_admin+"\">Click para enviar Whatsapp</a></td>\n" +
                "              \n" +
                "              <td></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Numero de orden:</td>\n" +
                "               <td style=\"padding: 10px;color: white;background:"+color3+";  font-family: Arial, Helvetica, sans-serif;font-size: 20px;font-weight: bold;text-align: center\">"+count+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "            <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\"><u>Tu pedido:</u></td>\n" +
                "               <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"></td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +

                "            "+datos+
                html_descuento+

                "         \n" +
                "              \n" +
                "              <tr style=\"background: white\">\n" +
                "                 <td></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td></td>\n" +
                "              </tr>\n" +
                "             <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 14px;font-weight: bold\">TOTAL:</td>\n" +
                "               <td style=\"color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 14px;text-align: center;font-weight: bold\">s/"+total+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          <tr style=\"background: white\">\n" +
                "                 <td style=\"padding: 5px\"></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td style=\"padding: 5px\"></td>\n" +
                "              </tr>\n" +

                " </tr>\n" +
                "             <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 14px;font-weight: bold\">PAGO DEL PEDIDO:</td>\n" +
                "               <td style=\"color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 14px;text-align: center;font-weight: bold\">"+pago+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +

                "<tr style='background: "+color1+"'><td style='padding: 5px;background: white'></td>"+
                "<td  colspan='2' style='padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px'>Datos para retirar pedido:</td>"+
                "<td style='padding: 5px;background: white'></td></tr>"+

                "<tr style='background: "+color2+"'><td style='padding: 5px;background: white'></td>"+
                "<td  colspan='2' style='padding: 10px;color: black;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Dirección:<b/>"+direccion_admin+" </td>"+
                "<td style='padding: 5px;background: white'></td></tr>"+
                "<tr style='background: "+color2+"'><td style='padding: 5px;background: white'></td>"+
                "<td  colspan='2' style='padding: 10px;color: black;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Ubicación gps:<b/><a href='"+ubicacion_admin+"'>Click ver ubicación</a> </td>"+
                "<td style='padding: 5px;background: white'></td></tr>"+

                "   </table>";

        return todo;
    }


    private String formatomail_admin_conenvio(int precio_envio,String count,String pago,String datos,
           String url,String total,String nombre,String mail,String telefono,String direccion,
                                              String promo_utilizado,String descuento_cupon,String cupon_utilizado,String zona_envio,String cancelacon) {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(!zona_envio.equals("sinzona")){
            html_zona_envio =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Zona envio:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"><b> "+zona_envio+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }else {
            html_zona_envio =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Envio:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"><b> $"+(String.valueOf(precio_envio))+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }

        if(!descuento_cupon.equals("sin_descuento")){
            html_descuento =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Descuento utilizado:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\">Cupon:<b>"+cupon_utilizado+" </b>($"+descuento_cupon+")"+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }

        String todo = "<body style=\"background: #eeeeee \">\n" +
                "   <table width='95%' style=\"margin:  0 auto;border-collapse: collapse\" >\n" +
                "      <tr style=\"background: "+color1+";color: white;font-weight: bold ;font-family: Arial, Helvetica, sans-serif;font-size: 16px\" >\n" +
                "         <td style=\"padding: 3px\"></td>\n" +
                "          \n" +
                "          <td  colspan=\"2\" style=\"padding: 18px;text-align:center\">"+nombreapp+
                "          <img src="+iconocarrito+" width=\"24px\" height=\"24px\"></td>\n" +
                "          <td style=\"padding: 3px\"></td>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;background: white\"><img src="+iconook+" width=\"60px\" height=\"60px\"></td>\n" +
                "                \n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;font-family: Arial, Helvetica, sans-serif;font-size: 16px;font-weight: bolder;color: #333333;background: white;padding-bottom: 16px\">¡Nuevo pedido de "+nombre+"!</td>\n" +
                "          </tr>\n" +
                "          \n" +
                "                         \n" +
                "          \n" +
                "        \n" +
                "          \n" +
                "          \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Numero de orden:</td>\n" +
                "               <td style=\"padding: 10px;color: white;background:"+color3+";  font-family: Arial, Helvetica, sans-serif;font-size: 20px;font-weight: bold;text-align: center\">"+count+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "            <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\"><u>Pedido del cliente:</u></td>\n" +
                "               <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"></td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                        datos+



                html_zona_envio+

                html_descuento+



                "         \n" +
                "              \n" +
                "              <tr style=\"background: white\">\n" +
                "                 <td></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td></td>\n" +
                "              </tr>\n" +
                "             <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 14px;font-weight: bold\">TOTAL:</td>\n" +
                "               <td style=\"color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 14px;text-align: center;font-weight: bold\">s/"+total+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          <tr style=\"background: white\">\n" +
                "                 <td style=\"padding: 5px\"></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td style=\"padding: 5px\"></td>\n" +
                "              </tr>\n" +
                "              \n" +
                "            \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td  colspan=\"2\" style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Datos del cliente:</td>\n" +
                "               <td style=\"padding: 5px;background: white\"></td>\n" +


                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Nombre cliente:</b>" + nombre + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+";padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Telefono:</b>" + telefono + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+";padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Dirección:</b>" + direccion + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+";padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Mail:</b>" + mail + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"+

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Estado pago:</b>"+pago+".</td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+

                "</tr>"+

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Cancela con:</b>"+cancelacon+".</td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Envio:</b>Llevar a domicilio del cliente.</td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Ubicación GPS:</b><a href='"+url+"'>Click ver ubicacion</a></td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+


                "   </table>\n" +
                "</body>";

        return todo;
    }

    private String formatomail_admin_sinenvio(String count,String pago,String datos,
                                              String url,String total,String nombre,
                                              String mail,String telefono,String direccion,String promo_utilizado,String descuento_cupon,String cupon_utilizado,String cancelacon) {


        if(!descuento_cupon.equals("sin_descuento")){
            html_descuento =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Descuento utilizado:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\">Cupon:<b>"+cupon_utilizado+" </b>($"+descuento_cupon+")"+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }

        String todo = "<body style=\"background: #eeeeee \">\n" +
                "   <table width='95%' style=\"margin:  0 auto;border-collapse: collapse\" >\n" +
                "      <tr style=\"background: "+color1+";color: white;font-weight: bold ;font-family: Arial, Helvetica, sans-serif;font-size: 16px\" >\n" +
                "         <td style=\"padding: 3px\"></td>\n" +
                "          \n" +
                "          <td  colspan=\"2\" style=\"padding: 18px;text-align:center\">"+nombreapp+
                "          <img src="+iconocarrito+" width=\"24px\" height=\"24px\"></td>\n" +
                "          <td style=\"padding: 3px\"></td>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;background: white\"><img src="+iconook+" width=\"60px\" height=\"60px\"></td>\n" +
                "                \n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;font-family: Arial, Helvetica, sans-serif;font-size: 16px;font-weight: bolder;color: #333333;background: white;padding-bottom: 16px\">¡Nuevo pedido de "+nombre+"!</td>\n" +
                "          </tr>\n" +
                "          \n" +
                "                         \n" +
                "          \n" +
                "        \n" +
                "          \n" +
                "          \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Numero de orden:</td>\n" +
                "               <td style=\"padding: 10px;color: white;background:"+color3+";  font-family: Arial, Helvetica, sans-serif;font-size: 20px;font-weight: bold;text-align: center\">"+count+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "            <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\"><u>Pedido del cliente:</u></td>\n" +
                "               <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"></td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                datos+

                html_descuento+


                "        \n" +
                "              \n" +
                "              <tr style=\"background: white\">\n" +
                "                 <td></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td></td>\n" +
                "              </tr>\n" +
                "             <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 14px;font-weight: bold\">TOTAL:</td>\n" +
                "               <td style=\"color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 14px;text-align: center;font-weight: bold\">s/"+total+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          <tr style=\"background: white\">\n" +
                "                 <td style=\"padding: 5px\"></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td style=\"padding: 5px\"></td>\n" +
                "              </tr>\n" +
                "              \n" +
                "            \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td  colspan=\"2\" style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Datos del cliente:</td>\n" +
                "               <td style=\"padding: 5px;background: white\"></td>\n" +


                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Nombre cliente:</b>" + nombre + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+";padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Telefono:</b>" + telefono + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+";padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Dirección:</b>" + direccion + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+";padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Mail:</b>" + mail + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"+

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Estado pago:</b>"+pago+".</td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+
                "  <tr style='background: white'>" +

                "</tr>"+

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Cancela con:</b>"+cancelacon+".</td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+

                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Envio:</b>Llevar a domicilio del cliente.</td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Ubicación GPS:</b><a href='"+url+"'>Click ver ubicacion</a></td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+


                "   </table>\n" +
                "</body>";

        return todo;
    }

    private String formatomail_admin_retirar(String count,String pago,String datos,
                                              String url,String total,String nombre,
                                              String mail,String telefono,String direccion,String promo_utilizado,String descuento_cupon,String cupon_utilizado,String cancelacon) {


        if(!descuento_cupon.equals("sin_descuento")){
            html_descuento =
                    "<tr style=\"background: white\">\n" +
                            "                <td style=\"padding: 5px\"></td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\">Descuento utilizado:</td>\n" +
                            "\n" +
                            "                <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\">Cupon:<b>"+cupon_utilizado+" </b>($"+descuento_cupon+")"+"</td>\n" +
                            "                <td style=\"padding: 5px;background: white\"></td>\n" +
                            "            </tr>";
        }


        String todo = "<body style=\"background: #eeeeee \">\n" +
                "   <table width='95%' style=\"margin:  0 auto;border-collapse: collapse\" >\n" +
                "      <tr style=\"background: "+color1+";color: white;font-weight: bold ;font-family: Arial, Helvetica, sans-serif;font-size: 16px\" >\n" +
                "         <td style=\"padding: 3px\"></td>\n" +
                "          \n" +
                "          <td  colspan=\"2\" style=\"padding: 18px;text-align:center\">"+nombreapp+
                "          <img src="+iconocarrito+" width=\"24px\" height=\"24px\"></td>\n" +
                "          <td style=\"padding: 3px\"></td>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;background: white\"><img src="+iconook+" width=\"60px\" height=\"60px\"></td>\n" +
                "                \n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td colspan=\"4\" style=\"text-align: center;font-family: Arial, Helvetica, sans-serif;font-size: 16px;font-weight: bolder;color: #333333;background: white;padding-bottom: 16px\">¡Nuevo pedido de "+nombre+"!</td>\n" +
                "          </tr>\n" +
                "          \n" +
                "                         \n" +
                "          \n" +
                "        \n" +
                "          \n" +
                "          \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Numero de orden:</td>\n" +
                "               <td style=\"padding: 10px;color: white;background:"+color3+";  font-family: Arial, Helvetica, sans-serif;font-size: 20px;font-weight: bold;text-align: center\">"+count+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          \n" +
                "            <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"padding: 10px;color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 13px\"><u>Pedido del cliente:</u></td>\n" +
                "               <td style=\"padding: 10px;color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 13px;text-align: center\"></td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                datos+

                html_descuento+


                "        \n" +
                "              \n" +
                "              <tr style=\"background: white\">\n" +
                "                 <td></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td></td>\n" +
                "              </tr>\n" +
                "             <tr style=\"background: white\">\n" +
                "              <td style=\"padding: 5px\"></td>\n" +
                "              <td style=\"color: #333333;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 14px;font-weight: bold\">TOTAL:</td>\n" +
                "               <td style=\"color: #333333;font-family: Arial, Helvetica, sans-serif;font-size: 14px;text-align: center;font-weight: bold\">s/"+total+"</td>\n" +
                "                <td style=\"padding: 5px;background: white\"></td>\n" +
                "          </tr>\n" +
                "          <tr style=\"background: white\">\n" +
                "                 <td style=\"padding: 5px\"></td>\n" +
                "                  <td colspan=\"2\"><hr style=\"border: 0; border-top: 4px solid "+color1+"; border-bottom: 1px solid "+color1+"; height:0;\"></td>\n" +
                "                  <td style=\"padding: 5px\"></td>\n" +
                "              </tr>\n" +
                "              \n" +
                "            \n" +
                "          <tr style=\"background: "+color1+"\">\n" +
                "              <td style=\"padding: 5px;background: white\"></td>\n" +
                "              <td  colspan=\"2\" style=\"padding: 10px;color: white;font-weight: bold;font-family: Arial, Helvetica, sans-serif;font-size: 20px\">Datos del cliente:</td>\n" +
                "               <td style=\"padding: 5px;background: white\"></td>\n" +


                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Nombre cliente:</b>" + nombre + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+";padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Telefono:</b>" + telefono + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+";padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Dirección:</b>" + direccion + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"
                +
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+";padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Mail:</b>" + mail + "</td>" +
                "<td style='padding: 5px;background: white'></td>" +
                "</tr>"+

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Estado pago:</b>"+pago+".</td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+

                "</tr>"+

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Cancela con:</b>"+cancelacon+".</td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+

                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Envio:</b>El cliente retira por la sucursal.</td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+
                "  <tr style='background: white'>" +
                "<td style=padding: 5px;background: white'></td>" +
                "<td  colspan='2' style='background: "+color2+"; padding: 5px;color: black;font-weight: normal;font-family: Arial, Helvetica, sans-serif;font-size: 13px'><b>Ubicación GPS:</b><a href='"+url+"'>Click ver ubicacion</a></td>" +
                "<td style='padding: 5px;background: white'></td>"+
                "</tr>"+


                "   </table>\n" +
                "</body>";

        return todo;
    }
    private class SendMail extends AsyncTask<Message,String,String> {



        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return  "success";

            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }

        }


    }


}
