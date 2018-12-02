package com.ponomarevigor.domain.repository;

import com.ponomarevigor.domain.model.user.User;

import io.reactivex.Single;

public interface ProfileRepository {

    String PROFILE_SERVER = "PROFILE_SERVER";
    String PROFILE_DB = "PROFILE_DB";

    Single<User> getUser(String username);
    void insertUser(User user);
}
