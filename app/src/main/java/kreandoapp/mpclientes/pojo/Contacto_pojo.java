package kreandoapp.mpclientes.pojo;

public class Contacto_pojo {

    String foto;
    String link;
    String cambiarfoto;


    public Contacto_pojo() {
    }
    public Contacto_pojo(String foto, String link, String cambiarfoto) {
        this.foto = foto;
        this.link = link;
        this.cambiarfoto = cambiarfoto;
    }



    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCambiarfoto() {
        return cambiarfoto;
    }

    public void setCambiarfoto(String cambiarfoto) {
        this.cambiarfoto = cambiarfoto;
    }
}
