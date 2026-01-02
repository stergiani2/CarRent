package gui;

import api.model.Client;
import api.services.AllClients;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Κλάση αυτή δημιουργεί πλαίσιο αλληλεπίδρασης με τον χρήστη για τη διαχείριση πελατών, όπως προσθήκη πελάτη, επεξεργασία, ανανέωση,
 * αναζήτηση και καθαρισμό.
 *
 * @author Αλεξάνδρα Σακελλαριάδη
 * @version 0.2(2025.12.10)
 **/
public class ClientFrame extends JFrame {
    //Δομή AllCars για την αποθήκευση όλων των πελατών
    private AllClients allClients;
    //Πίνακας που δημιουργεί γραμμές και στήλες για να εμφανίζονται τα αυτοκίνητα
    private JTable clientsTable;
    // Περιέχει όλα τα δεδομένα (τιμές των κελιών) και τη δομή (στήλες) του πίνακα
    private DefaultTableModel tableModel;
    //Πεδίο για την αναζήτηση πελάτη
    private JTextField searchField;
    //Μενού επιλογών που ο χρήστης διαλέγει μια τιμή από τη λίστα (ΑΦΜ, ονοματεπώνυμο ή τηλέφωνο)
    private JComboBox<String> searchComboBox;
    //Αρχείο για αποθήκευση των πελατών
    private String File="Clients.txt";

    /**
     * Κατασκευαστής πλαισίου με τα απαραίτητα στοιχεία
     * @param allClients Δομή AllClients για την αποθήκευση όλων των πελατών
     */
    public ClientFrame(AllClients allClients) {
        this.allClients = allClients;
        initComponents();
        setTitle("Διαχείριση πελατών");
        setSize(1000, 600);
        loadClientsFromFile();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Δημιουργία βασικών στοιχείων όπως το πάνελ του μενού, της αναζήτησης, τον πίνακα πελατών και των κουμπιών διαχείρισης
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Πάνελ μενού
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Αρχείο");

        JMenuItem exitItem = new JMenuItem("Έξοδος");
        exitItem.addActionListener(e -> dispose());
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Πάνελ αναζήτησης
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

        // Κουμπιά διαχείρισης
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Προσθήκη Πελάτη");
        addButton.addActionListener(e -> addClient());
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Επεξεργασία");
        editButton.addActionListener(e -> editClient());
        buttonPanel.add(editButton);

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
        for (Client client:allClients.getAllClients()) {
            Object[] rowData={
                    client.getAFM(),
                    client.getFirstName(),
                    client.getLastName(),
                    client.getPhone(),
                    client.getEmail()};
            tableModel.addRow(rowData);
        }
        tableModel.fireTableDataChanged();
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
                saveClientsToFile();
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
            if (!clientAFM.equals(updatedClient.getAFM())) {
                System.out.println("To ΑΦΜ δεν αλλάζει!");
                return;
            }
            allClients.getAllClients().add(updatedClient);
            refreshTable();
            SwingUtilities.invokeLater(() -> {
                clientsTable.revalidate();
                clientsTable.repaint();
            });
        }
        saveClientsToFile();
    }

    /**
     * Φόρτωση πελατών από αρχείο
     */
    private void loadClientsFromFile() {
        try (BufferedReader reader= new BufferedReader(new FileReader(File))) {
            String line;
            while ((line = reader.readLine())!=null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    Client client = new Client(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim()
                    );
                    allClients.addClient(client);
                }
            }
        refreshTable();
        JOptionPane.showMessageDialog(this, "Φορτώθηκαν " + allClients.getAllClients().size() + " πελάτες από το αρχείο.",
                "Πληροφορίες", JOptionPane.INFORMATION_MESSAGE);
    }catch(FileNotFoundException e) {
        // Το αρχείο δεν υπάρχει ακόμα, θα δημιουργηθεί όταν προστεθεί ο πρώτος πελάτης
        System.out.println("Το αρχείο " + File + " δεν βρέθηκε. Θα δημιουργηθεί αυτόματα.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάγνωση του αρχείου: " + e.getMessage(),
                "Σφάλμα", JOptionPane.ERROR_MESSAGE);
    }
}
    /**
     * Αποθήκευση όλων των πελατών σε αρχείο
     */
    private void saveClientsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(File))) {
            for (Client client : allClients.getAllClients()) {
                writer.write(client.getAFM() + "," +
                        client.getFirstName() + "," +
                        client.getLastName() + "," +
                        client.getPhone() + "," +
                        client.getEmail());
                writer.newLine();
            }
            writer.flush();
            System.out.println("Αποθηκεύτηκαν " + allClients.getAllClients().size() + " πελάτες στο αρχείο.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αποθήκευση στο αρχείο: " + e.getMessage(),
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }


}