package kreandoapp.mpclientes.pojo;

public class modelo_prod {

    private String codProd;
    private String prodNombre;
    private String prodImagen;
    private String prodDescripcion;
    private String prodPrecio;
    private String prodId;
    private String prodCategoriaID;
    private String prodCantidad;
    private String  prodUrlenproceso;
    private String   prodStock;
    private int orden;

    public modelo_prod() {
    }

    public modelo_prod(String codProd, String prodNombre, String prodImagen, String prodDescripcion, String prodPrecio, String prodId, String prodCategoriaID, String prodCantidad, String prodUrlenproceso, String prodStock, int orden) {
        this.codProd = codProd;
        this.prodNombre = prodNombre;
        this.prodImagen = prodImagen;
        this.prodDescripcion = prodDescripcion;
        this.prodPrecio = prodPrecio;
        this.prodId = prodId;
        this.prodCategoriaID = prodCategoriaID;
        this.prodCantidad = prodCantidad;
        this.prodUrlenproceso = prodUrlenproceso;
        this.prodStock = prodStock;
        this.orden = orden;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public String getProdImagen() {
        return prodImagen;
    }

    public void setProdImagen(String prodImagen) {
        this.prodImagen = prodImagen;
    }

    public String getProdDescripcion() {
        return prodDescripcion;
    }

    public void setProdDescripcion(String prodDescripcion) {
        this.prodDescripcion = prodDescripcion;
    }

    public String getProdPrecio() {
        return prodPrecio;
    }

    public void setProdPrecio(String prodPrecio) {
        this.prodPrecio = prodPrecio;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdCategoriaID() {
        return prodCategoriaID;
    }

    public void setProdCategoriaID(String prodCategoriaID) {
        this.prodCategoriaID = prodCategoriaID;
    }

    public String getProdCantidad() {
        return prodCantidad;
    }

    public void setProdCantidad(String prodCantidad) {
        this.prodCantidad = prodCantidad;
    }

    public String getProdUrlenproceso() {
        return prodUrlenproceso;
    }

    public void setProdUrlenproceso(String prodUrlenproceso) {
        this.prodUrlenproceso = prodUrlenproceso;
    }

    public String getProdStock() {
        return prodStock;
    }

    public void setProdStock(String prodStock) {
        this.prodStock = prodStock;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}


