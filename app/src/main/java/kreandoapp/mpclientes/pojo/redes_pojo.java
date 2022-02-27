package kreandoapp.mpclientes.pojo;

public class redes_pojo {
    String cambiarfoto;
    String imagen;
    String link;
    int orden;


    public redes_pojo() {
    }

    public redes_pojo(String cambiarfoto, String imagen, String link, int orden) {
        this.cambiarfoto = cambiarfoto;
        this.imagen = imagen;
        this.link = link;
        this.orden = orden;
    }

    public String getCambiarfoto() {
        return cambiarfoto;
    }

    public void setCambiarfoto(String cambiarfoto) {
        this.cambiarfoto = cambiarfoto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
