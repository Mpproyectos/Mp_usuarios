package kreandoapp.mpclientes.retrofit;


import io.reactivex.Observable;
import kreandoapp.mpclientes.models.PagoDetalles;
import kreandoapp.mpclientes.models.ResponsePago;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {
    String TOKEN = "APP_USR-875153567457308-112900-0d487bceb0daa786aae08b95c1ddcc49-628372420"; //reemplezar con su accestoken

    @POST ("preferences?access_token=" + TOKEN)
    Observable<ResponsePago> obtenerDatosPago(@Body PagoDetalles pagoDetalles);



}
