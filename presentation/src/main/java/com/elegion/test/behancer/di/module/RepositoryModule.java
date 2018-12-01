package com.elegion.test.behancer.di.module;

import com.ponomarevigor.data.repository.ProfileDatabaseRepository;
import com.ponomarevigor.data.repository.ProfileServerRepository;
import com.ponomarevigor.data.repository.ProjectDatabaseRepository;
import com.ponomarevigor.data.repository.ProjectServerRepository;
import com.ponomarevigor.domain.repository.ProfileRepository;
import com.ponomarevigor.domain.repository.ProjectRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    @Named(ProjectRepository.PROJECT_SERVER)
    ProjectRepository provideProjectServerRepository() {
        return new ProjectServerRepository();
    }

    @Singleton
    @Provides
    @Named(ProjectRepository.PROJECT_DB)
    ProjectRepository provideProjectDatabaseRepository() {
        return new ProjectDatabaseRepository();
    }

    @Singleton
    @Provides
    @Named(ProfileRepository.PROFILE_SERVER)
    ProfileRepository provideProfileServerRepository() {
        return new ProfileServerRepository();
    }

    @Singleton
    @Provides
    @Named(ProfileRepository.PROFILE_DB)
    ProfileRepository provideProfileDatabaseRepository() {
        return new ProfileDatabaseRepository();
    }

}
