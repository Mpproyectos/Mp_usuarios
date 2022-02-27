package kreandoapp.mpclientes.Notifications;

public class Sender2 {
    public Data2 data;
    public String to;

    public Sender2(Data2 data, String to) {
        this.data = data;
        this.to = to;
    }

    public Data2 getData() {
        return data;
    }

    public void setData(Data2 data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
