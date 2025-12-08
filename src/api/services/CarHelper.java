package api.services;
import java.io.*;
import java.util.*;
import api.model.Car;

public class CarHelper {
    public HashMap<String, Car> readFroFileCars(String fileName)throws Exception {
        HashMap<String, Car> cars = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {
                String[] tokens = line.split(",");
                if (line.isEmpty()) {
                    continue;
                }
                String id = tokens[0];
                if (cars.containsKey(id)) {
                    throw new Exception("Duplicate car id: " + id);
                }
                String plate = tokens[1];
                for (Car car : cars.values()) {
                    if (car.getPlate().equals(plate)) {
                        throw new Exception("Duplicate car plate: " + plate);
                    }
                }
                String carBrand = tokens[2];
                String type = tokens[3];
                String model = tokens[4];
                int year = Integer.parseInt(tokens[5].trim());
                String color = tokens[6];
                String situation = tokens[7];
                Car c = new Car(id, plate, carBrand, type, model, year, color, situation);
                cars.put(id, c);
            }
        }
        return cars;
    }
}
