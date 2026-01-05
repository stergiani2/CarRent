package gui;

import api.model.*;
import api.services.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Παράθυρο δημιουργίας ενοικίασης.
 * Ο χρήστης επιλέγει αυτοκίνητο και πελάτη
 * και καταχωρείται νέα ενοικίαση στο σύστημα.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2026.01.01)
 */
public class RentalsFrame extends JFrame {

    private RentalService rentalService;
    private AllCars allCars;
    private AllClients allClients;
    private AuthService authService;

    /**
     * Κατασκευαστής παραθύρου ενοικιάσεων.
     *
     * @param rentalService Υπηρεσία διαχείρισης ενοικιάσεων
     * @param allCars Δομή αυτοκινήτων
     * @param allClients Δομή πελατών
     * @param authService Υπηρεσία αυθεντικοποίησης
     */
    public RentalsFrame(RentalService rentalService,
                        AllCars allCars,
                        AllClients allClients,
                        AuthService authService) {
        this.rentalService = rentalService;
        this.allCars = allCars;
        this.allClients = allClients;
        this.authService = authService;

        setTitle("Ενοικιάσεις");
        setSize(500, 300);
        setLocationRelativeTo(null);

        //ComboBox επιλογής αυτοκινήτου
        JComboBox<Car> carBox = new JComboBox<>(
                allCars.getAllCars().values().toArray(new Car[0]));

        //Κουμπί δημιουργίας ενοικίασης
        JButton rentBtn = new JButton("Ενοικίαση");

        /**
         * Δημιουργία νέας ενοικίασης.
         * Ο πελάτης εντοπίζεται με βάση το ΑΦΜ.
         */
        rentBtn.addActionListener(e -> {
            Car car = (Car) carBox.getSelectedItem();
            Client client = allClients.searchClientByAFM(
                    JOptionPane.showInputDialog("ΑΦΜ πελάτη:"));

            if (client != null) {
                rentalService.createRental(
                        car,
                        client,
                        authService.getCurrentUser(),
                        LocalDate.now(),
                        LocalDate.now().plusDays(3)
                );
                JOptionPane.showMessageDialog(this, "Η ενοικίαση δημιουργήθηκε");
            }
        });

        setLayout(new BorderLayout());
        add(carBox, BorderLayout.CENTER);
        add(rentBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

}

