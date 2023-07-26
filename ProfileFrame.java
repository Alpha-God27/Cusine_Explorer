//Profile frame  


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProfileFrame extends JFrame {

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

    String mymail = null, myUsername = null, myName = null, myPhone = null;

    public ProfileFrame(String Email, String Username, String Name, String Phone) {
        mymail = Email;
        myUsername = Username;
        myName = Name;
        myPhone = Phone;
        setTitle("Signup Data Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\premr\\OneDrive\\Desktop\\ProfileImg.jpg"); // Replace with your image path
                g.drawImage(backgroundImage.getImage(), 0, 0, null);
            }
        };
        panel.setLayout(null);

        JLabel usernameLabel = new JLabel("Username      :" + myUsername);
        JLabel nameLabel = new JLabel("Name          :" + myName);
        JLabel phoneLabel = new JLabel("Phone Number  :" + myPhone);
        JLabel emailLabel = new JLabel("Email         :" + mymail);

        usernameLabel.setBounds(50, 50, 300, 30);
        nameLabel.setBounds(50, 100, 300, 30);
        phoneLabel.setBounds(50, 150, 300, 30);
        emailLabel.setBounds(50, 200, 300, 30);

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        usernameLabel.setFont(font);
        nameLabel.setFont(font);
        phoneLabel.setFont(font);
        emailLabel.setFont(font);
        usernameLabel.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);
        phoneLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);

        panel.add(usernameLabel);
        panel.add(nameLabel);
        panel.add(phoneLabel);
        panel.add(emailLabel);

        JButton backButton = new JButton("Home");
        backButton.setBounds(20, 310, 80, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame ods = new MainFrame(mymail);
                ods.show();
                dispose();
            }
        });
        panel.add(backButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(120, 310, 80, 30);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginFrame obs = new LoginFrame();
                obs.show();
                dispose();
            }
        });
        panel.add(logoutButton);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(220, 310, 80, 30);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchFrame searchFrame = new SearchFrame(mymail);
                searchFrame.setVisible(true);
                dispose();
            }
        });
        panel.add(searchButton);

        add(panel);
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        Connection con = getConnection();
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = con.prepareStatement("SELECT * FROM users WHERE email=?");
            pst.setString(1, mymail);
            rs = pst.executeQuery();
            if (rs.next()) {
                // Set the fields with the retrieved data
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String Email = null, Username = null, Name = null, Phone = null;
                ProfileFrame frame = new ProfileFrame(Email, Username, Name, Phone);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowOpened(java.awt.event.WindowEvent evt) {
                        frame.formWindowOpened(evt);
                    }
                });
                frame.setVisible(true);
            }
        });
    }
}
