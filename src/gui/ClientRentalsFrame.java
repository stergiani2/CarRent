package gui;

import api.model.Client;
import api.model.Rental;
import api.services.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

/**
 * Παράθυρο GUI που εμφανίζει το ιστορικό ενοικιάσεων ενός πελάτη.
 * Κάθε γραμμή αντιστοιχεί σε μία ενοικίαση, με πληροφορίες για
 * το αυτοκίνητο, την ημερομηνία, και τον υπάλληλο που την έκανε.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2026.01.02)
 */
public class ClientRentalsFrame extends JFrame {

    /**
     * Κατασκευαστής του παραθύρου.
     *
     * @param client Ο πελάτης για τον οποίο εμφανίζεται το ιστορικό
     * @param rentalService Η υπηρεσία που χειρίζεται τις ενοικιάσεις
     */
    public ClientRentalsFrame(Client client, RentalService rentalService) {

        //Ορίζουμε τον τίτλο του παραθύρου με το όνομα του πελάτη
        setTitle("Ενοικιάσεις Πελάτη: " + client.getFirstName() + " " + client.getLastName());
        setSize(700, 400);
        setLocationRelativeTo(null);

        //Δημιουργούμε το μοντέλο του πίνακα
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Αυτοκίνητο", "Από", "Έως", "Υπάλληλος"}, 0
        );

        //Παίρνουμε τις ενοικιάσεις του πελάτη
        Set<Rental> rentals = rentalService.getRentalByClient(client);
        for (Rental r : rentals) {
            //Προσθέτουμε κάθε ενοικίαση ως νέα γραμμή στον πίνακα
            model.addRow(new Object[]{
                    r.getCar().getModel() + " " + r.getCar().getModel(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getEmployee().getFirstName() + " " + r.getEmployee().getLastName()
            });
        }

        // Δημιουργούμε τον πίνακα και τον βάζουμε σε scroll pane για εύκολη περιήγηση
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }
}
