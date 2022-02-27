package kreandoapp.mpclientes.pojo;

public class Coctelpojo {
    String idpush;
    String titulo;
    String miniatura;
    String urlcambiar;
    int orden;

    public Coctelpojo() {
    }

    public Coctelpojo(String idpush, String titulo, String miniatura, String urlcambiar, int orden) {
        this.idpush = idpush;
        this.titulo = titulo;
        this.miniatura = miniatura;
        this.urlcambiar = urlcambiar;
        this.orden = orden;
    }

    public String getIdpush() {
        return idpush;
    }

    public void setIdpush(String idpush) {
        this.idpush = idpush;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public String getUrlcambiar() {
        return urlcambiar;
    }

    public void setUrlcambiar(String urlcambiar) {
        this.urlcambiar = urlcambiar;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
