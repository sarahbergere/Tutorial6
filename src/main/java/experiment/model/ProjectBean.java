package experiment.model;

import experiment.dbmanagement.EmbeddedDbCreator;
import jakarta.faces.bean.ViewScoped;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.bean.ManagedBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class ProjectBean implements Serializable {
    private Project project;
    private EmbeddedDbCreator dbCreator = new EmbeddedDbCreator(); //To get all of the project : useful for the index page
    private int projectId;
    private static List<Project> projects;

    @PostConstruct
    public void init() {
        projects = dbCreator.getAllProjects();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String param = params.get("p");

        if (param != null) {
            this.projectId = Integer.parseInt(param);
        } else {
            this.projectId = 1;
        }
        project = dbCreator.findProject(projectId);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project currentProject) {
        this.project = currentProject;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public boolean validateProject() {
        return project != null && project.getId() > 0
                && project.getTitle() != null && !project.getTitle().isEmpty()
                && project.getDescription() != null && !project.getDescription().isEmpty()
                && project.getImage() != null && !project.getImage().isEmpty()
                && project.getTechnologies() != null && !project.getTechnologies().isEmpty();
    }

    public String saveProject() {
        if (validateProject()) {
            dbCreator.saveProject(project);
            return "project?faces-redirect=true&p=" + projectId;  //redirecting to the page where the informations will be displayed
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please fill the required fields."));
            return "editProject?faces-redirect=true&p=" + projectId;
        }
    }
}
