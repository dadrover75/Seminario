package App.View;

import GestionRecursos.Controller.*;
import GestionRecursos.Model.*;
import GestionRecursos.Model.Configuracion.Ent.Configuracion;
import GestionRecursos.Model.Configuracion.DAO.ConfiguracionDAO;
import GestionRecursos.Model.Usuario.Ent.Usuario;
import GestionRecursos.Model.Usuario.DAO.UsuarioDAO;
import GestionRecursos.Model.Cultivo.Ent.Cultivo;
import GestionRecursos.Model.Cultivo.DAO.CultivoDAO;
import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;
import GestionRecursos.Model.Dispositivo.DAO.DispositivoDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContenedorAdmin extends JPanel {

    public ContenedorAdmin() {
        setLayout(new GridLayout(2, 2));

        // Panel para Usuarios
        JPanel panelUsuarios = new JPanel();
        panelUsuarios.setBorder(BorderFactory.createTitledBorder("Usuarios"));
        JTextArea textAreaUsuarios = new JTextArea(10, 30);
        textAreaUsuarios.setEditable(false);
        JScrollPane scrollUsuarios = new JScrollPane(textAreaUsuarios);
        panelUsuarios.add(scrollUsuarios);
        cargarUsuarios(textAreaUsuarios);

        // Panel para Configuraciones
        JPanel panelConfiguraciones = new JPanel();
        panelConfiguraciones.setBorder(BorderFactory.createTitledBorder("Configuraciones"));
        JTextArea textAreaConfiguraciones = new JTextArea(10, 30);
        textAreaConfiguraciones.setEditable(false);
        JScrollPane scrollConfiguraciones = new JScrollPane(textAreaConfiguraciones);
        panelConfiguraciones.add(scrollConfiguraciones);
        cargarConfiguraciones(textAreaConfiguraciones);

        // Panel para Cultivos
        JPanel panelCultivos = new JPanel();
        panelCultivos.setBorder(BorderFactory.createTitledBorder("Cultivos"));
        JTextArea textAreaCultivos = new JTextArea(10, 30);
        textAreaCultivos.setEditable(false);
        JScrollPane scrollCultivos = new JScrollPane(textAreaCultivos);
        panelCultivos.add(scrollCultivos);
        cargarCultivos(textAreaCultivos);

        // Panel para Dispositivos
        JPanel panelDispositivos = new JPanel();
        panelDispositivos.setBorder(BorderFactory.createTitledBorder("Dispositivos"));
        JTextArea textAreaDispositivos = new JTextArea(10, 30);
        textAreaDispositivos.setEditable(false);
        JScrollPane scrollDispositivos = new JScrollPane(textAreaDispositivos);
        panelDispositivos.add(scrollDispositivos);
        cargarDispositivos(textAreaDispositivos);

        // Agregar paneles al ContenedorAdmin
        add(panelUsuarios);
        add(panelConfiguraciones);
        add(panelCultivos);
        add(panelDispositivos);
    }

    private void cargarUsuarios(JTextArea textArea) {
        UsuarioControl usuarioControl = new UsuarioControl();
        List<Usuario> usuarios = usuarioControl.listarUsuario();
        StringBuilder sb = new StringBuilder();
        for (Usuario usuario : usuarios) {
            sb.append(usuario.getId()).append(": ").append(usuario.getNombre()).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void cargarConfiguraciones(JTextArea textArea) {
        ConfiguracionControl configuracionControl = new ConfiguracionControl();
        List<Configuracion> configuraciones = configuracionControl.listarConfiguracion();
        StringBuilder sb = new StringBuilder();
        for (Configuracion configuracion : configuraciones) {
            sb.append("ID: ").append(configuracion.getId()).append(", Minutos de Riego: ").append(configuracion.getMinutosRiego()).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void cargarCultivos(JTextArea textArea) {
        CultivoControl cultivoControl = new CultivoControl();
        List<Cultivo> cultivos = cultivoControl.listarCultivo();
        StringBuilder sb = new StringBuilder();
        for (Cultivo cultivo : cultivos) {
            sb.append("ID: ").append(cultivo.getId()).append(", Descripción: ").append(cultivo.getDescripcion()).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void cargarDispositivos(JTextArea textArea) {
        DispositivoControl dispositivoControl = new DispositivoControl();
        List<Dispositivo> dispositivos = dispositivoControl.listarDispositivo();
        StringBuilder sb = new StringBuilder();
        for (Dispositivo dispositivo : dispositivos) {
            sb.append("ID: ").append(dispositivo.getId()).append(", Estado: ").append(dispositivo.getEstado()).append("\n");
        }
        textArea.setText(sb.toString());
    }
}
