import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AppointmentDesk extends JFrame {
    private final JTextField patientId = new JTextField(12);
    private final JComboBox<String> department = new JComboBox<>(new String[]{"Cardiology","Dermatology","Orthopedics"});
    private final JComboBox<String> doctor = new JComboBox<>();
    private final JComboBox<String> slot = new JComboBox<>(new String[]{"09:00 AM","10:30 AM","02:00 PM","04:00 PM"});
    private boolean bookingInProgress = false;
    private final Map<String, String[]> doctors = Map.of(
        "Cardiology", new String[]{"Dr. Anand","Dr. Meera"},
        "Dermatology", new String[]{"Dr. Priya","Dr. Kumar"},
        "Orthopedics", new String[]{"Dr. Ravi","Dr. Nisha"}
    );

    public AppointmentDesk() {
        super("Hospital Outpatient Appointment Desk");
        setLayout(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(4,2,8,8));
        form.add(new JLabel("Patient ID:")); form.add(patientId);
        form.add(new JLabel("Department:")); form.add(department);
        form.add(new JLabel("Doctor:")); form.add(doctor);
        form.add(new JLabel("Time slot:")); form.add(slot);
        JButton book = new JButton("Book Appointment");
        JLabel message = new JLabel("Choose department, doctor and time.");
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(book, BorderLayout.CENTER); bottom.add(message, BorderLayout.SOUTH);
        add(form, BorderLayout.CENTER); add(bottom, BorderLayout.SOUTH);
        refreshDoctors();
        department.addActionListener(e -> refreshDoctors());
        book.addActionListener(e -> {
            if (patientId.getText().trim().isEmpty()) { message.setText("Error: Enter patient ID."); return; }
            bookingInProgress = true;
            int confirm = JOptionPane.showConfirmDialog(this,
                "Book appointment for " + patientId.getText() + " with " + doctor.getSelectedItem()
                + " at " + slot.getSelectedItem() + "?", "Confirm booking", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) message.setText("Appointment booked successfully.");
            else message.setText("Booking cancelled.");
            bookingInProgress = false;
        });
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                if (bookingInProgress && JOptionPane.showConfirmDialog(AppointmentDesk.this,
                    "A booking is in progress. Close anyway?", "Confirm close",
                    JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
                dispose();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(480,260); setLocationRelativeTo(null); setVisible(true);
    }
    private void refreshDoctors() {
        doctor.removeAllItems();
        for (String name : doctors.get((String)department.getSelectedItem())) doctor.addItem(name);
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(AppointmentDesk::new); }
}
