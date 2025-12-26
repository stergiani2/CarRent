import api.model.Car;
import api.services.AllCars;
import api.services.AllClients;
import api.services.CarHelper;
import gui.CarsFrame;

import javax.swing.*;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
       CarHelper carHelper=new CarHelper();
        AllCars allCars;
        AllClients allClients = new AllClients();
        try{
            allCars=carHelper.readFromFileCars("vehicles_with_plates.csv");
            for(String id:allCars.getAllCars().keySet()){
                System.out.println(allCars.getAllCars().get(id));
            }

        } catch (Exception e) {
            System.out.println("Error in reading from file: "+e.getMessage());
            return;
        }
        CarsFrame carsFrame=new CarsFrame(allCars);
        carsFrame.setVisible(true);

    }
}
