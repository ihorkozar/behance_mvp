package i.kozar.behance_mvp.common;


import i.kozar.behance_mvp.AppDelegate;
import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpPresenter;

public abstract class BasePresenter<V extends BaseView> extends MvpPresenter<V> {

    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void disposeAll(){
        compositeDisposable.clear();
    }

    /*//Для даггера с мокси
    public BasePresenter() {
        AppComponent component = AppDelegate.getAppScope();
        inject(component);
    }

    protected abstract void inject(AppComponent component);*/

}
