package gui;

import api.model.Employee;
import api.services.AuthService;
import api.services.UserHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Παράθυρο διαχείρισης υπαλλήλων.
 * Παρέχει προβολή, προσθήκη και διαγραφή χρηστών.
 *
 * @author Καραγιώργου Στεργιανή
 * @version 0.1(2026.01.02)
 */
public class EmployeeFrame extends JFrame {

    private AuthService authService;
    private UserHelper userHelper;
    private JTable table;
    private DefaultTableModel model;

    /**
     * Κατασκευαστής παραθύρου υπαλλήλων
     *
     * @param authService Υπηρεσία αυθεντικοποίησης
     */
    public EmployeeFrame(AuthService authService) {
        this.authService = authService;
        this.userHelper = new UserHelper();


        setTitle("Διαχείριση Υπαλλήλων");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        refreshTable();

        setVisible(true);
    }

    /**
     * Δημιουργία βασικών στοιχείων GUI
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        model = new DefaultTableModel(
                new String[]{"Όνομα", "Επώνυμο", "Username", "Email"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addBtn = new JButton("Προσθήκη");
        addBtn.addActionListener(e -> addEmployee());

        JButton deleteBtn = new JButton("Διαγραφή");
        deleteBtn.addActionListener(e -> deleteEmployee());

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Ανανέωση πίνακα υπαλλήλων
     */
    private void refreshTable() {
        model.setRowCount(0);

        for (Employee e : authService.getAllEmployees().values()) {
            model.addRow(new Object[]{
                    e.getFirstName(),
                    e.getLastName(),
                    e.getUsername(),
                    e.getEmail()
            });
        }
    }

    /**
     * Προσθήκη νέου υπαλλήλου
     */
    private void addEmployee() {
        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();

        Object[] fields = {
                "Όνομα:", nameField,
                "Επώνυμο:", surnameField,
                "Username:", usernameField,
                "Email:", emailField,
                "Password:", passField
        };

        int result = JOptionPane.showConfirmDialog(
                this, fields, "Νέος Υπάλληλος",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            if (usernameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Το username είναι υποχρεωτικό",
                        "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Employee emp = new Employee(
                    nameField.getText(),
                    surnameField.getText(),
                    usernameField.getText(),
                    emailField.getText(),
                    new String(passField.getPassword())
            );

            authService.addEmployee(emp);
            // Αποθήκευση σε αρχείο
            try {
                userHelper.saveUsersToBinary(authService.getAllEmployees());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Σφάλμα κατά την αποθήκευση των υπαλλήλων",
                        "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }

            refreshTable();
        }

    }

    /**
     * Διαγραφή υπαλλήλου
     */
    private void deleteEmployee() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Επιλέξτε έναν υπάλληλο",
                    "Προειδοποίηση",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = (String) model.getValueAt(row, 2);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Διαγραφή χρήστη: " + username + ";",
                "Επιβεβαίωση",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            authService.removeEmployee(username);
            // Αποθήκευση σε αρχείο
            try {
                userHelper.saveUsersToBinary(authService.getAllEmployees());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Σφάλμα κατά την αποθήκευση των υπαλλήλων",
                        "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            refreshTable();
        }
    }
}
