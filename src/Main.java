import api.model.Car;
import api.model.Employee;
import api.services.*;
import gui.LoginFrame;

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
        //ΑΡΧΙΚΟΠΟΙΗΣΗ ΠΕΛΑΤΩΝ
        ClientFrame clientFrame=new ClientFrame(allClients);
        clientFrame.setVisible(true);
        //ΦΟΡΤΩΣΗ ΥΠΑΛΛΗΛΩΝ
        UserHelper userHelper=new UserHelper();
        HashMap<String, Employee> employees = userHelper.loadUsersFromBinary();

        if (employees == null) {
            try {
                employees = userHelper.readUsersFromCSV();
                userHelper.saveUsersToBinary(employees);
                System.out.println("Οι υπάλληλοι φορτώθηκαν από CSV.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Σφάλμα φόρτωσης χρηστών",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        } else {
            System.out.println("Οι υπάλληλοι φορτώθηκαν από binary αρχείο.");
        }
        //ΥΠΗΡΕΣΙΕΣ
        AuthService authService = new AuthService(employees);
        RentalService rentalService = new RentalService();
        //ΕΚΚΙΝΗΣΗ LOGIN
        new LoginFrame(authService, allCars, allClients, rentalService);
    }
}
