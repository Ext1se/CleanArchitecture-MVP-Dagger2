package com.elegion.test.behancer.di.module;

import com.ponomarevigor.domain.service.ProjectService;
import com.ponomarevigor.domain.service.ProjectServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Singleton
    @Provides
    ProjectService provideProjectService(ProjectServiceImpl projectService) {
        return projectService;
    }
}
