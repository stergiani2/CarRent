package gui;

import api.services.*;
import javax.swing.*;
import java.awt.*;

/**
 * Παράθυρο σύνδεσης υπαλλήλου στο σύστημα.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2026.1.1)
 */
public class LoginFrame extends JFrame {

    /**
     * Κατασκευαστής παραθύρου σύνδεσης.
     *
     * @param authService
     * @param allCars
     * @param allClients
     * @param rentalService
     */
    public LoginFrame(AuthService authService,
                      AllCars allCars,
                      AllClients allClients,
                      RentalService rentalService) {

        setTitle("Σύνδεση");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton loginBtn = new JButton("Σύνδεση");

        loginBtn.addActionListener(e -> {
            if (authService.login(userField.getText(),
                    new String(passField.getPassword()))) {
                dispose();
                new MainFrame(authService, allCars, allClients, rentalService);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Λάθος στοιχεία", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginBtn);

        add(panel);
        setVisible(true);
    }
}
