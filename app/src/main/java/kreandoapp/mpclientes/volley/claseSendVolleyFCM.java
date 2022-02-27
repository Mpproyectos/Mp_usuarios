package kreandoapp.mpclientes.volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class claseSendVolleyFCM {

    String token_cloud = "AAAACWzU_-o:APA91bGFr5-0L_nZvA8KPoZ7Zg1enYOWv7xqWSU8EfRvb_ZOCk0PxxDHMdElmaIwUeFfnDZvCAJrA2odsDWK-nJgzqyw3VP_-kkjf4IDJABk8shcB9GOgx-F0BUyfAqCMkaEZCAz0MW4";


    public void volleyfcm_confoto(String t,String d,String f,String tipo,String ir_a) {
        RequestQueue mRequestQue = Volley.newRequestQueue(getApplicationContext());

        JSONObject json = new JSONObject();
        try {

            json.put("to", tipo);

            JSONObject notificationObj = new JSONObject();
            notificationObj.put("titulo",t);
            notificationObj.put("detalle",d);
            notificationObj.put("fotourl",f);
            notificationObj.put("ir_a",ir_a);


            //replace notification with data when went send data
            json.put("data", notificationObj);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,null,null) {


                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key="+token_cloud);
                    return header;
                }
            };


            mRequestQue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void volleyfcm_sinfoto(String t,String d,String tipo,String ir_a) {
        RequestQueue mRequestQue = Volley.newRequestQueue(getApplicationContext());

        JSONObject json = new JSONObject();
        try {

            json.put("to", tipo);

            JSONObject notificationObj = new JSONObject();
            notificationObj.put("titulo",t);
            notificationObj.put("detalle",d);
            notificationObj.put("ir_a",ir_a);



            //replace notification with data when went send data
            json.put("data", notificationObj);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,null,null) {


                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key="+token_cloud);
                    return header;
                }
            };


            mRequestQue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void volleyfcm_ped_entregado(String t,String d,String tipo,String id,String nped,String firstName,String idreq,String ir_a) {
        RequestQueue mRequestQue = Volley.newRequestQueue(getApplicationContext());

        JSONObject json = new JSONObject();
        try {

            json.put("to", tipo);

            JSONObject notificationObj = new JSONObject();
            notificationObj.put("titulo",t);
            notificationObj.put("detalle",d);
            notificationObj.put("idadmin",id);
            notificationObj.put("npedido",nped);
            notificationObj.put("firstName",firstName);
            notificationObj.put("idreq",idreq);

            notificationObj.put("ir_a",ir_a);



            //replace notification with data when went send data
            json.put("data", notificationObj);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,null,null) {


                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAAOhWdAhQ:APA91bE65oQ2cZKgMkCo1BOrZWm-0WaV8hML55xSav0sydstx5UtEWwlpH4jVYMJQPOw0BKK8mqHxfqyVA8_1greq6UDTspNLj9pnH2gYhZW8oll6B42dp5Z7vKLruK7AHcD8aEojM6H");
                    return header;
                }
            };


            mRequestQue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
