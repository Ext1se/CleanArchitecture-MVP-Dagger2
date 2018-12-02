package com.ponomarevigor.domain.repository;

import com.ponomarevigor.domain.model.project.Project;

import java.util.List;

import io.reactivex.Single;

public interface ProjectRepository {

    String PROJECT_SERVER = "PROJECT_SERVER";
    String PROJECT_DB = "PROJECT_DB";

    Single<List<Project>> getProjects();

    void insertProjects(List<Project> projects);
}
