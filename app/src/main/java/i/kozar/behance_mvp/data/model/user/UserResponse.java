package i.kozar.behance_mvp.data.model.user;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
