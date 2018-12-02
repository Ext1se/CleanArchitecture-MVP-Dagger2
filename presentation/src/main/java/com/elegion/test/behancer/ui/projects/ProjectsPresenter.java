package com.elegion.test.behancer.ui.projects;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.common.BasePresenter;
import com.ponomarevigor.data.BuildConfig;
import com.ponomarevigor.data.api.BehanceApi;
import com.ponomarevigor.domain.ApiUtils;
import com.ponomarevigor.domain.model.project.Project;
import com.ponomarevigor.domain.service.ProjectService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {

    @Inject
    ProjectService mService;

    public ProjectsPresenter() {
        AppDelegate.getAppComponent().inject(this);
        getProjects();
    }

    public void getProjects() {
        mCompositeDisposable.add(
                mService.getProject()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(() -> getViewState().hideRefresh())
                .subscribe(
                        response -> getViewState().showProjects(response),
                        throwable -> getViewState().showError()));
    }

    public void openProfileFragment(String username) {
        getViewState().openProfileFragment(username);
    }
}
