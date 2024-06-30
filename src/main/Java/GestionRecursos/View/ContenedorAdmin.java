package GestionRecursos.View;

import GestionRecursos.Controller.*;
import GestionRecursos.Model.Configuracion.Ent.Configuracion;
import GestionRecursos.Model.Usuario.Ent.Usuario;
import GestionRecursos.Model.Cultivo.Ent.Cultivo;
import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContenedorAdmin extends JPanel {

    public ContenedorAdmin() {
        setLayout(new BorderLayout());

        // Crear un panel principal con BoxLayout para colocar las secciones una debajo de la otra
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Crear secciones para Usuarios, Dispositivos, Cultivos y Configuraciones
        mainPanel.add(createUsuariosPanel());
        mainPanel.add(createDispositivosPanel());
        mainPanel.add(createCultivosPanel());
        mainPanel.add(createConfiguracionesPanel());

        // Crear un JScrollPane para envolver el panel principal
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Añadir el JScrollPane al ContenedorAdmin
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createUsuariosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Usuarios"));

        // Panel de formulario de Usuario
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Buscar por ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("Nombre:"));
        JTextField nombreField = new JTextField();
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Password:"));
        JTextField passwordField = new JTextField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Rol:"));
        JTextField rolField = new JTextField();
        formPanel.add(rolField);

        // Botones de acciones
        JPanel buttonPanel = new JPanel();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnBorrar = new JButton("Borrar");
        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnCrear);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnBorrar);

        // Área de listado
        JTextArea listArea = new JTextArea(10, 30);
        listArea.setEditable(false);
        JScrollPane listScroll = new JScrollPane(listArea);

        // Añadir componentes al panel de Usuarios
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(listScroll, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarUsuarios(listArea);

        // Acción del botón Buscar
        btnBuscar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            UsuarioControl usuarioControl = new UsuarioControl();
            Usuario usuario = usuarioControl.buscarUsuario(id);
            if (usuario != null) {
                nombreField.setText(usuario.getNombre());
                passwordField.setText(usuario.getPassword());
                rolField.setText(usuario.getRol());
            } else {
                JOptionPane.showMessageDialog(panel, "Usuario no encontrado");
            }
        });

        // Acción del botón Crear
        btnCrear.addActionListener(e -> {
            String nombre = nombreField.getText();
            String password = passwordField.getText();
            String rol = rolField.getText();
            UsuarioControl usuarioControl = new UsuarioControl();
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setPassword(password);
            usuario.setRol(rol);
            usuarioControl.crearUsuario(usuario);
            cargarUsuarios(listArea);
            limpiarFormulario(idField, nombreField, passwordField, rolField);

        });

        // Acción del botón Modificar
        btnModificar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String nombre = nombreField.getText();
            String password = passwordField.getText();
            String rol = rolField.getText();
            UsuarioControl usuarioControl = new UsuarioControl();
            Usuario usuario = usuarioControl.buscarUsuario(id);
            if (usuario != null) {
                usuario.setNombre(nombre);
                usuario.setPassword(password);
                usuario.setRol(rol);
                usuarioControl.modificarUsuario(usuario);
                cargarUsuarios(listArea);
                limpiarFormulario(idField, nombreField, passwordField, rolField);

            } else {
                JOptionPane.showMessageDialog(panel, "Usuario no encontrado");
            }
        });

        // Acción del botón Borrar
        btnBorrar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            UsuarioControl usuarioControl = new UsuarioControl();
            usuarioControl.eliminarUsuario(id);
            cargarUsuarios(listArea);
            limpiarFormulario(idField, nombreField, passwordField, rolField);
        });

        return panel;
    }

    private JPanel createDispositivosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dispositivos"));

        // Panel de formulario de Dispositivo
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Buscar por ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("Topic:"));
        JTextField nombreField = new JTextField();
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Estado:"));
        JTextField estadoField = new JTextField();
        formPanel.add(estadoField);

        // Botones de acciones
        JPanel buttonPanel = new JPanel();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnBorrar = new JButton("Borrar");
        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnCrear);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnBorrar);

        // Área de listado
        JTextArea listArea = new JTextArea(10, 30);
        listArea.setEditable(false);
        JScrollPane listScroll = new JScrollPane(listArea);

        // Añadir componentes al panel de Dispositivos
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(listScroll, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarDispositivos(listArea);

        // Acción del botón Buscar
        btnBuscar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            DispositivoControl dispositivoControl = new DispositivoControl();
            Dispositivo dispositivo = dispositivoControl.buscarDispositivo(id);
            if (dispositivo != null) {
                nombreField.setText(dispositivo.getTopic());
                estadoField.setText(Integer.toString(dispositivo.getEstado()));
            } else {
                JOptionPane.showMessageDialog(panel, "Dispositivo no encontrado");
            }
        });

        // Acción del botón Crear
        btnCrear.addActionListener(e -> {
            String topic = nombreField.getText();
            String estado = estadoField.getText();
            DispositivoControl dispositivoControl = new DispositivoControl();
            Dispositivo dispositivo = new Dispositivo();
            dispositivo.setTopic(topic);
            dispositivo.setEstado(Integer.parseInt(estado));
            dispositivoControl.crearDispositivo(dispositivo);
            cargarDispositivos(listArea);
            limpiarFormulario(idField, nombreField, estadoField);
        });

        // Acción del botón Modificar
        btnModificar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String topic = nombreField.getText();
            String estado = estadoField.getText();
            DispositivoControl dispositivoControl = new DispositivoControl();
            Dispositivo dispositivo = dispositivoControl.buscarDispositivo(id);
            if (dispositivo != null) {
                dispositivo.setTopic(topic);
                dispositivo.setEstado(Integer.parseInt(estado));
                dispositivoControl.modificarDispositivo(dispositivo);
                cargarDispositivos(listArea);
                limpiarFormulario(idField, nombreField, estadoField);
            } else {
                JOptionPane.showMessageDialog(panel, "Dispositivo no encontrado");
            }
        });

        // Acción del botón Borrar
        btnBorrar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            DispositivoControl dispositivoControl = new DispositivoControl();
            dispositivoControl.eliminarDispositivo(id);
            cargarDispositivos(listArea);
            limpiarFormulario(idField, nombreField, estadoField);
        });

        return panel;
    }

    private JPanel createCultivosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Cultivos"));

        // Panel de formulario de Cultivo
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Buscar por ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("Descripción:"));
        JTextField descripcionField = new JTextField();
        formPanel.add(descripcionField);

        formPanel.add(new JLabel("ID Configuración:"));
        JTextField idConfigField = new JTextField();
        formPanel.add(idConfigField);

        // Botones de acciones
        JPanel buttonPanel = new JPanel();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnBorrar = new JButton("Borrar");
        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnCrear);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnBorrar);

        // Área de listado
        JTextArea listArea = new JTextArea(10, 30);
        listArea.setEditable(false);
        JScrollPane listScroll = new JScrollPane(listArea);

        // Añadir componentes al panel de Cultivos
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(listScroll, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarCultivos(listArea);

        // Acción del botón Buscar
        btnBuscar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            CultivoControl cultivoControl = new CultivoControl();
            Cultivo cultivo = cultivoControl.buscarCultivo(id);
            if (cultivo != null) {
                descripcionField.setText(cultivo.getDescripcion());
                idConfigField.setText(String.valueOf(cultivo.getConfiguracion().getId()));
            } else {
                JOptionPane.showMessageDialog(panel, "Cultivo no encontrado");
            }
        });

        // Acción del botón Crear
        btnCrear.addActionListener(e -> {
            String descripcion = descripcionField.getText();
            int idConfig = Integer.parseInt(idConfigField.getText());
            CultivoControl cultivoControl = new CultivoControl();
            Cultivo cultivo = new Cultivo();
            cultivo.setDescripcion(descripcion);
            cultivo.setConfiguracion(new Configuracion(idConfig, 0, 0, 0, 0)); // Inicializar Configuración
            cultivoControl.crearCultivo(cultivo);
            cargarCultivos(listArea);
            limpiarFormulario(idField, descripcionField, idConfigField);
        });

        // Acción del botón Modificar
        btnModificar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String descripcion = descripcionField.getText();
            int idConfig = Integer.parseInt(idConfigField.getText());
            CultivoControl cultivoControl = new CultivoControl();
            Cultivo cultivo = cultivoControl.buscarCultivo(id);
            if (cultivo != null) {
                cultivo.setDescripcion(descripcion);
                cultivo.setConfiguracion(new Configuracion(idConfig, 0, 0, 0, 0)); // Inicializar Configuración
                cultivoControl.modificarCultivo(cultivo);
                cargarCultivos(listArea);
                limpiarFormulario(idField, descripcionField, idConfigField);
            } else {
                JOptionPane.showMessageDialog(panel, "Cultivo no encontrado");
            }
        });

        // Acción del botón Borrar
        btnBorrar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            CultivoControl cultivoControl = new CultivoControl();
            cultivoControl.eliminarCultivo(id);
            cargarCultivos(listArea);
            limpiarFormulario(idField, descripcionField, idConfigField);
        });

        return panel;
    }

    private JPanel createConfiguracionesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Configuraciones"));

        // Panel de formulario de Configuración
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("Buscar por ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("ID Usuario:"));
        JTextField idUsuarioField = new JTextField();
        formPanel.add(idUsuarioField);

        formPanel.add(new JLabel("Minutos Riego:"));
        JTextField minutosRiegoField = new JTextField();
        formPanel.add(minutosRiegoField);

        formPanel.add(new JLabel("Humedad Máxima:"));
        JTextField humMaxField = new JTextField();
        formPanel.add(humMaxField);

        formPanel.add(new JLabel("Humedad Mínima:"));
        JTextField humMinField = new JTextField();
        formPanel.add(humMinField);

        // Botones de acciones
        JPanel buttonPanel = new JPanel();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnBorrar = new JButton("Borrar");
        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnCrear);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnBorrar);

        // Área de listado
        JTextArea listArea = new JTextArea(10, 30);
        listArea.setEditable(false);
        JScrollPane listScroll = new JScrollPane(listArea);

        // Añadir componentes al panel de Configuraciones
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(listScroll, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarConfiguraciones(listArea);

        // Acción del botón Buscar
        btnBuscar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            ConfiguracionControl configuracionControl = new ConfiguracionControl();
            Configuracion configuracion = configuracionControl.buscarConfiguracion(id);
            if (configuracion != null) {
                idUsuarioField.setText(String.valueOf(configuracion.getUsuario().getId()));
                minutosRiegoField.setText(String.valueOf(configuracion.getMinutosRiego()));
                humMaxField.setText(String.valueOf(configuracion.getHumMax()));
                humMinField.setText(String.valueOf(configuracion.getHumMin()));
            } else {
                JOptionPane.showMessageDialog(panel, "Configuración no encontrada");
            }
        });

        // Acción del botón Crear
        btnCrear.addActionListener(e -> {
            int idUsuario = Integer.parseInt(idUsuarioField.getText());
            int minutosRiego = Integer.parseInt(minutosRiegoField.getText());
            int humMax = Integer.parseInt(humMaxField.getText());
            int humMin = Integer.parseInt(humMinField.getText());
            ConfiguracionControl configuracionControl = new ConfiguracionControl();
            Configuracion configuracion = new Configuracion();
            configuracion.setUsuario(new Usuario(idUsuario, "", "", "")); // Inicializar Usuario
            configuracion.setMinutosRiego(minutosRiego);
            configuracion.setHumMax(humMax);
            configuracion.setHumMin(humMin);
            configuracionControl.crearConfiguracion(configuracion);
            cargarConfiguraciones(listArea);
            limpiarFormulario(idField, idUsuarioField, minutosRiegoField, humMaxField, humMinField);
        });

        // Acción del botón Modificar
        btnModificar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            int idUsuario = Integer.parseInt(idUsuarioField.getText());
            int minutosRiego = Integer.parseInt(minutosRiegoField.getText());
            int humMax = Integer.parseInt(humMaxField.getText());
            int humMin = Integer.parseInt(humMinField.getText());
            ConfiguracionControl configuracionControl = new ConfiguracionControl();
            Configuracion configuracion = configuracionControl.buscarConfiguracion(id);
            if (configuracion != null) {
                configuracion.setUsuario(new Usuario(idUsuario, "", "", "")); // Inicializar Usuario
                configuracion.setMinutosRiego(minutosRiego);
                configuracion.setHumMax(humMax);
                configuracion.setHumMin(humMin);
                configuracionControl.modificarConfiguracion(configuracion);
                cargarConfiguraciones(listArea);
                limpiarFormulario(idField, idUsuarioField, minutosRiegoField, humMaxField, humMinField);
            } else {
                JOptionPane.showMessageDialog(panel, "Configuración no encontrada");
            }
        });

        // Acción del botón Borrar
        btnBorrar.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            ConfiguracionControl configuracionControl = new ConfiguracionControl();
            configuracionControl.eliminarConfiguracion(id);
            cargarConfiguraciones(listArea);
            limpiarFormulario(idField, idUsuarioField, minutosRiegoField, humMaxField, humMinField);
        });

        return panel;
    }

    private void cargarUsuarios(JTextArea textArea) {
        UsuarioControl usuarioControl = new UsuarioControl();
        List<Usuario> usuarios = usuarioControl.listarUsuario();
        StringBuilder sb = new StringBuilder();
        for (Usuario usuario : usuarios) {
            sb.append(usuario.getId()+" - ").append("Nombre: ").append(usuario.getNombre()).append(", Rol: ").append(usuario.getRol()).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void cargarDispositivos(JTextArea textArea) {
        DispositivoControl dispositivoControl = new DispositivoControl();
        List<Dispositivo> dispositivos = dispositivoControl.listarDispositivo();
        StringBuilder sb = new StringBuilder();
        for (Dispositivo dispositivo : dispositivos) {
            sb.append(dispositivo.getId()+" - ").append("Topico: ").append(dispositivo.getTopic()).append(", Estado: ").append(dispositivo.getEstado()).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void cargarCultivos(JTextArea textArea) {
        CultivoControl cultivoControl = new CultivoControl();
        List<Cultivo> cultivos = cultivoControl.listarCultivo();
        StringBuilder sb = new StringBuilder();
        for (Cultivo cultivo : cultivos) {
            sb.append(cultivo.getId()+" - ").append("Descripción: ").append(cultivo.getDescripcion()).append(", Config: ").append(cultivo.getConfiguracion().getId()).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void cargarConfiguraciones(JTextArea textArea) {
        ConfiguracionControl configuracionControl = new ConfiguracionControl();
        List<Configuracion> configuraciones = configuracionControl.listarConfiguracion();
        StringBuilder sb = new StringBuilder();
        for (Configuracion configuracion : configuraciones) {
            sb.append(configuracion.getId()+" - ").append("Minutos de Riego: ").append(configuracion.getMinutosRiego())
                    .append(", Humedad Máxima: ").append(configuracion.getHumMax()).append(", Humedad Mínima: ").append(configuracion.getHumMin()).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void limpiarFormulario(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}
