package gui;
import javax.swing.*;

import api.services.AllCars;
import api.services.AllClients;
import api.services.CarHelper;
import gui.CarsFrame;
import gui.ClientFrame;

import java.awt.*;

public class ManagementGUI extends JFrame{
    private AllCars allCars;
    private AllClients allClients;


    public ManagementGUI(AllCars allCars,AllClients allClients){
        this.allCars=allCars;
        this.allClients=allClients;
        initComponents();
        setTitle("Σύστημα Διαχείρισης");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void initComponents(){
        setLayout(new BorderLayout(10, 10));
        add(createMainPanel(), BorderLayout.CENTER);
    }

        private JPanel createMainPanel() {
            JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            panel.setBackground(new Color(190, 230, 190));

            panel.add(createDashboardCard("Αυτοκίνητα",e -> new CarsFrame(allCars).setVisible(true)));

            panel.add(createDashboardCard("Πελάτες",e -> new ClientFrame(allClients).setVisible(true)));

            return panel;
    }
    private JPanel createDashboardCard(String title, java.awt.event.ActionListener action) {
        JButton card = new JButton(title);
        card.setFont(new Font("Arial", Font.BOLD, 14));
        card.setBackground(Color.WHITE);
        card.addActionListener(action);
        JPanel panel=new JPanel(new GridLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.add(card);
        return panel;
    }
}
