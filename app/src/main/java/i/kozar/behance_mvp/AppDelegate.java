package i.kozar.behance_mvp;

import android.app.Application;
import i.kozar.behance_mvp.di.AppComponent;
import i.kozar.behance_mvp.di.AppModule;
import i.kozar.behance_mvp.di.DaggerAppComponent;
import i.kozar.behance_mvp.di.NetworkModule;

public class AppDelegate extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }
}
