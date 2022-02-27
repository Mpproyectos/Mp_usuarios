package kreandoapp.mpclientes.pojo;

public class ModoZona {
    String nombre;
    int valor;
    int orden;

    public ModoZona() {
    }

    public ModoZona(String nombre, int valor, int orden) {
        this.nombre = nombre;
        this.valor = valor;
        this.orden = orden;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
