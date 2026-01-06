import api.model.Employee;
import api.services.*;

import gui.EmployeeLoginFrame;
import gui.ManagementGUI;

import javax.swing.*;

import java.util.HashMap;

/**
 * Κύρια κλάση εκκίνησης του προγράμματος.
 * Ροή:
 * 1.Φόρτωση υπαλλήλων (binary ή CSV)
 * 2.Φόρτωση αυτοκινήτων και πελατών
 * 3.Εμφάνιση login υπαλλήλου
 * 4.Μετά το Login ανοίγει το ManagementGUI
 *
 * Αυτή η κλάση είναι η "είσοδος" της εφαρμογής.
 *
 * @author Αλεξάνδρα Σακελλαριάδη, Στεργιανή Καραγιώργου
 * @version 0.1(2026.01.04)
 */
public class Main extends JFrame {

    public static void main(String[] args)  {
        // Αρχικοποίηση δομών
        UserHelper userHelper = new UserHelper();
        AllCars allCars = new AllCars();
        AllClients allClients = new AllClients();
        RentalService rentalService = new RentalService();

        //  Φόρτωση Υπαλλήλων (Binary ή CSV)
        HashMap<String, Employee> employees = userHelper.loadUsersFromBinary();
        //Αν δεν υπάρχει στο binary, φόρτωση από CSV
        if (employees == null || employees.isEmpty()) {
            try {
                employees = userHelper.readUsersFromCSV();
                userHelper.saveUsersToBinary(employees); // Αποθήκευση για την επόμενη φορά
            } catch (Exception e) {
                employees = new HashMap<>();
            }
        }
        AuthService authService = new AuthService(employees);

        //  Φόρτωση Αυτοκινήτων
        CarHelper carHelper = new CarHelper(allCars);
        try {
            carHelper.readFromFileCars("vehicles_with_plates.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Φόρτωση Πελατών
        ClientHelper clientHelper = new ClientHelper(allClients);
        try {
            clientHelper.loadClientsFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Εκκίνηση GUI
        SwingUtilities.invokeLater(() -> {
            new EmployeeLoginFrame(authService, () -> {
                new ManagementGUI(allCars, allClients, rentalService, authService);
            });
        });

    }
}

