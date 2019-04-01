package com.naseem.naseemashraf.memento2.db;

import android.content.Context;
import android.os.AsyncTask;

public class DeleteTasksAsync extends AsyncTask<Void, Void, Void> {

    private Context context;

    public DeleteTasksAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        TasksSQLDatabase db = TasksSQLDatabase.getInstance(context);
        db.taskDao().deleteAll();

        return null;
    }
}