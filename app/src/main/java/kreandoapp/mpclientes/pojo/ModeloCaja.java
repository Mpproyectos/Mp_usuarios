package kreandoapp.mpclientes.pojo;

public class ModeloCaja {
    private String nombre_caja;
    private String id_caja;
    private String fecha;
    private String hora;
    private String id_supervisor;
    private String nombre_supervisor;
    private String id_usuario;
    private String estado;
    private String id_nodo;
    private String tipo_trabajo;

    private String fase1_fecha;
    private String fase1_hora;
    private String fase1_urlfoto;
    private String fase1_urlfoto_file;
    private String fase1_latitud;
    private String fase1_longitud;

    private String fase2_fecha;
    private String fase2_hora;
    private String fase2_urlfoto;
    private String fase2_urlfoto_file;
    private String fase2_latitud;
    private String fase2_longitud;

    private String inicio_caja_fase1_timestamp;
    private String fin_caja_fase2_timestamp;
    private String tiempo_transcurrido;
    private Integer puntos;
    private String visto;

    public ModeloCaja() {
    }

    public ModeloCaja(String nombre_caja, String id_caja, String fecha, String hora, String id_supervisor, String nombre_supervisor, String id_usuario, String estado, String id_nodo, String tipo_trabajo, String fase1_fecha, String fase1_hora, String fase1_urlfoto, String fase1_urlfoto_file, String fase1_latitud, String fase1_longitud, String fase2_fecha, String fase2_hora, String fase2_urlfoto, String fase2_urlfoto_file, String fase2_latitud, String fase2_longitud, String inicio_caja_fase1_timestamp, String fin_caja_fase2_timestamp, String tiempo_transcurrido, Integer puntos, String visto) {
        this.nombre_caja = nombre_caja;
        this.id_caja = id_caja;
        this.fecha = fecha;
        this.hora = hora;
        this.id_supervisor = id_supervisor;
        this.nombre_supervisor = nombre_supervisor;
        this.id_usuario = id_usuario;
        this.estado = estado;
        this.id_nodo = id_nodo;
        this.tipo_trabajo = tipo_trabajo;
        this.fase1_fecha = fase1_fecha;
        this.fase1_hora = fase1_hora;
        this.fase1_urlfoto = fase1_urlfoto;
        this.fase1_urlfoto_file = fase1_urlfoto_file;
        this.fase1_latitud = fase1_latitud;
        this.fase1_longitud = fase1_longitud;
        this.fase2_fecha = fase2_fecha;
        this.fase2_hora = fase2_hora;
        this.fase2_urlfoto = fase2_urlfoto;
        this.fase2_urlfoto_file = fase2_urlfoto_file;
        this.fase2_latitud = fase2_latitud;
        this.fase2_longitud = fase2_longitud;
        this.inicio_caja_fase1_timestamp = inicio_caja_fase1_timestamp;
        this.fin_caja_fase2_timestamp = fin_caja_fase2_timestamp;
        this.tiempo_transcurrido = tiempo_transcurrido;
        this.puntos = puntos;
        this.visto = visto;
    }

    public String getNombre_caja() {
        return nombre_caja;
    }

    public void setNombre_caja(String nombre_caja) {
        this.nombre_caja = nombre_caja;
    }

    public String getId_caja() {
        return id_caja;
    }

    public void setId_caja(String id_caja) {
        this.id_caja = id_caja;
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

    public String getId_supervisor() {
        return id_supervisor;
    }

    public void setId_supervisor(String id_supervisor) {
        this.id_supervisor = id_supervisor;
    }

    public String getNombre_supervisor() {
        return nombre_supervisor;
    }

    public void setNombre_supervisor(String nombre_supervisor) {
        this.nombre_supervisor = nombre_supervisor;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId_nodo() {
        return id_nodo;
    }

    public void setId_nodo(String id_nodo) {
        this.id_nodo = id_nodo;
    }

    public String getTipo_trabajo() {
        return tipo_trabajo;
    }

    public void setTipo_trabajo(String tipo_trabajo) {
        this.tipo_trabajo = tipo_trabajo;
    }

    public String getFase1_fecha() {
        return fase1_fecha;
    }

    public void setFase1_fecha(String fase1_fecha) {
        this.fase1_fecha = fase1_fecha;
    }

    public String getFase1_hora() {
        return fase1_hora;
    }

    public void setFase1_hora(String fase1_hora) {
        this.fase1_hora = fase1_hora;
    }

    public String getFase1_urlfoto() {
        return fase1_urlfoto;
    }

    public void setFase1_urlfoto(String fase1_urlfoto) {
        this.fase1_urlfoto = fase1_urlfoto;
    }

    public String getFase1_urlfoto_file() {
        return fase1_urlfoto_file;
    }

    public void setFase1_urlfoto_file(String fase1_urlfoto_file) {
        this.fase1_urlfoto_file = fase1_urlfoto_file;
    }

    public String getFase1_latitud() {
        return fase1_latitud;
    }

    public void setFase1_latitud(String fase1_latitud) {
        this.fase1_latitud = fase1_latitud;
    }

    public String getFase1_longitud() {
        return fase1_longitud;
    }

    public void setFase1_longitud(String fase1_longitud) {
        this.fase1_longitud = fase1_longitud;
    }

    public String getFase2_fecha() {
        return fase2_fecha;
    }

    public void setFase2_fecha(String fase2_fecha) {
        this.fase2_fecha = fase2_fecha;
    }

    public String getFase2_hora() {
        return fase2_hora;
    }

    public void setFase2_hora(String fase2_hora) {
        this.fase2_hora = fase2_hora;
    }

    public String getFase2_urlfoto() {
        return fase2_urlfoto;
    }

    public void setFase2_urlfoto(String fase2_urlfoto) {
        this.fase2_urlfoto = fase2_urlfoto;
    }

    public String getFase2_urlfoto_file() {
        return fase2_urlfoto_file;
    }

    public void setFase2_urlfoto_file(String fase2_urlfoto_file) {
        this.fase2_urlfoto_file = fase2_urlfoto_file;
    }

    public String getFase2_latitud() {
        return fase2_latitud;
    }

    public void setFase2_latitud(String fase2_latitud) {
        this.fase2_latitud = fase2_latitud;
    }

    public String getFase2_longitud() {
        return fase2_longitud;
    }

    public void setFase2_longitud(String fase2_longitud) {
        this.fase2_longitud = fase2_longitud;
    }

    public String getInicio_caja_fase1_timestamp() {
        return inicio_caja_fase1_timestamp;
    }

    public void setInicio_caja_fase1_timestamp(String inicio_caja_fase1_timestamp) {
        this.inicio_caja_fase1_timestamp = inicio_caja_fase1_timestamp;
    }

    public String getFin_caja_fase2_timestamp() {
        return fin_caja_fase2_timestamp;
    }

    public void setFin_caja_fase2_timestamp(String fin_caja_fase2_timestamp) {
        this.fin_caja_fase2_timestamp = fin_caja_fase2_timestamp;
    }

    public String getTiempo_transcurrido() {
        return tiempo_transcurrido;
    }

    public void setTiempo_transcurrido(String tiempo_transcurrido) {
        this.tiempo_transcurrido = tiempo_transcurrido;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }
}
