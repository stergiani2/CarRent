package api.services;

import api.model.Employee;
import java.io.*;
import java.util.HashMap;

/**
 * Η κλάση UserHelper διαχειρίζεται την ανάγνωση
 * και αποθήκευση των υπαλλήλων από ααρχεία CSV και binary.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2025.12.30)
 */
public class UserHelper {
    //Αρχείο CSV χρηστών
    private String csvFile="users.csv";
    //Δυαδικό αρχείο χρηστών
    private String binaryFile="users.data";

    /**Διαβάζει τους υπαλλήλους από το CSV αρχείο.
     *
     * @return HashMap με κλειδί το username
     * @version Exception Σε περίπτωση σφάλματος ανάγνωσης
     */
    public HashMap<String,Employee> readUsersFromCSV() throws Exception{
        HashMap<String, Employee> users =new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine=true;
            while ((line = br.readLine()) != null) {
                if(isFirstLine){
                    isFirstLine=false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length>=5){
                    //name,surname,username,email,password
                    String name=data[0].trim();
                    String surname=data[1].trim();
                    String username=data[2].trim();
                    String email=data[3].trim();
                    String password=data[4].trim();
                    Employee emp = new Employee(name, surname, username, email, password);
                    users.put(username,emp);
                }
            }
        }
        return users;
    }
    /**
     * Αποθηκεύει τους υπαλλήλους σε δυαδικό αρχείο.
     *
     * @param users Οι υπάλληλοι
     * @throws IOException Σε περίπτωση αποτυχίας εγγραφής
     */
    public void saveUsersToBinary(HashMap<String, Employee> users) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binaryFile))) {
            oos.writeObject(users);
        }
    }
    /**
     * Φορτώνει τους χρήστες από το δυαδικό αρχείο.
     *
     * @return HashMap υπαλλήλων ή null αν δεν υπάρχει αρχείο
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, Employee> loadUsersFromBinary() {
        File file = new File(binaryFile);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binaryFile))) {
            return (HashMap<String, Employee>) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}