package com.example.mymissingapp.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymissingapp.MainActivity;
import com.example.mymissingapp.modle.Todo;

import java.util.List;


public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) { // pass value with fragments

        switch (position)
        {
            case 1 :
                return new SecondFragment(MainActivity.todoList_frag_one);
            case 2 :
                return new ThirdFragment();
        }

        return new FirstFragment(MainActivity.todoList_frag_one);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
