package com.example.mymissingapp.Fragments;

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

import com.example.mymissingapp.MainActivity;
import com.example.mymissingapp.R;
import com.example.mymissingapp.modle.Todo;
import com.example.mymissingapp.modle.TodoRoomDatabase;

import java.util.List;


// ALL FRAGMENT
public class FirstFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View listItemView;


    public List<Todo> todoList_need;

    private String[] lng = {"java","python","git"};




    public FirstFragment(List<Todo> todoList1) {
        // Required empty public constructor
        todoList_need = todoList1;


    }


    public static FirstFragment newInstance(String param1, String param2) {
        return new FirstFragment(MainActivity.todoList_frag_one);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listItemView = inflater.inflate(R.layout.fragment_first, container, false);
        recyclerView = listItemView.findViewById(R.id.allRecyclerview);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AppAdapter(todoList_need);
        recyclerView.setAdapter(adapter);

        getAllTodos();




        return listItemView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    public void getAllTodos() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> todoList = TodoRoomDatabase.getInstance(getContext())
                        .todoDao()
                        .getAllTodos();

                Looper.getMainLooper();

                todoList_need = todoList;
                adapter = new AppAdapter(todoList_need);
                recyclerView.setAdapter(adapter);

            }
        });
        thread.start();
    }


}


