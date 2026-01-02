import api.model.Car;
import api.services.AllCars;
import api.services.AllClients;
import api.services.CarHelper;
import gui.CarsFrame;
import gui.ClientFrame;

import javax.swing.*;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        AllCars allCars=new AllCars();
        CarHelper carHelper=new CarHelper(allCars);
        AllClients allClients = new AllClients();



        try{
            allCars=carHelper.readFromFileCars("vehicles_with_plates.csv");
            for(String id:allCars.getAllCars().keySet()){
                System.out.println(allCars.getAllCars().get(id));
            }
            CarsFrame carsFrame=new CarsFrame(allCars);
            carsFrame.setVisible(true);

        } catch (Exception e) {
            System.out.println("Λάθος στο άνοιγμα του αρχείου: "+e.getMessage());
            return;
        }

        ClientFrame clientFrame=new ClientFrame(allClients);
        clientFrame.setVisible(true);

    }
}
