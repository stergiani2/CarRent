package api.services;

import api.model.Rental;

import api.model.Car;
import api.model.Client;
import api.model.Employee;

import java.time.LocalDate;
import java.util.HashSet;

/**
 * Η κλάση RentalService υλοποιεί τη βασική λογική
 * δημιουργίας, ολοκλήρωσης και αναζήτησης ενοικιάσεων.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1 (2025.12.30)
 */
public class RentalService {

    //Σύνολο με όλες τις ενοικιάσεις
    private HashSet<Rental> rentals;
    //Βοηθητική κλάση για αποθήκευση
    private RentalHelper helper;

    /**
     * Κατασκευαστής
     */
    public RentalService() {
        helper = new RentalHelper();
        // Φόρτωση ενοικιάσεων από binary
        rentals=helper.loadRentalsFromBinary();
    }


    /**
     * Δημιουργεί μία νέα ενοικίαση.
     *
     * @param car Αυτοκίνητο
     * @param client Πελάτης
     * @param employee Υπάλληλος
     * @param startDate Ημερομηνία έναρξης
     * @param endDate Ημερομηνία λήξης
     * @return Το αντικείμενο Rental
     */
    public Rental createRental(Car car,Client client,Employee employee,
                               LocalDate startDate,LocalDate endDate){
        if(!car.getSituation().equals("Διαθέσιμο")){
            throw new IllegalStateException("Το αυτοκίνητο δεν είναι διαθέσιμο");
        }
        car.setSituation("Ενοικιασμένο");
        Rental rental=new Rental(car,client,employee,startDate,endDate);
        rentals.add(rental);

        helper.saveRentalsToBinary(rentals); // αποθηκεύουμε αμέσως
        return rental;
    }

    /**
     * Ολοκληρώνει μία ενοικίαση.
     *
     * @param rental Η ενοικίαση
     */
    public void completeRental(Rental rental){

        rental.completeRental();
        helper.saveRentalsToBinary(rentals); // αποθηκεύουμε μετά την ολοκλήρωση
    }

    /**
     * Αναζήτηση ενοικιάσεων ανά πελάτη.
     *
     * @param client Πελάτης
     * @return Σύνολο ενοικιάσεων
     */
    public HashSet<Rental> getRentalByClient(Client client) {
        HashSet<Rental> result = new HashSet<>();
        for (Rental r : rentals) {
            if (r.getClient().getAFM().equals(client.getAFM())) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Αναζήτηση ενοικιάσεων ανά αυτοκίνητο.
     *
     * @param car Αυτοκίνητο
     * @return Σύνολο ενοικιάσεων
     */
    public HashSet<Rental> getRentalsByCar(Car car){
        HashSet<Rental> result = new HashSet<>();
        for (Rental r : rentals) {
            if(r.getCar().getId().equals(car.getId())){
                result.add(r);
            }
        }
        return result;
    }
    public java.util.HashSet<Rental> getAllRentals() {
        return this.rentals;
        // Υποθέτω ότι η μεταβλητή που κρατάει τις ενοικιάσεις
        // μέσα στη RentalService λέγεται "rentals"
    }
}