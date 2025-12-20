package gui;

import api.model.Car;

import javax.swing.*;

public class CarDialog extends JDialog {
    private Car car;
    private boolean saved=false;
    private JTextField idField, plateField, brandField, modelField, yearField, colorField,situationField,typeField;
    public CarDialog(JFrame parent,Car existingCar){

    }
}
