package ControlRiego.View;

import ControlRiego.Controller.ControlRiegoControl;
import GestionRecursos.Model.Cultivo.Ent.Cultivo;
import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContenedorRiego extends JFrame {

    private JPanel contenedor;
    private JPanel tarjetaCultivos;
    private Map<Integer, Mosaico> mosaicosMap;

    public ContenedorRiego(List<Cultivo> cultivos, ControlRiegoControl controlRiegoControl) {
        //this.controlRiegoControl = controlRiegoControl;
        this.mosaicosMap = new HashMap<>();

        this.contenedor = new JPanel(new BorderLayout());
        this.tarjetaCultivos = new JPanel();
        this.tarjetaCultivos.setLayout(new BoxLayout(tarjetaCultivos, BoxLayout.Y_AXIS));

        contenedor.add(new JScrollPane(tarjetaCultivos), BorderLayout.CENTER);

        this.setContentPane(contenedor);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);

        initializeCultivoList(cultivos, controlRiegoControl);
    }

    private void initializeCultivoList(List<Cultivo> cultivos, ControlRiegoControl controlRiegoControl) {
        for (Cultivo cultivo : cultivos) {
            List<Dispositivo> dispositivos = controlRiegoControl.listarDispCultivo(cultivo.getId());
        }
        tarjetaCultivos.revalidate();
        tarjetaCultivos.repaint();
    }

    public Mosaico getMosaicoByCultivoId(int idcultivo) {
        return mosaicosMap.get(idcultivo);
    }

/*
    private JPanel contenedor;
    private JList<Mosaico> mosaico;

    public ContenedorRiego(List<Cultivo> cultivos) {

            this.contenedor = new JPanel(new BorderLayout());
            this.tarjetaCultivos = new JList<>();

            contenedor.add(new JScrollPane(tarjetaCultivos), BorderLayout.WEST);

            this.setContentPane(contenedor);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
            this.setVisible(true);

            //initializeCultivoList(cultivos);
    }
*/
}
