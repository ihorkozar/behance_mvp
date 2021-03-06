package i.kozar.behance_mvp.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import i.kozar.behance_mvp.AppDelegate;
import i.kozar.behance_mvp.R;
import i.kozar.behance_mvp.common.PresenterFragment;
import i.kozar.behance_mvp.common.RefreshOwner;
import i.kozar.behance_mvp.common.Refreshable;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.model.user.User;
import i.kozar.behance_mvp.ui.projects.user_projects.UserProjectsActivity;
import i.kozar.behance_mvp.ui.projects.user_projects.UserProjectsFragment;
import i.kozar.behance_mvp.utils.DateUtils;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class ProfileFragment extends PresenterFragment
        implements Refreshable, ProfileView {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    private RefreshOwner refreshOwner;
    private View errorView, profileView;
    private String username;
    private Button button;
    private ImageView profileImage;
    private TextView profileName, profileCreatedOn, profileLocation;

    @InjectPresenter
    ProfilePresenter profilePresenter;

    @Override
    protected ProfilePresenter getPresenter() {
        return profilePresenter;
    }

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            refreshOwner = ((RefreshOwner) context);
        }
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDelegate.getAppComponent().injectProfile(this);
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        errorView = view.findViewById(R.id.errorView);
        profileView = view.findViewById(R.id.view_profile);
        button = view.findViewById(R.id.btn_profile);
        button.setOnClickListener(v -> profilePresenter.openUserProjectsFragment(username));

        profileImage = view.findViewById(R.id.iv_profile);
        profileName = view.findViewById(R.id.tv_display_name_details);
        profileCreatedOn = view.findViewById(R.id.tv_created_on_details);
        profileLocation = view.findViewById(R.id.tv_location_details);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            username = getArguments().getString(PROFILE_KEY);
        }

        if (getActivity() != null) {
            getActivity().setTitle(username);
        }

        //profilePresenter.setView(this);
        profileView.setVisibility(View.VISIBLE);

        onRefreshData();
    }

    @Override
    public void onRefreshData() {
        profilePresenter.getProfile(username);
    }

    @Override
    public void onDetach() {
        refreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showLoading() {
        refreshOwner.setRefreshState(true);
    }

    @Override
    public void hideLoading() {
        refreshOwner.setRefreshState(false);
    }

    @Override
    public void showError() {
        errorView.setVisibility(View.VISIBLE);
        profileView.setVisibility(View.GONE);
    }

    @Override
    public void showProfile(User user) {
        errorView.setVisibility(View.GONE);
        profileView.setVisibility(View.VISIBLE);
        Picasso.with(getContext())
                .load(user.getImage().getPhotoUrl())
                .fit()
                .into(profileImage);
        profileName.setText(user.getDisplayName());
        profileCreatedOn.setText(DateUtils.format(user.getCreatedOn()));
        profileLocation.setText(user.getLocation());
    }

    @Override
    public void openUserProjectsFragment(String username) {
        Intent intent = new Intent(getActivity(), UserProjectsActivity.class);
        Bundle args = new Bundle();
        args.putString(UserProjectsFragment.UserProjects_KEY, username);
        intent.putExtra(UserProjectsActivity.USERNAME_KEY, args);
        startActivity(intent);
    }
}
