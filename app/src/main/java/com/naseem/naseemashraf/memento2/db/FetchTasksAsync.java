package com.naseem.naseemashraf.memento2.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class FetchTasksAsync extends AsyncTask<Void, Void, List<Task>> {

    private Context context;

    public FetchTasksAsync(Context context) {
        this.context = context;
    }

    @Override
    protected List<Task> doInBackground(Void... params) {
        TasksSQLDatabase db = TasksSQLDatabase.getInstance(context);
        List<Task> taskListdb = db.taskDao().getAll();

        return taskListdb;
    }
}
