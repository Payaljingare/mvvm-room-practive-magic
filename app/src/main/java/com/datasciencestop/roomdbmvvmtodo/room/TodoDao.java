package com.datasciencestop.roomdbmvvmtodo.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todos")
    LiveData<List<Todo>> getAllTodos();

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM todos WHERE id = :id")
    LiveData<Todo> getTodoById(int id);

    @Query("SELECT * FROM todos WHERE title LIKE :query")
    LiveData<List<Todo>> searchTodos(String query);
}
