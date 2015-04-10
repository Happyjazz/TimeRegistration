package com.example.martin.timeregistration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.timeregistration.Data.DataAccessObject;
import com.example.martin.timeregistration.Data.inMemory.DataAccessObjectInMemory;
import com.example.martin.timeregistration.Model.TaskRegistration;
import com.example.martin.timeregistration.Model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TaskRegistrationActivity extends ActionBarActivity {

    private Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");

    private TextView tvDate;

    List<EditText> etTaskList;
    private EditText etTask1;
    private EditText etTask2;
    private EditText etTask3;
    private EditText etTask4;
    private EditText etTask5;
    private EditText etTask6;
    private EditText etTask7;
    private EditText etTask8;
    private EditText etTask9;

    private TextView tvSum;

    private DataAccessObject dao;

    private static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_registration);

        if (savedInstanceState != null)
        {
            Calendar c = (Calendar)savedInstanceState.getSerializable("current_date");
            if (c!=null) calendar = c;
        }

        //Get the TextView for the date-headline
        tvDate = (TextView)findViewById(R.id.textViewCurrentDate);
        tvDate.setText(dateFormat.format(calendar.getTime()));

        //Get all the EditTexts for the tasks
        prepareEditTexts();

        //Gets the DataAccessObject instance
        dao = DataAccessObjectInMemory.getInstance();

        //Gets the user that is logged in
        currentUser = dao.getUser(getIntent().getStringExtra(LoginActivity.KEY_USERNAME), getIntent().getStringExtra(LoginActivity.KEY_PASSWORD));

        GetTask();
        updateSum();
    }

    @Override
    public void finish() {
        SaveTasks();
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_logout){
            new AlertDialog.Builder(this)
                    .setTitle("Log out")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                            editor.remove(LoginActivity.KEY_USERNAME);
                            editor.remove(LoginActivity.KEY_PASSWORD);
                            editor.putBoolean(LoginActivity.KEY_CHECKBOX, false);
                            editor.apply();
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("current_date", calendar);
    }

    //Click handler for select next date button
    public void SelectNextDate(View view) {
        SaveTasks();
        calendar.add(calendar.DATE, 1);
        tvDate.setText(dateFormat.format(calendar.getTime()));
        GetTask();
        updateSum();
    }

    //Click handler for select previous date button
    public void SelectPrevDate(View view) {
        SaveTasks();
        calendar.add(calendar.DATE, -1);
        tvDate.setText(dateFormat.format(calendar.getTime()));
        GetTask();
        updateSum();
    }

    private void GetTask() {
        List<TaskRegistration> tasks = dao.getRegistrations(currentUser, calendar);

        int etIndex = 0;

        for (TaskRegistration task : tasks) {
            EditText currentET = etTaskList.get(etIndex);

            double taskHours = task.getHours();

            currentET.setText(String.valueOf(taskHours));

            etIndex++;
        }
    }

    private void SaveTasks() {
        List<TaskRegistration> tasks = new ArrayList<TaskRegistration>();

        for (EditText et : etTaskList) {
            int taskNumber = 1;

            String taskName = "Task "+taskNumber;
            Double taskValue = Double.valueOf(et.getText().toString());

            TaskRegistration currentTask = new TaskRegistration(taskName, taskValue);
            tasks.add(currentTask);
            taskNumber++;
        }
        dao.saveRegistration(currentUser, calendar, tasks);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    //Method for updating the text in the sum EditText
    public void updateSum(){
        double sum = 0;
        for (EditText et : etTaskList) {
            double value;
            try {
                value = Double.parseDouble(et.getText().toString());
            } catch (NumberFormatException e) {
                value = 0;
            }
            sum += value;
        }
        sum = round(sum, 2);
        tvSum.setText(String.valueOf(sum));
    }

    //Method for instantiating and setting listeners on EditText fields
    public void prepareEditTexts(){
        etTaskList = new ArrayList<EditText>();
        etTask1 = (EditText)findViewById(R.id.editTextTask1);

        etTaskList.add(etTask1);
        etTask2 = (EditText)findViewById(R.id.editTextTask2);
        etTaskList.add(etTask2);
        etTask3 = (EditText)findViewById(R.id.editTextTask3);
        etTaskList.add(etTask3);
        etTask4 = (EditText)findViewById(R.id.editTextTask4);
        etTaskList.add(etTask4);
        etTask5 = (EditText)findViewById(R.id.editTextTask5);
        etTaskList.add(etTask5);
        etTask6 = (EditText)findViewById(R.id.editTextTask6);
        etTaskList.add(etTask6);
        etTask7 = (EditText)findViewById(R.id.editTextTask7);
        etTaskList.add(etTask7);
        etTask8 = (EditText)findViewById(R.id.editTextTask8);
        etTaskList.add(etTask8);
        etTask9 = (EditText)findViewById(R.id.editTextTask9);
        etTaskList.add(etTask9);

        tvSum = (TextView)findViewById(R.id.TextViewSumValue);

        //Add a TextChangedListener on all Task-EditTexts.
        for (EditText et : etTaskList) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updateSum();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    //Method for rounding a double to 2 decimal points
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
