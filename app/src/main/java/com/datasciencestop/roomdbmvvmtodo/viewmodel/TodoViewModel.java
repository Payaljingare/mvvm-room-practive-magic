package com.datasciencestop.roomdbmvvmtodo.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.datasciencestop.roomdbmvvmtodo.repository.TodoRepository;
import com.datasciencestop.roomdbmvvmtodo.room.Todo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository todoRepository;
    private LiveData<List<Todo>> allTodos;

    public TodoViewModel(Application application) {
        super(application);
        todoRepository = new TodoRepository(application);
        allTodos = todoRepository.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    public void insert(Todo todo) {
        todoRepository.insert(todo);
    }

    public void update(Todo todo) {
        todoRepository.update(todo);
    }

    public void delete(Todo todo) {
        todoRepository.delete(todo);
    }
    public LiveData<Todo> getTodoById(int id) {
        return todoRepository.getTodoById(id);
    }

    public LiveData<List<Todo>> searchTodos(String query) {
        return todoRepository.searchTodos(query);
    }
}
