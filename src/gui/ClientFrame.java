package gui;


import api.model.Client;
import api.services.AllClients;
import api.services.ClientHelper;
import api.services.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;


/**
 * Κλάση αυτή δημιουργεί πλαίσιο αλληλεπίδρασης με τον χρήστη για τη διαχείριση πελατών, όπως προσθήκη πελάτη, επεξεργασία, ανανέωση,
 * αναζήτηση και καθαρισμό.
 *
 * @author Αλεξάνδρα Σακελλαριάδη
 * @version 0.2(2025.12.10)
 **/
public class ClientFrame extends JFrame {
    //Δομή AllClients για την αποθήκευση όλων των πελατών
    private AllClients allClients;
    //Πίνακας που δημιουργεί γραμμές και στήλες για να εμφανίζονται τα αυτοκίνητα
    private JTable clientsTable;
    // Περιέχει όλα τα δεδομένα (τιμές των κελιών) και τη δομή (στήλες) του πίνακα
    private DefaultTableModel tableModel;
    //Πεδίο για την αναζήτηση πελάτη
    private JTextField searchField;
    //Μενού επιλογών που ο χρήστης διαλέγει μια τιμή από τη λίστα (ΑΦΜ, ονοματεπώνυμο ή τηλέφωνο)
    private JComboBox<String> searchComboBox;
    //Δομή ClientHelper
    private ClientHelper clientHelper;
    private RentalService rentalService;
    /**
     * Κατασκευαστής πλαισίου με τα απαραίτητα στοιχεία
     * @param allClients Δομή AllClients για την αποθήκευση όλων των πελατών
     */
    public ClientFrame(AllClients allClients,RentalService rentalService) {
        this.allClients = allClients;
        this.rentalService = rentalService;
        clientHelper=new ClientHelper(allClients);
        initComponents();
        setTitle("Διαχείριση πελατών");
        setSize(1000, 600);
        try{
            clientHelper.loadClientsFromFile();
            refreshTable();
        }catch (IOException e){
            JOptionPane.showMessageDialog(this,
                    "Σφάλμα κατά την φόρτωση των δεδομένων: " + e.getMessage(),
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Δημιουργία βασικών στοιχείων όπως το πάνελ του μενού, της αναζήτησης, τον πίνακα πελατών και των κουμπιών διαχείρισης
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Αρχείο");

        JMenuItem exitItem = new JMenuItem("Έξοδος");
        exitItem.addActionListener(e -> dispose());
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Αναζήτηση:"));
        searchComboBox = new JComboBox<>(new String[]{"ΑΦΜ","Όνομα","Επώνυμο","Τηλέφωνο","Email"});
        searchPanel.add(searchComboBox);
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Αναζήτηση");
        searchButton.addActionListener(e -> searchClients());
        searchPanel.add(searchButton);
        JButton clearSearchButton = new JButton("Καθαρισμός");
        clearSearchButton.addActionListener(e -> clearSearch());
        searchPanel.add(clearSearchButton);

        add(searchPanel, BorderLayout.NORTH);

        String[] columns = {"ΑΦΜ","Όνομα","Επώνυμο","Τηλέφωνο","Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        clientsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clientsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Προσθήκη Πελάτη");
        addButton.addActionListener(e -> addClient());
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Επεξεργασία");
        editButton.addActionListener(e -> editClient());
        buttonPanel.add(editButton);
        JButton rentalsButton = new JButton("Ιστορικό Ενοικιάσεων");
        rentalsButton.addActionListener(e -> showClientRentals());
        buttonPanel.add(rentalsButton);


        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    /**
     * Ανανέωση του πίνακα
     */

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Client client:allClients.getAllClients()) {
            Object[] rowData={
                    client.getAFM(),
                    client.getFirstName(),
                    client.getLastName(),
                    client.getPhone(),
                    client.getEmail()};
            tableModel.addRow(rowData);
        }
    }

    /**
     * Αναζήτηση πελάτη
     */
    private void searchClients() {
        String searchText = searchField.getText().trim().toLowerCase();
        String searchType = (String) searchComboBox.getSelectedItem();

        tableModel.setRowCount(0);

        for (Client client : allClients.getAllClients()) {
            boolean match = false;

            if (searchText.isEmpty()) {
                match = true;
            } else {
                switch (searchType) {
                    case "Όλα":
                        match = client.getAFM().toLowerCase().contains(searchText) ||
                                client.getFirstName().toLowerCase().contains(searchText) ||
                                client.getLastName().toLowerCase().contains(searchText) ||
                                client.getPhone().toLowerCase().contains(searchText) ||
                                client.getEmail().toLowerCase().contains(searchText);
                        break;
                    case "ΑΦΜ":
                        match=client.getAFM().toLowerCase().contains(searchText);
                        break;
                    case "Όνομα":
                        match = client.getFirstName().toLowerCase().contains(searchText);
                        break;
                    case "Επώνυμο":
                        match =client.getLastName().toLowerCase().contains(searchText);
                        break;
                    case "Τηλέφωνο":
                        match = client.getPhone().toLowerCase().contains(searchText);
                        break;
                    case "Email":
                        match = client.getEmail().toLowerCase().contains(searchText);
                        break;
                }
            }

            if (match) {
                tableModel.addRow(new Object[]{
                        client.getAFM().trim(),
                        client.getFirstName().trim(),
                        client.getLastName().trim(),
                        client.getPhone().trim(),
                        client.getEmail().trim()
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
     * Προσθήκη πελάτη και έλεγχος για το αν υπάρχει ήδη
     */
    private void addClient() {
        ClientDialog dialog = new ClientDialog(this, null,allClients);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            Client newClient = dialog.getClient();
            if (allClients.addClient(newClient)) {
                JOptionPane.showMessageDialog(this, "Ο πελάτης προστέθηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                clientHelper.saveClientsToFile();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Ο πελάτης υπάρχει ήδη ή τα δεδομένα δεν είναι έγκυρα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Επεξεργασία πελάτη
     */
    private void editClient() {
        int selectedRow = clientsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε έναν πελάτη για επεξεργασία.", "Προειδοποίηση", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String clientAFM = (String) tableModel.getValueAt(selectedRow, 0);
        Client selectedClient = allClients.searchClientByAFM(clientAFM);

        ClientDialog dialog=new ClientDialog(this,selectedClient,allClients);
        dialog.setVisible(true);
        if(dialog.isSaved()){
            Client updatedClient=dialog.getClient();
            System.out.println(updatedClient.getAFM());
            allClients.getAllClients().remove(selectedClient);
            allClients.getAllClients().add(updatedClient);
            clientHelper.saveClientsToFile();
            refreshTable();
            SwingUtilities.invokeLater(() -> {
                clientsTable.revalidate();
                clientsTable.repaint();
            });
        }

    }
    private void showClientRentals() {
        int selectedRow = clientsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Επιλέξτε έναν πελάτη",
                    "Προειδοποίηση",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String afm = (String) tableModel.getValueAt(selectedRow, 0);
        Client client = allClients.searchClientByAFM(afm);

        new ClientRentalsFrame(client, rentalService);
    }

}