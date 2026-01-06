package api.services;

import api.model.Client;

import java.io.*;

/**
 * Η κλάση ClientHelper διαχειρίζεται την αποθήκευση και φόρτωση των πελατών
 * από απλά αρχεία κειμένου. Παρέχει δυνατότητα:
 *  - φόρτωσης πελατών από αρχείο
 *  - αποθήκευσης πελατών σε αρχείο
 *  - ανάγνωσης και τροποποίησης του ονόματος του αρχείου
 *
 * Κάθε γραμμή στο αρχείο αναμένεται να έχει τη μορφή:
 * AFM,FirstName,LastName,Phone,Email
 */
public class ClientHelper {
    //Αρχείο για αποθήκευση των πελατών
    private String File = "Clients.txt";
    //Δομή AllClients για την αποθήκευση όλων των πελατών
    private AllClients allClients;

    /**
     * Κατασκευαστής για τη διαχείριση αρχείων
     * @param allClients
     */
    public ClientHelper(AllClients allClients){
        this.allClients=allClients;
    }
    /**
     * Φόρτωση πελατών από αρχείο
     */
    public void loadClientsFromFile() throws IOException{
        try (BufferedReader reader=new BufferedReader(new FileReader(File))) {
            String line;
            while ((line = reader.readLine()) != null) {
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
            System.out.println("Φορτώθηκαν "+allClients.getAllClients().size()+" πελάτες από το αρχείο.");
        } catch (FileNotFoundException e) {
            System.out.println("Το αρχείο "+File +" δεν βρέθηκε. Θα δημιουργηθεί αυτόματα.");
            new File(File).createNewFile();
        }
    }
    /**
     * Αποθήκευση όλων των πελατών σε αρχείο
     */
    public void saveClientsToFile() {
        try (BufferedWriter writer=new BufferedWriter(new FileWriter(File))) {
            for (Client client:allClients.getAllClients()) {
                writer.write(client.getAFM() + "," +
                        client.getFirstName() + "," +
                        client.getLastName() + "," +
                        client.getPhone() + "," +
                        client.getEmail());
                writer.newLine();
            }
            System.out.println("Αποθηκεύτηκαν "+allClients.getAllClients().size()+" πελάτες στο αρχείο.");
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση στο αρχείο: " + e.getMessage());
        }
    }

    /**
     *
     * @return File
     */
    public String getFile(){
        return File;
    }

    /**
     * Θέτει το όνομα του αρχείου
     * @param file
     */
    public void setFile(String file) {
        File = file;
    }
}

