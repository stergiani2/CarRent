package gui;

import api.services.AllCars;
import api.services.CarHelper;

import javax.swing.*;
import java.awt.*;

public class CarsFrame extends JFrame {
        private AllCars allCars;
        private CarHelper carHelper;
        private JTable carsTable;
        private JLabel statusLabel;

        private final String[] COLUMN_CARS={
                "ID","Πινακίδα","Μάρκα","Τύπος","Μοντέλο","Έτος","Χρώμα","Κατάσταση"};

        public CarsFrame() {
                 carHelper=new CarHelper();
                initializeFrame();
        }
        public void initializeFrame(){
                setTitle("Διαχείριση αυτοκινήτων");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setSize(1200,700);
                setLocationRelativeTo(null);
                setLayout(new BorderLayout(10,10));

                JPanel contentPanel=new JPanel(new BorderLayout(10,10));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                add(contentPanel,BorderLayout.CENTER);

        }
        public void loadCarsData(){
                try {
                        allCars=carHelper.readFromFileCars("vehicles_with_plates.csv");
                        statusLabel.setText("Φορτώθηκαν " + allCars.getAllCars().size() + " αυτοκίνητα από αρχείο");

                }catch (Exception e1){
                        JOptionPane.showMessageDialog(this,"Σφάλμα φόρτωσης αρχείου: "+e1.getMessage(),"Σφάλμα",JOptionPane.WARNING_MESSAGE);
                        allCars=new AllCars();
                }
        }
}
