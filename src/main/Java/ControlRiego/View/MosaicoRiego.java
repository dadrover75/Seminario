package ControlRiego.View;

import ControlRiego.Controller.ControlRiegoControl;
import ControlRiego.Model.ControlRiegoDAO;
import GestionRecursos.Controller.DispositivoControl;//////////////////
import GestionRecursos.Model.Cultivo.Ent.Cultivo;
import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MosaicoRiego extends JFrame {
    private int idCultivo;
    private List<Dispositivo> dispositivos;
    private JList<String> sensores;
    private JRadioButton bomba;
    private JPanel contenedor;
    private JPanel card;
    private ControlRiegoDAO controlRiegoControl = new ControlRiegoDAO();/////////////////////////
    private final DefaultListModel<String> sensoresModel;
    private final Map<String, Integer> sensorValues;
    ///////////////////////////////////// probar mosaico///////////////////////////////////////
    private DispositivoControl dispositivoControl = new DispositivoControl();
    private List<Dispositivo> dispo = new ArrayList<>(dispositivoControl.listarDispCultivo("sensor/humidity/3"));
    private Mosaico mosaico = new Mosaico(1, "sensor/humidity/3", 45, dispo);


    public MosaicoRiego(/*ControlRiegoControl controlRiegoControl*/) {
        //this.controlRiegoControl = controlRiegoControl;
        this.sensorValues = new HashMap<>();
        this.sensoresModel = new DefaultListModel<>();
        this.dispositivos = new ArrayList<>();
        
        // Inicializar los componentes
        this.sensores = new JList<>(sensoresModel);
        this.bomba = new JRadioButton("Bomba");
        
        // Crear el panel contenedor y agregar los componentes
        this.contenedor = new JPanel(new BorderLayout());
        this.card = new JPanel(new CardLayout());
        
        // Configurar el layout y agregar los componentes al panel contenedor
        contenedor.add(new JScrollPane(sensores), BorderLayout.WEST);
        contenedor.add(bomba, BorderLayout.EAST);
        contenedor.add(mosaico, BorderLayout.SOUTH);

        this.setContentPane(contenedor);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        // Inicializar la lista de sensores
        //initializeSensorList();

    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public void setDispositivos(List<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public List<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

/*
// REVISAR COMO HACER
    private void initializeSensorList() {
        for (Dispositivo dispositivo : dispositivos) {
            sensoresModel.addElement(dispositivo.getTopic() + " ---------- ND");
            sensorValues.put(dispositivo.getTopic(), null);
        }
    }

    public void setControlRiegoControl(ControlRiegoControl controlRiegoControl) {
        this.controlRiegoControl = controlRiegoControl;
    }
*/
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
