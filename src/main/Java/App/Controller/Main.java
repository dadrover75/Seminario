package App.Controller;

import Comunication.ConnMQTT.MqttSingleton;
import ControlRiego.Controller.ControlRiegoControl;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        // Crear una instancia de la conexión MQTT y del la vista y controlador
        MqttSingleton mqttConnection = MqttSingleton.getInstance();

        //MosaicoRiego mosaicoRiego = new MosaicoRiego();
        ControlRiegoControl controlRiegoControl = new ControlRiegoControl(mqttConnection);

        // Conectar el cliente MQTT y añadir un listener para los mensajes
        mqttConnection.connect();
        mqttConnection.setMessageListener(controlRiegoControl::handleMessage);

        // Inicializar la vista y el seteamos el controlador
        controlRiegoControl.initialize();

        System.out.println("-----------------------riego en accion--------------------------");

        // Iniciar la simulación de dispositivos
        TestDispositivos testDispositivos = new TestDispositivos(mqttConnection);
        testDispositivos.startSendingMessages();

        // Añadir un shutdown hook para cerrar la conexión al terminar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(mqttConnection::disconnect));

    }

}
