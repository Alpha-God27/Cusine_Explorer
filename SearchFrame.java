import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SearchFrame extends JFrame implements ActionListener {

    String RecipeName,Ingrediants,PrepTime,Servings,Cusine,Course,Diet,Url;

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

    private JTextField searchField;
    private JLabel resultLabel;
    private JPanel resultPanel;
    private JTextArea ingredientsTextArea;

    JLabel recipeNameLabel = new JLabel("Recipe Name:");
    JLabel prepTimeLabel = new JLabel("Prep Time:");
    JLabel servingsLabel = new JLabel("Servings:");
    JLabel cuisineLabel = new JLabel("Cuisine:");
    JLabel courseLabel = new JLabel("Course:");
    JLabel dietLabel = new JLabel("Diet:");
    JLabel urlLabel = new JLabel("URL for Recipe Steps:");

    String mymail = null;

    public SearchFrame(String Email) {
        mymail = Email;
        setTitle("Search Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\premr\\OneDrive\\Desktop\\searchImg1.jpg"); // Replace with your image path
                g.drawImage(backgroundImage.getImage(), 0, 0, null);
            }
        };
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(this);
        topPanel.add(backButton, BorderLayout.WEST);

        searchField = new JTextField(20);
        topPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        topPanel.add(searchButton, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setPreferredSize(new Dimension(300, 30));
        panel.add(resultLabel, BorderLayout.SOUTH);

        resultPanel = new JPanel();
        resultPanel.setPreferredSize(new Dimension(300, 300));
        resultPanel.setBackground(new Color(255, 255, 255, 150));
        resultPanel.setLayout(new GridLayout(2, 2));

        panel.add(resultPanel, BorderLayout.CENTER);

        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Search")) {
            String searchText = searchField.getText();
            Connection con = getConnection();
            PreparedStatement pst;
            ResultSet rs;
            try {
                pst = con.prepareStatement("SELECT * FROM INDIAN_FOOD WHERE RECIPENAME LIKE ?");
                pst.setString(1, searchText + "%");
                rs = pst.executeQuery();
                while (rs.next()) {
                    RecipeName = rs.getString("RECIPENAME");
                    Ingrediants = rs.getString("INGREDIENTS");
                    PrepTime = "" + rs.getInt("PREPTIMEINMINS");
                    Servings = "" + rs.getInt("SERVINGS");
                    Cusine = "" + rs.getString("CUISINE");
                    Course = rs.getString("COURSE");
                    Diet = rs.getString("DIET");
                    Url = rs.getString("URL");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            resultLabel.setText("Search Result: " + searchText);

            showSearchResult(searchText, RecipeName, Ingrediants, PrepTime, Servings, Cusine, Course, Diet, Url);
        } else if (command.equals("Back")) {
            MainFrame ods = new MainFrame(mymail);
            ods.show();
            dispose();
        }
    }

    private void showSearchResult(String searchText, String recipeName, String ingredients, String prepTime, String servings, String cuisine, String course, String diet, String url) {
        resultPanel.removeAll();

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(7, 2));

        dataPanel.add(recipeNameLabel);
        dataPanel.add(new JLabel(recipeName));
        dataPanel.add(prepTimeLabel);
        dataPanel.add(new JLabel(prepTime));
        dataPanel.add(servingsLabel);
        dataPanel.add(new JLabel(servings));
        dataPanel.add(cuisineLabel);
        dataPanel.add(new JLabel(cuisine));
        dataPanel.add(courseLabel);
        dataPanel.add(new JLabel(course));
        dataPanel.add(dietLabel);
        dataPanel.add(new JLabel(diet));
        dataPanel.add(urlLabel);
        dataPanel.add(new JLabel(url));

        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setPreferredSize(new Dimension(300, 100));
        ingredientsPanel.setLayout(new BorderLayout());

        ingredientsTextArea = new JTextArea(ingredients, 5, 20);
        ingredientsTextArea.setLineWrap(true);
        ingredientsTextArea.setWrapStyleWord(true);
        ingredientsTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(ingredientsTextArea);
        ingredientsPanel.add(scrollPane, BorderLayout.CENTER);

        resultPanel.add(dataPanel);
        resultPanel.add(ingredientsPanel);

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            String Email = null;

            public void run() {
                new SearchFrame(Email).setVisible(true);
            }
        });
    }
}
