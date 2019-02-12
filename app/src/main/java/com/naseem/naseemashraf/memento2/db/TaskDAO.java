package com.naseem.naseemashraf.memento2.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM task_table")
    List<Task> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    @Query("DELETE FROM task_table")
    void deleteAll();
}
