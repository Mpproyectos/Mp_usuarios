package kreandoapp.mpclientes.pojo;

public class User {
    private String id;
    private String username;
    private String imageUrl;
    private  String midireccion;
    private  String mitelefono;
    private  int countpedidos;
    private String mail;
    private String latitud_user;
    private String longitud_user;
    private String timestamp;


    public User(String id, String username, String imageUrl, String midireccion, String mitelefono, int countpedidos, String mail, String latitud_user, String longitud_user, String timestamp) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.midireccion = midireccion;
        this.mitelefono = mitelefono;
        this.countpedidos = countpedidos;
        this.mail = mail;
        this.latitud_user = latitud_user;
        this.longitud_user = longitud_user;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMidireccion() {
        return midireccion;
    }

    public void setMidireccion(String midireccion) {
        this.midireccion = midireccion;
    }

    public String getMitelefono() {
        return mitelefono;
    }

    public void setMitelefono(String mitelefono) {
        this.mitelefono = mitelefono;
    }

    public int getCountpedidos() {
        return countpedidos;
    }

    public void setCountpedidos(int countpedidos) {
        this.countpedidos = countpedidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLatitud_user() {
        return latitud_user;
    }

    public void setLatitud_user(String latitud_user) {
        this.latitud_user = latitud_user;
    }

    public String getLongitud_user() {
        return longitud_user;
    }

    public void setLongitud_user(String longitud_user) {
        this.longitud_user = longitud_user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
