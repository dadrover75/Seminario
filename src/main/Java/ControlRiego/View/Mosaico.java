package ControlRiego.View;

import ControlRiego.Controller.ControlRiegoControl;
import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mosaico extends JPanel {
    //private int idCultivo;
    //private List<Dispositivo> dispositivos;
    private JList<String> sensores;
    private JRadioButton bomba;
    private DefaultListModel<String> sensoresModel;
    private Map<String, Integer> sensorValues;


    public Mosaico(List<Dispositivo> dispositivos) {
        //this.controlRiegoControl = controlRiegoControl;
        this.sensorValues = new HashMap<>();
        this.sensoresModel = new DefaultListModel<>();
        //this.dispositivos = new ArrayList<>();

        // Inicializar los componentes
        this.sensores = new JList<>(sensoresModel);
        this.bomba = new JRadioButton("Bomba");

        for (Dispositivo dispositivo : dispositivos) {
            sensorValues.put(dispositivo.getTopic(), null);
            sensoresModel.addElement(dispositivo.getTopic() + " ---------- ND");
        }

        // Configurar el layout y agregar los componentes al panel contenedor
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(sensores), BorderLayout.CENTER);
        this.add(bomba, BorderLayout.SOUTH);
        this.setVisible(true);
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
