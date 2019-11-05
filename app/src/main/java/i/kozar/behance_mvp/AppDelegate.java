package i.kozar.behance_mvp;

import android.app.Application;
import androidx.room.Room;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.database.BehanceDatabase;

public class AppDelegate extends Application {

    private Storage storage;

    @Override
    public void onCreate() {
        super.onCreate();

        final BehanceDatabase database = Room.databaseBuilder(this, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        storage = new Storage(database.getBehanceDao());
    }

    public Storage getStorage() {
        return storage;
    }
}
