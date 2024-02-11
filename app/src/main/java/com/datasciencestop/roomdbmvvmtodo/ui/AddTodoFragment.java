package com.datasciencestop.roomdbmvvmtodo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.datasciencestop.roomdbmvvmtodo.R;
import com.datasciencestop.roomdbmvvmtodo.databinding.FragmentAddTodoBinding;
import com.datasciencestop.roomdbmvvmtodo.room.Todo;
import com.datasciencestop.roomdbmvvmtodo.viewmodel.TodoViewModel;

public class AddTodoFragment extends Fragment {

    private FragmentAddTodoBinding binding;
    private TodoViewModel todoViewModel;

    private int todoId = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTodoBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Initialize ViewModel
        todoViewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);

        if (getArguments() != null && getArguments().containsKey("todoId")) {
            todoId = getArguments().getInt("todoId");
            todoViewModel.getTodoById(todoId).observe(getViewLifecycleOwner(), todo -> {
                binding.editTextTodoTitle.setText(todo.getTitle());
                binding.btnSaveTodo.setText(R.string.update_todo); // Change button text to "Update"
            });
        } else {
            binding.btnSaveTodo.setText(R.string.add_todo); // Set button text to "Add" for new todos
        }

        binding.btnSaveTodo.setOnClickListener(v -> saveTodo());


        return rootView;
    }

    private void saveTodo() {
        String todoTitle = binding.editTextTodoTitle.getText().toString().trim();

        if (todoTitle.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a todo title", Toast.LENGTH_SHORT).show();
            return;
        }

        if (todoId == -1) {
            // Insert new todo
            Todo newTodo = new Todo(todoTitle, false);
            todoViewModel.insert(newTodo);
            Toast.makeText(getActivity(), "Todo added: " + todoTitle, Toast.LENGTH_SHORT).show();
        } else {
            // Update existing todo
            Todo updatedTodo = new Todo(todoTitle, false);
            updatedTodo.setId(todoId); // Set the ID to ensure the correct todo is updated
            todoViewModel.update(updatedTodo);
            Toast.makeText(getActivity(), "Todo updated: " + todoTitle, Toast.LENGTH_SHORT).show();
        }

        getParentFragmentManager().popBackStack();
    }
}
