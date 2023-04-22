package com.example.taskapplication.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// adding annotation for our database entities and db version.
@Database(entities = {Users.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    // below line is to create instance
    // for our database class.
    private static UserDatabase instance;

    // below line is to create
    // abstract variable for dao.
    public abstract UserDao getDao();

    // on below line we are getting instance for our database.
    public static UserDatabase getInstance(Context context) {
        // below line is to check if
        // the instance is null or not.
        if (instance == null) {
            // if the instance is null we
            // are creating a new instance
            instance =
                    // for creating a instance for our database
                    // we are creating a database builder and passing
                    // our database class with our database name.
                    Room.databaseBuilder(context,
                                    UserDatabase.class, "user_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        // after creating an instance
        // we are returning our instance
        return instance;
    }
}