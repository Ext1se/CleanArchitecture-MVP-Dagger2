package com.elegion.test.behancer.ui.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.common.PresenterFragment;
import com.ponomarevigor.data.model.project.Project;
import com.elegion.test.behancer.di.module.ProjectsFragmentModule;
import com.elegion.test.behancer.ui.profile.ProfileActivity;
import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.common.Refreshable;

import java.util.List;

import javax.inject.Inject;

public class ProjectsFragment extends PresenterFragment
        implements ProjectsView, Refreshable, ProjectsAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private View mErrorView;

    @Inject
    RefreshOwner mRefreshOwner;
    @Inject
    ProjectsAdapter mProjectsAdapter;
    @InjectPresenter
    ProjectsPresenter mPresenter;

    @ProvidePresenter
    ProjectsPresenter providePresenter() {
        return new ProjectsPresenter();
    }

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    protected void injectDependencies() {
        AppDelegate
                .getAppComponent()
                .plusProjectsFragment(new ProjectsFragmentModule(this))
                .inject(this);
    }

    @Inject
    @Override
    public void setDependencies() {
        mProjectsAdapter.setOnItemClickListener(this);
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

        if (getActivity() != null) {
            getActivity().setTitle(R.string.projects);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mProjectsAdapter);
    }

    @Override
    public void onItemClick(String username) {
        mPresenter.openProfileFragment(username);
    }

    @Override
    protected ProjectsPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDetach() {
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void onRefreshData() {
        mPresenter.getProjects();
    }

    @Override
    public void showProjects(List<Project> projects) {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProjectsAdapter.addData(projects, true);
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
    public void showRefresh() {
        if (mRefreshOwner != null) {
            mRefreshOwner.setRefreshState(true);
        }
    }

    @Override
    public void hideRefresh() {
        if (mRefreshOwner != null) {
            mRefreshOwner.setRefreshState(false);
        }
    }

    @Override
    public void showMessage(boolean networkConnection) {
        if (!networkConnection) {
            Toast.makeText(getContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }
}
