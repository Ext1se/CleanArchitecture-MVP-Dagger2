package com.elegion.test.behancer.ui.projects;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.common.BasePresenter;
import com.ponomarevigor.data.BuildConfig;
import com.ponomarevigor.data.Storage;
import com.ponomarevigor.data.api.BehanceApi;
import com.elegion.test.behancer.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {

    @Inject
    Storage mStorage;
    @Inject
    BehanceApi mApi;

    public ProjectsPresenter() {
        AppDelegate.getAppComponent().inject(this);
        getProjects();
    }

    public void getProjects() {
        mCompositeDisposable.add(
                mApi.getProjects(BuildConfig.API_QUERY)
                .doOnSuccess(response -> {
                    mNetworkConnection = true;
                    mStorage.insertProjects(response);
                })
                .onErrorReturn(throwable ->
                {
                    mNetworkConnection = false;
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                        return mStorage.getProjects();
                    }
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(() -> {
                    getViewState().hideRefresh();
                    getViewState().showMessage(mNetworkConnection);
                })
                .subscribe(
                        response -> getViewState().showProjects(response.getProjects()),
                        throwable -> getViewState().showError()));
    }

    public void openProfileFragment(String username) {
        getViewState().openProfileFragment(username);
    }
}
