import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HospitalGUI extends JFrame {
    private HospitalQueueManager manager;
    private JTextField nameField, ageField, diseaseField, severityField, searchNameField, searchAgeField;
    private JTable patientTable;
    private DefaultTableModel tableModel;

    public HospitalGUI() {
        manager = new HospitalQueueManager();

        setTitle("Hospital Queue Management System");
        setSize(900, 600);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Form fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 50, 20);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(70, 20, 150, 20);
        add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(230, 20, 40, 20);
        add(ageLabel);
        ageField = new JTextField();
        ageField.setBounds(270, 20, 50, 20);
        add(ageField);

        JLabel diseaseLabel = new JLabel("Disease:");
        diseaseLabel.setBounds(330, 20, 60, 20);
        add(diseaseLabel);
        diseaseField = new JTextField();
        diseaseField.setBounds(390, 20, 100, 20);
        add(diseaseField);

        JLabel severityLabel = new JLabel("Severity (1-5):");
        severityLabel.setBounds(500, 20, 100, 20);
        add(severityLabel);
        severityField = new JTextField();
        severityField.setBounds(600, 20, 50, 20);
        add(severityField);

        // Buttons
        JButton addButton = new JButton("Add Patient");
        addButton.setBounds(20, 60, 150, 30);
        add(addButton);

        JButton nextButton = new JButton("Call Next Patient");
        nextButton.setBounds(180, 60, 180, 30);
        add(nextButton);

        JButton showButton = new JButton("Show Queue");
        showButton.setBounds(370, 60, 150, 30);
        add(showButton);

        JButton showDischargedButton = new JButton("Show Discharge Patients");
        showDischargedButton.setBounds(530, 60, 200, 30);
        add(showDischargedButton);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Name", "Age", "Disease", "Severity"}, 0);
        patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setBounds(20, 100, 850, 300);
        add(scrollPane);

        // Search Panel
        JLabel searchLabel = new JLabel("Search Patient:");
        searchLabel.setBounds(20, 420, 150, 20);
        add(searchLabel);

        searchNameField = new JTextField();
        searchNameField.setBounds(150, 420, 150, 20);
        add(searchNameField);

        searchAgeField = new JTextField();
        searchAgeField.setBounds(310, 420, 50, 20);
        add(searchAgeField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(370, 420, 100, 20);
        add(searchButton);

        // Events
        addButton.addActionListener(e -> addPatient());
        showButton.addActionListener(e -> showPatients());
        nextButton.addActionListener(e -> callNextPatient());
        searchButton.addActionListener(e -> searchPatient());
        showDischargedButton.addActionListener(e -> showDischargedPatients());

        setVisible(true);
    }

    private void addPatient() {
        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String disease = diseaseField.getText().trim();
            int severity = Integer.parseInt(severityField.getText().trim());

            if (severity < 1 || severity > 5) {
                JOptionPane.showMessageDialog(this, "Severity must be between 1 and 5.");
                return;
            }

            Patient p = new Patient(name, age, disease, severity);
            manager.addPatient(p);
            JOptionPane.showMessageDialog(this, "Patient added!");

            nameField.setText("");
            ageField.setText("");
            diseaseField.setText("");
            severityField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid details.");
        }
    }

    private void showPatients() {
        tableModel.setRowCount(0);
        PriorityQueue<Patient> temp = new PriorityQueue<>(manager.queue);
        while (!temp.isEmpty()) {
            Patient p = temp.poll();
            tableModel.addRow(new Object[]{p.name, p.age, p.disease, p.severity});
        }
    }

    private void callNextPatient() {
        Patient p = manager.callNextPatient();
        if (p != null) {
            JOptionPane.showMessageDialog(this, "Next Patient: " + p.name + ", " + p.disease);
            showPatients();
        } else {
            JOptionPane.showMessageDialog(this, "No patients in queue.");
        }
    }

    private void searchPatient() {
        String name = searchNameField.getText().trim();
        try {
            int age = Integer.parseInt(searchAgeField.getText().trim());
            Patient p = manager.findPatient(name, age);
            if (p != null) {
                JOptionPane.showMessageDialog(this, "Found: " + p);
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid age input.");
        }
    }

    // Placeholder for Show Discharge Patients
    private void showDischargedPatients() {
        tableModel.setRowCount(0);
        List<Patient> discharged = manager.getDischargedPatients();
        if (discharged.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No patients have been discharged yet.");
        } else {
            for (Patient p : discharged) {
                tableModel.addRow(new Object[]{p.name, p.age, p.disease, p.severity});
            }
        }
    }
    public static void main(String[] args) {
        new HospitalGUI();
    }
}
