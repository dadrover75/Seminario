package ControlRiego.Controller;

import Comunication.ConnMQTT.IMqttConnection;
import ControlRiego.Model.ControlRiegoDAO;
import ControlRiego.View.CultivoPanel;
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
        private final List<CultivoPanel> cultivoPanels;

        public ControlRiegoControl(IMqttConnection mqttConnection, List<CultivoPanel> cultivoPaneles) {
            this.mqttConnection = mqttConnection;
            this.cultivoPanels = cultivoPaneles;
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

                int idCultivo = infoMap.get("id_cultivo");
                Dispositivo sensor = dispositivoControl.getSensorEsByCultivo(idCultivo);
                Dispositivo bomba = dispositivoControl.buscarBombasPorCultivo(idCultivo);

                String datoLimpio = String.valueOf(payload).replaceAll("\\s", "");
                try {
                    int humidity = Integer.parseInt(datoLimpio);
                    for (CultivoPanel panel : cultivoPanels) {

                            if (topic.equals(sensor.getTopic())) {
                                panel.setHumidity(humidity);
                            }

                        /*if (topic.equals(panel.getPumpTopic())) {
                            panel.setHumidity(humidity);
                        }*/
                    }

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
                    if (nuevoEstado != estado) {
                        dispositivoControl.cambiarEstadoBomba(topic);
                    }
                    for (CultivoPanel panel : cultivoPanels) {
                        if (topic.equals(panel.getPumpTopic())) {
                            panel.setPumpState(nuevoEstado == 1);
                        }
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
                // TODO actualizar en pantalla
                CompletableFuture.delayedExecutor(tiempo, TimeUnit.SECONDS).execute(() -> { // TODO cambiar el tiempo por minutos despues de pruebas
                    apagarRiego(topicBomba);
                    dispositivoControl.cambiarEstadoBomba(topicBomba);
                });
            }
        }

        private void apagarRiego(String topic) {

            if ( dispositivoControl.estadoDispositivo(topic) == 1 ) {

                System.out.println("Se completo el ciclo de riego! \nApagando el riego...");
                mqttConnection.publish(topic, "0");
                dispositivoControl.cambiarEstadoBomba(topic);
                // TODO actualizar en pantalla
            }
        }

        private Map<String, Integer> configCultivo(String topic) {

            String topicSensor = topic;
            Map<String, Integer> infoCultivo = controlRiegoDAO.getInfoRiego(topicSensor);

            return infoCultivo;

        }

    }

