package i.kozar.behance_mvp.ui.profile;

import javax.inject.Inject;
import i.kozar.behance_mvp.common.BasePresenter;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.api.BehanceApi;
import i.kozar.behance_mvp.ui.projects.ProjectsView;
import i.kozar.behance_mvp.utils.ApiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;


public class ProfilePresenter extends BasePresenter {

    private ProfileView profileView;
    @Inject
    Storage storage;
    @Inject
    BehanceApi behanceApi;

    @Inject
    public ProfilePresenter() {
    }

    public void setView(ProfileView view){
        profileView = view;
    }

    public void getProfile(String username){
        compositeDisposable.add(
                behanceApi.getUserInfo(username)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> storage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                storage.getUser(username) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> profileView.showLoading())
                .doFinally(() -> profileView.hideLoading())
                .subscribe(
                        response -> profileView.showProfile(response.getUser()),
                        throwable -> profileView.showError()));
    }

    public void openUserProjectsFragment(String username) {
        profileView.openUserProjectsFragment(username);
    }
}
