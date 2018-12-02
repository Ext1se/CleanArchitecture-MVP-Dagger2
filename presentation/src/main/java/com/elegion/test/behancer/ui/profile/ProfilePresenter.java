package com.elegion.test.behancer.ui.profile;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.common.BasePresenter;
import com.ponomarevigor.data.api.BehanceApi;
import com.ponomarevigor.domain.ApiUtils;
import com.ponomarevigor.domain.service.ProfileService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {

    @Inject
    ProfileService mService;

    public ProfilePresenter() {
        AppDelegate.getAppComponent().inject(this);
    }

    public void getProfile(String username) {
       mCompositeDisposable.add(
               mService.getUser(username)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(() -> getViewState().hideRefresh())
                .subscribe(
                        response -> getViewState().showProfile(response),
                        throwable -> getViewState().showError()));
    }
}
