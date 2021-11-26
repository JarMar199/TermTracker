package com.example.termtracker.app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.termtracker.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Database.Repository;
import Entities.Course;

public class AddCourse extends AppCompatActivity {
    String termName, termStart, termEnd;
    int termId;
    TextView textName;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText startText;
    EditText endText;
    EditText instructorName, instructorPhone, instructorEmail, note;
    private EditText mEditName;
    private EditText mEditID;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    private Repository repository;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());
        mEditName = findViewById(R.id.courseName);
        startText = findViewById(R.id.courseStartDate);
        endText = findViewById(R.id.courseEndDate);
        spinner = findViewById(R.id.courseStatusSpinner);
        instructorName = findViewById(R.id.courseInstructorName);
        instructorPhone = findViewById(R.id.courseInstructorPhone);
        instructorEmail = findViewById(R.id.courseInstructorEmail);
        note = findViewById(R.id.courseNote);

        termName = getIntent().getStringExtra("termName");
        termId = getIntent().getIntExtra("termId", -1);
        termStart = getIntent().getStringExtra("termStart");
        termEnd = getIntent().getStringExtra("termEnd");
        textName = findViewById(R.id.courseTermText);
        textName.setText(termName);

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabelDate(startText);
            }
        };
        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabelDate(endText);
            }
        };
        startText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddCourse.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddCourse.this, endDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        List<String> statuses = Arrays.asList("In Progress", "Completed", "Dropped","Planning to take");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddCourse.this, android.R.layout.simple_spinner_dropdown_item,statuses);
        spinner.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateLabelDate(EditText date) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendarStart.getTime()));
    }

    public void addCourse(View view) {
        String name = mEditName.getText().toString();
        String start = startText.getText().toString();
        String end = endText.getText().toString();
        String status = spinner.getSelectedItem().toString();
        String insName = instructorName.getText().toString();
        String insPhone = instructorPhone.getText().toString();
        String insEmail = instructorEmail.getText().toString();
        String notes = note.getText().toString();
        Course newCourse = new Course(name, start, end, status, notes, insName, insPhone, insEmail, termId);
        repository.insert(newCourse);

        Intent intent = new Intent(AddCourse.this, CourseList.class);
        intent.putExtra("termName", termName);
        intent.putExtra("termId", termId);
        intent.putExtra("termStart",termStart);
        intent.putExtra("termEnd", termEnd);
        startActivity(intent);

    }
}