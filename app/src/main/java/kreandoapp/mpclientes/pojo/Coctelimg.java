package kreandoapp.mpclientes.pojo;

public class Coctelimg {
    String idcoctelimg;
    String foto;
    String urlfotocambiar;
    String idcatefoto;
    int orden;

    public Coctelimg() {
    }

    public Coctelimg(String idcoctelimg, String foto, String urlfotocambiar, String idcatefoto, int orden) {
        this.idcoctelimg = idcoctelimg;
        this.foto = foto;
        this.urlfotocambiar = urlfotocambiar;
        this.idcatefoto = idcatefoto;
        this.orden = orden;
    }

    public String getIdcoctelimg() {
        return idcoctelimg;
    }

    public void setIdcoctelimg(String idcoctelimg) {
        this.idcoctelimg = idcoctelimg;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUrlfotocambiar() {
        return urlfotocambiar;
    }

    public void setUrlfotocambiar(String urlfotocambiar) {
        this.urlfotocambiar = urlfotocambiar;
    }

    public String getIdcatefoto() {
        return idcatefoto;
    }

    public void setIdcatefoto(String idcatefoto) {
        this.idcatefoto = idcatefoto;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
