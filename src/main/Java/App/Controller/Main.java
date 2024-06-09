package App.Controller;

import Comunication.ConnMQTT.MqttSingleton;
import ControlRiego.Controller.ControlRiegoControl;
import ControlRiego.View.Dashboard;
import GestionRecursos.Controller.ConfiguracionControl;
import GestionRecursos.Controller.DispositivoControl;
import GestionRecursos.Controller.UsuarioControl;

import javax.swing.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        // Inicializar la conexión MQTT
        MqttSingleton mqttConnection = MqttSingleton.getInstance();
        mqttConnection.connect();

        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard(mqttConnection);
            dashboard.setVisible(true);
        });

        // Crear e inicializar el controlador de humedad
        // ControlRiegoControl controlHumedad = new ControlRiegoControl(mqttConnection, riegoPanel.getCultivoPanels());
        // controlHumedad.initialize();

        // Configurar el listener para manejar mensajes
        //mqttConnection.setMessageListener(controlHumedad::handleMessage);

        // Añadir un shutdown hook para cerrar la conexión al terminar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(mqttConnection::disconnect));

    }

}
