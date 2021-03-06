package i.kozar.behance_mvp.ui.profile;

import javax.inject.Inject;
import i.kozar.behance_mvp.common.BasePresenter;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.api.BehanceApi;
import i.kozar.behance_mvp.di.AppComponent;
import i.kozar.behance_mvp.ui.projects.ProjectsView;
import i.kozar.behance_mvp.utils.ApiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {

    //private ProfileView profileView;
    @Inject
    Storage storage;
    @Inject
    BehanceApi behanceApi;

    /*@Inject
    public ProfilePresenter() {
    }

    public void setView(ProfileView view){
        profileView = view;
    }*/

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
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().hideLoading())
                .subscribe(
                        response -> getViewState().showProfile(response.getUser()),
                        throwable -> getViewState().showError()));
    }

    public void openUserProjectsFragment(String username) {
        getViewState().openUserProjectsFragment(username);
    }

    @Override
    protected void inject(AppComponent component) {
        component.inject(this);
    }
}
