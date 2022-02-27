package kreandoapp.mpclientes.pojo;

public class Zonas {
    private  String id;
    private int orden;
    private int valor;
    private String nombre;

    public Zonas() {
    }

    public Zonas(String id, int orden, int valor, String nombre) {
        this.id = id;
        this.orden = orden;
        this.valor = valor;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
