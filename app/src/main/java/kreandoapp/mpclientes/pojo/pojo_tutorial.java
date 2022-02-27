package kreandoapp.mpclientes.pojo;

public class pojo_tutorial {
    String usuario;
    String imagen;
    String imagenacambiar;

    public pojo_tutorial() {
    }

    public pojo_tutorial(String usuario, String imagen, String imagenacambiar) {
        this.usuario = usuario;
        this.imagen = imagen;
        this.imagenacambiar = imagenacambiar;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagenacambiar() {
        return imagenacambiar;
    }

    public void setImagenacambiar(String imagenacambiar) {
        this.imagenacambiar = imagenacambiar;
    }
}
