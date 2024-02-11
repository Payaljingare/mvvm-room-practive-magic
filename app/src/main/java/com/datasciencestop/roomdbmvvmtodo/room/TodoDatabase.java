package com.datasciencestop.roomdbmvvmtodo.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {
    private static volatile TodoDatabase INSTANCE;

    public abstract TodoDao todoDao();

    public static synchronized TodoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoDatabase.class, "todo_database")
                    .build();
        }
        return INSTANCE;
    }
}
