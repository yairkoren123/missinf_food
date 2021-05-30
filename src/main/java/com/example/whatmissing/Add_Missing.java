package com.example.whatmissing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.whatmissing.modle.Todo;
import com.example.whatmissing.modle.TodoRoomDatabase;
import com.google.android.material.textfield.TextInputLayout;

public class Add_Missing extends AppCompatActivity {

    //todo  1 : data not more then my date (time)

    //
    String need_add_food , need_add_reasons , need_add_date , need_add_time_meal;

    //layout
    AutoCompleteTextView time_auto;
    private TextInputLayout missingFood , theReason;
    private DatePickerDialog datePickerDialog;
    Button save_button , dateButton;
    ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing);

        //move menu
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        initDatePicker();

        //layout
        back_button = findViewById(R.id.back_button_a);
        missingFood = findViewById(R.id.missing_food_et_a);
        theReason = findViewById(R.id.reason_et_a);
        time_auto = findViewById(R.id.selected_time_autu_text_a);
        save_button = findViewById(R.id.save_button_a);
        dateButton = findViewById(R.id.datePicker_Button_a);

        // set date to the cal
        dateButton.setText(getTodaysDate());

        // for time selector
        String[] option = new String[]{"Breakfast","Lunch","Afternoon meal","Dinner"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Add_Missing.this,R.layout.list_item, option);
        time_auto.setText(arrayAdapter.getItem(0).toString(),true);
        time_auto.setAdapter(arrayAdapter);


        // save button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("needadd", "onCreate: "+time_auto.getText()
                + "missing :  " + missingFood.getEditText().getText().toString().trim()
                + " reason  " + theReason.getEditText().getText().toString().trim()
                + " time : " + dateButton.getText().toString().trim());

                // check if its valid input in the reasons and food
                boolean input = check_input();
                // if its yes we need to save it in data base
                // input == true
                if (input) {

                    // save in local value
                    need_add_food =  missingFood.getEditText().getText().toString().trim();
                    need_add_date = dateButton.getText().toString().trim();
                    need_add_reasons = theReason.getEditText().getText().toString().trim();
                    need_add_time_meal = time_auto.getText().toString().trim();


                    if (need_add_reasons.equals("") || need_add_reasons == ""){
                        need_add_reasons = "Empty";
                    }
                    insertSingleTodo(need_add_food,need_add_reasons,need_add_time_meal,need_add_date,false);
                    missingFood.getEditText().setText("");





                }


            }
        });

        // back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to the main activity
                onBackPressed();
            }
        });


    }
    public void msg(String text){
        Toast.makeText(Add_Missing.this,text,Toast.LENGTH_LONG)
                .show();
    }

    private boolean check_input() {
        // if the length is more then needed
        if ( missingFood.getEditText().getText().toString().trim().length() > missingFood.getCounterMaxLength()){
            Log.d("input", "cheak_input: to many food" );
            msg("input not valid");
            return false;
        }else {
            Log.d("input", "cheak_input: good food " );
        }
        if ( theReason.getEditText().getText().toString().trim().length() > theReason.getCounterMaxLength()){
            Log.d("input", "cheak_input: to many reasons" );
            msg("input not valid");
            return false;
        }else {
            Log.d("input", "cheak_input: good reasons " );
        }

        return true;
    }


    // Before all the date button
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
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
    // cal the function in the layout
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    // After all the date button

    public void nukeTable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TodoRoomDatabase.getInstance(getApplicationContext())
                            .todoDao().nukeTable();

                    Log.d("nuke", "run: all dead");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }




    public void insertSingleTodo(String missing_get ,String reason_get , String meal_get , String date_get , boolean completed_get ) {

        // enter the value from user in the add_food activity
        Todo todo = new Todo(missing_get,reason_get,meal_get,date_get,completed_get);
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        insertAsyncTask.execute(todo);
        Log.d("saved", "insertSingleTodo:  is in !!!");
        msg("its saved :) ");
    }

    class InsertAsyncTask extends AsyncTask<Todo, Void, Void> {

        @Override
        protected Void doInBackground(Todo... todos) {

            TodoRoomDatabase.getInstance(getApplicationContext())
                    .todoDao()
                    .insertTodo(todos[0]);

            return null;
        }
    }
}

