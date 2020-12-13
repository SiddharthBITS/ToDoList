package com.example.appdev2;

import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase m_database;
    private Adapter m_adapter;
    private EditText m_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        m_database = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        m_adapter = new Adapter(this, getTasks());

        recyclerView.setAdapter(m_adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteTask((long) viewHolder.itemView.getTag());
            }

        }).attachToRecyclerView(recyclerView);

        m_task = findViewById(R.id.task);
        Button buttonAdd = findViewById(R.id.button_add);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void addItem() {
        if (m_task.getText().toString().trim().length() == 0) {
            Toast empty = Toast.makeText(getApplicationContext(),"Entry Empty", Toast.LENGTH_SHORT);
            empty.show();
            return;
        }

        String name = m_task.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(Database.Entry.COLUMN_TASK, name);
        m_database.insert(Database.Entry.TABLE_NAME, null, cv);
        m_adapter.swapCursor(getTasks());
        m_task.getText().clear();
    }

    private void deleteTask(long id) {
        m_database.delete(Database.Entry.TABLE_NAME, Database.Entry._ID + "=" + id, null);
        m_adapter.swapCursor(getTasks());
    }

    private Cursor getTasks() {
        return m_database.query(
                Database.Entry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Database.Entry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}