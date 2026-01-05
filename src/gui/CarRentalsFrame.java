package gui;

import api.model.Car;
import api.model.Rental;
import api.services.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

/**
 * Παράθυρο GUI που εμφανίζει το ιστορικό ενοικιάσεων ενός αυτοκινήτου.
 * Κάθε γραμμή αντιστοιχεί σε μία ενοικίαση, με πληροφορίες για
 * τον πελάτη, την ημερομηνία, και τον υπάλληλο που την έκανε.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2026.01.03)
 */
public class CarRentalsFrame extends JFrame {

    /**
     * Κατασκευαστής του παραθύρου.
     *
     * @param car Το αυτοκίνητο για το οποίο εμφανίζεται το ιστορικό
     * @param rentalService Η υπηρεσία που χειρίζεται τις ενοικιάσεις
     */
    public CarRentalsFrame(Car car, RentalService rentalService) {

        // Ορίζουμε τον τίτλο του παραθύρου με τα στοιχεία του αυτοκινήτου
        setTitle("Ενοικιάσεις Αυτοκινήτου: " + car.getModel() + " " + car.getModel());
        setSize(700, 400);
        setLocationRelativeTo(null);

        // Δημιουργούμε το μοντέλο του πίνακα
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Πελάτης", "Από", "Έως", "Υπάλληλος"}, 0
        );

        // Παίρνουμε τις ενοικιάσεις του αυτοκινήτου
        Set<Rental> rentals = rentalService.getRentalsByCar(car);
        for (Rental r : rentals) {
            // Προσθέτουμε κάθε ενοικίαση ως νέα γραμμή στον πίνακα
            model.addRow(new Object[]{
                    r.getClient().getFirstName() + " " + r.getClient().getLastName(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getEmployee().getFirstName() + " " + r.getEmployee().getLastName()
            });
        }

        // Δημιουργούμε τον πίνακα και τον βάζουμε σε scroll pane
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }
}

