package App.Controller;

import Comunication.ConnMQTT.MqttSingleton;
import ControlRiego.Controller.ControlRiegoControl;
import GestionRecursos.Controller.ConfiguracionControl;
import GestionRecursos.Controller.DispositivoControl;
import GestionRecursos.Controller.UsuarioControl;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        UsuarioControl usu = new UsuarioControl();
        ConfiguracionControl conf = new ConfiguracionControl();
        DispositivoControl disp = new DispositivoControl();

        // Prueba de listado de datos
        usu.listarUsuario().forEach(u -> System.out.println( u.getId() + " | " + u.getNombre() + " | " + u.getRol()));
        System.out.println("-------------------------------------------------");
        conf.listarConfiguracion().forEach(c -> System.out.println( c.getId() + " | " + c.getHumMin() + " | " + c.getHumMax() + "| " + c.getMinutosRiego()));
        System.out.println("-------------------------------------------------");
        disp.listarDispositivo().forEach(d -> System.out.println( d.getId() + " | " + d.getTopic() + " | " + d.getEstado()));

        //-------------------------------------------------------------------

        // Inicializar la conexión MQTT
        MqttSingleton mqttConnection = MqttSingleton.getInstance();
        mqttConnection.connect();

        // Crear e inicializar el controlador de humedad
        ControlRiegoControl controlHumedad = new ControlRiegoControl(mqttConnection);
        controlHumedad.initialize();

        // Configurar el listener para manejar mensajes
        mqttConnection.setMessageListener(controlHumedad::handleMessage);

        // Añadir un shutdown hook para cerrar la conexión al terminar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(mqttConnection::disconnect));

    }

}
