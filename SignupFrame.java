//sign up frame

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class SignupFrame extends JFrame implements ActionListener {

    public Connection getConnection() {
        Connection conn;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sys as sysdba", "Apache@64");
            return conn;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private JTextField usernameField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public SignupFrame() {
        // Set the frame properties
        setTitle("Signup Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setResizable(false);

        // Create a panel with a background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\premr\\OneDrive\\Desktop\\signupImage1.jpg"); // Replace "background.jpg" with your image path
                g.drawImage(backgroundImage.getImage(), 0, 0, null);
            }
        };
        panel.setLayout(null);

        // Create a dark box panel
        JPanel darkBoxPanel = new JPanel();
        darkBoxPanel.setBackground(new Color(0, 0, 0, 200)); // Set background color with alpha value
        darkBoxPanel.setBounds(40, 40, 400, 420);
        darkBoxPanel.setLayout(null);
        panel.add(darkBoxPanel);

        // Create the username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD)); // Set bold font
        usernameLabel.setForeground(Color.white); // Set text color to white
        darkBoxPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 50, 200, 30);
        darkBoxPanel.add(usernameField);

        // Create the name label and text field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 100, 100, 30);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD)); // Set bold font
        nameLabel.setForeground(Color.white); // Set text color to white
        darkBoxPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 100, 200, 30);
        darkBoxPanel.add(nameField);

        // Create the phone number label and text field
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(50, 150, 100, 30);
        phoneLabel.setFont(phoneLabel.getFont().deriveFont(Font.BOLD)); // Set bold font
        phoneLabel.setForeground(Color.white); // Set text color to white
        darkBoxPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(160, 150, 200, 30);
        darkBoxPanel.add(phoneField);

        // Create the email label and text field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 200, 100, 30);
        emailLabel.setFont(emailLabel.getFont().deriveFont(Font.BOLD)); // Set bold font
        emailLabel.setForeground(Color.white); // Set text color to white
        darkBoxPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 200, 200, 30);
        darkBoxPanel.add(emailField);

        // Create the password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 250, 100, 30);
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(Font.BOLD)); // Set bold font
        passwordLabel.setForeground(Color.white); // Set text color to white
        darkBoxPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 250, 200, 30);
        darkBoxPanel.add(passwordField);

        // Create the signup button
        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(160, 300, 100, 30);
        signupButton.addActionListener(this);
        darkBoxPanel.add(signupButton);

        // Add the panel to the frame
        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Perform signup logic here
        String command = e.getActionCommand();
        if (command.equals("Signup")) {
            // signup operations
            if (username.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Data !");
            } else {
                Connection con = getConnection();
                PreparedStatement pst;
                ResultSet rs;
                try {
                    pst = con.prepareStatement("SELECT * FROM PROFILE_DATA WHERE pemail=?");
                    pst.setString(1, email);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "This Email is Already Registered!");
                    } else {
                        pst = con.prepareStatement("INSERT INTO PROFILE_DATA(pusername,pname,pnumber,pemail,ppassword) VALUES(?,?,?,?,?)");
                        pst.setString(1, username);
                        pst.setString(2, name);
                        pst.setString(3, phone);
                        pst.setString(4, email);
                        pst.setString(5, password);
                        int k = pst.executeUpdate();
                        if (k == 1) {
                            JOptionPane.showMessageDialog(this, "Registered Successfully");
                            LoginFrame obj = new LoginFrame();
                            obj.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Registration Failed");
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            LoginFrame obs = new LoginFrame();
            obs.show();
            dispose();
        }

        // Clear the input fields after signup
//        usernameField.setText("");
//        nameField.setText("");
//        phoneField.setText("");
//        emailField.setText("");
//        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignupFrame().setVisible(true);
            }
        });
    }
}
