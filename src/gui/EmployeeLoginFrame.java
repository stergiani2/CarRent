package gui;

import api.services.AuthService;

import javax.swing.*;
import java.awt.*;

/**
 * Παράθυρο login για υπαλλήλους.
 */
public class EmployeeLoginFrame extends JFrame {

    private AuthService authManager;

    public EmployeeLoginFrame(AuthService authManager,Runnable onLoginSuccess) {
        this.authManager = authManager;

        setTitle("Σύνδεση Υπαλλήλου");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        JButton loginBtn = new JButton("Σύνδεση");
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authManager.login(username, password)) {
                JOptionPane.showMessageDialog(this, "Σύνδεση επιτυχής!");
                // εδώ ανοίγεις το ManagementGUI
                // π.χ. new ManagementGUI(...);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Λάθος username ή password", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel());
        panel.add(loginBtn);

        add(panel);
        setVisible(true);
    }
}
