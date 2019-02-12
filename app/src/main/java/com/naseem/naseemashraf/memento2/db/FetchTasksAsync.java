package com.naseem.naseemashraf.memento2.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class FetchTasksAsync extends AsyncTask<Void, Void, List<Task>> {

    private Context context;
    private List<Task> taskListdb;
    private TasksSQLDatabase db;

    public FetchTasksAsync(Context context) {
        this.context = context;
    }

    @Override
    protected List<Task> doInBackground(Void... params) {
        db = TasksSQLDatabase.getInstance(context);
        taskListdb = db.taskDao().getAll();

        return taskListdb;
    }
}
