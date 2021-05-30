package com.example.whatmissing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.whatmissing.modle.Todo;
import com.example.whatmissing.modle.TodoRoomDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    // values
    Context context;

    // layout
    Button add_missing,see_table,allGood;
    TextView date_main,hello_to_app;

    // intents
    public static final String EXTRA_DATE = "DATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // remove Menu
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        context = getApplicationContext();


        // layout
        add_missing = findViewById(R.id.main_app_missing_food_button);
        see_table = findViewById(R.id.main_see_food_table_buyton);
        allGood = findViewById(R.id.main_all_good_button);
        date_main = findViewById(R.id.main_date);
        hello_to_app = findViewById(R.id.main_hello_textview);

        // set data
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date_now = dtf.format(now);
        date_main.setText(date_now);

        // click buttons

        add_missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this
                        , Add_Missing.class);
                intent.putExtra(EXTRA_DATE,date_now);
                startActivity(intent);

            }
        });

        see_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this
                        , Add_Missing.class);
                intent.putExtra(EXTRA_DATE,date_now);
                startActivity(intent);
            }
        });

        //insertSingleTodo(date_main);



    }



    public void insertSingleTodo(String missing_get ,String reason_get , String meal_get , String date_get , boolean completed_get ) {

        // enter the value from user in the add_food activity
        Todo todo = new Todo(missing_get,reason_get,meal_get,date_get,completed_get);
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        insertAsyncTask.execute(todo);
        Log.d("saved", "insertSingleTodo:  is in !!!");
    }

//    public void getAllTodos(View view) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Todo> todoList = TodoRoomDatabase.getInstance(getApplicationContext())
//                        .todoDao()
//                        .getAllTodos();
//
//                Log.d(TAG, "run: " + todoList.toString());
//            }
//        });
//        thread.start();
//    }
//
//    public void deleteATodo(View view) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Todo todo = TodoRoomDatabase.getInstance(getApplicationContext())
//                        .todoDao()
//                        .findTodoById(4);
//
//                Log.d(TAG, "run: " + todo.toString());
//                if (todo != null) {
//                    TodoRoomDatabase.getInstance(getApplicationContext())
//                            .todoDao()
//                            .deleteTodo(todo);
//
//                    Log.d(TAG, "run: todo has been deleted...");
//                }
//
//            }
//        }).start();
//    }
//
//    public void updateATodo(View view) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Todo todo = TodoRoomDatabase.getInstance(getApplicationContext())
//                        .todoDao()
//                        .findTodoById(1);
//
//                if (todo != null) {
//                    todo.setCompleted(true);
//
//                    TodoRoomDatabase.getInstance(getApplicationContext())
//                            .todoDao()
//                            .updateTodo(todo);
//
//                    Log.d(TAG, "run: todo has been updated...");
//                }
//            }
//        }).start();
//
//    }
//
//    public void insertMultipleTodos(View view) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Todo> todoList = new ArrayList<>();
//                todoList.add(new Todo("make a video on kotlin", false));
//                todoList.add(new Todo("watch black panther", true));
//                todoList.add(new Todo("watch marvel movies seris", true));
//                todoList.add(new Todo("make a beginner video on pyhton", false));
//
//                TodoRoomDatabase.getInstance(getApplicationContext())
//                        .todoDao()
//                        .insertMultipleTodos(todoList);
//
//                Log.d(TAG, "run: todos has been inserted...");
//            }
//        }).start();
//
//    }
//
//    public void findCompletedTodos(View view) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Todo> todoList = TodoRoomDatabase.getInstance(getApplicationContext())
//                        .todoDao()
//                        .getAllCompletedTodos();
//
//                Log.d(TAG, "run: " + todoList.toString());
//            }
//        }).start();
//
//    }

    class InsertAsyncTask extends AsyncTask<Todo, Void, Void> {

        @Override
        protected Void doInBackground(Todo... todos) {

            TodoRoomDatabase.getInstance(context)
                    .todoDao()
                    .insertTodo(todos[0]);

            return null;
        }
    }
}