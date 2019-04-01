package com.naseem.naseemashraf.memento2.ui;

public interface OnTaskAddListener {
    void onTaskAdded(String taskTitle, String taskContent);
    void onTaskUpdated(int taskID, String taskTitle, String taskContent);
}
