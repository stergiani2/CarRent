package gui;

import api.model.Car;
import api.services.AllCars;
import api.services.CarHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CarsFrame extends JFrame {
        private AllCars allCars;
        private CarHelper carHelper;
        private JTable carsTable;
        private JLabel statusLabel;
        private JButton addButton,deleteButton,saveButton,refreshButton;
        private DefaultTableModel tableModel;

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
                setVisible(true);

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
        private void setupListeners(){
                addButton.addActionListener(e -> addNewCar());
                deleteButton.addActionListener(e -> deleteCar());
                saveButton.addActionListener(e -> saveCars());
                refreshButton.addActionListener(e -> loadCarsToTable());
        }
        private void loadCarsToTable(){
                tableModel.setRowCount(0);
                try {
                        int count=0;
                        for(Car car:allCars.getAllCars().values()){
                                Object[] row={car.getId(),car.getPlate(),car.getCarBrand(),car.getType(),car.getModel(),car.getYear(),
                                        car.getColor(),car.getSituation()
                                };
                                tableModel.addRow(row);
                                count++;
                        }
                        statusLabel.setText("Φορτώθηκαν "+count+" αυτοκίνητα.");

                }catch (Exception e){
                        JOptionPane.showMessageDialog(this,"Σφάλμα φόρτωσης αυτοκινήτων: "+e.getMessage(),"Σφάλμα",JOptionPane.ERROR_MESSAGE);
                }
        }
        private void saveCars(){
                try {
                        carHelper.saveToBinary(allCars);
                        JOptionPane.showMessageDialog(this,"Τα αυτοκίνητα αποθηκεύτηκαν.","Επιτυχία",JOptionPane.INFORMATION_MESSAGE);
                        statusLabel.setText("Αποθηκεύτηκαν " + allCars.getAllCars().size() + " αυτοκίνητα");
                }catch (Exception e){
                        JOptionPane.showMessageDialog(this,"Σφάλμα αποθήκευσης: "+ e.getMessage(),"Σφάλμα",JOptionPane.ERROR_MESSAGE);
                }
        }
        private void deleteCar(){
                int selectedRow=carsTable.getSelectedRow();
                if(selectedRow>=0){
                        int modelRow=carsTable.convertRowIndexToModel(selectedRow);
                        String carId=(String) tableModel.getValueAt(modelRow,0);
                        String plate=(String)  tableModel.getValueAt(modelRow,1);
                        int confirm=JOptionPane.showConfirmDialog(this,"Θέλετε να διαγράψετε αυτό το αυτοκίνητο με πινακίδα: "+plate+";","Επιβεβαίωση διαγραφής",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                        if (confirm==JOptionPane.YES_OPTION){
                                allCars.getAllCars().remove(carId);
                                loadCarsToTable();
                                statusLabel.setText("Διαγράφτηκε το αυτοκίνητο: "+plate);
                        }else {
                                JOptionPane.showMessageDialog(this,"Παρακαλώ επιλέξτε ένα αυτοκίνητο για διαγραφή","Προειδοποίηση",JOptionPane.WARNING_MESSAGE);
                        }
                }
        }

        private void addNewCar(){
                CarDialog dialog=new CarDialog(this,null);
                dialog.setVisible(true);
                if(dialog.isSaved()){

                }
        }


        /*public JPanel createFilterPanel() {
                JPanel panel = new JPanel(new GridLayout());
                panel.setBorder(BorderFactory.createTitledBorder("Φίλτρα αναζήτησης"));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 3;
                gbc.weightx = 1.0;
                searchField = new JTextField(20);
                panel.add(searchField, gbc);

                gbc.gridx = 4;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                gbc.weightx = 0;
                JButton searchButton = new JButton("Αναζήτηση:");
                searchButton.addActionListener(e -> applyFilters());
                panel.add(searchButton, gbc);
                return panel;
        }
        private void applyFilters(){

        }*/

        private JPanel createButtonPanel(){
                JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER,15,10));

                addButton=new JButton("Προσθήκη");
                deleteButton=new JButton("Διαγραφή");
                refreshButton=new JButton("Ανανέωση");
                saveButton=new JButton("Αποθήκευση");

                panel.add(addButton);
                panel.add(deleteButton);
                panel.add(refreshButton);
                panel.add(saveButton);

                return panel;
        }

}
