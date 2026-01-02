package api.services;
import api.model.Client;
import java.util.HashSet;

/**
 * Η κλάση αυτή δημιουργεί μια δομή HashSet, στην οποία αποθηκεύονται όλοι οι πελάτες
 *
 * @author Αλεξάνδρα Σακελλαριάδη
 * @version 0.1(2025.11.25)
 */

public class AllClients {
    //Η δομή που αποθηκεύει τους πελάτες
    HashSet<Client> clients;

    /**
     * Κατασκευαστής
     */
    public AllClients() {
        clients = new HashSet<>();
    }

    /**
     * Προσθήκη πελάτη στο σύστημα.Γίνεται και έλεγχος για το αν υπάρχει ήδη αυτός ο πελάτης με βάση το ΑΦΜ.
     * @param client Πελάτης
     * @return true/false
     */
    public boolean addClient(Client client) {
        return clients.add(client);
    }

    /**
     * Αναζήτηση πελάτη με βάση το ΑΦΜ.
     * @param AFM ΑΦΜ
     * @return Πελάτη(αν υπάρχει)/null
     */
    public Client searchClientByAFM(String AFM) {
        for (Client c : clients) {
            if(c.getAFM().equals(AFM)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Αναζήτηση πελάτη με βάση το ονοματεπώνυμό του.
     * @param firstName Όνομα
     * @param lastName Επώνυμο
     * @return Ένα σύνολο που έχει όλους τους πελάτες με το ονοματεπώνυμο που ψάχνουμε
     */
    public HashSet<Client> searchByName(String firstName, String lastName) {
        HashSet<Client> clientsByName = new HashSet<>();
        for (Client c : clients) {
            if(firstName.equals(c.getFirstName()) && lastName.equals(c.getLastName())) {
                 addClient(c);
            }
        }
        return clientsByName;
    }

    /**
     * Αναζήτηση με βάση το τηλέφωνο του πελάτη.
     *
     * @param phone Το τηλέφωνο
     * @return Τον συγκεκριμένο πελάτη που ψάχνουμε
     */
    public Client searchClientByPhone(String phone) {
        return null;
    }
    public HashSet<Client> getAllClients(){
        return clients;
    }

}
