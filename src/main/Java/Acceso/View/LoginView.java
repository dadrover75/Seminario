package Acceso.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginView(ActionListener loginAction) {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        userField = new JTextField(15);
        passField = new JPasswordField(15);
        loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(Color.LIGHT_GRAY);
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(180, 180, 180));
                } else {
                    g.setColor(getBackground());
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(Color.GRAY);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }

            @Override
            public void setContentAreaFilled(boolean b) {
            }
        };
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Candara", Font.BOLD, 14));
        loginButton.setBackground(new Color(220, 220, 220));
        loginButton.setOpaque(false);
/*
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(220, 220, 220));
            }
        });
*/
        // Crear el panel con borde y layout
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.lightGray);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Configurar el estilo de los labels
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Candara", Font.PLAIN, 14));
        userLabel.setForeground(Color.DARK_GRAY);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Candara", Font.PLAIN, 14));
        passLabel.setForeground(Color.DARK_GRAY);

        // Configurar el estilo de los campos de texto
        userField.setFont(new Font("Candara", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        passField.setFont(new Font("Candara", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Añadir componentes al panel con GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        // Añadir el panel a la ventana
        add(panel);

        // Añadir el action listener al botón
        loginButton.addActionListener(loginAction);
    }

    public String getUsername() {
        return userField.getText();
    }

    public String getPassword() {
        return new String(passField.getPassword());
    }
}

