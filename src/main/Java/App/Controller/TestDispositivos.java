package App.Controller;

import Comunication.ConnMQTT.IMqttConnection;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestDispositivos {
    private static final String[] TOPICS = {
            "sensor/humidity/1",
            "sensor/humidity/2",
            "sensor/humidity/3",
            "sensor/humidity/4",
            "sensor/humidity/5",
            "sensor/humidity/6"
    };

    private final IMqttConnection mqttConnection;
    private final Random random = new Random();

    public TestDispositivos(IMqttConnection mqttConnection) {
        this.mqttConnection = mqttConnection;
    }

    public void startSendingMessages() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(TOPICS.length);

        for (String topic : TOPICS) {
            executorService.scheduleAtFixedRate(() -> sendRandomHumidity(topic), 0, 10, TimeUnit.SECONDS);
        }
    }

    private void sendRandomHumidity(String topic) {
        int humidity = 15 + random.nextInt(66); // Generates a random number between 15 and 80
        String message = String.valueOf(humidity);

        mqttConnection.publish(topic, message);
        System.out.println("Sent message to " + topic + ": " + message);
    }
}
