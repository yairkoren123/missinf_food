package com.example.mymissingapp.Fragments;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.mymissingapp.MainActivity;
import com.example.mymissingapp.R;
import com.example.mymissingapp.modle.Todo;
import com.example.mymissingapp.modle.TodoRoomDatabase;

import java.time.LocalDate;
import java.util.List;

public class ThirdFragment extends Fragment {

    Button dateButton;

    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View listItemView;


    public List<Todo> todoList_need3;

    String the_date = "";


    private String[] lng = {"java","python","git"};




    public ThirdFragment(List<Todo> todoList1) {
        todoList_need3 = todoList1;



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
        listItemView = inflater.inflate(R.layout.fragment_third, container, false);
        recyclerView = listItemView.findViewById(R.id.allRecyclerview3);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

//        adapter = new AppAdapter(todoList_need1);
//        recyclerView.setAdapter(adapter);


        LocalDate currentdate = LocalDate.now();
        Log.d("frg3", "onCreateView: " + currentdate);
        String date =  currentdate.toString();
        Calendar cal = Calendar.getInstance();

        int month = cal.get(Calendar.MONTH); // 5



        findCompletedTodos();

        Log.d("frg3", "onCreateView: date :  " + month);

        return listItemView;
    }

    private String getMonthFormat(int month)
    {
        month++;
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void findCompletedTodos(String date) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("date", "run: "  +date.trim() + "  org   " + "MAY 30 2021");

                String pp = "MAY 30 2021";
                List<Todo> todoList = TodoRoomDatabase.getInstance(getContext())
                        .todoDao()
                        .getAllCompletedTodosById(the_date);


                Looper.getMainLooper();


                todoList_need3 = todoList;
                adapter = new AppAdapter(todoList_need3);
                recyclerView.setAdapter(adapter);
                Log.d("todo2", "run: " + todoList.size());



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
                Log.d("todo", "run: " + todoList.toString());
            }
        });
        thread.start();
    }

    public void findCompletedTodos() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> todoList = TodoRoomDatabase.getInstance(getContext())
                        .todoDao()
                        .getAllCompletedTodos();
                Looper.getMainLooper();
                if (todoList.isEmpty()){
                    Log.d("none", "run: no values");
                }
                todoList_need3 = todoList;
                adapter = new AppAdapter(todoList_need3);
                recyclerView.setAdapter(adapter);

                Log.d("frg3", "run: " + todoList.toString());
            }
        }).start();
        Looper.getMainLooper();
        adapter = new AppAdapter(todoList_need3);
        recyclerView.setAdapter(adapter);


    }



    }


