package kreandoapp.mpclientes.pojo;

public class TrabajoxDia {

    private String fecha;
    private String estado;
    private String id;
    private String horainicio;

    public TrabajoxDia() {
    }

    public TrabajoxDia(String fecha, String estado, String id, String horainicio) {
        this.fecha = fecha;
        this.estado = estado;
        this.id = id;
        this.horainicio = horainicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }
}
