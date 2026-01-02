package gui;

import api.services.*;
import javax.swing.*;
import java.awt.*;

/**
 * Παράθυρο σύνδεσης υπαλλήλου στο σύστημα.
 * Ο υπάλληλος εισάγει username και password και γίνεται έλεγχος
 * μέσω της κλάσης AuthService.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2026.1.1)
 */
public class LoginFrame extends JFrame {

    /**
     * Κατασκευαστής παραθύρου σύνδεσης.
     *
     * @param authService Υπηρεσία αυθεντικοποίησης χρηστών
     * @param allCars Δομή που περιέχει όλα τα αυτοκίνητα
     * @param allClients Δομή που περιέχει όλους τους πελάτες
     * @param rentalService Υπηρεσία διαχείρισης ενοικιάσεων
     */
    public LoginFrame(AuthService authService,
                      AllCars allCars,
                      AllClients allClients,
                      RentalService rentalService) {

        setTitle("Σύνδεση");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Πεδίο εισαγωγής username
        JTextField userField = new JTextField(15);
        //Πεδίο εισαγωγής κωδικού
        JPasswordField passField = new JPasswordField(15);
        //Κουμπί σύνδεσης
        JButton loginBtn = new JButton("Σύνδεση");

        /**
         * Έλεγχος στοιχείων σύνδεσης.
         * Αν τα στοιχεία είναι σωστά,ανοίγει το MainFrame.
         */
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

        //Πάνελ διάταξης στοιχείων
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
