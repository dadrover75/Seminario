package App.Controller;

import Comunication.ConnMQTT.IMqttConnection;
import Comunication.ConnBD.DataBaseConnection;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestDispositivos {
    // Array de topics para simular dispositivos
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
        // Asegurarse de que las bombas comiencen apagadas
        String sql = "UPDATE dispositivos SET estado = 0 WHERE topic LIKE '%actuator/waterpump/%'";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("Todas las bombas han sido apagadas.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Iniciar el envío de mensajes simulados
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(TOPICS.length);
        for (String topic : TOPICS) {
            executorService.scheduleAtFixedRate(() -> sendRandomHumidity(topic), 0, 15, TimeUnit.SECONDS);
        }
    }

    private void sendRandomHumidity(String topic) {
        int humidity = 15 + random.nextInt(66); // ramdom entre 15 y 80
        String message = String.valueOf(humidity);

        mqttConnection.publish(topic, message);
        System.out.println("Sent message to " + topic + ": " + message);
    }
}
