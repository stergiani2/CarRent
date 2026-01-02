package api.model;
import java.util.Objects;

/**
 * Η κλάση Client αντιπροσωπεύει έναν πελάτη, ο οποίος επιθυμεί να εγγραφεί στην αντιπροσωπία για να
 * μπορεί να ενοικιάσει ένα αυτοκίνητο.
 *
 * @author Αλεξάνδρα Σακελλαριάδη
 * @version 0.1(2025.11.20)
 */
public class Client {
    //Το ΑΦΜ του πελάτη
    private String AFM;
    //Το όνομα του πελάτη
    private String firstName;
    //Το επίθετο του πελάτη
    private String lastName;
    //Το κινητό τηλέφωνο του πελάτη
    private String phone;
    //Το email του πελάτη
    private String email;

    /**
     * Κατασκευαστής για τη δημιουργία αντικειμένων της κλάσης Client
     * @param AFM ΑΦΜ
     * @param firstName To όνομα
     * @param lastName Το επίθετο
     * @param phone Το τηλέφωνο
     * @param email Η ηλεκτρονική διεύθυνση
     */
    public Client(String AFM, String firstName, String lastName, String phone, String email) {
        this.AFM = AFM;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    /**
     * @return Το ΑΦΜ
     */
    public String getAFM() {
        return AFM;
    }

    /**
     * @return Το όνομα
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return Το επίθετο
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return Το τηλέφωνο
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return Το email
     */
    public String getEmail() {
        return email;
    }

    /**
     *Θέτει το ΑΦΜ
     * @param AFM Το ΑΦΜ
     */
    public void setAFM(String AFM) {
        this.AFM = AFM;
    }

    /**
     * Θέτει το όνομα
     * @param first_name Το όνομα του πελάτη
     */
    public void setFirstName(String first_name) {
        this.firstName = firstName;
    }
    /**
     * Θέτει το επώνυμο του πελάτη
     * @param last_name Το επώνυμο του πελάτη
     */
    public void setLastName(String last_name) {
        this.lastName = lastName;
    }
    /**
     * Θέτει το τηλέφωνο του πελάτη
     * @param phone Το τηλέφωνο του πελάτη
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * Θέτει το email
     * @param email Το email του πελάτη
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Υπερφόρτωση για ισότητα 2 αντικειμένων
     */
    @Override
    public boolean equals(Object o){
        if(this==o) {
            return true;
        }
        if(!(o instanceof Client)) {
            return false;
        }
        Client c=(Client)o;
        return (c.getAFM().equals(AFM));
    }

    /**
     *
     * @return Ακέραιος βάση του οποίου ένα αντικείμενο μπαίνει σε πίνακα κατακερματισμού
     */
    @Override
    public int hashCode(){
        return Objects.hash(AFM);
    }

}
