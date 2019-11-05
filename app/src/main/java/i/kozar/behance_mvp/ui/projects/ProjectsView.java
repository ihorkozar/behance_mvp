package i.kozar.behance_mvp.ui.projects;


import java.util.List;
import i.kozar.behance_mvp.common.BaseView;
import i.kozar.behance_mvp.data.model.project.Project;

public interface ProjectsView extends BaseView {

    void showProjects(List<Project> projects);

    void openProfileFragment(String username);
}
