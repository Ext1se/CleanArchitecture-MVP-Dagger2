package com.ponomarevigor.domain.service;

import com.ponomarevigor.domain.ApiUtils;
import com.ponomarevigor.domain.model.user.User;
import com.ponomarevigor.domain.repository.ProfileRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ProfileServiceImpl implements ProfileService {

    @Inject
    @Named(ProfileRepository.PROFILE_SERVER)
    ProfileRepository mServerRepository;

    @Inject
    @Named(ProfileRepository.PROFILE_DB)
    ProfileRepository mDatabaseRepository;

    @Inject
    public ProfileServiceImpl() {
    }

    @Override
    public Single<User> getUser(String username) {
        return mServerRepository.getUser(username)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(user -> mDatabaseRepository.insertUser(user))
                .onErrorReturn(throwable ->
                {
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                        return mDatabaseRepository.getUser(username).blockingGet();
                    }
                    return null;
                });
    }

    @Override
    public void insertUser(User user) {
        mDatabaseRepository.insertUser(user);
    }
}
