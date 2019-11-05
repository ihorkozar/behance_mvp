package i.kozar.behance_mvp.ui.projects;

import androidx.fragment.app.Fragment;
import i.kozar.behance_mvp.common.SingleFragmentActivity;

public class ProjectsActivity extends SingleFragmentActivity {

    public static final String USERNAME_KEY = "USERNAME_KEY";

    @Override
    protected Fragment getFragment() {
        return ProjectsFragment.newInstance();
    }
}
