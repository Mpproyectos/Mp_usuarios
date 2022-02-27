package kreandoapp.mpclientes.pojo;

public class Chat {
    private String sender;
    private String receiver;
    private String message;
    private String visto;
    private String img_profile;


    public Chat() {
    }

    public Chat(String sender, String receiver, String message,String visto,String img_profile) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
       this.visto = visto;
       this.img_profile =img_profile;
    }

    public String getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(String img_profile) {
        this.img_profile = img_profile;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
