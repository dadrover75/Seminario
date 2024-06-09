package ControlRiego.Controller;

import Comunication.ConnMQTT.IMqttConnection;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ControlRiegoControl {

        private final IMqttConnection mqttConnection;

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

            try {
                int humidity = Integer.parseInt(payload);
                if (humidity < 30) { // TODO buscar valor min o max en configuracion del cultivo
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
                apagarRiego(); // TODO cambiar el tiempo por el tiempo de riego de la configuracion del cultivo
            });
        }

        private void apagarRiego() {
            System.out.println("Desactivando el riego...");
            mqttConnection.publish("actuator/waterpump/1", "off");
        }
    }

