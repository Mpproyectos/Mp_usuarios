package kreandoapp.mpclientes.pojo;

public class ModeloLastCaja {
    private  String last_id;
    private  String id_usuario;

    public ModeloLastCaja(String last_id, String id_usuario) {
        this.last_id = last_id;
        this.id_usuario = id_usuario;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
