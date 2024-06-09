package Comunication.ConnMQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttSingleton implements IMqttConnection {

    private static MqttSingleton instance = null;
    private IMqttClient client;
    private MessageListener messageListener;
    private String brokerUrl = "tcp://test.mosquitto.org:1883";
    private String clientId = "javaClient";


    private MqttSingleton() {
        try {
            client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static MqttSingleton getInstance() {

        if (instance == null) {
            instance = new MqttSingleton();
        }
        return instance;

    }

    @Override
    public void connect() {

        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
            System.out.println("Conectado al broker: " + brokerUrl);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disconnect() {

        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                System.out.println("Desconectado del broker");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void publish(String topic, String message) {

        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(2);
            client.publish(topic, mqttMessage);
            System.out.println("Mensaje publicado: " + message);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void subscribe(String topic) {

        try {
            client.subscribe(topic, (t, msg) -> {
                if (messageListener != null) {
                    messageListener.onMessage(t, msg);
                }
            });
            System.out.println("Suscrito al tópico: " + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public interface MessageListener {
        void onMessage(String topic, MqttMessage message);
    }

}
