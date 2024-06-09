package App.Controller;

import Comunication.ConnMQTT.MqttSingleton;
import ControlRiego.Controller.ControlRiegoControl;
import ControlRiego.View.MosaicoRiego;
import ControlRiego.View.MosaicoRiego;
import GestionRecursos.Controller.ConfiguracionControl;
import GestionRecursos.Controller.DispositivoControl;
import GestionRecursos.Controller.UsuarioControl;

import javax.swing.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        // Inicializar la conexión MQTT
        MqttSingleton mqttConnection = MqttSingleton.getInstance();
        MosaicoRiego mosaicoRiego = new MosaicoRiego(null);
        ControlRiegoControl controlRiegoControl = new ControlRiegoControl(mqttConnection, mosaicoRiego);

        mqttConnection.connect();
        mqttConnection.setMessageListener(controlRiegoControl::handleMessage);


        mosaicoRiego.setControlRiegoControl(controlRiegoControl);
        controlRiegoControl.initialize();

        System.out.println("-----------------------riego en accion--------------------------");
        /*SwingUtilities.invokeLater(() -> {
            MosaicoRiego dashboard = new MosaicoRiego(controlHumedad);
            dashboard.setVisible(true);
        });*/
        // Configurar el listener para manejar mensajes

        // Añadir un shutdown hook para cerrar la conexión al terminar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(mqttConnection::disconnect));

    }

}
