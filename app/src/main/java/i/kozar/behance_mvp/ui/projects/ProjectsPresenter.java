package i.kozar.behance_mvp.ui.projects;

import javax.inject.Inject;
import i.kozar.behance_mvp.BuildConfig;
import i.kozar.behance_mvp.common.BasePresenter;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.api.BehanceApi;
import i.kozar.behance_mvp.di.AppComponent;
import i.kozar.behance_mvp.utils.ApiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {
    //private ProjectsView projectsView;
    @Inject
    Storage storage;
    @Inject
    BehanceApi behanceApi;

    /*@Inject
    public ProjectsPresenter() {
    }*/

    /*public void setView(ProjectsView view){
        projectsView = view;
    }*/

    public void getProjects() {
        compositeDisposable.add(
                behanceApi.getProjects(BuildConfig.API_QUERY)
                .doOnSuccess(response -> storage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? storage.getProjects() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().hideLoading())
                .subscribe(
                        response -> getViewState().showProjects(response.getProjects()),
                        throwable -> getViewState().showError()));
    }

    public void openProfileFragment(String username) {
        getViewState().openProfileFragment(username);
    }

    @Override
    protected void inject(AppComponent component) {
        component.inject(this);
    }
}
