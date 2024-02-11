package com.datasciencestop.roomdbmvvmtodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.datasciencestop.roomdbmvvmtodo.databinding.ActivityMainBinding;
import com.datasciencestop.roomdbmvvmtodo.ui.TodoListFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new TodoListFragment())
                    .commit();
        }
    }


}