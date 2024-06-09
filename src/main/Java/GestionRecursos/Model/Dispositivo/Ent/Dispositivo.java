package GestionRecursos.Model.Dispositivo.Ent;

public class Dispositivo {

    int id;
    String topic;
    int estado;

    public Dispositivo(int id, String topic, int estado) {
        this.id = id;
        this.topic = topic;
        this.estado = estado;
    }

    public Dispositivo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
