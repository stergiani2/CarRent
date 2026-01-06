package gui;

import api.services.AuthService;

import javax.swing.*;
import java.awt.*;

/**
 * Παράθυρο login για υπαλλήλους.
 * Ο χρήστης εισάγει username και password.
 * Σε επιτυχή είσοδο τρέχει Runnable που ανοίγει το ManagementGUI.
 * Σε αποτυχία εμφανίζεται σφάλμα.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2026.01.03)
 */
public class EmployeeLoginFrame extends JFrame {

    private AuthService authService;

    /**
     * Κατασκευαστής login
     *
     * @param authService Υπηρεσία αυθεντικοποίησης
     * @param onLoginSuccess Runnable που εκτελείται αν η σύνδεση επιτύχει
     */
    public EmployeeLoginFrame(AuthService authService,Runnable onLoginSuccess) {
        this.authService = authService;

        setTitle("Σύνδεση Υπαλλήλου");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel με GridLayout για πεδία username/password
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        //Κουμπί login
        JButton loginBtn = new JButton("Σύνδεση");
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Έλεγχος σύνδεσης
            if (authService.login(username, password)) {

                dispose(); //Κλείνει το Login
                onLoginSuccess.run(); //Ανοίγει το ManagementGUI
            } else {
                //Σφάλμα Login
                JOptionPane.showMessageDialog(this,
                        "Λάθος username ή password",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel());
        panel.add(loginBtn);

        add(panel);
        setVisible(true);
    }
}
