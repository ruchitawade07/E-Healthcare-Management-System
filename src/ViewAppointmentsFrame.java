import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewAppointmentsFrame extends JFrame {

    public ViewAppointmentsFrame() {
        setTitle("Appointment List");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load the background image
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/background.jpg"));
        BackgroundPanel background = new BackgroundPanel(icon.getImage());
        background.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Appointment List", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        background.add(titleLabel, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        // Center align table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Transparent table
        table.setOpaque(false);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(false);

        // Transparent scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Add columns
        model.addColumn("No.");
        model.addColumn("Appointment ID");
        model.addColumn("Patient ID");
        model.addColumn("Doctor Name");
        model.addColumn("Appointment Date");

        // Fetch data
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM appointments")) {

            int count = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                        count++,
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("doctor_name"),
                        rs.getDate("appointment_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading appointment data: " + e.getMessage());
        }

        background.add(scrollPane, BorderLayout.CENTER);
        add(background);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewAppointmentsFrame().setVisible(true));
    }
}
