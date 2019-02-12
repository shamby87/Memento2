package com.naseem.naseemashraf.memento2.db;

import android.content.Context;
import android.os.AsyncTask;

public class DeleteTasksAsync extends AsyncTask<Void, Void, Void> {

    private Context context;
    private TasksSQLDatabase db;

    public DeleteTasksAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db = TasksSQLDatabase.getInstance(context);
        db.taskDao().deleteAll();

        return null;
    }
}