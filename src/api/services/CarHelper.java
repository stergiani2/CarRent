package api.services;
import java.io.*;
import java.util.*;
import api.model.Car;

/**
 * Η κλάση αυτή διαχειρίζεται τα αρχεία.Πραγματοποιεί διάβασμα από αρχείο, διάβασμα που δυαδικό αρχείο και αποθήκευση.
 *
 * @author Αλεξάνδρα Σακελλαριάδη
 * @version 0.1(2025.12.05)
 */
public class CarHelper {
    private AllCars allCars;

    public CarHelper(AllCars allCars){
        this.allCars=allCars;
    }
    /**
     * Διάβασμα αρχείου για αυτοκίνητα
     * @param fileName
     * @return Δομή AllCars που αποθηκεύει όλα τα αυτοκίνητα που διαβάζονται από το αρχείο
     * @throws Exception για μη-αποδεκτές τιμές διαβάσματος
     */
    public AllCars readFromFileCars(String fileName)throws Exception {
        AllCars allCars=new AllCars();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int numberOfLine=0;
            while ((line=br.readLine())!= null) {
                numberOfLine++;
                if(numberOfLine==1){
                    continue;
                }
                String[] tokens = line.split(",");
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (tokens.length<8) {
                    throw new Exception("Στη γραμμή "+numberOfLine+" υπάρχουν λιγότερα πεδία!");
                }
                String id = tokens[0].trim();
                if (allCars.getCar(id)!=null) {
                    throw new Exception("Διπλό id αυτοκινήτου: " + id+" στη γραμμή "+numberOfLine);
                }
                String plate = tokens[1].trim();
                for (Car car : allCars.getAllCars().values()) {
                    if (car.getPlate().equals(plate)) {
                        throw new Exception("Διπλή πινακίδα: " + plate+" στη γραμμή "+numberOfLine);
                    }
                }
                String carBrand = tokens[2];
                String type = tokens[3];
                String model = tokens[4];
                int year;
                try {
                    year = Integer.parseInt(tokens[5].trim());
                    if(year<1900){
                        throw new Exception("Μη έγκυρη χρονολογία στη γραμμή: "+numberOfLine);
                    }

                }catch (NumberFormatException e){
                    throw new Exception("Μη έγκυρη χρονολογία στη γραμμή: "+numberOfLine);
                }
                String color = tokens[6];
                String situation = tokens[7];
                Car c = new Car(id, plate, carBrand, type, model, year, color, situation);
                allCars.addCar(c);
            }
        }
        return allCars;
    }
    /**
     * Αποθήκευση όλων των αυτοκινήτων σε αρχείο
     */
    public void saveCarToFile() {
        try (BufferedWriter writer=new BufferedWriter(new FileWriter("vehicles_with_plates.csv"))) {
            for (Car car:allCars.getAllCars().values()){
                writer.write(car.getId()+","+
                        car.getPlate()+","+
                        car.getCarBrand()+","+
                        car.getType()+","+
                        car.getModel()+","+
                        car.getYear()+","+
                        car.getColor()+","+
                        car.getSituation());
                writer.newLine();
            }
            System.out.println("Αποθηκεύτηκαν "+allCars.getAllCars().size()+" αυτοκίνητα στο αρχείο.");
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση στο αρχείο: " + e.getMessage());
        }
    }
}
