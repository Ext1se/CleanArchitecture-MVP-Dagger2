package com.ponomarevigor.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ponomarevigor.domain.model.project.Cover;
import com.ponomarevigor.domain.model.project.Owner;
import com.ponomarevigor.domain.model.project.Project;
import com.ponomarevigor.domain.model.user.Image;
import com.ponomarevigor.domain.model.user.User;

@Database(entities = {Project.class, Cover.class, Owner.class, User.class, Image.class}, version = 1)
public abstract class BehanceDatabase extends RoomDatabase {

    public abstract BehanceDao getBehanceDao();
}
