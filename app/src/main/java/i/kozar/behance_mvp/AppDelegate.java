package i.kozar.behance_mvp;

import android.app.Application;

import i.kozar.behance_mvp.di.AppModule;
import i.kozar.behance_mvp.di.NetworkModule;
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.smoothie.module.SmoothieApplicationModule;

public class AppDelegate extends Application {

    private static Scope appScope;

    @Override
    public void onCreate() {
        super.onCreate();

        appScope = Toothpick.openScope(AppDelegate.class);
        appScope.installModules(new SmoothieApplicationModule(this), new NetworkModule(), new AppModule(this));

    }

    public static Scope getAppScope(){
        return appScope;
    }
}
