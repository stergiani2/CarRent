package api.services;
import api.model.Car;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Κλάση που δημιουργεί έναν πίνακα κατακερματισμού (με κλειδί το id του αυτοκινήτου και τιμή το αυτοκίνητο)
 * από αυτοκίνητα που μπορεί να ενοικιάζει η επιχείρηση στους πελάτες.
 *
 * @author Αλεξάνδρα Σακελλαριάδη
 * @version 0.1(2025.11.30)
 */

public class AllCars {
    //Πίνακας κατακερματισμού για την αποθήκευση όλων των αυτοκινήτων στο σύστημα
    HashMap<String,Car> allCars;
     /**
      * Κατασκευαστής της κλάσης
      */
    public AllCars() {
        allCars = new HashMap<>();
    }

    /**
     * Προσθήκη αυτοκινήτου στο σύστημα και έλεγχος αν υπάρχει ήδη ή το δοθέν αυτοκίνητο έχει τιμή null.
     * @param car Αυτοκίνητο
     * @return true/false
     */
    public boolean addCar(Car car) {
        if(allCars.containsKey(car.getId())||car==null) {
            return false;
        }
        allCars.put(car.getId(), car);
        return true;
    }

    /**
     * Αναζήτηση αυτοκινήτου με βάση το id.
     * @param id ID
     * @return Το συγκεκριμένο αυτοκίνητο που θέλουμε ή null
     */
    public Car getCar(String id) {
        for(Car car : allCars.values()) {
            if(car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    /**
     * Αναζήτηση αυτοκινήτου με βάση τη μάρκα, το μοντέλο, το χρώμα, την κατάσταση μεμονωμένα ή σε συνδυασμό.
     * @param carBrand Μάρκα αυτοκινήτου
     * @param carModel Μοντέλο αυτοκινήτου
     * @param carColor Χρώμα αυτοκινήτου
     * @param carSituation Κατάσταση αυτοκινήτου
     * @return Σύνολο από αυτοκίνητα που πληρούν αυτά που δόθηκαν από τις παραμέτρους ταυτόχρονα.
     */
    public HashSet<Car> getCar(String carBrand, String carModel, String carColor, String carSituation) {
        HashSet<Car> carSet = new HashSet<>();

        for(Car c : allCars.values()) {
            boolean flag = true;
            if(carBrand!=null){
                if(!(c.getCarBrand().equals(carBrand))){
                    flag = false;
                }
            }
            if(carModel!=null){
                if(!c.getModel().equals(carModel)) {
                    flag = false;
                }
            }
            if(carColor!=null){
                if (!c.getColor().equals(carColor)) {
                    flag = false;
                }
            }
            if(carSituation!=null){
                if(!c.getSituation().equals(carSituation)) {
                    flag = false;
                }

            }
            if(flag){
                carSet.add(c);
            }
        }
        return carSet;
    }
    public HashMap<String, Car> getAllCars(){
        return allCars;
    }


}
