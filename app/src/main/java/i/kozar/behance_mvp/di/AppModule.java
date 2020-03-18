package i.kozar.behance_mvp.di;

import androidx.room.Room;
import i.kozar.behance_mvp.AppDelegate;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.database.BehanceDatabase;
import toothpick.config.Module;

public class AppModule extends Module {

    private final AppDelegate appDelegate;

    public AppModule(AppDelegate app) {
        this.appDelegate = app;
        bind(AppDelegate.class).toInstance(appDelegate);
        bind(Storage.class).toInstance(provideStorage());
    }

    AppDelegate provideAppDelegate(){
        return appDelegate;
    }

    Storage provideStorage(){
        final BehanceDatabase database = Room.databaseBuilder(appDelegate, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        return new Storage(database.getBehanceDao());
    }
}
