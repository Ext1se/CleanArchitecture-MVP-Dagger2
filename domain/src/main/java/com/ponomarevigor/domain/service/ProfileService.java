package com.ponomarevigor.domain.service;

import com.ponomarevigor.domain.model.user.User;

import io.reactivex.Single;

public interface ProfileService {

    public Single<User> getUser(String username);
    public void insertUser(User user);
}
