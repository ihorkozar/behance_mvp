package i.kozar.behance_mvp.ui.projects.user_projects;


import i.kozar.behance_mvp.common.BasePresenter;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.utils.ApiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class UserProjectsPresenter extends BasePresenter<UserProjectsView> {

    private UserProjectsView userProjectsView;
    private Storage storage;

    public UserProjectsPresenter(UserProjectsView userProjectsView, Storage storage) {
        this.userProjectsView = userProjectsView;
        this.storage = storage;
    }

    public void getUserProjects(String username){
        compositeDisposable.add(ApiUtils.getApiService()
                .getUserProjects(username)
                .doOnSuccess(response -> storage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? storage.getProjects() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> userProjectsView.showLoading())
                .doFinally(() -> userProjectsView.hideLoading())
                .subscribe(
                        response -> userProjectsView.showUserProjects(response.getProjects()),
                        throwable -> userProjectsView.showError()));
    }
}