package api.services;

import api.model.Employee;
import java.util.HashMap;
import java.io.IOException;

/**
 * Η κλάση AuthService διαχειρίζεται την αυθεντικοποποίηση των υπαλλήλων.
 * Ελέγχει τα στοιχεία σύνδεσης (username & password) και
 * κρατά πληροφορία για τον χρήστη που είναι συνδεδεμένος στο σύστημα.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1 (2025.12.30)
 */
public class AuthService {
    //Πίνακας κατακερματισμού με όλους τους υπαλλήλους (κλειδί: username)
    private HashMap<String, Employee> allEmployees;
    //Ο υπάλληλος που είναι συνδεδεμένος στο σύστημα
    private Employee currentUser; // Ο χρήστης που είναι συνδεδεμένος [cite: 90]
    private UserHelper helper=new UserHelper();
    /**
     * Κατασκευαστής της κλάσης.
     *
     * @param employees Όλοι οι υπάλληλοι του συστήματος
     */
    public AuthService(HashMap<String, Employee> employees) {

        this.allEmployees = employees;
    }

    /**
     * Ελέγχει τα διαπιστευτήρια του χρήστη.
     *
     * @param username Όνομα χρήστη
     * @param password Κωδικός πρόσβασης
     * @return true αν η σύνδεση είναι επιτυχής, αλλιώς false
     */
    public boolean login(String username, String password) {
        if (allEmployees.containsKey(username)) {
            Employee emp = allEmployees.get(username);
            if(emp!=null){
                if (emp.checkPassword(password)) {
                    currentUser = emp;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Αποσυνδέει τον τρέχοντα χρήστη.
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * @return Ο συνδεδεμένος υπάλληλος ή null αν δεν υπάρχει.
     */
    public Employee getCurrentUser() {
        return currentUser;
    }

    /**
     * Προσθέτει νέο υπάλληλο στο σύστημα
     *
     * @param employee Ο νέος υπάλληλος
     */
    public void addEmployee(Employee employee) {
        allEmployees.put(employee.getUsername(), employee);
        try {
            helper.saveUsersToBinary(allEmployees); // αποθήκευση αμέσως
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Διαγράφει υπάλληλο με βάση το username
     *
     * @param username Το username του υπαλλήλου
     */
    public void removeEmployee(String username) {
        allEmployees.remove(username);
        try {
            helper.saveUsersToBinary(allEmployees); // αποθήκευση αμέσως
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @return Όλοι οι υπάλληλοι του συστήματος
     */
    public HashMap<String, Employee> getAllEmployees() {
        return allEmployees;
    }

}

