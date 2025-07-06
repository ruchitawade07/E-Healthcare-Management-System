import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewPatientsFrame extends JFrame {

    public ViewPatientsFrame() {
        setTitle("Patient List");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load background image
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/background.jpg"));
        BackgroundPanel background = new BackgroundPanel(icon.getImage());
        background.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Patient List", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        background.add(titleLabel, BorderLayout.NORTH);

        // Table Model and JTable
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);

        // Table Header Font
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Make table background transparent
        table.setOpaque(false);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(false);

        // Transparent scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Add columns
        model.addColumn("No.");
        model.addColumn("Name");
        model.addColumn("Age");
        model.addColumn("Gender");
        model.addColumn("Contact");
        model.addColumn("Disease");

        // Load data from database
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM patients")) {

            int count = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                        count++,
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("contact"),
                        rs.getString("disease")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading patient data: " + e.getMessage());
        }

        background.add(scrollPane, BorderLayout.CENTER);
        add(background);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewPatientsFrame().setVisible(true));
    }
}
