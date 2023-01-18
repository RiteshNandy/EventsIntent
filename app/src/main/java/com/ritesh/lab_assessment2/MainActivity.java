package com.ritesh.lab_assessment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button timeButton, timeButton2;
    int hour, minute, seconds;

    EditText title;
    EditText location;
    EditText description;
    Button addEvent;
    SwitchCompat allDay;

    //Initializing for date picker spinner.
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        addEvent = findViewById(R.id.event);
        allDay = findViewById(R.id.allDaySwitch);

        timeButton = findViewById(R.id.startTimePicker);
        timeButton2 = findViewById(R.id.endTimePicker);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty()
                && !description.getText().toString().isEmpty()){

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());

                    if(allDay.isChecked()) {
                        intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    }else{
                        Toast.makeText(MainActivity.this, "Please fill all the details",
                                Toast.LENGTH_SHORT).show();
                    }


                    if(intent.resolveActivity(getPackageManager())!= null) {
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "No such App to handle Intent",
                                Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "Please fill all the details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


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

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.BUTTON_NEUTRAL;

        datePickerDialog = new DatePickerDialog(this, style, dateListener, year, month, day);


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

    public void datePicker(View view)
    {
        datePickerDialog.show();
    }


    public void timePicker(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                timeButton2.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                onTimeSetListener, hour, minute, true);

        timePickerDialog.show();
    }
}
