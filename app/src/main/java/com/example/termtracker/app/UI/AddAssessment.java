package com.example.termtracker.app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import Entities.Assessment;

public class AddAssessment extends AppCompatActivity {
    String courseName, courseStartDate, courseEndDate, status, insName, insPhone, insEmail, note;
    int courseId, termId;
    TextView mTextId, courseTextView;
    EditText mEditName, mEditStart, mEditEnd;
    final Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    private Repository repository;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        repository = new Repository(getApplication());
        mEditName = findViewById(R.id.assessmentName);
        mTextId = findViewById(R.id.assessmentID);
        mEditStart = findViewById(R.id.assessmentStartDate);
        mEditEnd = findViewById(R.id.assessmentEndDate);
        spinner = findViewById(R.id.assessmentStatusSpinner);

        courseName = getIntent().getStringExtra("courseName");
        courseStartDate = getIntent().getStringExtra("courseStart");
        courseEndDate = getIntent().getStringExtra("courseEnd");
        status = getIntent().getStringExtra("courseStatus");
        insName = getIntent().getStringExtra("courseInsName");
        insPhone = getIntent().getStringExtra("courseInsPhone");
        insEmail = getIntent().getStringExtra("courseInsEmail");
        note = getIntent().getStringExtra("courseNote");
        courseId = getIntent().getIntExtra("courseId", -1);
        termId = getIntent().getIntExtra("courseTermId", -1);

        courseTextView = findViewById(R.id.assessmentCourseText);
        courseTextView.setText(courseName);

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabelDate(mEditStart);
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

                updateLabelDate(mEditEnd);
            }
        };
        mEditStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAssessment.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mEditEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAssessment.this, endDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        List<String> assessmentTypes = Arrays.asList("Performance", "Objective");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAssessment.this, android.R.layout.simple_spinner_dropdown_item,assessmentTypes);
        spinner.setAdapter(adapter);
    }

    private void updateLabelDate(EditText date) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendarStart.getTime()));
    }

    public void addAssessment(View view) {
        String name, start, end, type;
        name = mEditName.getText().toString();
        start = mEditStart.getText().toString();
        end = mEditEnd.getText().toString();
        type = spinner.getSelectedItem().toString();
        Assessment newAssessment = new Assessment(name, type, start, end, courseId);
        repository.insert(newAssessment);

        Intent intent = new Intent(AddAssessment.this, AssessmentList.class);
        intent.putExtra("courseName", courseName);
        intent.putExtra("courseId", courseId);
        intent.putExtra("courseStart", courseStartDate);
        intent.putExtra("courseEnd", courseEndDate);
        intent.putExtra("courseStatus", status);
        intent.putExtra("courseInsName",insName);
        intent.putExtra("courseInsPhone", insPhone);
        intent.putExtra("courseInsEmail", insEmail);
        intent.putExtra("courseNote", note);
        intent.putExtra("courseTermId", termId);
        startActivity(intent);

    }
}