package experiment.dbmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import experiment.model.Project;

/**
 * Creates "myDatabase" DB and "project" table.
 * <p>
 * From <a href="http://courses.coreservlets.com/Course-Materials/">the
 * coreservlets.com tutorials on servlets, JSP, Struts, JSF, Ajax, GWT,
 * Spring, Hibernate/JPA, and Java programming</a>.
 */
public class EmbeddedDbCreator {
    // Driver only needed for Tomcat to find Derby.
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
    private String username = "someuser";
    private String password = "somepassword";
    private String dbName = "projectdb";
    private String tableName = "project";
    private Properties userInfo;

    public EmbeddedDbCreator() {
        userInfo = new Properties();
        userInfo.put("user", username);
        userInfo.put("password", password);
    }

    public void createDatabase() {
        Project[] projects = {
                new Project(1, "E-commerce Web Application", "img/project1.png", "The goal of this project was to do an E-commerce Web Application where the user would be able to create and connect to an account as well as purchase items. The administrator interface offered functionalities such as product management and moderator administration.", "I worked on both front end and back end with the jsp pages, the servlets and the connection to our database.", "I learned a lot on Java Persistence, Hibernate and in general on making a web application.", "MySQL database, Java Persistence API, Hibernate, Java Database Connectivity"),
                new Project(2, "Java Game", "img/map.png", "This project consisted of creating a sliding puzzle in different shapes and levels. We implemented a map that provided access to different levels. The user could save their progress in the game, and they had to solve the first level to unlock the other ones. Additionally, we developed an algorithm that could solve the puzzle and show each steps, in case the user needed assistance.", "With the help of one of my teammates, we worked on the algorithm to solve the puzzle. I also helped designing our game.", "I learned a lot on the different types of algorithms and their efficiency.", "Java, JavaFX")
        };

        try {
            Class.forName(driver);  // Only needed for Tomcat to find Derby.
            String dbUrl = protocol + dbName + ";create=true";
            try (Connection connection = DriverManager.getConnection(dbUrl, userInfo)) {
                Statement statement = connection.createStatement();

                boolean tableExists = false;  // dropping the table in case it already exists
                try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
                    if (rs.next()) {
                        tableExists = true;
                    }
                }
                if (tableExists) {
                    System.out.println("Dropping table " +tableName);
                    statement.execute("DROP TABLE " + tableName);
                }

                // Creating the table
                String smallStringType = "VARCHAR(30)";
                String bigStringType = "VARCHAR(500)";
                String tableDescription =
                        String.format("CREATE TABLE %s"
                                        + "(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                                        + "title %s, image %s, "
                                        + "description %s, myPart %s, learned %s, technologies %s)",
                                tableName, smallStringType,
                                smallStringType, bigStringType, bigStringType, bigStringType, bigStringType
                        );
                System.out.println("Creating table " +tableDescription);
                statement.execute(tableDescription);

                // Inserting elements in the table
                String template =
                        String.format("INSERT INTO %s (title, image, description, myPart, learned, technologies) VALUES (?, ?, ?, ?, ?, ?)",
                                tableName);
                try (PreparedStatement inserter = connection.prepareStatement(template)) {
                    for (Project e : projects) {
                        inserter.setString(1, e.getTitle());
                        inserter.setString(2, e.getImage());
                        inserter.setString(3, e.getDescription());
                        inserter.setString(4, e.getMyPart());
                        inserter.setString(5, e.getLearned());
                        inserter.setString(6, e.getTechnologies());
                        inserter.executeUpdate();
                        System.out.printf("Inserted %s %s.%n", e.getId(), e.getTitle());
                    }
                } catch (SQLException sqle) {
                    System.err.println("Error inserting projects: " + sqle.getMessage());
                    sqle.printStackTrace();
                }
            } catch (SQLException sqle) {
                System.err.println("Error connecting to database: " + sqle.getMessage());
                sqle.printStackTrace();
            }
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }


    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String dbUrl = protocol + dbName;

        try {
            Class.forName(driver);
            try (Connection connection = DriverManager.getConnection(dbUrl, userInfo)) {
                Statement statement = connection.createStatement();
                String query = String.format("SELECT * FROM %s", tableName);
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Project project = new Project(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("image"),
                            resultSet.getString("description"),
                            resultSet.getString("myPart"),
                            resultSet.getString("learned"),
                            resultSet.getString("technologies")
                    );
                    projects.add(project);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public Project findProject(int projectId) {
        Project project = null;
        String dbUrl = protocol + dbName;
        try (Connection connection = DriverManager.getConnection(dbUrl, userInfo)) {
            String query = String.format("SELECT * FROM %s WHERE id = ?", tableName);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, projectId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    project = new Project(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("image"),
                            resultSet.getString("description"),
                            resultSet.getString("myPart"),
                            resultSet.getString("learned"),
                            resultSet.getString("technologies")
                    );
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return project;
    }

    public void saveProject(Project project) {
        String dbUrl = protocol + dbName;
        try (Connection connection = DriverManager.getConnection(dbUrl, userInfo)){
            String query = String.format("Update %s SET title = ?, image = ?, description = ?, myPart = ?, learned = ?, technologies = ? WHERE id = ?", tableName);
            try (PreparedStatement update = connection.prepareStatement(query)) {
                update.setString(1, project.getTitle());
                update.setString(2, project.getImage());
                update.setString(3, project.getDescription());
                update.setString(4, project.getMyPart());
                update.setString(5, project.getLearned());
                update.setString(6, project.getTechnologies());
                update.setInt(7, project.getId());
                update.executeUpdate();
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EmbeddedDbCreator tester = new EmbeddedDbCreator();
        tester.createDatabase();
        tester.getAllProjects();
    }
}
