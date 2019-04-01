package com.naseem.naseemashraf.memento2.activity;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.naseem.naseemashraf.memento2.R;
import com.naseem.naseemashraf.memento2.db.Task;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private List<Task> taskList;
	private TasksAdapterCallback callback;

    public interface TasksAdapterCallback{
        void onTaskChecked(int position, boolean checkState);
        void onTaskSelected(int position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTask;
        private CheckBox chkDone;

        private Context context;

        public TaskViewHolder(@NonNull final View itemView) {
            super(itemView);

            context = itemView.getContext();

            tvTask = itemView.findViewById(R.id.tvTask);
            chkDone = itemView.findViewById(R.id.chkDone);

            tvTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onTaskSelected(getLayoutPosition());
                }
            });

            chkDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        tvTask.setPaintFlags(tvTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        if(callback != null) {
                            taskList.get(getLayoutPosition()).setChecked(true);
                            callback.onTaskChecked(getLayoutPosition(), true);
                        }
                    }else{
                        tvTask.setPaintFlags(tvTask.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        if(callback != null) {
                            taskList.get(getLayoutPosition()).setChecked(false);
                            callback.onTaskChecked(getLayoutPosition(), false);
                        }
                    }
                }
            });
        }

        // We'll use these to showcase matching the holder from the test.
        private String getTaskTitle(){
            return tvTask.getText().toString();
        }

        public TaskViewHolder getIsMatchingTaskTitle(String s) {
            if(getTaskTitle().equals(s)){
                return new TaskViewHolder(this.itemView);
            }

            return null;
        }

    }

    public TasksAdapter(List<Task> taskListIn, TasksAdapterCallback callback) {
        this.taskList = taskListIn;
        this.callback = callback;
    }

    @Override
    public TasksAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item_view, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder taskViewHolder, int i) {
        final Task task = taskList.get(i);

        taskViewHolder.chkDone.setChecked(task.isChecked());
        taskViewHolder.tvTask.setText(task.getTitle());

        if(task.isChecked()) {
            taskViewHolder.tvTask.setPaintFlags(taskViewHolder.tvTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public void removeTask(int position) {
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    public void onTaskMove(int fromPosition, int toPosition){
        Collections.swap(taskList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void restoreTask(Task task, int position) {
        taskList.add(position, task);
        notifyItemInserted(position);
    }

    public void updateTask(Task task, int index) {
        taskList.get(index).setTitle(task.getTitle());
        taskList.get(index).setContent(task.getContent());
        notifyItemChanged(index);
    }

    public void onTaskAdd(List<Task> inTaskList) {
        this.taskList = inTaskList;
        notifyItemInserted(taskList.size());

        //logAdapterTaskList();
    }

    //Logging Methods
    public void logAdapterTaskList() {
        Log.e("Adapter Task List", String.valueOf(this.taskList.size()));

        for(int i=0; i<this.taskList.size(); i++){
            Log.e("Adapter Task Item", this.taskList.get(i).toString());
        }
    }
}
