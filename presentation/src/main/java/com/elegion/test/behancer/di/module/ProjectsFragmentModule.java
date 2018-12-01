package com.elegion.test.behancer.di.module;

import com.elegion.test.behancer.ui.projects.ProjectsFragment;

import dagger.Module;

@Module
public class ProjectsFragmentModule extends FragmentViewModule {

    public ProjectsFragmentModule(ProjectsFragment fragment) {
        super(fragment);
    }
}
