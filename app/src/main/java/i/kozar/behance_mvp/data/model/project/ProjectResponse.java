package i.kozar.behance_mvp.data.model.project;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ProjectResponse implements Serializable {

    @SerializedName("projects")
    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
