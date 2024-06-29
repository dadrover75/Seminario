package App.View;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private JTabbedPane tabbedPane;

    public MainView() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    public void addTab(String title, Component component) {
        tabbedPane.addTab(title, component);
    }
}

