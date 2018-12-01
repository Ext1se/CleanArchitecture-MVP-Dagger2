package com.ponomarevigor.data.repository;

import com.ponomarevigor.data.BuildConfig;
import com.ponomarevigor.data.api.BehanceApi;
import com.ponomarevigor.domain.model.project.Project;
import com.ponomarevigor.domain.model.project.ProjectResponse;
import com.ponomarevigor.domain.repository.ProjectRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class ProjectServerRepository implements ProjectRepository {

    @Inject
    BehanceApi mApi;

    @Inject
    public ProjectServerRepository() {
    }

    @Override
    public Single<List<Project>> getProject() {
        return mApi.getProjects(BuildConfig.API_QUERY).map(new Function<ProjectResponse, List<Project>>() {
            @Override
            public List<Project> apply(ProjectResponse projectResponse) throws Exception {
                return projectResponse.getProjects();
            }
        });
    }

    @Override
    public void insertProject(List<Project> projects) {
        //
    }
}
