//MainFrame

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame extends JFrame implements ActionListener {

    String username;
    String name;
    String phone;;

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
    String mymail=null;
    public MainFrame(String Email) {
        // Set the frame properties
        mymail=Email;
        System.out.println(mymail);
        setTitle("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setResizable(false);

        // Create a panel with a background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\premr\\OneDrive\\Desktop\\backFrame1.png"); // Replace "background.jpg" with your image path
                g.drawImage(backgroundImage.getImage(), 0, 0, null);
            }
        };
        panel.setLayout(null);

        // Create the logo label
        ImageIcon logoImage = new ImageIcon("C:\\Users\\premr\\OneDrive\\Desktop\\backFrame1\\logoce.png"); // Replace "logo.png" with your logo image path
        JLabel logoLabel = new JLabel(logoImage);
        logoLabel.setBounds(200, 200, logoImage.getIconWidth(), logoImage.getIconHeight());
        panel.add(logoLabel);

        // Create the profile button
        JButton profileButton = new JButton("Profile");
        profileButton.setBounds(20, 20, 100, 30);
        profileButton.addActionListener(this);
        panel.add(profileButton);

        // Create the search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(getWidth() - 120, 20, 100, 30);
        searchButton.addActionListener(this);
        panel.add(searchButton);

        // Add the panel to the frame
        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Profile")) {
            Connection con = getConnection();
            PreparedStatement pst;
            ResultSet rs;
            try {
                pst = con.prepareStatement("SELECT * FROM PROFILE_DATA WHERE pemail=?");
                pst.setString(1, mymail);
                rs = pst.executeQuery();
                while(rs.next())
                {
                    username=rs.getString(1);
                    name=rs.getString(2);
                    phone=rs.getString(4);
                }
                //JOptionPane.showMessageDialog(this, "");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            // Handle profile button click
            ProfileFrame ob= new ProfileFrame(mymail,username,name,phone);
            ob.show();
            dispose();
        } else if (command.equals("Search")) {
            // Handle search button click
            SearchFrame obj= new SearchFrame(mymail);
            obj.show();
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String Email=null;
                MainFrame frame = new MainFrame(Email);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null); // Center the frame on the screen
            }
        });
    }
}
