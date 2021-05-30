package com.example.mymissingapp.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mymissingapp.MainActivity;
import com.example.mymissingapp.R;
import com.example.mymissingapp.modle.Todo;
import com.example.mymissingapp.modle.TodoRoomDatabase;

import java.util.List;


public class SecondFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    Button dateButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View listItemView;


    public List<Todo> todoList_need;

    private String[] lng = {"java","python","git"};




    public SecondFragment(List<Todo> todoList1) {
        // Required empty public constructor
        todoList_need = todoList1;

        findCompletedTodos();


    }


    public static com.example.mymissingapp.Fragments.FirstFragment newInstance(String param1, String param2) {
        return new com.example.mymissingapp.Fragments.FirstFragment(MainActivity.todoList_frag_one);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listItemView = inflater.inflate(R.layout.fragment_second, container, false);
        recyclerView = listItemView.findViewById(R.id.allRecyclerview2);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AppAdapter(todoList_need);
        recyclerView.setAdapter(adapter);

        dateButton = listItemView.findViewById(R.id.datePicker_Button_t);




        return listItemView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void findCompletedTodos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> todoList = TodoRoomDatabase.getInstance(getContext())
                        .todoDao()
                        .getAllCompletedTodosById("MAY 30 2021");

                Log.d("todo2", "run: " + todoList.toString());
            }
        }).start();

    }





    public void getAllTodos() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> todoList = TodoRoomDatabase.getInstance(getContext())
                        .todoDao()
                        .getAllTodos();

                todoList_need = todoList;

                Log.d("todo", "run: " + todoList.toString());
            }
        });
        thread.start();
    }


}


