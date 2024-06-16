package ControlRiego.View;

import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mosaico extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JRadioButton bomba;

    public Mosaico(List<Dispositivo> dispositivos) {
        this.tableModel = new DefaultTableModel();
        this.tableModel.addColumn("Sensor");
        this.tableModel.addColumn("Valor");
        this.table = new JTable(tableModel);
        this.bomba = new JRadioButton("Riego");

        // Establecer el formato
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(Color.lightGray);

        table.setPreferredScrollableViewportSize(new Dimension(300, 60));
        table.setShowGrid(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        bomba.setHorizontalAlignment(SwingConstants.CENTER);

        // Agregar los dispositivos a la tabla
        for (Dispositivo dispositivo : dispositivos) {
            Object[] rowData = {dispositivo.getTopic(), "esperando lectura..."};
            tableModel.addRow(rowData);
        }

        this.setLayout(new GridLayout(0, 1));
        this.add(table);
        this.add(bomba);

        bomba.setAlignmentX(Component.CENTER_ALIGNMENT);


        this.setVisible(true);
    }

    public void updateSensorValue(String topic, int humidity) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(topic)) {
                tableModel.setValueAt(humidity + " %", i, 1);
                break;
            }
        }
    }


    public void updatePumpState(String topic, int nuevoEstado) {
        Boolean estado = nuevoEstado == 1;
        bomba.setSelected(estado);
        if (estado) {
            bomba.setForeground(Color.red);
        } else {
            bomba.setForeground(Color.black);
        }
    }
}


/*
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

        // Inicializar los componentes
        this.sensores = new JList<>(sensoresModel);
        this.bomba = new JRadioButton("Bomba");

        // Establecer el formato
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añade margen alrededor del mosaico
        this.setBackground(Color.lightGray); // Establece el color de fondo del mosaico

        // Establece el color del texto de la bomba cuando está seleccionada
        bomba.setForeground(Color.red); // Cambia el color del texto de la bomba cuando está seleccionada

        // Configurar el layout y agregar los componentes al panel contenedor
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(sensores), BorderLayout.CENTER);
        this.add(bomba, BorderLayout.SOUTH);
        this.setVisible(true);

        // Iniciar la lista de sensores
        for (Dispositivo dispositivo : dispositivos) {
            sensorValues.put(dispositivo.getTopic(), null);
            sensoresModel.addElement(dispositivo.getTopic() + " ---------- " + "N/A");
        }


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
*/

