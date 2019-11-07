package i.kozar.behance_mvp.ui.projects;

import android.content.Context;
import android.content.Intent;
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
import i.kozar.behance_mvp.common.PresenterFragment;
import i.kozar.behance_mvp.common.RefreshOwner;
import i.kozar.behance_mvp.common.Refreshable;
import i.kozar.behance_mvp.data.Storage;
import i.kozar.behance_mvp.data.model.project.Project;
import i.kozar.behance_mvp.ui.profile.ProfileActivity;
import i.kozar.behance_mvp.ui.profile.ProfileFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class ProjectsFragment extends PresenterFragment
        implements ProjectsView, Refreshable, ProjectsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RefreshOwner refreshOwner;
    private View errorView;
    private Storage storage;
    private ProjectsAdapter projectsAdapter;
    @InjectPresenter
    ProjectsPresenter projectsPresenter;

    @ProvidePresenter
    ProjectsPresenter providePresenter(){
        return new ProjectsPresenter(storage);
    }

    @Override
    protected ProjectsPresenter getPresenter() {
        return projectsPresenter;
    }


    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            storage = ((Storage.StorageOwner) context).obtainStorage();
        }

        if (context instanceof RefreshOwner) {
            refreshOwner = ((RefreshOwner) context);
        }
    }

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

        if (getActivity() != null) {
            getActivity().setTitle(R.string.projects);
        }

        projectsAdapter = new ProjectsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(projectsAdapter);

        onRefreshData();
    }

    @Override
    public void onItemClick(String username) {
        projectsPresenter.openProfileFragment(username);
    }

    @Override
    public void onDetach() {
        storage = null;
        refreshOwner = null;
        super.onDetach();
    }

    @Override
    public void onRefreshData() {
        projectsPresenter.getProjects();
    }

    @Override
    public void showProjects(List<Project> projects) {
        errorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        projectsAdapter.addData(projects, true);

    }

    @Override
    public void openProfileFragment(String username) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        Bundle args = new Bundle();
        args.putString(ProfileFragment.PROFILE_KEY, username);
        intent.putExtra(ProfileActivity.USERNAME_KEY, args);
        startActivity(intent);
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
}