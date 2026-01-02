package gui;

import api.services.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(AuthService authService,
                     AllCars allCars,
                     AllClients allClients,
                     RentalService rentalService) {

        setTitle("Σύστημα Ενοικίασης");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton carsBtn = new JButton("Αυτοκίνητα");
        carsBtn.addActionListener(e -> new CarsFrame(allCars));

        JButton clientsBtn = new JButton("Πελάτες");
        clientsBtn.addActionListener(e -> new ClientsFrame(allClients));

        JButton rentalsBtn = new JButton("Ενοικιάσεις");
        rentalsBtn.addActionListener(e ->
                new RentalsFrame(rentalService, allCars, allClients, authService));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            authService.logout();
            dispose();
            new LoginFrame(authService, allCars, allClients, rentalService);
        });

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(carsBtn);
        panel.add(clientsBtn);
        panel.add(rentalsBtn);
        panel.add(logoutBtn);

        add(panel);
        setVisible(true);
    }
}
