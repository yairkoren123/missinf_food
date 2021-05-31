package com.example.mymissingapp.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import java.time.Year;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class SecondFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    Button dateButton;

    EditText d;
    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View listItemView;


    public List<Todo> todoList_need1;


    private String[] lng = {"java","python","git"};




    public SecondFragment(List<Todo> todoList1) {
        // Required empty public constructor
        todoList_need1 = todoList1;



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

//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new AppAdapter(todoList_need1);
//        recyclerView.setAdapter(adapter);

        dateButton = listItemView.findViewById(R.id.datePicker_Button_t);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear , mMonth,mDay;
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        Log.d("time", "onDateSet: y "
                                +selectedday + "  m  " + selectedmonth + " d  " +selectedday );
                        String Month = getMonthFormat(selectedmonth);
                        String date  = Month +  " " + selectedday + " " + selectedyear;
                        findCompletedTodosBYID(date);
                        dateButton.setText(date) ;

                        layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);


                    }
                },mYear, mMonth, mDay);

                mDatePicker.getDatePicker().setCalendarViewShown(false);
                //mDatePicker.setTitle("Select date");

                mDatePicker.show();  }

        });
        Log.d("array", "onCreateView: " + todoList_need1.size()) ;

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

    public void findCompletedTodosBYID(String date) {

        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<5;i++)
            es.execute(new Runnable() {
                public void run() {

                    Log.d("date", "run: " + date.trim() + "  org   " + "MAY 30 2021");

                    String pp = "MAY 30 2021";
                    List<Todo> todoList = TodoRoomDatabase.getInstance(getContext())
                            .todoDao()
                            .getAllCompletedTodosById(date.trim());

                    if (todoList.isEmpty()) {
                        Log.d("none", "run: no values");
                    }

                    Looper.getMainLooper();


                    todoList_need1 = todoList;
                    adapter = new AppAdapter(todoList_need1);
                    //recyclerView.setAdapter(adapter);
                    Log.d("todo2", "run: " + todoList.size());
                    Log.d("todo2", "run: " + todoList.toString());
                }

            });
        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adapter = new AppAdapter(todoList_need1);
        recyclerView.setAdapter(adapter);


// all tasks have finished or the time has been reached.


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("date", "run: "  +date.trim() + "  org   " + "MAY 30 2021");
//
//                String pp = "MAY 30 2021";
//                List<Todo> todoList = TodoRoomDatabase.getInstance(getContext())
//                        .todoDao()
//                        .getAllCompletedTodosById(date.trim());
//
//                if (todoList.isEmpty()){
//                    Log.d("none", "run: no values");
//                }
//
//                Looper.getMainLooper();
//
//
//                todoList_need1 = todoList;
//                adapter = new AppAdapter(todoList_need1);
//                //recyclerView.setAdapter(adapter);
//                Log.d("todo2", "run: " + todoList.size());
//                Log.d("todo2", "run: " + todoList.toString());
//            }
//        }).start();
//        Looper.getMainLooper();
//        adapter = new AppAdapter(todoList_need1);
//        recyclerView.setAdapter(adapter);
//
//





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


}


