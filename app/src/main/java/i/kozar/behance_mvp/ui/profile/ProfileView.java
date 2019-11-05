package i.kozar.behance_mvp.ui.profile;

import i.kozar.behance_mvp.common.BaseView;
import i.kozar.behance_mvp.data.model.user.User;

public interface ProfileView extends BaseView {
    void showProfile(User user);

    void openUserProjectsFragment(String username);
}
