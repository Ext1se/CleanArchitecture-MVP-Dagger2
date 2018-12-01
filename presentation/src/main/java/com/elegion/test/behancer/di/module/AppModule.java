package com.elegion.test.behancer.di.module;

import android.arch.persistence.room.Room;

import com.elegion.test.behancer.AppDelegate;
import com.ponomarevigor.data.Storage;
import com.ponomarevigor.data.database.BehanceDao;
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
    BehanceDatabase provideBehanceDatabase() {
        return Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    BehanceDao provideBehanceDao(BehanceDatabase database) {
        return database.getBehanceDao();
    }

    @Provides
    @Singleton
    Storage provideStorage(BehanceDao behanceDao) {
        return new Storage(behanceDao);
    }
}
