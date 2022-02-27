package kreandoapp.mpclientes.pojo;

public class Categoria {
    private String catNombre;
    private String catImage;
    private String catUrlCambiarImagen;
    private String catId;
    private int orden;


    public Categoria() {
    }

    public Categoria(String catNombre, String catImage, String catUrlCambiarImagen, String catId, int orden) {
        this.catNombre = catNombre;
        this.catImage = catImage;
        this.catUrlCambiarImagen = catUrlCambiarImagen;
        this.catId = catId;
        this.orden = orden;
    }

    public String getCatNombre() {
        return catNombre;
    }

    public void setCatNombre(String catNombre) {
        this.catNombre = catNombre;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getCatUrlCambiarImagen() {
        return catUrlCambiarImagen;
    }

    public void setCatUrlCambiarImagen(String catUrlCambiarImagen) {
        this.catUrlCambiarImagen = catUrlCambiarImagen;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}

