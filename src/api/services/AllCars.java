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
        if(allCars.containsKey(car.getId())) {
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
    public Car searchCarById(String id) {
        for(Car car : allCars.values()) {
            if(car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    /**
     * Αναζήτηση αυτοκινήτου με βάση την πινακίδα, τη μάρκα, το μοντέλο, το χρώμα, την κατάσταση μεμονωμένα ή σε συνδυασμό.
     * @param text οι παράμετροι του αυτοκινήτου

     * @return Σύνολο από αυτοκίνητα που πληρούν αυτά που δόθηκαν από τις παραμέτρους ταυτόχρονα.
     */
    public boolean getCar(Car car,String text) {
        String[] param=text.trim().toLowerCase().split(" ");
        int i=0,p=0;

        for(;i<param.length;i++){
            if(car.getPlate().toLowerCase().contains(param[i].trim())){
                p++;
            }else if(car.getCarBrand().toLowerCase().contains(param[i].trim())){
                p++;
            } else if (car.getModel().toLowerCase().contains(param[i].trim())) {
                p++;
            }else if (car.getColor().toLowerCase().contains(param[i].trim())){
                p++;
            } else if (car.getSituation().toLowerCase().contains(param[i].trim())) {
                p++;
            }
        }

        if(p==param.length){
            return true;
        }
        return false;
    }


    /**
     * @return Hashmap allCars
     */
    public HashMap<String, Car> getAllCars(){
        return allCars;
    }


}
