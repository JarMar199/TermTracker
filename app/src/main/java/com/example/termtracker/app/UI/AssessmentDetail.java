package com.example.termtracker.app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.termtracker.R;

import Database.Repository;
import Entities.Assessment;
import Entities.Course;

public class AssessmentDetail extends AppCompatActivity {
    String assessmentName, startDate, endDate, type;
    int courseId, assessmentId;
    Course selectedCourse;
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

        repository = new Repository(getApplication());
        selectedCourse = repository.getCourse(courseId);
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
        Intent intent = new Intent(AssessmentDetail.this, AddAssessment.class);
        intent.putExtra("assessmentName", assessmentName);
        intent.putExtra("assessmentId", assessmentId);
        intent.putExtra("assessmentStart", startDate);
        intent.putExtra("assessmentEnd", endDate);
        intent.putExtra("assessmentCourseId", courseId);
        intent.putExtra("assessmentType", type);

        intent.putExtra("courseName", selectedCourse.getCourseName());
        intent.putExtra("courseId", selectedCourse.getCourseId());
        intent.putExtra("courseStart", selectedCourse.getStartDate());
        intent.putExtra("courseEnd", selectedCourse.getEndDate());
        intent.putExtra("courseStatus", selectedCourse.getStatus());
        intent.putExtra("courseInsName",selectedCourse.getInstructorName());
        intent.putExtra("courseInsPhone", selectedCourse.getInstructorPhone());
        intent.putExtra("courseInsEmail", selectedCourse.getInstructorEmail());
        intent.putExtra("courseNote", selectedCourse.getNote());
        intent.putExtra("courseTermId", selectedCourse.getTermId());
        startActivity(intent);
    }
}