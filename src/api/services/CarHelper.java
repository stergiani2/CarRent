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
    //Δυαδικό αρχείο
    private String binaryFile="cars.dat";

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
                    throw new Exception("The line "+numberOfLine+" has less fields");
                }
                String id = tokens[0].trim();
                if (allCars.getCar(id)!=null) {
                    throw new Exception("Duplicate car id: " + id+" in line "+numberOfLine);
                }
                String plate = tokens[1].trim();
                for (Car car : allCars.getAllCars().values()) {
                    if (car.getPlate().equals(plate)) {
                        throw new Exception("Duplicate car plate: " + plate+" in line "+numberOfLine);
                    }
                }
                String carBrand = tokens[2];
                String type = tokens[3];
                String model = tokens[4];
                int year;
                try {
                    year = Integer.parseInt(tokens[5].trim());
                    if(year<1900){
                        throw new Exception("Invalid year in line: "+numberOfLine);
                    }

                }catch (NumberFormatException e){
                    throw new Exception("Invalid year in line: "+numberOfLine);
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
     * Αποθήκευση της δομής AllCars
     * @param allCars
     * @throws IOException
     */
    public void saveToBinary(AllCars allCars) throws IOException{
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(binaryFile))) {
                oos.writeObject(allCars.getAllCars());
        }
    }

    /**
     * Φόρτωση δυαδικού αρχείου
     * @return δομή AllCars που αποθηκεύει όλα τα αυτοκίνητα από το δυαδικό αρχείο
     */
    public AllCars loadFromBinaryFile() {
        AllCars allCars = new AllCars();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binaryFile))) {
            Object obj=ois.readObject();
            if(obj instanceof HashMap){
                @SuppressWarnings("unchecked")
                HashMap<String, Car> carMap = (HashMap<String, Car>) obj;

                for(Car car:carMap.values()) {
                    allCars.addCar(car);
                }
            }
            System.out.println("Data loaded successfully from cars.dat");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with empty car list.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        }
        return allCars;

    }

}
