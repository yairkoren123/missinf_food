package com.example.mymissingapp.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymissingapp.R;
import com.example.mymissingapp.modle.Todo;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

    String food ,time,meal,reason;

    List<Todo> langdata = new ArrayList<>();

    private LayoutInflater layoutInflater;

    AppAdapter(List<Todo> _data){
        langdata = _data;
    }


    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_item_list, parent, false);

        return new AppViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  AppViewHolder holder, int position) {

        food = String.valueOf(langdata.get(position).getMissing());
        holder.food_adapter.setText(food);

        time = String.valueOf(langdata.get(position).getDate());
        holder.date_adapter.setText(time);

        meal = String.valueOf(langdata.get(position).getTime_meal());
        holder.meal_adapter.setText(meal);

        reason = String.valueOf(langdata.get(position).getReason());
        holder.reason_adapter.setText(reason);



    }

    @Override
    public int getItemCount() {
        return langdata.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder{

        TextView food_adapter,reason_adapter,date_adapter,meal_adapter;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            food_adapter = itemView.findViewById(R.id.single_food);
            reason_adapter = itemView.findViewById(R.id.single_reasons);
            date_adapter = itemView.findViewById(R.id.single_date);
            meal_adapter = itemView.findViewById(R.id.single_meal);


        }
    }
}
