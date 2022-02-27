package kreandoapp.mpclientes.pojo;

public class EncuestaSatis {
    String mensaje;
    String entregado;
    String idped;
    String fecha;
    String hora;
    String visto;

    public EncuestaSatis() {
    }

    public EncuestaSatis(String mensaje, String entregado, String idped, String fecha, String hora, String visto) {
        this.mensaje = mensaje;
        this.entregado = entregado;
        this.idped = idped;
        this.fecha = fecha;
        this.hora = hora;
        this.visto = visto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEntregado() {
        return entregado;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }

    public String getIdped() {
        return idped;
    }

    public void setIdped(String idped) {
        this.idped = idped;
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

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }
}
