package com.ponomarevigor.domain.repository;

import com.ponomarevigor.domain.model.user.User;

import io.reactivex.Single;

public interface ProfileRepository {

    public static final String PROFILE_SERVER = "PROFILE_SERVER";
    public static final String PROFILE_DB = "PROFILE_DB";

    public Single<User> getUser(String username);

    public void insertUser(User user);
}
