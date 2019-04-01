package com.naseem.naseemashraf.memento2.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class InitTasksAsync extends AsyncTask<Void, Void, Void> {

    private List<Task> taskListdb;
    private Context context;

    public InitTasksAsync(Context inContext, List<Task> inTaskList) {
        this.taskListdb = inTaskList;
        this.context = inContext;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        TasksSQLDatabase db = TasksSQLDatabase.getInstance(context);

        if(db.taskDao().getAll().isEmpty()) {
            db.taskDao().insertAll(taskListdb);
        }

        return null;
    }
}
