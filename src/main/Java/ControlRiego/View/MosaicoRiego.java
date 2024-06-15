package ControlRiego.View;

import ControlRiego.Controller.ControlRiegoControl;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class MosaicoRiego extends JFrame {
    private JList<String> sensores;
    private JRadioButton bomba;
    private JPanel contenedor;
    private JPanel card;
    private ControlRiegoControl controlRiegoControl;
    private final DefaultListModel<String> sensoresModel;
    private final Map<String, Integer> sensorValues;

    public MosaicoRiego() {
        //this.controlRiegoControl = controlRiegoControl;
        this.sensorValues = new HashMap<>();
        this.sensoresModel = new DefaultListModel<>();
        this.sensores.setModel(sensoresModel);

        this.setContentPane(contenedor);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

    }

    public void setControlRiegoControl(ControlRiegoControl controlRiegoControl) {
        this.controlRiegoControl = controlRiegoControl;
    }

    public void updateSensorValue(String topic, int humidity) {
        sensorValues.put(topic, humidity);
        updateSensorList();
    }

    public void updatePumpState(String topic, int nuevoEstado) {
        bomba.setSelected(nuevoEstado == 1);
    }

    private void updateSensorList() {
        sensoresModel.clear();
        sensorValues.forEach((topic, value) -> sensoresModel.addElement(topic + " ---------- " + value));
    }
}
