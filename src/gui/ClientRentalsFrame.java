package gui;

import api.model.Client;
import api.model.Rental;
import api.services.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;

public class ClientRentalsFrame extends JFrame {

    public ClientRentalsFrame(Client client, RentalService rentalService) {
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
            model.addRow(new Object[]{
                    r.getCar().getModel() + " " + r.getCar().getModel(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getEmployee().getFirstName() + " " + r.getEmployee().getLastName()
            });
        }

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }
}
