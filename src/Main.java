
import api.model.Employee;
import api.services.*;
import gui.EmployeeLoginFrame;
import gui.ManagementGUI;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;



public class Main extends JFrame{
    public static void main(String[] args) throws IOException {
        AllClients allClients = new AllClients();
        RentalService rentalService=new RentalService();
        AllCars allCars=new AllCars();
        UserHelper userHelper=new UserHelper();
        HashMap<String,Employee> allEmployees;
        try {
            allEmployees=userHelper.readUsersFromCSV();
            AuthService authService=new AuthService(allEmployees);
            ManagementGUI managementGUI=new ManagementGUI(allCars,allClients,rentalService,authService);
            // Ανοίγει το Login και μετά το ManagementGUI
            new EmployeeLoginFrame(authService, () ->
                    new ManagementGUI(allCars, allClients, rentalService, authService)
            );


        }catch (Exception e){
            JOptionPane.showMessageDialog(
                    null,
                    "Σφάλμα φόρτωσης χρηστών",
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
    }
}
