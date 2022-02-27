package kreandoapp.mpclientes.pojo;

public class Demoras {
    private  String id;
    private int orden;
    private String demora;

    public Demoras() {
    }

    public Demoras(String id, int orden, String demora) {
        this.id = id;
        this.orden = orden;
        this.demora = demora;
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

    public String getDemora() {
        return demora;
    }

    public void setDemora(String demora) {
        this.demora = demora;
    }
}
