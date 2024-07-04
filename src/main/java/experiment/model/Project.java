package experiment.model;

public class Project {
    private int id;
    private String title;
    private String image;
    private String description;
    private String myPart;
    private String learned;
    private String technologies;

    public Project(int id, String title, String image, String description, String myPart, String learned, String technologies) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.myPart = myPart;
        this.learned = learned;
        this.technologies = technologies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMyPart() {
        return myPart;
    }

    public void setMyPart(String myPart) {
        this.myPart = myPart;
    }

    public String getLearned() {
        return learned;
    }

    public void setLearned(String learned) {
        this.learned = learned;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getTechnologies() {
        return this.technologies;
    }

}
