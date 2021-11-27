package com.example.termtracker.app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.termtracker.R;

import Database.Repository;

public class AssessmentDetail extends AppCompatActivity {
    String assessmentName, startDate, endDate, type;
    int courseId, assessmentId;
    TextView textName, textStart, textEnd, textType;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        assessmentName = getIntent().getStringExtra("assessmentName");
        startDate = getIntent().getStringExtra("assessmentStart");
        endDate = getIntent().getStringExtra("assessmentEnd");
        type = getIntent().getStringExtra("assessmentType");
        courseId = getIntent().getIntExtra("assessmentCourseId", -1);
        assessmentId = getIntent().getIntExtra("assessmentId", -1);

        textName = findViewById(R.id.assessmentText);
        textName.setText(assessmentName);
        textStart = findViewById(R.id.assessmentStartDate);
        textStart.setText("Start Date: " + startDate);
        textEnd = findViewById(R.id.assessmentEndDate);
        textEnd.setText("End Date: " + endDate);
        textType = findViewById(R.id.assessmentType);
        textType.setText(type);


    }

    public void editTerm(View view) {
    }
}