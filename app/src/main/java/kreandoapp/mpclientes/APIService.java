package kreandoapp.mpclientes;

import kreandoapp.mpclientes.Notifications.MyResponse;
import kreandoapp.mpclientes.Notifications.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {

                  "Content-Type:application/json",
                  "Authorization:key=AAAACWzU_-o:APA91bGFr5-0L_nZvA8KPoZ7Zg1enYOWv7xqWSU8EfRvb_ZOCk0PxxDHMdElmaIwUeFfnDZvCAJrA2odsDWK-nJgzqyw3VP_-kkjf4IDJABk8shcB9GOgx-F0BUyfAqCMkaEZCAz0MW4"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
