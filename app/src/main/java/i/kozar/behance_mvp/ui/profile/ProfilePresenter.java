package i.kozar.behance_mvp.ui.profile;


import i.kozar.behance_mvp.common.BasePresenter;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.utils.ApiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {
    private ProfileView profileView;
    private Storage storage;

    public ProfilePresenter(ProfileView profileView, Storage storage) {
        this.profileView = profileView;
        this.storage = storage;
    }

    public void getProfile(String username){
        compositeDisposable.add(ApiUtils.getApiService().getUserInfo(username)
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
