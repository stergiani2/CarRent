package gui;
import javax.swing.*;

import api.services.AllCars;
import api.services.AllClients;
import api.services.AuthService;
import api.services.RentalService;


import java.awt.*;

public class ManagementGUI extends JFrame{
    //Δομή AllCars
    private AllCars allCars;
    //Δομή AllClients
    private AllClients allClients;
    private RentalService rentalService;
    private AuthService authService;

    /**
     * Παράθυρο γενικής διαχείρισης
     * @param allCars Αποθηκεύει όλα τα αυτοκίνητα
     * @param allClients Αποθηκεύει όλους τους πελάτες
     */
    public ManagementGUI(AllCars allCars, AllClients allClients, RentalService rentalService,AuthService authService){
        this.allCars=allCars;
        this.allClients=allClients;
        this.rentalService=rentalService;
        this.authService=authService;
        initComponents();
        setTitle("Σύστημα Διαχείρισης");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Βασικά στοιχεία
     */
    private void initComponents(){
        setLayout(new BorderLayout(10, 10));
        add(createMainPanel(), BorderLayout.CENTER);
    }

    /**
     * Πάνελ για τις κατηγορίες αυτοκίνητα, πελάτες, υπάλληλοι, ενοικίαση
     * @return panel
     */
        private JPanel createMainPanel() {
            JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            panel.setBackground(new Color(190, 230, 190));

            panel.add(createDashboardCard("Αυτοκίνητα",e -> new CarsFrame(allCars).setVisible(true)));

            panel.add(createDashboardCard("Πελάτες",e -> new ClientFrame(allClients,rentalService).setVisible(true)));

            panel.add(createDashboardCard("Ενοικίαση",e->new RentalsFrame(rentalService,allCars,allClients,authService).setVisible(true)));

            panel.add(createDashboardCard("Υπάλληλοι",e->new EmployeeFrame(authService).setVisible(true)));

            return panel;
    }

    /**
     * Βοηθητική μέθοδος για τις κατηγορίες αυτοκίνητα, πελάτες, υπάλληλοι, ενοικίαση που δημιουργεί τα πλαίσια στη διεπαφή
     * @param title Τίτος πλαισίου
     * @param action Η αντίστοιχη ενέργεια που θέλουμε
     * @return Πάνελ
     */
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
