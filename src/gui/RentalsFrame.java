package gui;

import api.model.*;
import api.services.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;


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

    private JTable rentalTable;
    private DefaultTableModel tableModel;

    public RentalsFrame(RentalService rentalService, AllCars allCars, AllClients allClients, AuthService authService) {
        this.rentalService = rentalService;
        this.allCars = allCars;
        this.allClients = allClients;
        this.authService = authService;

        setTitle("Διαχείριση Ενοικιάσεων");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Δημιουργία Πίνακα
        String[] columns = {"ID", "Αυτοκίνητο", "Πελάτης", "Από", "Έως", "Κατάσταση"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        rentalTable = new JTable(tableModel);
        add(new JScrollPane(rentalTable), BorderLayout.CENTER);

        // 2. Πάνελ Ελέγχου (Κάτω μέρος)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Επιλογή Αυτοκινήτου (Μόνο τα διαθέσιμα)
        JComboBox<Car> carBox = new JComboBox<>();
        for (Car car : allCars.getAllCars().values()) {
            if (car.getSituation().equals("Διαθέσιμο")) {
                carBox.addItem(car);
            }
        }

        JButton rentBtn = new JButton("Καταχώρηση Ενοικίασης");
        rentBtn.addActionListener(e -> {
            Car selectedCar = (Car) carBox.getSelectedItem();
            if (selectedCar == null) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν διαθέσιμα αυτοκίνητα.");
                return;
            }

            String afm = JOptionPane.showInputDialog(this, "Εισάγετε ΑΦΜ Πελάτη:");
            if (afm == null || afm.trim().isEmpty()) return;

            Client client = allClients.searchClientByAFM(afm.trim());
            if (client == null) {
                JOptionPane.showMessageDialog(this, "Ο πελάτης δεν βρέθηκε!");
                return;
            }

            // Δημιουργία ενοικίασης (π.χ. για 3 ημέρες από σήμερα)
            try {
                rentalService.createRental(
                        selectedCar,
                        client,
                        authService.getCurrentUser(),
                        LocalDate.now(),
                        LocalDate.now().plusDays(3)
                );

                JOptionPane.showMessageDialog(this, "Η ενοικίαση καταχωρήθηκε επιτυχώς!");
                refreshTable(); // Ανανέωση πίνακα
                carBox.removeItem(selectedCar); // Αφαίρεση από τη λίστα αφού νοικιάστηκε
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα: " + ex.getMessage());
            }
        });

        controlPanel.add(new JLabel("Διαθέσιμα Αυτοκίνητα:"));
        controlPanel.add(carBox);
        controlPanel.add(rentBtn);

        add(controlPanel, BorderLayout.SOUTH);

        // Αρχικό γέμισμα πίνακα
        refreshTable();
        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Rental r : rentalService.getAllRentals()) {
            tableModel.addRow(new Object[]{
                    r.getRentalId(),
                    r.getCar().getCarBrand() + " " + r.getCar().getModel(),
                    r.getClient().getLastName() + " " + r.getClient().getFirstName(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.isActive() ? "Ενεργή" : "Ολοκληρωμένη"
            });
        }
    }

}