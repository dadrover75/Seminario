package App.Controller;

import App.View.LoginView;
import App.View.MainView;
import App.View.PlaceholderPanel;
import ControlRiego.Controller.ControlRiegoControl;
import ControlRiego.View.ContenedorRiego;
import Comunication.ConnMQTT.MqttSingleton;
import GestionRecursos.Model.Cultivo.Ent.Cultivo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(() -> {
            final LoginView loginView = new LoginView(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Simulate login check
                    String username = loginView.getUsername();
                    String password = loginView.getPassword();

                    if (username.equals("user") && password.equals("pass")) { // Replace with real authentication
                        loginView.dispose();

                        MainView mainView = new MainView();

                        // Create MQTT connection
                        MqttSingleton mqttConnection = MqttSingleton.getInstance();

                        // Control Riego setup
                        ControlRiegoControl controlRiegoControl = new ControlRiegoControl(mqttConnection);
                        List<Cultivo> cultivos = controlRiegoControl.getCultivos();
                        ContenedorRiego contenedorRiego = new ContenedorRiego(cultivos, controlRiegoControl);
                        mainView.addTab("Control de Riego", contenedorRiego);

                        // Placeholder tabs
                        mainView.addTab("Control de Agua", new PlaceholderPanel("Control de Agua - Próximamente"));
                        mainView.addTab("Control Cámara Frigorífica", new PlaceholderPanel("Control Cámara Frigorífica - Próximamente"));

                        mainView.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(loginView, "Login failed. Try again.");
                    }
                }
            });
            loginView.setVisible(true);
        });
    }
}
