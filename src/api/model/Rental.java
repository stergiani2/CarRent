package api.model;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Η κλάση Rental αντιπροσωπεύει μία ενοικίαση αυτοκινήτου.
 * Κάθε ενοικίαση συσχετίζει ένα αυτοκίνητο, έναν πελάτη
 * και τον υπάλληλο που καταχώρησε την ενοικίαση.
 *
 * Ο κωδικός ενοικίασης δημιουργείται αυτόματα και είναι μοναδικός.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1 (2025.12.29)
 */
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;
    //Μετρητής για αυτόματη παραγωγή μοναδικών κωδικών ενοικίασης
    private static int counter=1;
    //Κωδικός ενοικίασης
    private int rentalId;
    //Το ενοικιαζόμενο αυτοκίνητο
    private Car car;
    //Ο πελάτης που ενοικιάζει
    private Client client;
    //Ο υπάλληλος που καταχώρησε την ενοικίαση
    private Employee employee;
    //Ημερομηνία έναρξης
    private LocalDate startDate;
    //Ημερομηνία λήξης
    private LocalDate endDate;
    //Κατάσταση ενοικίασης (ενεργή/ολοκληρωμένη)
    private boolean active;

    /**
     * Κατασκευαστής ενοικίασης.
     *
     * @param car Το αυτοκίνητο
     * @param client Ο πελάτης
     * @param employee Ο υπάλληλος
     * @param startDate Ημερομηνία έναρξης
     * @param endDate Ημερομηνία λήξης
     */
    public Rental(Car car,Client client,Employee employee,LocalDate startDate,LocalDate endDate){
        this.rentalId=counter++;
        this.car = car;
        this.client = client;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active=true;
    }

    /**@return Κωδικός ενοικίασης*/
    public int getRentalId() {return rentalId;}
    /**@return Αυτοκίνητο */
    public Car getCar(){return car;}
    /**@return Πελάτης */
    public Client getClient(){return client;}
    /**@return Υπάλληλος */
    public Employee getEmployee(){return employee;}
    /**@return Ημερομηνία έναρξης */
    public LocalDate getStartDate(){return startDate;}
    /**@return Ημερομηνία λήξης */
    public LocalDate getEndDate(){return endDate;}
    /**@return true αν η ενοικίαση είναι ενεργή */
    public boolean isActive(){return active;}

    /**
     * Ολοκλήρωση ενοικίασης και ενημέρωση διαθεσιμότητας αυτοκινήτου.
     */
    public void completeRental(){
        this.active=false;
        car.setSituation("Διαθέσιμο");
    }
}
