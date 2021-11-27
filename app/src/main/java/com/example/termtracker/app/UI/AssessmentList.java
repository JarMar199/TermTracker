package com.example.termtracker.app.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termtracker.R;

import java.util.Objects;

import Database.Repository;

public class AssessmentList extends AppCompatActivity {
    String courseName, startDate, endDate, status, insName, insPhone, insEmail, note;
    int courseId, termId, assessmentCount;
    TextView textName, textStart, textEnd, textStatus, textInsName, textInsPhone, textInsEmail, textNote;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        courseName = getIntent().getStringExtra("courseName");
        startDate = getIntent().getStringExtra("courseStart");
        endDate = getIntent().getStringExtra("courseEnd");
        status = getIntent().getStringExtra("courseStatus");
        insName = getIntent().getStringExtra("courseInsName");
        insPhone = getIntent().getStringExtra("courseInsPhone");
        insEmail = getIntent().getStringExtra("courseInsEmail");
        note = getIntent().getStringExtra("courseNote");
        courseId = getIntent().getIntExtra("courseId", -1);
        termId = getIntent().getIntExtra("courseTermId", -1);

        textName = findViewById(R.id.assessmentCourseText);
        textName.setText(courseName);
        textStart = findViewById(R.id.assessmentCourseStartDate);
        textStart.setText("Start Date: " + startDate);
        textEnd = findViewById(R.id.assessmentCourseEndDate);
        textEnd.setText("End Date: " + endDate);
        textStatus = findViewById(R.id.assessmentCourseStatus);
        textStatus.setText(status);
        textInsName = findViewById(R.id.assessmentCourseInsName);
        textInsName.setText(insName);
        textInsPhone = findViewById(R.id.assessmentCourseInsPhone);
        textInsPhone.setText(insPhone);
        textInsEmail = findViewById(R.id.assessmentCourseInsEmail);
        textInsEmail.setText(insEmail);
        textNote = findViewById(R.id.assessmentCourseNote);
        textNote.setText(note);

        repository = new Repository(getApplication());
        repository.getAssociatedAssessments(courseId);

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessment(repository.getAssociatedAssessments(courseId));
        assessmentCount = adapter.getItemCount();

    }

    public void addAssessment(View view) {

        if (assessmentCount < 5) {
            Intent intent = new Intent(AssessmentList.this, AddAssessment.class);
            intent.putExtra("courseName", courseName);
            intent.putExtra("courseId", courseId);
            intent.putExtra("courseStart", startDate);
            intent.putExtra("courseEnd", endDate);
            intent.putExtra("courseStatus", status);
            intent.putExtra("courseInsName", insName);
            intent.putExtra("courseInsPhone", insPhone);
            intent.putExtra("courseInsEmail", insEmail);
            intent.putExtra("courseNote", note);
            intent.putExtra("courseTermId", termId);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),"Only 5 assessments may be added",Toast.LENGTH_LONG).show();
        }
    }
}