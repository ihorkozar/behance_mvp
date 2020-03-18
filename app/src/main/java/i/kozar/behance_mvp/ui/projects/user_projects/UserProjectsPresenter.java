package i.kozar.behance_mvp.ui.projects.user_projects;


import javax.inject.Inject;

import i.kozar.behance_mvp.common.BasePresenter;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.api.BehanceApi;
import i.kozar.behance_mvp.utils.ApiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class UserProjectsPresenter extends BasePresenter<UserProjectsView> {
    //private UserProjectsView userProjectsView;
    @Inject
    Storage storage;
    @Inject
    BehanceApi behanceApi;

    /*@Inject
    public UserProjectsPresenter() {
    }

    public void setView(UserProjectsView view){
        userProjectsView = view;
    }*/

    public void getUserProjects(String username){
        compositeDisposable.add(behanceApi
                .getUserProjects(username)
                .doOnSuccess(response -> storage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? storage.getProjects() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().hideLoading())
                .subscribe(
                        response -> getViewState().showUserProjects(response.getProjects()),
                        throwable -> getViewState().showError()));
    }
}