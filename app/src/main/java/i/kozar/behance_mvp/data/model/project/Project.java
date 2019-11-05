package i.kozar.behance_mvp.data.model.project;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class Project implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "published_on")
    @SerializedName("published_on")
    private long publishedOn;

    @SerializedName("covers")
    @Ignore
    private Cover cover;

    @SerializedName("owners")
    @Ignore
    private List<Owner> owners;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public long getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(long publishedOn) {
        this.publishedOn = publishedOn;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(@NonNull Cover cover) {
        this.cover = cover;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(@NonNull List<Owner> owners) {
        this.owners = owners;
    }
}
