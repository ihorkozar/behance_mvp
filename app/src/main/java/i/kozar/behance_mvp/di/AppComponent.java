package i.kozar.behance_mvp.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;
import dagger.Component;
import i.kozar.behance_mvp.ui.profile.ProfileFragment;
import i.kozar.behance_mvp.ui.profile.ProfilePresenter;
import i.kozar.behance_mvp.ui.projects.ProjectsFragment;
import i.kozar.behance_mvp.ui.projects.ProjectsPresenter;
import i.kozar.behance_mvp.ui.projects.user_projects.UserProjectsFragment;
import i.kozar.behance_mvp.ui.projects.user_projects.UserProjectsPresenter;


@CustomScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    /*void injectProjects(ProjectsFragment injector);
    void injectProfile(ProfileFragment injector);
    void injectUserProjects(UserProjectsFragment injector);*/

    void inject(ProfilePresenter profilePresenter);
    void inject(ProjectsPresenter projectsPresenter);
    void inject(UserProjectsPresenter userProjectsPresenter);
}
