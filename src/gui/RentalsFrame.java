package gui;

import api.model.*;
import api.services.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class RentalsFrame extends JFrame {

    public RentalsFrame(RentalService rentalService,
                        AllCars allCars,
                        AllClients allClients,
                        AuthService authService) {

        setTitle("Ενοικιάσεις");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JComboBox<Car> carBox = new JComboBox<>(
                allCars.getAllCars().values().toArray(new Car[0]));

        JButton rentBtn = new JButton("Ενοικίαση");
        rentBtn.addActionListener(e -> {
            Car car = (Car) carBox.getSelectedItem();
            Client client = allClients.searchClientByAFM(
                    JOptionPane.showInputDialog("ΑΦΜ πελάτη:"));

            if (client != null) {
                rentalService.createRental(
                        car, client, authService.getCurrentUser(),
                        LocalDate.now(), LocalDate.now().plusDays(3)
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
