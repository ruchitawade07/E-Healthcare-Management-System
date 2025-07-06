import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddPatientFrame extends JFrame {
    public AddPatientFrame() {
        setTitle("Add Patient");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        // Form components
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(220, 35));
        nameField.setFont(fieldFont);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        JTextField ageField = new JTextField();
        ageField.setPreferredSize(new Dimension(220, 35));
        ageField.setFont(fieldFont);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);
        genderComboBox.setPreferredSize(new Dimension(220, 35));
        genderComboBox.setFont(fieldFont);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setFont(labelFont);
        JTextField contactField = new JTextField();
        contactField.setPreferredSize(new Dimension(220, 35));
        contactField.setFont(fieldFont);

        JLabel diseaseLabel = new JLabel("Disease:");
        diseaseLabel.setFont(labelFont);
        JTextField diseaseField = new JTextField();
        diseaseField.setPreferredSize(new Dimension(220, 35));
        diseaseField.setFont(fieldFont);

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(150, 45));
        saveButton.setFont(buttonFont);

        // Add components to the background
        gbc.gridx = 0; gbc.gridy = 0; background.add(nameLabel, gbc);
        gbc.gridx = 1; background.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; background.add(ageLabel, gbc);
        gbc.gridx = 1; background.add(ageField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; background.add(genderLabel, gbc);
        gbc.gridx = 1; background.add(genderComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3; background.add(contactLabel, gbc);
        gbc.gridx = 1; background.add(contactField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; background.add(diseaseLabel, gbc);
        gbc.gridx = 1; background.add(diseaseField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        background.add(saveButton, gbc);

        add(background);

        // Save button action
        saveButton.addActionListener(e -> {
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO patients(name, age, gender, contact, disease) VALUES (?, ?, ?, ?, ?)"
                );
                ps.setString(1, nameField.getText());
                ps.setString(2, ageField.getText());
                ps.setString(3, (String) genderComboBox.getSelectedItem());
                ps.setString(4, contactField.getText());
                ps.setString(5, diseaseField.getText());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Patient added successfully!");
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding patient: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
