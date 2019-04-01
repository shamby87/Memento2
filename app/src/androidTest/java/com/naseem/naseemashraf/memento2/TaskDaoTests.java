package com.naseem.naseemashraf.memento2;

import android.content.Context;

import com.naseem.naseemashraf.memento2.db.Task;
import com.naseem.naseemashraf.memento2.db.TaskDAO;
import com.naseem.naseemashraf.memento2.db.TasksSQLDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.runner.AndroidJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTests extends AndroidJUnitRunner {

    private TaskDAO taskDao;
    private TasksSQLDatabase taskDB;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        taskDB = Room.inMemoryDatabaseBuilder(context, TasksSQLDatabase.class).build();
        taskDao = taskDB.taskDao();
    }

    @After
    public void closeDb(){
        taskDB.close();
    }

    @Test
    public void writeTaskListReadFetchedList() {

        List<Task> expectedList = new ArrayList<>();
        for(int i=0; i<10; i++){
            expectedList.add(new Task(i, "Task "+i, "This is Task number "+i, false));
        }

        taskDao.insertAll(expectedList);
        List<Task> resultList = taskDao.getAll();
        for(int i=0; i<resultList.size(); i++)
            assertEquals(resultList.get(i).getTitle(), expectedList.get(i).getTitle());
    }

    @Test
    public void writeTaskListDeleteAllTasks() {

        List<Task> expectedList = new ArrayList<>();
        for(int i=0; i<10; i++){
            expectedList.add(new Task(i, "Task "+i, "This is Task number "+i, false));
        }

        taskDao.insertAll(expectedList);
        taskDao.deleteAll();
        List<Task> resultList = null;
        resultList = taskDao.getAll();
        assertNotSame(expectedList.size(),resultList.size());
    }

}
