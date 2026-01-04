package api.services;

import api.model.Rental;
import java.io.*;
import java.util.HashSet;

/**
 * Η κλάση RentalHelper είναι υπεύθυνη για την αποθήκευση
 * και φόρτωση των ενοικιάσεων από δυαδικό αρχείο.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(202.12.30)
 */
public class RentalHelper {

    //Όνομα δυαδικού αρχείου
    private String binaryFile = "rentals.dat";

    /**
     * Αποθηκεύει τις ενοικιάσεις σε δυαδικό αρχείο.
     *
     * @param rentals Σύνολο ενοικιάσεων
     * @throws IOException Σε περίπτωση αποτυχίας εγγραφής
     */
    public void saveRentalsToBinary(HashSet<Rental> rentals) throws IOException {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(binaryFile))) {
            oos.writeObject(rentals);
        }
    }

    /**
     * Φορτώνει τις ενοικιάσεις από δυαδικό αρχείο.
     *
     * @return Σύνολο ενοικιάσεων (κενό αν δεν υπάρχει αρχείο)
     */
    @SuppressWarnings("unchecked")
    public HashSet<Rental> loadRentalsFromBinary() {
        File file = new File(binaryFile);
        if (!file.exists()) {
            return new HashSet<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(binaryFile))) {
            return (HashSet<Rental>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Πρόβλημα κατά τη φόρτωση των ενοικιάσεωνν");
            return new HashSet<>();
        }
    }
}