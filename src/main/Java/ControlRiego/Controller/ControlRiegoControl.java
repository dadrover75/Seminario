package ControlRiego.Controller;

import Comunication.ConnMQTT.IMqttConnection;
import ControlRiego.Model.ControlRiegoDAO;
import ControlRiego.View.MosaicoRiego;
import GestionRecursos.Controller.DispositivoControl;
import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ControlRiegoControl {

        private final IMqttConnection mqttConnection;
        private ControlRiegoDAO controlRiegoDAO = new ControlRiegoDAO();
        private DispositivoControl dispositivoControl = new DispositivoControl();
        private MosaicoRiego mosaicoRiego;

        public ControlRiegoControl(IMqttConnection mqttConnection, MosaicoRiego mosaicoRiego) {

            this.mqttConnection = mqttConnection;
            this.mosaicoRiego = mosaicoRiego;

        }

        public void initialize() {

            List<Dispositivo> dispositivos = dispositivoControl.listarDispositivo();
            dispositivos.forEach(dispositivo -> mqttConnection.subscribe(dispositivo.getTopic()));

        }

        public void handleMessage(String topic, MqttMessage message) {

            String payload = new String(message.getPayload());
            Map<String, Integer> infoMap = configCultivo(topic);

            System.out.println("Mensaje recibido en " + topic + ": " + payload);

            if (topic.contains("sensor/humidity")){

                String datoLimpio = String.valueOf(payload).replaceAll("\\s", "");
                try {
                    int humidity = Integer.parseInt(datoLimpio);
                    mosaicoRiego.updateSensorValue(topic, humidity);
                    if (humidity < infoMap.get("humedad_min")) {
                        encenderRiego(topic);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            if (topic.contains("actuator/waterpump")) {

                try {
                    int estado = dispositivoControl.estadoDispositivo(topic);
                    int nuevoEstado = Integer.parseInt(payload);
                    mosaicoRiego.updatePumpState(topic, nuevoEstado);
                    if (nuevoEstado != estado) {
                        dispositivoControl.cambiarEstadoBomba(topic);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        }

        private void encenderRiego(String topic) {
            int tiempo = configCultivo(topic).get("minutos_riego");
            String topicBomba = controlRiegoDAO.getTopicBomba(topic);

            if ( dispositivoControl.estadoDispositivo(topic) == 0 ) {

                System.out.println("Activando el riego...");
                mqttConnection.publish(topicBomba, "1");
                dispositivoControl.cambiarEstadoBomba(topicBomba);
                mosaicoRiego.updatePumpState(topicBomba, 1);
                CompletableFuture.delayedExecutor(tiempo, TimeUnit.SECONDS).execute(() -> { // TODO cambiar el tiempo por minutos despues de pruebas
                    apagarRiego(topicBomba);
                    dispositivoControl.cambiarEstadoBomba(topicBomba);
                    mosaicoRiego.updatePumpState(topicBomba, 0);
                });
            }
        }

        private void apagarRiego(String topic) {

            if ( dispositivoControl.estadoDispositivo(topic) == 1 ) {

                System.out.println("Se completo el ciclo de riego! \nApagando el riego...");
                mqttConnection.publish(topic, "0");
                dispositivoControl.cambiarEstadoBomba(topic);
            }
        }

        private Map<String, Integer> configCultivo(String topic) {

            String topicSensor = topic;
            Map<String, Integer> infoCultivo = controlRiegoDAO.getInfoRiego(topicSensor);

            return infoCultivo;

        }

    }

