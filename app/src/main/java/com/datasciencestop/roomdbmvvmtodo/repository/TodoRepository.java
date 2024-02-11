package com.datasciencestop.roomdbmvvmtodo.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.datasciencestop.roomdbmvvmtodo.TodoApplication;
import com.datasciencestop.roomdbmvvmtodo.room.Todo;
import com.datasciencestop.roomdbmvvmtodo.room.TodoDao;
import com.datasciencestop.roomdbmvvmtodo.room.TodoDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;
    private ExecutorService databaseWriteExecutor;

    public TodoRepository(Application application) {
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllTodos();
        databaseWriteExecutor = TodoApplication.databaseWriteExecutor;

    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    public void insert(Todo todo) {
        databaseWriteExecutor.execute(() -> {
            todoDao.insert(todo);
        });
    }

    public void update(Todo todo) {
        databaseWriteExecutor.execute(() -> {
            todoDao.update(todo);
        });
    }

    public void delete(Todo todo) {
        databaseWriteExecutor.execute(() -> {
            todoDao.delete(todo);
        });
    }

    public LiveData<Todo> getTodoById(int id) {
        return todoDao.getTodoById(id);
    }


    public LiveData<List<Todo>> searchTodos(String query) {
        // Assuming you have a method in TodoDao for searching todos by title
        return todoDao.searchTodos("%" + query + "%");
    }


}
