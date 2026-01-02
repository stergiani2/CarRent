package api.model;

import java.util.Objects;

/**
 * Η κλάση αυτή αντιπροσωπεύει την οντότητα του αυτοκινήτου.
 * Σε αυτή καταγράφονται οι ιδιότητες που έχει ένα αυτοκίνητο, όπως το id του στη βάση δεδομένων, η πινακίδα του,
 * η μάρκα του, το μοντέλο, το έτος κατασκευής, το χρώμα και η κατάσταση(ενοικιασμένο ή μη).
 *
 *@author Αλεξάνδρα Σακελλαριάδη
 *@version 0.1(2025.11.15)
*/
public class Car {
    //Κωδικός αυτοκινήτου
    private String id;
    //Πινακίδα του αυτοκινήτου
    private String plate;
    //Μάρκα του αυτοκινήτου
    private String carBrand;
    //Τύπος του αυτοκινήτου
    private String type;
    //Συγκεκριμένο μοντέλο του αυτοκινήτου
    private String model;
    //Έτος κατασκευής του αυτοκινήτου
    private int year;
    //Χρώμα του αυτοκινήτου
    private String color;
    //Διαθέσιμο ή Ενοικιασμένο
    private String situation;

    /**
     * Δημιουργία ενός αντικειμένου της κλάσης αυτοκινήτου.
     * @param id Ο κωδικός
     * @param plate Η πινακίδα
     * @param carBrand Η μάρκα
     * @param type Ο τύπος
     * @param model Το μοντέλο
     * @param year Έτος κατασκευής
     * @param color Χρώμα
     * @param situation Ενοικιασμένο ή μη
     */
    public Car(String id, String plate, String carBrand,String type, String model, int year, String color, String situation) {
        this.id = id;
        this.plate = plate;
        this.carBrand = carBrand;
        this.type = type;
        this.model = model;
        this.year = year;
        this.color = color;
        this.situation = situation;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return Την πινακίδα
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @return Τη μάρκα
     */
    public String getCarBrand() {
        return carBrand;
    }

    /**
     * @return Τον τύπο
     */
    public String getType() {
        return type;
    }

    /**
     * @return Το μοντέλο
     */
    public String getModel() {
        return model;
    }

    /**
     * @return Το έτος κατασκευής
     */
    public int getYear() {
        return year;
    }

    /**
     * @return Το χρώμα
     */
    public String getColor() {
        return color;
    }

    /**
     * @return Διαθέσιμο ή ενοικιασμένο
     */
    public String getSituation() {
        return situation;
    }

    /**
     * Θέτει την πινακίδα
     * @param plate Η πινακίδα
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }
    /**
     * Θέτει τη μάρκα
     * @param carBrand Η μάρκα
     */
    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }
    /**
     * Θέτει το μοντέλο
     * @param model Το μοντέλο
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * Θέτει το έτος κατασκευής
     * @param year Το έτος κατασκευής
     */
    public void setYear(int year) {
        this.year = year;
    }
    /**
     * Θέτει το χρώμα
     * @param color Το χρώμα
     */
    public void setColor(String color) {
        this.color = color;
    }
    /**
     * Θέτει την κατάσταση(Ενοικιασμένο ή μη)
     * @param situation Η κατάσταση
     */
    public void setSituation(String situation) {
        this.situation = situation;
    }
    /**
     * Θέτει τον τύπο
     * @param type Ο τύπος
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Υπερφόρτωση για ισότητα 2 αντικειμένων τύπου Car
     * @param o Αντικείμενο
     * @return true/false
     */
    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof Car)){
            return false;
        }
        Car car = (Car)o;
        return (((Car) o).getId().equals(this.getId()));
    }
    /**
     * @return Ακέραιος βάση του οποίου ένα αντικείμενο μπαίνει σε πίνακα κατακερματισμού
     */
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    /**
     * @return String που περιέχει όλα τα χαρακτηριστικά του αυτοκινήτου
     */
    @Override
    public String toString(){
        return ("Id: "+getId()+",Plate: "+getPlate()+",CarBrand: "+getCarBrand()+",Type: "+getType()+",Model: "+getModel()+",Year: "+getYear()+",Color: "+getColor()+",Situation: "+getSituation());

    }
}
