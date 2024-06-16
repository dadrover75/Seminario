package ControlRiego.Controller;

import Comunication.ConnMQTT.IMqttConnection;
import ControlRiego.Model.ControlRiegoDAO;
import ControlRiego.View.ContenedorRiego;
import ControlRiego.View.Mosaico;
import GestionRecursos.Controller.CultivoControl;
import GestionRecursos.Controller.DispositivoControl;
import GestionRecursos.Model.Cultivo.Ent.Cultivo;
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
    private CultivoControl cultivoControl = new CultivoControl();
    private List<Cultivo> cultivos = cultivoControl.listarCultivo();
    private List<Dispositivo> dispositivos;
    private ContenedorRiego contenedorRiego = new ContenedorRiego(cultivos, this);
    //private MosaicoRiego mosaicoRiego = new MosaicoRiego();
    ///////////////////////////////////// probar mosaico///////////////////////////////////////
    //private List<Dispositivo> dispo = new ArrayList<>();
    //private Mosaico mosaico = new Mosaico();

    public ControlRiegoControl(IMqttConnection mqttConnection/*, MosaicoRiego mosaicoRiego*/) {

        this.mqttConnection = mqttConnection;
        //this.mosaicoRiego = mosaicoRiego;

    }

    // Inicializar la vista y el seteamos el controlador
    public void initialize() {

        List<Dispositivo> dispositivos = dispositivoControl.listarDispositivo();
        dispositivos.forEach(dispositivo -> mqttConnection.subscribe(dispositivo.getTopic()));

    }

    // Manejar los mensajes recibidos
    public void handleMessage(String topic, MqttMessage message) {

        // Obtener el payload del mensaje y la informacion del cultivo
        String payload = new String(message.getPayload());
        Map<String, Integer> infoMap = configCultivo(topic);

        System.out.println("Mensaje recibido en " + topic + ": " + payload);

        if (topic.contains("sensor/humidity")){

            String datoLimpio = String.valueOf(payload).replaceAll("\\s", "");
            try {
                int humidity = Integer.parseInt(datoLimpio);

                // Actualizar la vista con el valor del sensor
                //mosaicoRiego.updateSensorValue(topic, humidity);
                int cultivoId = cultivoControl.buscarCultivoPorTopic(topic).getId();
                Mosaico mosaico = contenedorRiego.getMosaicoByCultivoId(cultivoId);
                if (mosaico != null){
                    mosaico.updateSensorValue(topic, humidity);
                }
                // Encender el riego si la humedad es menor a la mínima configurada para el cultivo
                if (    infoMap.size() != 0 &&
                        humidity < infoMap.get("humedad_min")) {
                    encenderRiego(topic);
                } else if (infoMap.size() == 0){
                    System.out.println("El sensor no esta asociado a ningun cultivo por ahora");
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

        if (topic.contains("actuator/waterpump")) {

            try {
                int estado = dispositivoControl.estadoDispositivo(topic);
                int nuevoEstado = Integer.parseInt(payload);

                // Actualizar la vista con el estado de la bomba
                //mosaicoRiego.updatePumpState(topic, nuevoEstado);

                int cultivoId = cultivoControl.buscarCultivoPorTopic(topic).getId();
                Mosaico mosaico = contenedorRiego.getMosaicoByCultivoId(cultivoId);
                if (mosaico != null){
                    mosaico.updatePumpState(topic, nuevoEstado);
                }

                // Swith para cambiar el estado de la bomba
                if (nuevoEstado != estado) {
                    dispositivoControl.cambiarEstadoBomba(topic);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
    }

    // Enciende la bomba de riego y la apaga después de un tiempo
    private void encenderRiego(String topic) {
        int tiempo = configCultivo(topic).get("minutos_riego");
        String topicBomba = controlRiegoDAO.getTopicBomba(topic);

        System.out.println("topicBomba: " + topicBomba);

        if(topicBomba == null) {
            System.out.println("No se encontro la bomba asociada al sensor...");
        } else if ( dispositivoControl.estadoDispositivo(topicBomba) == 0 ) {

            int cultivoId = cultivoControl.buscarCultivoPorTopic(topic).getId();
            Mosaico mosaico = contenedorRiego.getMosaicoByCultivoId(cultivoId);

            System.out.println("Activando el riego...");
            mqttConnection.publish(topicBomba, "1");
            dispositivoControl.cambiarEstadoBomba(topicBomba);
            mosaico.updatePumpState(topicBomba, 1);

            // Apagar el riego después de un tiempo (para las pruebas se usaron segundos)
            CompletableFuture.delayedExecutor(tiempo, TimeUnit.SECONDS).execute(() -> { // TODO cambiar el tiempo por minutos despues de pruebas
                apagarRiego(topicBomba);
                dispositivoControl.cambiarEstadoBomba(topicBomba);
                mosaico.updatePumpState(topicBomba, 0);
            });
        }
    }

    // Apaga la bomba de riego
    private void apagarRiego(String topic) {

        if ( dispositivoControl.estadoDispositivo(topic) == 1 ) {

            System.out.println("Se completo el ciclo de riego! \nApagando el riego...");
            mqttConnection.publish(topic, "0");
            dispositivoControl.cambiarEstadoBomba(topic);
        }
    }

    // Obtiene la configuración del cultivo segun su sensor
    private Map<String, Integer> configCultivo(String topic) {

        String topicSensor = topic;
        Map<String, Integer> infoCultivo = controlRiegoDAO.getInfoRiego(topicSensor);

        return infoCultivo;

    }

    // Consultar al controlador de dispositivos el cultivo al que pertenece el sensor
    public List<Dispositivo> listarDispCultivo(String topic) {
        return dispositivoControl.listarDispCultivo(topic);
    }

    public List<Dispositivo> listarDispCultivo(int id) {
        return dispositivoControl.listarDispCultivo(id);
    }

}

