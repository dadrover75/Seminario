package ControlRiego.View;

import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class Mosaico extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JToggleButton bomba;

    public Mosaico(List<Dispositivo> dispositivos) {
        this.tableModel = new DefaultTableModel();
        this.tableModel.addColumn("Sensor");
        this.tableModel.addColumn("Valor");
        this.table = new JTable(tableModel);
        this.bomba = new JToggleButton("Riego Off");

        // Establecer el formato
        this.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        this.setBackground(Color.lightGray);
        table.setPreferredScrollableViewportSize(new Dimension(300, 60));
        table.setShowGrid(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        bomba.setHorizontalAlignment(SwingConstants.CENTER);
        //bomba.setFont(new Font("Candara", Font.BOLD, 14));

        // Agregar los dispositivos a la tabla
        for (Dispositivo dispositivo : dispositivos) {
            Object[] rowData = {dispositivo.getTopic(), "esperando lectura..."};
            tableModel.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(bomba, BorderLayout.SOUTH);

        setOpaque(false);

        bomba.setAlignmentX(Component.CENTER_ALIGNMENT);
        bomba.addActionListener(e -> togglePumpState());

        this.setVisible(true);
    }

    public void updateSensorValue(String topic, int humidity) {
        if (SwingUtilities.isEventDispatchThread()) {
            doUpdateSensorValue(topic, humidity);
        } else {
            SwingUtilities.invokeLater(() -> doUpdateSensorValue(topic, humidity));
        }
    }

    private void doUpdateSensorValue(String topic, int humidity) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(topic)) {
                tableModel.setValueAt(humidity + " %", i, 1);
                break;
            }
        }
        revalidate();
        repaint();
    }

    public void updatePumpState(String topic, int nuevoEstado) {
        if (SwingUtilities.isEventDispatchThread()) {
            doUpdatePumpState(topic, nuevoEstado);
        } else {
            SwingUtilities.invokeLater(() -> doUpdatePumpState(topic, nuevoEstado));
        }
    }

    private void doUpdatePumpState(String topic, int nuevoEstado) {
        boolean estado = nuevoEstado == 1;
        bomba.setSelected(estado);
        if (estado) {
            bomba.setForeground(Color.red);
            bomba.setText("Riego On");
        } else {
            bomba.setForeground(Color.black);
            bomba.setText("Riego Off");
        }
        revalidate();
        repaint();
    }

    private void togglePumpState() {
        if (bomba.isSelected()) {
            bomba.setForeground(Color.red);
            bomba.setText("Riego On");
        } else {
            bomba.setForeground(Color.black);
            bomba.setText("Riego Off");
        }
    }


    // bordes redondeados ;)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    }
}
