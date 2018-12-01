package com.elegion.test.behancer.di.module;

import android.arch.persistence.room.Room;

import com.elegion.test.behancer.AppDelegate;
import com.ponomarevigor.data.Storage;
import com.ponomarevigor.data.database.BehanceDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final AppDelegate mApp;

    public AppModule(AppDelegate app) {
        mApp = app;
    }

    @Provides
    @Singleton
    AppDelegate provideApp() {
        return mApp;
    }

    @Provides
    @Singleton
    Storage provideStorage() {
        BehanceDatabase database = Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        return new Storage(database.getBehanceDao());
    }
}
