package com.naseem.naseemashraf.memento2.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class AddAllTasksAsync extends AsyncTask<Void, Void, Void> {

    private List<Task> taskListdb;
    private TasksSQLDatabase db;

    public AddAllTasksAsync(Context inContext,List<Task> inTaskList) {
        this.taskListdb = inTaskList;
        db = TasksSQLDatabase.getInstance(inContext);

        //logSavedTaskList();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db.taskDao().insertAll(taskListdb);
        return null;
    }

    public void logSavedTaskList(){
        Log.e("DB Task List", String.valueOf(this.taskListdb.size()));

        for(int i=0; i<this.taskListdb.size(); i++){
            Log.e("DB Task Item", this.taskListdb.get(i).toString());
        }
    }
}