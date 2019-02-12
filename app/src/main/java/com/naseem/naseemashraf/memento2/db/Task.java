package com.naseem.naseemashraf.memento2.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "task_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "task_title")
    private String title;

    @NonNull
    @ColumnInfo(name = "task_content")
    private String content;

    @NonNull
    @ColumnInfo(name = "task_checked")
    private boolean checked;

    public Task(int id, String title, String content, boolean checked) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
