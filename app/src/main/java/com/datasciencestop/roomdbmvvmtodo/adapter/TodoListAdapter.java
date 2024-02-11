package com.datasciencestop.roomdbmvvmtodo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datasciencestop.roomdbmvvmtodo.R;
import com.datasciencestop.roomdbmvvmtodo.room.Todo;
import com.datasciencestop.roomdbmvvmtodo.viewmodel.TodoViewModel;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private List<Todo> todos = new ArrayList<>();
    private TodoViewModel todoViewModel;

    private OnTodoDeleteListener deleteListener;

    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Todo todo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }


    public interface OnTodoDeleteListener {
        void onTodoDelete(Todo todo);
    }

    public void setOnTodoDeleteListener(OnTodoDeleteListener listener) {
        this.deleteListener = listener;
    }


    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo currentTodo = todos.get(position);
        holder.textViewTitle.setText(currentTodo.getTitle());
        holder.checkBoxStatus.setChecked(currentTodo.isCompleted());

        holder.checkBoxStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTodo.setCompleted(isChecked);
            todoViewModel.update(currentTodo);
        });

        holder.deleteIcon.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onTodoDelete(currentTodo);
            }
        });

        // Inside your ViewHolder class
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null && position != RecyclerView.NO_POSITION) {
                clickListener.onItemClick(todos.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public void setTodoViewModel(TodoViewModel viewModel) {
        this.todoViewModel = viewModel;
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private CheckBox checkBoxStatus;
        private ImageView deleteIcon;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            checkBoxStatus = itemView.findViewById(R.id.checkBox_status);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
        }
    }
}
