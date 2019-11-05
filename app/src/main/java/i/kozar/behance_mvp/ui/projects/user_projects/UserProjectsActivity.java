package i.kozar.behance_mvp.ui.projects.user_projects;



import i.kozar.behance_mvp.common.SingleFragmentActivity;

public class UserProjectsActivity extends SingleFragmentActivity {
    public static final String USERNAME_KEY = "USERNAME_KEY";

    @Override
    protected UserProjectsFragment getFragment() {
        if (getIntent() != null) {
            return UserProjectsFragment.newInstance(getIntent().getBundleExtra(USERNAME_KEY));
        }
        throw new IllegalStateException("getIntent cannot be null");
    }
}
