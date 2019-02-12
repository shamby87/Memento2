package com.naseem.naseemashraf.memento2.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TasksSQLDatabase extends RoomDatabase {


    private static TasksSQLDatabase tasksSQLDatabase;

    public static TasksSQLDatabase getInstance(Context context){

        if(tasksSQLDatabase==null){
            tasksSQLDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    TasksSQLDatabase.class, "tasks_table").build();
        }
        return tasksSQLDatabase;
    }

    public abstract TaskDAO taskDao();

}
