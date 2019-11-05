package i.kozar.behance_mvp.ui.projects.user_projects;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import i.kozar.behance_mvp.R;
import i.kozar.behance_mvp.common.BasePresenter;
import i.kozar.behance_mvp.common.PresenterFragment;
import i.kozar.behance_mvp.common.RefreshOwner;
import i.kozar.behance_mvp.common.Refreshable;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.model.project.Project;
import i.kozar.behance_mvp.ui.projects.ProjectsAdapter;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class UserProjectsFragment extends PresenterFragment
        implements UserProjectsView, Refreshable, ProjectsAdapter.OnItemClickListener {

    public static final String UserProjects_KEY = "UserProjects_KEY";

    private RecyclerView mRecyclerView;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private Storage mStorage;
    private ProjectsAdapter mProjectsAdapter;
    private String username;
    @InjectPresenter
    UserProjectsPresenter userProjectsPresenter;

    @ProvidePresenter
    UserProjectsPresenter providePresenter(){
        return new UserProjectsPresenter(this, mStorage);
    }

    public static UserProjectsFragment newInstance(Bundle args) {
        UserProjectsFragment userProjectsFragment = new UserProjectsFragment();
        userProjectsFragment.setArguments(args);
        return userProjectsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }

        if (context instanceof RefreshOwner) {
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            username = getArguments().getString(UserProjects_KEY);
        }

        if (getActivity() != null) {
            getActivity().setTitle(R.string.projects + username);
        }
        mProjectsAdapter = new ProjectsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mProjectsAdapter);

        onRefreshData();
    }

    @Override
    protected BasePresenter getPresenter() {
        return userProjectsPresenter;
    }

    @Override
    public void onRefreshData() {
        userProjectsPresenter.getUserProjects(username);
    }

    @Override
    public void onDetach() {
        mStorage = null;
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showUserProjects(List<Project> projects) {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProjectsAdapter.addData(projects, true);
    }

    @Override
    public void showLoading() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideLoading() {
        mRefreshOwner.setRefreshState(false);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(String username) {
    }
}
