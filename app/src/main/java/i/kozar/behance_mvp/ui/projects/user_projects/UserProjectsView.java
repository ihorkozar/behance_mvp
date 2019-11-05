package i.kozar.behance_mvp.ui.projects.user_projects;


import java.util.List;
import i.kozar.behance_mvp.common.BaseView;
import i.kozar.behance_mvp.data.model.project.Project;

public interface UserProjectsView extends BaseView {
    void showUserProjects(List<Project> projects);
}
