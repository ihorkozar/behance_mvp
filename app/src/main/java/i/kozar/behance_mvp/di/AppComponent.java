package i.kozar.behance_mvp.di;

import javax.inject.Singleton;
import dagger.Component;
import i.kozar.behance_mvp.ui.profile.ProfileFragment;
import i.kozar.behance_mvp.ui.projects.ProjectsFragment;
import i.kozar.behance_mvp.ui.projects.user_projects.UserProjectsFragment;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void injectProjects(ProjectsFragment injector);
    void injectProfile(ProfileFragment injector);
    void injectUserProjects(UserProjectsFragment injector);
}
