import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.sql.*;
import java.util.Date;

public class AppointmentFrame extends JFrame {

    public AppointmentFrame() {
        setTitle("Manage Appointment");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load the background image
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/background.jpg"));
        BackgroundPanel background = new BackgroundPanel(icon.getImage());
        background.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdLabel.setFont(labelFont);
        JTextField patientIdField = new JTextField();
        patientIdField.setPreferredSize(new Dimension(250, 35));
        patientIdField.setFont(fieldFont);

        JLabel doctorLabel = new JLabel("Doctor:");
        doctorLabel.setFont(labelFont);
        JTextField doctorField = new JTextField();
        doctorField.setPreferredSize(new Dimension(250, 35));
        doctorField.setFont(fieldFont);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(labelFont);
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(250, 35));
        dateChooser.setFont(fieldFont);

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.setPreferredSize(new Dimension(150, 45));

        gbc.gridx = 0; gbc.gridy = 0; background.add(patientIdLabel, gbc);
        gbc.gridx = 1; background.add(patientIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; background.add(doctorLabel, gbc);
        gbc.gridx = 1; background.add(doctorField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; background.add(dateLabel, gbc);
        gbc.gridx = 1; background.add(dateChooser, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        background.add(saveButton, gbc);

        add(background);

        saveButton.addActionListener(e -> {
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO appointments(patient_id, doctor_name, appointment_date) VALUES (?, ?, ?)"
                );

                ps.setInt(1, Integer.parseInt(patientIdField.getText()));
                ps.setString(2, doctorField.getText());

                Date selectedDate = dateChooser.getDate();
                if (selectedDate != null) {
                    ps.setDate(3, new java.sql.Date(selectedDate.getTime()));
                } else {
                    ps.setNull(3, Types.DATE);
                }

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Appointment added successfully!");
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding appointment: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            }
        });

        setVisible(true);
    }
}
