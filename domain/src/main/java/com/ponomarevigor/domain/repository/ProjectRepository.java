package com.ponomarevigor.domain.repository;

import com.ponomarevigor.domain.model.project.Project;

import java.util.List;

import io.reactivex.Single;

public interface ProjectRepository {

    public static final String PROJECT_SERVER = "PROJECT_SERVER";
    public static final String PROJECT_DB = "PROJECT_DB";

    Single<List<Project>> getProject();

    void insertProject(List<Project> projects);
}
