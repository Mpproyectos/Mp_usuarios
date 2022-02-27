package kreandoapp.mpclientes.pojo;

public class SubCategoria {
    String subnombreSub;
    String idcate;
    String idsub;
    int orden;
    String subImagen;
    String subUrlCambiar;

    public SubCategoria() {
    }

    public SubCategoria(String subnombreSub, String idcate, String idsub, int orden, String subImagen, String subUrlCambiar) {
        this.subnombreSub = subnombreSub;
        this.idcate = idcate;
        this.idsub = idsub;
        this.orden = orden;
        this.subImagen = subImagen;
        this.subUrlCambiar = subUrlCambiar;
    }

    public String getSubnombreSub() {
        return subnombreSub;
    }

    public void setSubnombreSub(String subnombreSub) {
        this.subnombreSub = subnombreSub;
    }

    public String getIdcate() {
        return idcate;
    }

    public void setIdcate(String idcate) {
        this.idcate = idcate;
    }

    public String getIdsub() {
        return idsub;
    }

    public void setIdsub(String idsub) {
        this.idsub = idsub;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getSubImagen() {
        return subImagen;
    }

    public void setSubImagen(String subImagen) {
        this.subImagen = subImagen;
    }

    public String getSubUrlCambiar() {
        return subUrlCambiar;
    }

    public void setSubUrlCambiar(String subUrlCambiar) {
        this.subUrlCambiar = subUrlCambiar;
    }
}