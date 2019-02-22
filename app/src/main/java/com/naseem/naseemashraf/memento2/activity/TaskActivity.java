package com.naseem.naseemashraf.memento2.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.naseem.naseemashraf.memento2.R;
import com.naseem.naseemashraf.memento2.UI.OnTaskAddListener;
import com.naseem.naseemashraf.memento2.db.AddAllTasksAsync;
import com.naseem.naseemashraf.memento2.db.DeleteTasksAsync;
import com.naseem.naseemashraf.memento2.db.FetchTasksAsync;
import com.naseem.naseemashraf.memento2.db.InitTasksAsync;
import com.naseem.naseemashraf.memento2.db.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskActivity extends AppCompatActivity implements TasksAdapter.TasksAdapterCallback, OnTaskAddListener {

    private RecyclerView mRecyclerView;
    private TasksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CoordinatorLayout coordinatorLayout;
    public Context global_context;

    private List<Task> taskList;
    private List<Task> deletedTasks;

    private boolean draggedAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        global_context = getApplicationContext();
        deletedTasks = new ArrayList<>();
        setupDB();
        draggedAction = false;

        //RECYCLER
        mRecyclerView = findViewById(R.id.tasks_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //RECYCLER ADAPTER
        mAdapter = new TasksAdapter(taskList, TaskActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        //ItemTouchHelper
        new ItemTouchHelper(setupItemTouchHolder()).attachToRecyclerView(mRecyclerView);

        //FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(TaskActivity.this);

                final View sheetView = TaskActivity.this.getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                mBottomSheetDialog.setContentView(sheetView);

                final EditText inputTaskTitle = sheetView.findViewById(R.id.EditTextTitle);
                final EditText inputTaskContent = sheetView.findViewById(R.id.EditTextContent);
                final Button saveButton = sheetView.findViewById(R.id.ButtonSave);
                saveButton.setText("Save");
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String taskTitle = inputTaskTitle.getText().toString();
                        String taskContent = inputTaskContent.getText().toString();

                        onTaskAdded(taskTitle,taskContent);
                        if(mBottomSheetDialog.isShowing()){
                            mBottomSheetDialog.dismiss();

                            String name = taskTitle;
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, name + " added!", Snackbar.LENGTH_LONG);
                            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        }
                    }
                });

                mBottomSheetDialog.show();
            }
        });

    }

    @Override
    public void onTaskChecked(final int position, boolean checkState) {
        taskList.get(position).setChecked(checkState);
    }

    @Override
    public void onTaskSelected(final int position) {

        final Task selectedTask = taskList.get(position);

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(TaskActivity.this);

        final View sheetView = TaskActivity.this.getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        mBottomSheetDialog.setContentView(sheetView);

        final EditText inputTaskTitle = sheetView.findViewById(R.id.EditTextTitle);
        inputTaskTitle.setText(selectedTask.getTitle());
        final EditText inputTaskContent = sheetView.findViewById(R.id.EditTextContent);
        inputTaskContent.setText(selectedTask.getContent());
        final Button saveButton = sheetView.findViewById(R.id.ButtonSave);
        saveButton.setText("Update");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitleIn = inputTaskTitle.getText().toString();
                String taskContentIn = inputTaskContent.getText().toString();

                if(!(taskTitleIn.equals(selectedTask.getTitle()))||!(taskContentIn.equals(selectedTask.getContent()))){
                    onTaskUpdated(position, taskTitleIn, taskContentIn);
                }

                if(mBottomSheetDialog.isShowing()){
                    mBottomSheetDialog.dismiss();

                    String name = taskTitleIn;
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, name + " updated!", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }
            }
        });

        mBottomSheetDialog.show();

    }

    @Override
    public void onTaskAdded(String taskTitle, String taskContent) {
        Log.e("Add Task", "Title = "+taskTitle+" Size = "+taskList.size()+1);
        Task task = new Task(taskList.size()+1, taskTitle, taskContent, false);
        taskList.add(task);
        mAdapter.onTaskAdd(taskList);

        AddAllTasksAsync addTasks = new AddAllTasksAsync(global_context, taskList);
        addTasks.execute();

        if(taskList.size()==1){
            resetAdapter();
        }
        if(draggedAction){
            resetAdapter();
            draggedAction = false;
        }
    }

    @Override
    public void onTaskUpdated(int taskID, String taskTitle, String taskContent) {
        Log.e("Update Task", "Title = "+taskTitle+" ID = "+taskID);
        taskList.get(taskID).setTitle(taskTitle);
        taskList.get(taskID).setContent(taskContent);
        mAdapter.updateTask(taskList.get(taskID), taskID);
        AddAllTasksAsync addTasks = new AddAllTasksAsync(global_context, taskList);
        addTasks.execute();
    }

    private ItemTouchHelper.SimpleCallback setupItemTouchHolder() {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
                    {
                        int oldID = taskList.get(viewHolder.getAdapterPosition()).getId();
                        taskList.get(viewHolder.getAdapterPosition()).setId(taskList.get(target.getAdapterPosition()).getId());
                        taskList.get(target.getAdapterPosition()).setId(oldID);

                        mAdapter.onTaskMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                        draggedAction = true;
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                        if (viewHolder instanceof TasksAdapter.TaskViewHolder) {
                            String name = taskList.get(viewHolder.getAdapterPosition()).getTitle();

                            final Task deletedTask = taskList.get(viewHolder.getAdapterPosition());
                            final int deletedIndex = viewHolder.getAdapterPosition();

                            mAdapter.removeTask(viewHolder.getAdapterPosition());
                            deletedTasks.add(deletedTask);

                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, name + " removed!", Snackbar.LENGTH_LONG);
                            snackbar.setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    mAdapter.restoreTask(deletedTask,deletedIndex);
                                    deletedTasks.remove(deletedTask);
                                    resetAdapter();
                                }
                            });
                            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                            draggedAction = true;
                        }
                    }

                    @Override
                    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                            TasksAdapter.TaskViewHolder holder = (TasksAdapter.TaskViewHolder) viewHolder;
                            holder.itemView.setBackgroundColor(Color.LTGRAY);
                        }
                        super.onSelectedChanged(viewHolder, actionState);
                    }

                    @Override
                    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        TasksAdapter.TaskViewHolder holder = (TasksAdapter.TaskViewHolder) viewHolder;
                        holder.itemView.setBackgroundColor(0);
                    }


                };

        return itemTouchHelperCallback1;
    }

    private void setupDB() {
        InitTasksAsync initDB = new InitTasksAsync(global_context, getMyDataset());
        initDB.execute();

        FetchTasksAsync fetchDB = new FetchTasksAsync(global_context);
        try {
            taskList = fetchDB.execute().get();

            if(taskList.isEmpty()){Log.e("DB response","PANIC! ABANDON SHIP!!");}

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        DeleteTasksAsync delTasks = new DeleteTasksAsync(global_context);
        delTasks.execute();

        AddAllTasksAsync addTasks = new AddAllTasksAsync(global_context, taskList);
        addTasks.execute();
    }

    public void resetAdapter(){
        mAdapter = new TasksAdapter(taskList,TaskActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyItemRangeChanged(0,taskList.size());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, CreditsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    private List<Task> getMyDataset(){
        List<Task> sampleList = new ArrayList<>();
        sampleList.add(new Task(0, "Your Task Here.", "Replace this task with yours.", false));
        return sampleList;
    }
}