import api.model.Car;
import api.model.Employee;

import api.services.*;
import gui.CarsFrame;
import gui.LoginFrame;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.HashMap;


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
    UserHelper userHelper=new UserHelper();
    HashMap<String,Employee> employees=userHelper.loadUsersFromBinary();
    if (employees== null){
        try{
            employees=userHelper.readUsersFromCSV();
            userHelper.saveUsersToBinary(employees);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Σφάλμα φόρτωσης χρηστών","Σφάλμα",JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    AuthService authService=new AuthService(employees);
    RentalService rentalService=new RentalService();
    SwingUtilities.invokeLater(()-> new LoginFrame(authService,allCars,allClients,rentalService));
}
