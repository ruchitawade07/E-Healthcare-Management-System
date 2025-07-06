import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        setTitle("Dashboard");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the background image
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/background.jpg"));
        BackgroundPanel background = new BackgroundPanel(icon.getImage());
        background.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 15, 20, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        JButton addPatient = new JButton("Add Patient");
        addPatient.setFont(buttonFont);
        addPatient.setPreferredSize(new Dimension(220, 45));

        JButton viewPatients = new JButton("View Patients");
        viewPatients.setFont(buttonFont);
        viewPatients.setPreferredSize(new Dimension(220, 45));

        JButton appointment = new JButton("Manage Appointments");
        appointment.setFont(buttonFont);
        appointment.setPreferredSize(new Dimension(220, 45));

        JButton viewAppointments = new JButton("View Appointments");
        viewAppointments.setFont(buttonFont);
        viewAppointments.setPreferredSize(new Dimension(220, 45));

        // Add buttons to the background panel
        gbc.gridy = 0;
        background.add(addPatient, gbc);

        gbc.gridy = 1;
        background.add(viewPatients, gbc);

        gbc.gridy = 2;
        background.add(appointment, gbc);

        gbc.gridy = 3;
        background.add(viewAppointments, gbc);

        // Add the background panel to the frame
        add(background);

        // Action listeners open frames
        addPatient.addActionListener(e -> new AddPatientFrame().setVisible(true));
        viewPatients.addActionListener(e -> new ViewPatientsFrame().setVisible(true));
        appointment.addActionListener(e -> new AppointmentFrame().setVisible(true));
        viewAppointments.addActionListener(e -> new ViewAppointmentsFrame().setVisible(true));

        setVisible(true);
    }
}
