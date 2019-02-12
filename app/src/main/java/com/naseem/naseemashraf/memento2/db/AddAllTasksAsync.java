package com.naseem.naseemashraf.memento2.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class AddAllTasksAsync extends AsyncTask<Void, Void, Void> {

    private Context context;
    private List<Task> taskListdb;
    private TasksSQLDatabase db;

    public AddAllTasksAsync(Context inContext,List<Task> inTaskList) {
        this.taskListdb = inTaskList;
        db = TasksSQLDatabase.getInstance(inContext);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db.taskDao().insertAll(taskListdb);
        return null;
    }
}
