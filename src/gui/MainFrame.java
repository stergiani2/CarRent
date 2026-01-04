package gui;

import api.services.*;
import javax.swing.*;
import java.awt.*;

/**
 * Κεντρικό παράθυρο του συστήματος ενοικίασης.
 * Από εδώ ο χρήστης μπορεί να μεταβεί στις βασικές λειτουργίες
 * (Αυτοκίνητα, Πελάτες, Ενοικιάσεις) ή να αποσυνδεθεί.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2026.01.01)
 */
public class MainFrame extends JFrame {

    /**
     * Κατασκευαστής του κεντρικού παραθύρου.
     *
     * @param authService Υπηρεσία αυθεντικοποίησης
     * @param allCars Δομή αποθήκευσης αυτοκινήτων
     * @param allClients Δομή αποθήκευσης πελατών
     * @param rentalService Υπηρεσία ενοικιάσεων
     */
    public MainFrame(AuthService authService,
                     AllCars allCars,
                     AllClients allClients,
                     RentalService rentalService) {

        setTitle("Σύστημα Ενοικίασης");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Κουμπί μετάβασης στη διαχείριση αυτοκινήτων
        JButton carsBtn = new JButton("Αυτοκίνητα");
        carsBtn.addActionListener(e -> new CarsFrame(allCars));

        //Κουμπί μετάβασης στη διαχείριση πελατών
        JButton clientsBtn = new JButton("Πελάτες");
        clientsBtn.addActionListener(e -> new ClientFrame(allClients));

        //Κουμπί μετάβασης στις ενοικιάσεις
        JButton rentalsBtn = new JButton("Ενοικιάσεις");
        rentalsBtn.addActionListener(e ->
                new RentalsFrame(rentalService, allCars, allClients, authService));

        //Κουμπί αποσύνδεσης
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            authService.logout();
            dispose();
            new LoginFrame(authService, allCars, allClients, rentalService);
        });

        //Πάνελ κουμπιών
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(carsBtn);
        panel.add(clientsBtn);
        panel.add(rentalsBtn);
        panel.add(logoutBtn);

        add(panel);
        setVisible(true);
    }
}
