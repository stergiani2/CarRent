package gui;

import api.model.Car;
import api.services.AllCars;
import api.services.CarHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

/**
 * Κλάση αυτή δημιουργεί πλαίσιο αλληλεπίδρασης με τον χρήστη για τη διαχείριση αυτοκινήτων, όπως προσθήκη αυτοκινήτου, επεξεργασία, ανανέωση,
 * αναζήτηση και καθαρισμό.
 *
 * @author Αλεξάνδρα Σακελλαριάδη
 * @version 0.2(2025.12.10)
 */
public class CarsFrame extends JFrame {
    //Δομή AllCars για την αποθήκευση οών των αυτοκινήτων
    private AllCars allCars;
    //Δομή για τη διαχείριση αρχείων
    private CarHelper carHelper;
    //Πίνακας που δημιουργεί γραμμές και στήλες για να εμφανίζονται τα αυτοκίνητα
    private JTable carTable;
    // Περιέχει όλα τα δεδομένα (τιμές των κελιών) και τη δομή (στήλες) του πίνακα
    private DefaultTableModel tableModel;
    //Πεδίο για την αναζήτηση αυτοκίνητου
    private JTextField searchField;
    //Μενού επιλογών που ο χρήστης διαλέγει μια τιμή από τη λίστα(Όλα, ID, Πινακίδα, Μάρκα, Τύπος, Μοντέλο, Χρώμα, Κατάσταση)
    private JComboBox<String> searchComboBox;


    /**
     * Κατασκευαστής πλαισίου με τα απαραίτητα στοιχεία
     * @param allCars Δομή AllCars για την αποθήκευση όλων των αυτοκινήτων
     */
    public CarsFrame(AllCars allCars) {
        this.allCars = allCars;
        this.carHelper = new CarHelper();
        initComponents();
        setTitle("Διαχείριση Αυτοκινήτων");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Δημιουργία βασικών στοιχείων όπως το πάνελ του μενού, της αναζήτησης, τον πίνακα αυτοκινήτων και των κουμπιών διαχείρισης
     */

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Πάνελ μενού
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Αρχείο");

        JMenuItem loadFromCSVItem = new JMenuItem("Φόρτωση από CSV");
        loadFromCSVItem.addActionListener(e -> loadFromCSV());
        fileMenu.add(loadFromCSVItem);

        JMenuItem saveToBinaryItem = new JMenuItem("Αποθήκευση σε Binary");
        saveToBinaryItem.addActionListener(e -> saveToBinary());
        fileMenu.add(saveToBinaryItem);

        JMenuItem loadFromBinaryItem = new JMenuItem("Φόρτωση από Binary");
        loadFromBinaryItem.addActionListener(e -> loadFromBinary());
        fileMenu.add(loadFromBinaryItem);

        JMenuItem exitItem = new JMenuItem("Έξοδος");
        exitItem.addActionListener(e -> dispose());
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Πάνελ αναζήτησης
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Αναζήτηση:"));
        searchComboBox = new JComboBox<>(new String[]{"Όλα", "ID", "Πινακίδα", "Μάρκα", "Τύπος", "Μοντέλο", "Χρώμα", "Κατάσταση"});
        searchPanel.add(searchComboBox);
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Αναζήτηση");
        searchButton.addActionListener(e -> searchCars());
        searchPanel.add(searchButton);
        JButton clearSearchButton = new JButton("Καθαρισμός");
        clearSearchButton.addActionListener(e -> clearSearch());
        searchPanel.add(clearSearchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Πίνακας αυτοκινήτων
        String[] columns = {"ID", "Πινακίδα", "Μάρκα", "Τύπος", "Μοντέλο", "Έτος", "Χρώμα", "Κατάσταση"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        carTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carTable);
        add(scrollPane, BorderLayout.CENTER);

        // Κουμπιά διαχείρισης
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Προσθήκη Αυτοκινήτου");
        addButton.addActionListener(e -> addCar());
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Επεξεργασία");
        editButton.addActionListener(e -> editCar());
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Διαγραφή");
        deleteButton.addActionListener(e -> deleteCar());
        buttonPanel.add(deleteButton);

        JButton refreshButton = new JButton("Ανανέωση");
        refreshButton.addActionListener(e -> refreshTable());
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    /**
     * Ανανέωση του πίνακα
     */

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Car car : allCars.getAllCars().values()) {
            tableModel.addRow(new Object[]{
                    car.getId(),
                    car.getPlate(),
                    car.getCarBrand(),
                    car.getType(),
                    car.getModel(),
                    car.getYear(),
                    car.getColor(),
                    car.getSituation()
            });
        }
    }

