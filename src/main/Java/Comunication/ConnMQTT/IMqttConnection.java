package Comunication.ConnMQTT;

public interface IMqttConnection {

    void connect();
    void disconnect();
    void publish(String topic, String message);
    void subscribe(String topic);
    void setMessageListener(MqttSingleton.MessageListener messageListener);
}
