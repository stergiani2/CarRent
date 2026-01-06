import api.model.Employee;
import api.services.*;

import gui.EmployeeLoginFrame;
import gui.ManagementGUI;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;

/**
 * Κύρια κλάση εκκίνησης του προγράμματος.
 * Δημιουργεί τις δομές δεδομένων (πελάτες, αυτοκίνητα, υπαλλήλους)
 * και ανοίγει πρώτα το παράθυρο login υπαλλήλου.
 * Μετά τη σωστή είσοδο, ανοίγει το παράθυρο διαχείρισης (ManagementGUI).
 *
 * Αυτή η κλάση είναι η "είσοδος" της εφαρμογής.
 *
 * @author Αλεξάνδρα Σακελλαριάδη, Στεργιανή Καραγιώργου
 * @version 0.1(2026.01.05)
 */
public class Main extends JFrame {

    public static void main(String[] args)  {
        // 1. Αρχικοποίηση Helpers και Services
        UserHelper userHelper = new UserHelper();
        AllCars allCars = new AllCars();
        AllClients allClients = new AllClients();
        RentalService rentalService = new RentalService();

        // 2. Φόρτωση Υπαλλήλων (Binary ή CSV)
        HashMap<String, Employee> employees = userHelper.loadUsersFromBinary();
        if (employees == null || employees.isEmpty()) {
            try {
                employees = userHelper.readUsersFromCSV();
                userHelper.saveUsersToBinary(employees); // Αποθήκευση για την επόμενη φορά
            } catch (Exception e) {
                employees = new HashMap<>();
            }
        }
        AuthService authService = new AuthService(employees);

        // 3. Φόρτωση Αυτοκινήτων και Πελατών
        // Εδώ καλό είναι να προσθέσετε παρόμοιο έλεγχο binary και για τα αυτοκίνητα
        CarHelper carHelper = new CarHelper(allCars);
        try {
            carHelper.readFromFileCars("vehicles_with_plates.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClientHelper clientHelper = new ClientHelper(allClients);
        try {
            clientHelper.loadClientsFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. Εκκίνηση GUI
        SwingUtilities.invokeLater(() -> {
            new EmployeeLoginFrame(authService, () -> {
                new ManagementGUI(allCars, allClients, rentalService, authService);
            });
        });

    }
}

