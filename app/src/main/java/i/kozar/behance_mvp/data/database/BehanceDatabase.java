package i.kozar.behance_mvp.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import i.kozar.behance_mvp.data.model.project.Cover;
import i.kozar.behance_mvp.data.model.project.Owner;
import i.kozar.behance_mvp.data.model.project.Project;
import i.kozar.behance_mvp.data.model.user.Image;
import i.kozar.behance_mvp.data.model.user.User;

@Database(entities = {Project.class, Cover.class, Owner.class, User.class, Image.class}, version = 1)
public abstract class BehanceDatabase extends RoomDatabase {

    public abstract BehanceDao getBehanceDao();
}
