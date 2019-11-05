package i.kozar.behance_mvp.data.model.user;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class User {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "username")
    @SerializedName("username")
    private String username;

    @ColumnInfo(name = "location")
    @SerializedName("location")
    private String location;

    @ColumnInfo(name = "created_on")
    @SerializedName("created_on")
    private long createdOn;

    @SerializedName("images")
    @Ignore
    private Image image;

    @ColumnInfo(name = "display_name")
    @SerializedName("display_name")
    private String displayName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(@NonNull Image image) {
        this.image = image;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@NonNull String displayName) {
        this.displayName = displayName;
    }
}
