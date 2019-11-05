package i.kozar.behance_mvp.common;

import moxy.MvpView;

public interface BaseView extends MvpView {
    void showLoading();
    void hideLoading();
    void showError();
}
