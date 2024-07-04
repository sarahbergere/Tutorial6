package experiment.dbmanagement;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Makes sure database and projects table exists. Runs at Web app
 * startup, but even that may be too often, so code has try/catch
 * internally to prevent problems if table already exists.
 * <p>
 * From <a href="http://courses.coreservlets.com/Course-Materials/">the
 * coreservlets.com tutorials on servlets, JSP, Struts, JSF, Ajax, GWT,
 * Spring, Hibernate/JPA, and Java programming</a>.
 */
@WebListener
public class DatabaseInitialiser implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        new EmbeddedDbCreator().createDatabase();
    }

    public void contextDestroyed(ServletContextEvent event) {}
}
