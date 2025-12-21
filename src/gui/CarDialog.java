package gui;

import api.model.Car;
import api.services.AllCars;

import javax.swing.*;
import java.awt.*;

public class CarDialog extends JDialog {
    private AllCars allCars;
    private Car car;
    private boolean saved=false;
    private JTextField idField, plateField, brandField, modelField, yearField, colorField,situationField,typeField;
    private JButton saveButton,cancelButton;
    public CarDialog(JFrame parent,Car existingCar){
            super(parent,existingCar==null);
            this.car=existingCar;
            initDialog();
    }
    private void initDialog(){
        setSize(500,400);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10,10));

        JPanel mainPanel=new JPanel(new GridLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(5,5,5,5);
        gbc.fill=GridBagConstraints.HORIZONTAL;

        gbc.gridx=0;
        gbc.gridy=0;
        mainPanel.add(new JLabel("ID:"),gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        idField=new JTextField(20);
        if(car!=null){
            idField.setText(car.getId());
            idField.setEditable(false);
        }else{
            idField.setText("CAR" + (allCars.getAllCars().size() + 1));
        }
        mainPanel.add(idField, gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        mainPanel.add(new JLabel("Πινακίδα:"),gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        plateField=new JTextField(20);
        if(car!=null){
            plateField.setText(car.getPlate());
        }
        mainPanel.add(plateField,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        mainPanel.add(new JLabel("Μάρκα:"),gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        brandField=new JTextField(20);
        if(car!=null){
            brandField.setText(car.getCarBrand());
        }
        mainPanel.add(brandField,gbc);


        gbc.gridx=0;
        gbc.gridy=3;
        mainPanel.add(new JLabel("Τύπος:"),gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        typeField=new JTextField(20);
        if(car!=null){
            typeField.setText(car.getType());
        }
        mainPanel.add(typeField,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        mainPanel.add(new JLabel("Μοντέλο:"),gbc);

        gbc.gridx=1;
        gbc.gridy=4;
        modelField=new JTextField(20);
        if(car!=null){
            modelField.setText(car.getModel());
        }
        mainPanel.add(modelField,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        mainPanel.add(new JLabel("Έτος:"),gbc);

        gbc.gridx=1;
        gbc.gridy=5;
        yearField=new JTextField(4);
        mainPanel.add(yearField,gbc);

        gbc.gridx=0;
        gbc.gridy=6;
        mainPanel.add(new JLabel("Πινακίδα:"),gbc);

        gbc.gridx=1;
        gbc.gridy=6;
        colorField=new JTextField(15);
        if(car!=null){
            colorField.setText(car.getColor());
        }
        mainPanel.add(colorField,gbc);

        gbc.gridx=0;
        gbc.gridy=7;
        mainPanel.add(new JLabel("Κατάσταση:"),gbc);

        gbc.gridx=1;
        gbc.gridy=7;
        situationField=new JTextField(20);
        if(car!=null){
            situationField.setText(car.getSituation());
        }
        mainPanel.add(situationField,gbc);

        JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        saveButton=new JButton("Αποθήκευση");
        saveButton.addActionListener(e -> saveCar());

        cancelButton=new JButton("Ακύρωση");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(mainPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);

        getRootPane().setDefaultButton(saveButton);
    }

    private void saveCar(){
        if(idField.getText().trim().isEmpty()){
            showError("Το id είναι υποχρεωτικό πεδίο!");
            return;
        }
        if(plateField.getText().trim().isEmpty()){
            showError("Η πινακίδα είναι υποχρεωτικό πεδίο!");
            return;
        }
        if(brandField.getText().trim().isEmpty()){
            showError("Η μάρκα είναι υποχρεωτικό πεδίο!");
        }
        if (modelField.getText().trim().isEmpty()){
            showError("Το μοντέλο είναι υποχρεωτικό πεδίο!");
        }
        int year;
        try{
            year=Integer.parseInt(yearField.getText().trim());
            if(year<1900 || year>2027) {
                showError("Το έτος πρέπει να είναι μεταξύ 1900 και 2027.");
                return;
            }
        }catch(NumberFormatException e){
            showError("Το έτος πρέπει να είναι αριθμός.");
            return;
        }
        this.car=new Car(idField.getText().trim(),plateField.getText().trim(),brandField.getText().trim(),typeField.getText().trim(),modelField.getText().trim(),year,colorField.getText().trim(),situationField.getText().trim());
        saved=true;
        dispose();
    }

    private void showError(String message){
        JOptionPane.showMessageDialog(this,message,"Σφάλμα Εισαγωγής!",JOptionPane.ERROR_MESSAGE);
    }
    public boolean isSaved(){
        return saved;
    }


}
