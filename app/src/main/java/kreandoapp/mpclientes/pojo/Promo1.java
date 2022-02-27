package kreandoapp.mpclientes.pojo;

public class Promo1 {
   private int condicion;
    private int descuento;
    private int maxdesc;
    private  String estado;
    private int porcentaje;

    public Promo1() {
    }

    public Promo1(int condicion, int descuento, int maxdesc, String estado, int porcentaje) {
        this.condicion = condicion;
        this.descuento = descuento;
        this.maxdesc = maxdesc;
        this.estado = estado;
        this.porcentaje = porcentaje;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getMaxdesc() {
        return maxdesc;
    }

    public void setMaxdesc(int maxdesc) {
        this.maxdesc = maxdesc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }
}
