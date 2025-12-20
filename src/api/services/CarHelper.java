package api.services;
import java.io.*;
import java.util.*;
import api.model.Car;

public class CarHelper {
    private String binaryFile="cars.dat";
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

    public void writeCarsToBinaryFile(String filename, AllCars allCars) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(allCars.getAllCars());
        }
    }
    public HashMap<String,Car> readCarsFromBinaryFile(String filename) throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object o = ois.readObject();
            if(o instanceof HashMap){
                HashMap<String,Car> carMap=(HashMap<String,Car>) o;
                for (Object item: carMap.values()) {
                    if (!(item instanceof Car)) {
                        throw new ClassNotFoundException("File does not contain Car objects!");
                    }
                }
                return carMap;
            }else{
                throw new ClassNotFoundException("File does not contain a HashMap!");
            }
        }catch (EOFException e){
            throw new IOException("Empty or corrupted file: "+filename);
        }
    }



    public boolean removeCars(AllCars allCars, Set<String> codesToRemove) {
        boolean removed = false;
        HashSet<String> lowercaseCodes = new HashSet<>();
        for(String code : codesToRemove) {
            lowercaseCodes.add(code.toLowerCase());
        }
        Iterator<Car> iterator = allCars.getAllCars().values().iterator();
        while(iterator.hasNext()) {
            Car car = iterator.next();
            if(lowercaseCodes.contains(car.getId().toLowerCase())) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    public void saveToBinary(AllCars allCars) throws IOException{
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(binaryFile))) {
                oos.writeObject(allCars.getAllCars());
        }
    }



}
