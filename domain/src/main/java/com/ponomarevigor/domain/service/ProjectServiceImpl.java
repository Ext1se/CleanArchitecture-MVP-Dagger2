package com.ponomarevigor.domain.service;

import com.ponomarevigor.domain.ApiUtils;
import com.ponomarevigor.domain.model.project.Project;
import com.ponomarevigor.domain.repository.ProjectRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ProjectServiceImpl implements ProjectService {

    @Inject
    @Named(ProjectRepository.PROJECT_SERVER)
    ProjectRepository mServerRepository;

    @Inject
    @Named(ProjectRepository.PROJECT_DB)
    ProjectRepository mDatabaseRepository;

    @Inject
    public ProjectServiceImpl() {
    }

    @Override
    public Single<List<Project>> getProject() {
        return mServerRepository.getProjects()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mDatabaseRepository.insertProjects(response))
                .onErrorReturn(throwable ->
                {
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                        return mDatabaseRepository.getProjects().blockingGet();
                    }
                    return null;
                });
    }

    @Override
    public void insertProject(List<Project> projects) {
        mDatabaseRepository.insertProjects(projects);
    }
}
