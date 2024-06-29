package ControlRiego.View;

import ControlRiego.Controller.ControlRiegoControl;
import GestionRecursos.Model.Cultivo.Ent.Cultivo;
import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContenedorRiego extends JPanel {

    private JPanel tarjetaCultivos;
    private Map<Integer, Mosaico> mosaicosMap;

    public ContenedorRiego(List<Cultivo> cultivos, ControlRiegoControl controlRiegoControl) {
        this.mosaicosMap = new HashMap<>();

        this.setLayout(new BorderLayout());
        this.tarjetaCultivos = new JPanel();
        tarjetaCultivos.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        tarjetaCultivos.setLayout(new GridLayout(0, 2, 30, 30));
        tarjetaCultivos.setSize(300,200);

        this.add(new JScrollPane(tarjetaCultivos), BorderLayout.CENTER);

        initializeCultivoList(cultivos, controlRiegoControl);
    }

    private void initializeCultivoList(List<Cultivo> cultivos, ControlRiegoControl controlRiegoControl) {
        for (Cultivo cultivo : cultivos) {
            List<Dispositivo> dispositivos = controlRiegoControl.listarDispCultivo(cultivo.getId());
            List <Dispositivo> sensores = dispositivos.stream().filter(dispositivo -> dispositivo.getTopic().contains("sensor/humidity")).toList();
            Mosaico mosaico = new Mosaico(sensores);

            JLabel titleLabel = new JLabel("Cultivo: " + cultivo.getDescripcion());
            titleLabel.setFont(new Font("Candara", Font.BOLD, 20));
            titleLabel.setForeground(Color.DARK_GRAY);
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mosaico.setPreferredSize(new Dimension(300, 200));
            mosaico.add(titleLabel, BorderLayout.NORTH);

            tarjetaCultivos.add(mosaico);
            mosaicosMap.put(cultivo.getId(), mosaico);
        }
        tarjetaCultivos.revalidate();
        tarjetaCultivos.repaint();
    }

    public Mosaico getMosaicoByCultivoId(int idcultivo) {
        return mosaicosMap.get(idcultivo);
    }
}