    /**
     * Αναζήτηση αυτοκινήτου
     */
    private void searchCars() {
        String searchText = searchField.getText().trim().toLowerCase();
        String searchType = (String) searchComboBox.getSelectedItem();

        tableModel.setRowCount(0);

        for (Car car : allCars.getAllCars().values()) {
            boolean match = false;

            if (searchText.isEmpty()) {
                match = true;
            } else {
                switch (searchType) {
                    case "Όλα":
                        match = car.getId().toLowerCase().contains(searchText) ||
                                car.getPlate().toLowerCase().contains(searchText) ||
                                car.getCarBrand().toLowerCase().contains(searchText) ||
                                car.getType().toLowerCase().contains(searchText) ||
                                car.getModel().toLowerCase().contains(searchText) ||
                                car.getColor().toLowerCase().contains(searchText) ||
                                car.getSituation().toLowerCase().contains(searchText);
                        break;
                    case "ID":
                        match=car.getId().toLowerCase().contains(searchText);
                        break;
                    case "Πινακίδα":
                        match = car.getPlate().toLowerCase().contains(searchText);
                        break;
                    case "Μάρκα":
                        match = car.getCarBrand().toLowerCase().contains(searchText);
                        break;
                    case "Τύπος":
                        match = car.getType().toLowerCase().contains(searchText);
                        break;
                    case "Μοντέλο":
                        match = car.getModel().toLowerCase().contains(searchText);
                        break;
                    case "Χρώμα":
                        match = car.getColor().toLowerCase().contains(searchText);
                        break;
                    case "Κατάσταση":
                        match = car.getSituation().toLowerCase().contains(searchText);
                        break;
                }
            }

            if (match) {
                tableModel.addRow(new Object[]{
                        car.getId(),
                        car.getPlate(),
                        car.getCarBrand(),
                        car.getType(),
                        car.getModel(),
                        car.getYear(),
                        car.getColor(),
                        car.getSituation()
                });
            }
        }
    }

    /**
     * Καθαρισμός φίλτρου αναζήτησης
     */
    private void clearSearch() {
        searchField.setText("");
        searchComboBox.setSelectedIndex(0);
        refreshTable();
    }

    /**
     * Προσθήκη αυτοκινήτου και έλεγχος για το αν υπάρχει ήδη
     */
    private void addCar() {
        CarDialog dialog = new CarDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            Car newCar = dialog.getCar();
            if (allCars.addCar(newCar)) {
                JOptionPane.showMessageDialog(this, "Το αυτοκίνητο προστέθηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Το αυτοκίνητο υπάρχει ήδη ή τα δεδομένα δεν είναι έγκυρα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Επεξεργασία αυτοκίνητου
     */
    private void editCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε ένα αυτοκίνητο για επεξεργασία.", "Προειδοποίηση", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String carId = (String) tableModel.getValueAt(selectedRow, 0);
        Car selectedCar = allCars.getCar(carId);

        CarDialog dialog = new CarDialog(this, selectedCar);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            Car updatedCar = dialog.getCar();
            // Ενημέρωση του αυτοκινήτου
            allCars.getAllCars().put(carId, updatedCar);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Οι αλλαγές αποθηκεύτηκαν.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Διαγραφή αυτοκινήτου
     */
    private void deleteCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε ένα αυτοκίνητο για διαγραφή.", "Προειδοποίηση", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String carId = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Είστε σίγουρος ότι θέλετε να διαγράψετε αυτό το αυτοκίνητο;\nID: " + carId,
                "Επιβεβαίωση Διαγραφής",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            allCars.getAllCars().remove(carId);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Το αυτοκίνητο διαγράφηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Φόρτωση αρχείου τύπου CSV
     */
    private void loadFromCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Επιλογή CSV αρχείου");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                AllCars loadedCars = carHelper.readFromFileCars(fileChooser.getSelectedFile().getPath());
                allCars.getAllCars().clear();
                allCars.getAllCars().putAll(loadedCars.getAllCars());
                refreshTable();
                JOptionPane.showMessageDialog(this,
                        "Φορτώθηκαν " + allCars.getAllCars().size() + " αυτοκίνητα από το CSV αρχείο.",
                        "Επιτυχία",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Σφάλμα κατά τη φόρτωση: " + e.getMessage(),
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Αποθήκευση δυαδικού αρχείου
     */
    private void saveToBinary() {
        try {
            carHelper.saveToBinary(allCars);
            JOptionPane.showMessageDialog(this,
                    "Τα δεδομένα αποθηκεύτηκαν επιτυχώς στο binary αρχείο.",
                    "Επιτυχία",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Σφάλμα κατά την αποθήκευση: " + e.getMessage(),
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Φόρτωση αυτοκίνητων από δυαδικό αρχείο
     */
    private void loadFromBinary() {
        try {
            AllCars loadedCars=carHelper.loadFromBinaryFile();

            // Αφαίρεση των υπαρχόντων αυτοκινήτων
            allCars.getAllCars().clear();

            // Προσθήκη των φορτωμένων αυτοκινήτων
            for (Car car : loadedCars.getAllCars().values()) {
                allCars.addCar(car);
            }

            refreshTable();
            JOptionPane.showMessageDialog(this,
                    "Φορτώθηκαν " + allCars.getAllCars().size() + " αυτοκίνητα από το binary αρχείο.",
                    "Επιτυχία",
                    JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Σφάλμα κατά τη φόρτωση: " + e.getMessage(),
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}
