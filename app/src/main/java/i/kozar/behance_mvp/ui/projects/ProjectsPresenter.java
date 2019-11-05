package i.kozar.behance_mvp.ui.projects;



import i.kozar.behance_mvp.BuildConfig;
import i.kozar.behance_mvp.common.BasePresenter;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.utils.ApiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {
    private ProjectsView projectsView;
    private Storage storage;

    public ProjectsPresenter(ProjectsView projectsView, Storage storage) {
        this.projectsView = projectsView;
        this.storage = storage;
    }

    public void getProjects() {
        compositeDisposable.add( ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                .doOnSuccess(response -> storage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? storage.getProjects() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> projectsView.showLoading())
                .doFinally(() -> projectsView.hideLoading())
                .subscribe(
                        response -> projectsView.showProjects(response.getProjects()),
                        throwable -> projectsView.showError()));
    }

    public void openProfileFragment(String username) {
        projectsView.openProfileFragment(username);
    }
}
