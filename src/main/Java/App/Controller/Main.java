package App.Controller;

import App.View.ContenedorAdmin;
import App.View.LoginView;
import App.View.MainView;
import App.View.PlaceholderPanel;
import ControlRiego.Controller.ControlRiegoControl;
import ControlRiego.View.ContenedorRiego;
import Comunication.ConnMQTT.MqttSingleton;
import GestionRecursos.Controller.CultivoControl;
import GestionRecursos.Model.Cultivo.Ent.Cultivo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        final LoginView[] loginView = {null};

        SwingUtilities.invokeLater(() -> {
            loginView[0] = new LoginView(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Simulate login check
                    String username = loginView[0].getUsername();
                    String password = loginView[0].getPassword();

                    if (username.equals("user") && password.equals("pass")) { // Replace with real authentication
                        loginView[0].dispose();

                        MainView mainView = new MainView();

                        // Create MQTT connection
                        MqttSingleton mqttConnection = MqttSingleton.getInstance();

                        // Control Riego setup
                        ControlRiegoControl controlRiegoControl = new ControlRiegoControl(mqttConnection);

                        mqttConnection.connect();
                        mqttConnection.setMessageListener(controlRiegoControl::handleMessage);

                        ContenedorRiego contenedorRiego = controlRiegoControl.initialize();

                        // Tabs
                        mainView.addTab("Control de Riego", contenedorRiego);
                        mainView.addTab("Control de Agua", new PlaceholderPanel("Control de Agua - Próximamente"));
                        mainView.addTab("Control Cámara Frigorífica", new PlaceholderPanel("Control Cámara Frigorífica - Próximamente"));
                        mainView.addTab("Administrador", new ContenedorAdmin());

                        mainView.setVisible(true);

                        System.out.println("-----------------------riego en accion--------------------------");

                        // Iniciar la simulación de dispositivos
                        TestDispositivos testDispositivos = new TestDispositivos(mqttConnection);
                        testDispositivos.startSendingMessages();

                        // Añadir un shutdown hook para cerrar la conexión al terminar la aplicación
                        Runtime.getRuntime().addShutdownHook(new Thread(mqttConnection::disconnect));
                    } else {
                        JOptionPane.showMessageDialog(loginView[0], "Login failed. Try again.");
                    }
                }
            });
            loginView[0].setVisible(true);
        });
    }
}
