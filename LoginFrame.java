//Login frame for the application using java awt and swing


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginFrame extends JFrame implements ActionListener {


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
    private JTextField EmailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        // Set the frame properties
        setTitle("Login Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setResizable(false);

        // Create a panel with a background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\premr\\OneDrive\\Desktop\\loginImage1.jpg"); // Replace "background.jpg" with your image path
                g.drawImage(backgroundImage.getImage(), 0, 0, null);
            }
        };
        panel.setLayout(new GridBagLayout());

        // Create the Email label and text field
        JLabel EmailLabel = new JLabel("Email:");
        EmailLabel.setFont(EmailLabel.getFont().deriveFont(Font.BOLD, 20)); // Increase the font size
        EmailField = new JTextField(15);
        EmailField.setText("@gmail.com");

        // Create the password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(Font.BOLD, 20)); // Increase the font size
        passwordField = new JPasswordField(15);

        // Create the login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        // Create the signup button
        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(this);

        // Create a constraint for center alignment
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.anchor = GridBagConstraints.CENTER;
        constraint.insets = new Insets(5, 5, 5, 5);

        // Add components to the panel using the constraint
        constraint.gridx = 0;
        constraint.gridy = 2; // Move the Email label lower
        panel.add(EmailLabel, constraint);

        constraint.gridx = 1;
        constraint.gridy = 2; // Move the Email field lower
        panel.add(EmailField, constraint);

        constraint.gridx = 0;
        constraint.gridy = 3; // Move the password label lower
        panel.add(passwordLabel, constraint);

        constraint.gridx = 1;
        constraint.gridy = 3; // Move the password field lower
        panel.add(passwordField, constraint);

        constraint.gridx = 0;
        constraint.gridy = 4; // Move the login button lower
        constraint.gridwidth = 2;
        panel.add(loginButton, constraint);

        constraint.gridx = 0;
        constraint.gridy = 5; // Move the signup button lower
        panel.add(signupButton, constraint);

        // Add the panel to the frame
        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Login")) {
            String Email = EmailField.getText();
            String password = new String(passwordField.getPassword());

            if (Email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Data !");
            } else {
                Connection con = getConnection();
                PreparedStatement pst;
                ResultSet rs;
                try {
                    pst = con.prepareStatement("SELECT * FROM PROFILE_DATA WHERE pemail=? and ppassword=?");
                    pst.setString(1, Email);
                    pst.setString(2, password);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Login Sucessfully!");
                        MainFrame ods = new MainFrame(Email);
                        ods.show();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Data !");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }



        } else if (command.equals("Signup")) {
            // Perform signup logic here
            SignupFrame ob= new SignupFrame();
            ob.show();
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
