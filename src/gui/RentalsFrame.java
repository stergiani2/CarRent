package gui;

import api.model.*;
import api.services.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;


/**
 * Παράθυρο δημιουργίας ενοικίασης.
 * Επιτρέπει:
 * -προβολή όλων των ενοικιάσεων
 * -δημιουργία νέας ενοικίασης
 * -επιλογή διαθέσιμου αυτοκινήτου
 *
 * Οι ενοικιάσεις αντλούνται από το RentalService.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2026.01.01)
 */
public class RentalsFrame extends JFrame {

    //Υπηρεσίες & δομές δεδομένων
    private RentalService rentalService;
    private AllCars allCars;
    private AllClients allClients;
    private AuthService authService;

    //Πίνακας εμφάνισης ενοικιάσεων
    private JTable rentalTable;
    private DefaultTableModel tableModel;

    /**
     * Κατασκευαστής παραθύρου ενοικιάσεων.
     *
     * @param rentalService Υπηρεσία ενοικιάσεων
     * @param allCars Όλα τα αυτοκίνητα
     * @param allClients Όλοι οι πελάτες
     * @param authService Υπρεσία αυθεντικοποίησης
     */
    public RentalsFrame(RentalService rentalService, AllCars allCars, AllClients allClients, AuthService authService) {
        this.rentalService = rentalService;
        this.allCars = allCars;
        this.allClients = allClients;
        this.authService = authService;

        setTitle("Διαχείριση Ενοικιάσεων");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        //ΠΙΝΑΚΑΣ ΕΝΙΚΙΑΣΕΩΝ

        // Ορισμός στηλών πίνακα
        String[] columns = {"ID", "Αυτοκίνητο", "Πελάτης", "Από", "Έως", "Κατάσταση"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Απαγόρευση επεξεργασίας
            }
        };
        rentalTable = new JTable(tableModel);
        add(new JScrollPane(rentalTable), BorderLayout.CENTER);

        // Πάνελ Ελέγχου
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Επιλογή Αυτοκινήτου (Μόνο τα διαθέσιμα)
        JComboBox<Car> carBox = new JComboBox<>();
        for (Car car : allCars.getAllCars().values()) {
            if (car.getSituation().equals("Διαθέσιμο")) {
                carBox.addItem(car);
            }
        }

        //Κουμπί δμιουργίας ενοικίασης
        JButton rentBtn = new JButton("Καταχώρηση Ενοικίασης");

        rentBtn.addActionListener(e -> {
            //Επιλεγμένο αυτοκίνητο
            Car selectedCar = (Car) carBox.getSelectedItem();
            if (selectedCar == null) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν διαθέσιμα αυτοκίνητα.");
                return;
            }

            //Εισαγωγή ΑΦΜ πελάτη
            String afm = JOptionPane.showInputDialog(this, "Εισάγετε ΑΦΜ Πελάτη:");
            if (afm == null || afm.trim().isEmpty()) return;

            Client client = allClients.searchClientByAFM(afm.trim());
            if (client == null) {
                JOptionPane.showMessageDialog(this, "Ο πελάτης δεν βρέθηκε!");
                return;
            }

            // Δημιουργία ενοικίασης
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

    /**
     * Ανανέωση πίνακα ενοικιάσεων.
     * Διαβάζει όλες τις ενοικιάσεις από το RentalService
     * και τις εμφανίζει στο JTable.
     */
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