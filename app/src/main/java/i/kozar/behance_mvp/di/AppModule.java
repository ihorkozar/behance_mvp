package i.kozar.behance_mvp.di;

import androidx.room.Room;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import i.kozar.behance_mvp.AppDelegate;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.database.BehanceDatabase;

@Module
public class AppModule {

    private final AppDelegate appDelegate;

    public AppModule(AppDelegate appDelegate) {
        this.appDelegate = appDelegate;
    }

    @Provides
    @CustomScope
    AppDelegate provideAppDelegate(){
        return appDelegate;
    }

    @Provides
    @CustomScope
    Storage provideStorage(){
        final BehanceDatabase database = Room.databaseBuilder(appDelegate, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        return new Storage(database.getBehanceDao());
    }
}
