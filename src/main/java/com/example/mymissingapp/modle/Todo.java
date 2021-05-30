package com.example.mymissingapp.modle;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_uid")
    private int uid;

    @ColumnInfo(name = "todo_miss")
    private String missing;

    @ColumnInfo(name = "todo_reason")
    private String reason;

    @ColumnInfo(name = "todo_meal")
    private String time_meal;

    @ColumnInfo(name = "todo_date")
    private String date;

    @ColumnInfo(name = "todo_completed")
    private boolean completed;

    //getter and setters


    public Todo(String missing, String reason, String time_meal, String date, boolean completed) {
        this.missing = missing;
        this.reason = reason;
        this.time_meal = time_meal;
        this.date = date;
        this.completed = completed;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMissing() {
        return missing;
    }

    public void setMissing(String missing) {
        this.missing = missing;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime_meal() {
        return time_meal;
    }

    public void setTime_meal(String time_meal) {
        this.time_meal = time_meal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}