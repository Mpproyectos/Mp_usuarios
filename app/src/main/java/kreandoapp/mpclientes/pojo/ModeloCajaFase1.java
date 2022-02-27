package kreandoapp.mpclientes.pojo;

public class ModeloCajaFase1 {


    private String fase1_fecha;
    private String fase1_hora;
    private String fase1_urlfoto;
    private String fase1_urlfoto_file;
    private String fase1_latitud;
    private String fase1_longitud;



    public ModeloCajaFase1() {
    }

    public ModeloCajaFase1(String fase1_fecha, String fase1_hora, String fase1_urlfoto, String fase1_urlfoto_file, String fase1_latitud, String fase1_longitud) {
        this.fase1_fecha = fase1_fecha;
        this.fase1_hora = fase1_hora;
        this.fase1_urlfoto = fase1_urlfoto;
        this.fase1_urlfoto_file = fase1_urlfoto_file;
        this.fase1_latitud = fase1_latitud;
        this.fase1_longitud = fase1_longitud;
    }

    public String getFase1_fecha() {
        return fase1_fecha;
    }

    public void setFase1_fecha(String fase1_fecha) {
        this.fase1_fecha = fase1_fecha;
    }

    public String getFase1_hora() {
        return fase1_hora;
    }

    public void setFase1_hora(String fase1_hora) {
        this.fase1_hora = fase1_hora;
    }

    public String getFase1_urlfoto() {
        return fase1_urlfoto;
    }

    public void setFase1_urlfoto(String fase1_urlfoto) {
        this.fase1_urlfoto = fase1_urlfoto;
    }

    public String getFase1_urlfoto_file() {
        return fase1_urlfoto_file;
    }

    public void setFase1_urlfoto_file(String fase1_urlfoto_file) {
        this.fase1_urlfoto_file = fase1_urlfoto_file;
    }

    public String getFase1_latitud() {
        return fase1_latitud;
    }

    public void setFase1_latitud(String fase1_latitud) {
        this.fase1_latitud = fase1_latitud;
    }

    public String getFase1_longitud() {
        return fase1_longitud;
    }

    public void setFase1_longitud(String fase1_longitud) {
        this.fase1_longitud = fase1_longitud;
    }
}
