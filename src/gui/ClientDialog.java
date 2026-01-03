package gui;

import api.model.Client;
import api.services.AllClients;

import javax.swing.*;
import java.awt.*;

/**
 * Παράθυρο διαλόγου για την προσθήκη πελάτη
 *
 * @author Αλεξάνδρα Σακελλαριάδη
 * @version 0.1(2025.12.12)
 */
public class ClientDialog extends JDialog {
    //Δομή πελατών για την αποθήκευση των πελατών
    private AllClients allClients;
    //Πελάτης
    private Client client;
    //Σημαιοφόρος για το αν αποθηκεύτηκαν οι αλλαγές
    private boolean saved=false;
    //Πεδία χαρακτηριστικών πελάτη
    private JTextField AFMField, firstNameField, lastNameField, phoneField, emailField;
    //Κουμπιά αποθήκευσης και ακύρωσης
    private JButton saveButton,cancelButton;

    /**
     * Δημιουργία παραθύρου διαλόγου για την προσθήκη πελάτη
     * @param parent Παράθυρο από το οποίο ενεργοποιήθηκε το παράθυρο διαλόγου
     * @param existingClient Πελάτης που θα προσθέσουμε
     */
    public ClientDialog(JFrame parent,Client existingClient,AllClients allClients){
        super(parent, existingClient == null ? "Προσθήκη Πελάτη" : "Επεξεργασία", true);
        this.client=existingClient;
        this.allClients=allClients;
        initDialog();
    }

    /**
     * Πεδία που προορίζονται να συμπληρωθούν από τον χρήστη για την προσθήκη πελάτη
     */
    private void initDialog(){
        setSize(600,400);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10,10));

        JPanel mainPanel=new JPanel(new GridLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(5,5,5,5);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=1.0;

        gbc.gridx=0;
        gbc.gridy=0;
        mainPanel.add(new JLabel("ΑΦΜ:"),gbc);

        gbc.gridx=1;
        AFMField=new JTextField(9);
        if(client!=null){
            AFMField.setText(client.getAFM());
        }
        mainPanel.add(AFMField, gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        mainPanel.add(new JLabel("Όνομα:"),gbc);

        gbc.gridx=1;
        firstNameField=new JTextField(20);
        if(client!=null){
            firstNameField.setText(client.getFirstName());
        }
        mainPanel.add(firstNameField,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        mainPanel.add(new JLabel("Επώνυμο:"),gbc);

        gbc.gridx=1;
        lastNameField=new JTextField(30);
        if(client!=null){
            lastNameField.setText(client.getLastName());
        }
        mainPanel.add(lastNameField,gbc);


        gbc.gridx=0;
        gbc.gridy=3;
        mainPanel.add(new JLabel("Τηλέφωνο:"),gbc);

        gbc.gridx=1;
        phoneField=new JTextField(12);
        if(client!=null){
            phoneField.setText(client.getPhone());
        }
        mainPanel.add(phoneField,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        mainPanel.add(new JLabel("Email:"),gbc);

        gbc.gridx=1;
        emailField=new JTextField(30);
        if(client!=null){
            emailField.setText(client.getEmail());
        }
        mainPanel.add(emailField,gbc);

        JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        saveButton=new JButton("Αποθήκευση");
        saveButton.addActionListener(e -> saveClient());

        cancelButton=new JButton("Ακύρωση");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(mainPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);

        getRootPane().setDefaultButton(saveButton);
    }

    /**
     * Αποθήκευση πελάτη και έλεγχος των πεδίων
     */
    private void saveClient(){
        if(AFMField.getText().trim().isEmpty()){
            showError("Το ΑΦΜ είναι υποχρεωτικό πεδίο!");
            return;
        }
        if(firstNameField.getText().trim().isEmpty()){
            showError("Η όνομα είναι υποχρεωτικό πεδίο!");
            return;
        }
        if(lastNameField.getText().trim().isEmpty()){
            showError("Η επώνυμο είναι υποχρεωτικό πεδίο!");
            return;
        }
        if (phoneField.getText().trim().isEmpty()){
            showError("Το τηλέφωνο είναι υποχρεωτικό πεδίο!");
            return;
        }
        if(emailField.getText().trim().isEmpty()){
            showError("Το email είναι υποχρεωτικό πεδίο!");
            return;
        }
        client=new Client(AFMField.getText().trim(),firstNameField.getText().trim(),lastNameField.getText().trim(),phoneField.getText().trim(),emailField.getText().trim());
        saved=true;
        dispose();
    }

    /**
     * Εμφάνιση λάθους
     * @param message Μήνυμα κατάλληλου λάθους
     */
    private void showError(String message){
        JOptionPane.showMessageDialog(this,message,"Σφάλμα Εισαγωγής!",JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @return saved Για τον έλεγχο αποθήκευσης
     */
    public boolean isSaved(){
        return saved;
    }

    /**
     * @return πελάτη
     */
    public Client getClient(){
        return client;
    }


}
