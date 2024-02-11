package com.datasciencestop.roomdbmvvmtodo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.datasciencestop.roomdbmvvmtodo.R;
import com.datasciencestop.roomdbmvvmtodo.adapter.TodoListAdapter;
import com.datasciencestop.roomdbmvvmtodo.databinding.FragmentTodoListBinding;
import com.datasciencestop.roomdbmvvmtodo.room.Todo;
import com.datasciencestop.roomdbmvvmtodo.viewmodel.TodoViewModel;

public class TodoListFragment extends Fragment implements TodoListAdapter.OnTodoDeleteListener, TodoListAdapter.OnItemClickListener {

    private FragmentTodoListBinding binding;
    private TodoViewModel todoViewModel;

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentTodoListBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        TodoListAdapter adapter = new TodoListAdapter();
        adapter.setTodoViewModel(todoViewModel); // Pass ViewModel to adapter
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        // Update the cached copy of the todos in the adapter.
        todoViewModel.getAllTodos().observe(getViewLifecycleOwner(), adapter::setTodos);
        binding.fabAddTodo.setOnClickListener(v -> openAddTodoFragment());
        adapter.setOnTodoDeleteListener(this); // Set the delete listener
        adapter.setOnItemClickListener(this); // Set the item click listener

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTodos(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchTodos(newText);
                return false;
            }

            private void searchTodos(String query) {
                todoViewModel.searchTodos(query).observe(getViewLifecycleOwner(), adapter::setTodos);
            }
        });
    }

    private void openAddTodoFragment() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new AddTodoFragment())
                .addToBackStack(null) // Add this transaction to the back stack
                .commit();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }

    @Override
    public void onTodoDelete(Todo todo) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Todo")
                .setMessage("Are you sure you want to delete this todo?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Delete the todo item
                    todoViewModel.delete(todo);
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    @Override
    public void onItemClick(Todo todo) {
        Bundle bundle = new Bundle();
        bundle.putInt("todoId", todo.getId());
        AddTodoFragment fragment = new AddTodoFragment();
        fragment.setArguments(bundle); // Set arguments to pass to the fragment

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
