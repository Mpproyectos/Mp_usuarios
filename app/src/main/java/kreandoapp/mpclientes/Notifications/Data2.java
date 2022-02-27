package kreandoapp.mpclientes.Notifications;

public class Data2 {
    private String titulo;
    private String detalle;
    private String fotourl;


    public Data2() {
    }

    public Data2(String titulo, String detalle, String fotourl) {
        this.titulo = titulo;
        this.detalle = detalle;
        this.fotourl = fotourl;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getFotourl() {
        return fotourl;
    }

    public void setFotourl(String fotourl) {
        this.fotourl = fotourl;
    }
}