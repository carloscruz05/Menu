package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout taskListLayout;
    EditText editTextTask;
    Button btnAddTask;
    String taskToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskListLayout = findViewById(R.id.taskListLayout);
        editTextTask = findViewById(R.id.editTextTask);
        btnAddTask = findViewById(R.id.btnAddTask);

        setAddTaskClickListener();
    }

    private void setAddTaskClickListener() {
        btnAddTask.setText(R.string.add_task);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = editTextTask.getText().toString().trim();
                if (!taskText.isEmpty()) {
                    addTaskToList(taskText);
                    editTextTask.setText("");
                }
            }
        });
    }

    private void addTaskToList(final String taskText) {
        final View taskView = getLayoutInflater().inflate(R.layout.task_item, null);

        final TextView taskTextView = taskView.findViewById(R.id.taskTextView);
        taskTextView.setText(taskText);

        final Button btnTaskMenu = taskView.findViewById(R.id.btnTaskMenu);
        btnTaskMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskMenu(v, taskView, taskTextView);
            }
        });

        taskListLayout.addView(taskView);
    }

    private void showTaskMenu(View view, final View taskView, final TextView taskTextView) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.task_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_edit) {
                    editTask(taskTextView);
                    return true;
                } else if (itemId == R.id.action_delete) {
                    taskListLayout.removeView(taskView);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void editTask(final TextView taskTextView) {
        String currentText = taskTextView.getText().toString();
        editTextTask.setText(currentText);
        btnAddTask.setText(R.string.update_task);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedText = editTextTask.getText().toString().trim();
                if (!updatedText.isEmpty()) {
                    taskTextView.setText(updatedText);
                    editTextTask.setText("");
                    setAddTaskClickListener();
                }
            }
        });
    }
}
