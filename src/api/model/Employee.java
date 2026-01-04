package api.model;

/**
 * Η κλάση Employee αντιπροσωπεύει έναν υπάλληλο της εταιρίας ενοικίασης αυτοκινήτων.
 * Οι υπάλληλοι αποτελούν τους χρήστες του συστήματος και έχουν πρόσβαση στην εφαρμογή
 * μέσω μηχανισμού σύνδεσης (login).
 *
 * Κάθε υπάλληλος διαθέτει μοναδικό username και email.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1 (2025.12.28)
 */
public class Employee {

    //Όνομα υπαλλήλου
    private String firstName;
    //Επώνυμο υπαλλήλου
    private String lastName;
    //Username(μοναδικό)
    private String username;
    //Email υπαλλήλου(μοναδικό)
    private String email;
    //Κωδικός πρόσβασης
    private String password;

    /**
     * Κατασκευαστής για τη δημιουργία υπαλλήλου.
     *
     * @param firstName Όνομα
     * @param lastName Επώνυμο
     * @param username Username σύνδεσης
     * @param email Email
     * @param password Κωδικός πρόσβασης
     */
    public Employee(String firstName,String lastName,String username,String email,String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    /** @return Όνομα */
    public String getFirstName() {
        return firstName;
    }
    /** @return Επώνυμο */
    public String getLastName() {
        return lastName;
    }
    /**@return Username */
    public String getUsername() {return username;}
    /**@return Email */
    public String getEmail() {
        return email;
    }

    /**
     * Έλεγχος κωδικού πρόσβασης.
     *
     * @param password Κωδικός προς έλεγχο
     * @return true αν ο κωδικός είναι σωστός
     */
    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    /** Θέτει το όνομα*/
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /** Θέτει το επώνυμο */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /** Θέτει το email*/
    public void setEmail(String email) {
        this.email = email;
    }
    /** Θέτει νέο κωδικό */
    public void setPassword(String password) {
        this.password = password;
    }
}
