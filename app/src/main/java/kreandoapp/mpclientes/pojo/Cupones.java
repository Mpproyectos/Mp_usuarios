package kreandoapp.mpclientes.pojo;

public class Cupones {
    private int porcentaje;
    private int condicion;
    private String estado;
    private String id;
    private int monto;
    private String utilizado;
    private String modo;
    private  int count_uso;
    private int orden;
    private String fecha;
    private String hora;

    public Cupones() {
    }

    public Cupones(int porcentaje, int condicion, String estado, String id, int monto, String utilizado, String modo, int count_uso, int orden, String fecha, String hora) {
        this.porcentaje = porcentaje;
        this.condicion = condicion;
        this.estado = estado;
        this.id = id;
        this.monto = monto;
        this.utilizado = utilizado;
        this.modo = modo;
        this.count_uso = count_uso;
        this.orden = orden;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(String utilizado) {
        this.utilizado = utilizado;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public int getCount_uso() {
        return count_uso;
    }

    public void setCount_uso(int count_uso) {
        this.count_uso = count_uso;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
