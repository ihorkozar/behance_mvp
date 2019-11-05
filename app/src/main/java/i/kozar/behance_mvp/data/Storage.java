package i.kozar.behance_mvp.data;


import androidx.core.util.Pair;
import java.util.ArrayList;
import java.util.List;
import i.kozar.behance_mvp.data.database.BehanceDao;
import i.kozar.behance_mvp.data.model.project.Cover;
import i.kozar.behance_mvp.data.model.project.Owner;
import i.kozar.behance_mvp.data.model.project.Project;
import i.kozar.behance_mvp.data.model.project.ProjectResponse;
import i.kozar.behance_mvp.data.model.user.Image;
import i.kozar.behance_mvp.data.model.user.User;
import i.kozar.behance_mvp.data.model.user.UserResponse;

public class Storage {

    private BehanceDao behanceDao;

    public Storage(BehanceDao behanceDao) {
        this.behanceDao = behanceDao;
    }

    public void insertProjects(ProjectResponse response) {
        List<Project> projects = response.getProjects();
        behanceDao.insertProjects(projects);

        Pair<List<Cover>, List<Owner>> assembled = assemble(projects);

        behanceDao.clearCoverTable();
        behanceDao.insertCovers(assembled.first);
        behanceDao.clearOwnerTable();
        behanceDao.insertOwners(assembled.second);
    }



    private Pair<List<Cover>, List<Owner>> assemble(List<Project> projects) {

        List<Cover> covers = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            Cover cover = projects.get(i).getCover();
            cover.setId(i);
            cover.setProjectId(projects.get(i).getId());
            covers.add(cover);

            Owner owner = projects.get(i).getOwners().get(0);
            owner.setId(i);
            owner.setProjectId(projects.get(i).getId());
            owners.add(owner);
        }
        return new Pair<>(covers, owners);
    }

    public ProjectResponse getProjects() {
        List<Project> projects = behanceDao.getProjects();
        for (Project project : projects) {
            project.setCover(behanceDao.getCoverFromProject(project.getId()));
            project.setOwners(behanceDao.getOwnersFromProject(project.getId()));
        }

        ProjectResponse response = new ProjectResponse();
        response.setProjects(projects);

        return response;
    }

    public void insertUser(UserResponse response) {
        User user = response.getUser();
        Image image = user.getImage();
        image.setId(user.getId());
        image.setUserId(user.getId());

        behanceDao.insertUser(user);
        behanceDao.insertImage(image);
    }

    public UserResponse getUser(String username) {
        User user = behanceDao.getUserByName(username);
        Image image = behanceDao.getImageFromUser(user.getId());
        user.setImage(image);

        UserResponse response = new UserResponse();
        response.setUser(user);

        return response;
    }

    public interface StorageOwner {
        Storage obtainStorage();
    }

}
