import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the background image
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/background.jpg"));
        BackgroundPanel background = new BackgroundPanel(icon.getImage());
        background.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);

        JTextField userText = new JTextField();
        userText.setPreferredSize(new Dimension(220, 35));
        userText.setFont(fieldFont);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setPreferredSize(new Dimension(220, 35));
        passwordText.setFont(fieldFont);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(140, 45));
        loginButton.setFont(buttonFont);

        // Add components to background panel
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        background.add(userLabel, gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        background.add(userText, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        background.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        background.add(passwordText, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        background.add(loginButton, gbc);

        add(background);

        // Login Button Action
        loginButton.addActionListener(e -> {
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
                stmt.setString(1, userText.getText());
                stmt.setString(2, new String(passwordText.getPassword()));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    dispose();
                    new DashboardFrame();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
