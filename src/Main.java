import api.model.Car;
import api.services.AllCars;
import api.services.AllClients;

import gui.ManagementGUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Main extends JFrame{
    public static void main(String[] args) throws IOException {
        AllClients allClients = new AllClients();
        AllCars allCars=new AllCars();
        ManagementGUI managementGUI=new ManagementGUI(allCars,allClients);


    }
}
