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

import javax.inject.Inject;

import i.kozar.behance_mvp.AppDelegate;
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

    private RecyclerView recyclerView;
    private RefreshOwner refreshOwner;
    private View errorView;
    private ProjectsAdapter projectsAdapter;
    private String username;

    @InjectPresenter
    UserProjectsPresenter userProjectsPresenter;

    public static UserProjectsFragment newInstance(Bundle args) {
        UserProjectsFragment userProjectsFragment = new UserProjectsFragment();
        userProjectsFragment.setArguments(args);
        return userProjectsFragment;
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
        AppDelegate.getAppComponent().injectUserProjects(this);
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler);
        errorView = view.findViewById(R.id.errorView);
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
        //userProjectsPresenter.setView(this);
        projectsAdapter = new ProjectsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(projectsAdapter);

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
        refreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showUserProjects(List<Project> projects) {
        errorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        projectsAdapter.addData(projects, true);
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
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(String username) {
    }
}