package com.ponomarevigor.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ponomarevigor.data.model.project.Cover;
import com.ponomarevigor.data.model.project.Owner;
import com.ponomarevigor.data.model.project.Project;
import com.ponomarevigor.data.model.user.Image;
import com.ponomarevigor.data.model.user.User;

@Database(entities = {Project.class, Cover.class, Owner.class, User.class, Image.class}, version = 1)
public abstract class BehanceDatabase extends RoomDatabase {

    public abstract BehanceDao getBehanceDao();
}
