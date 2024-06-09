package ControlRiego.Controller;

import Comunication.ConnMQTT.IMqttConnection;
import ControlRiego.Model.ControlRiegoDAO;
import GestionRecursos.Model.Dispositivo.DAO.DispositivoDAO;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ControlRiegoControl {

        private final IMqttConnection mqttConnection;
        private ControlRiegoDAO controlRiegoDAO = new ControlRiegoDAO();
        private DispositivoDAO dispositivoDAO = new DispositivoDAO();

        public ControlRiegoControl(IMqttConnection mqttConnection) {
            this.mqttConnection = mqttConnection;
        }

        public void initialize() { // TODO hacer un loop con la lista de dispositivos que tienen el topic de la configuracion de un cultivo
            mqttConnection.subscribe("sensor/humidity/2"); // TODO buscar topic en configuracion del cultivo
            mqttConnection.subscribe("sensor/humidity/3"); // TODO buscar topic en configuracion del cultivo
        }

        public void handleMessage(String topic, MqttMessage message) {
            String payload = new String(message.getPayload());
            System.out.println("Mensaje recibido en " + topic + ": " + payload);
            int humedad_min = configCultivo(topic).get("humedad_min");

            try {
                int humidity = Integer.parseInt(payload);
                if (humidity < humedad_min) {
                    encenderRiego();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        private void encenderRiego() {
            // TODO pedir estado de la bomba para cambiar el estado, por ejemplo publish algun get estado,
            // que devuelva el estado actual de la bomba, y de acuerdo a eso cambiar el estado.
            // Para apagar un set timeout con los minuto de la configuracion del cultivo, y publicar que apague
            System.out.println("Activando el riego...");
            mqttConnection.publish("actuator/waterpump/1", "on");
            CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
                apagarRiego(); // TODO cambiar el tiempo por minutos despues de pruebas
            });
        }

        private void apagarRiego() {
            System.out.println("Se completo el ciclo de riego \n \tApagando el riego..");
            mqttConnection.publish("actuator/waterpump/1", "off");
        }

        private Map<String, Integer> configCultivo(String topic) {

            String topicSensor = topic;
            Map<String, Integer> infoCultivo = controlRiegoDAO.getInfoCultivo(topicSensor);

            return infoCultivo;

        }

    }

