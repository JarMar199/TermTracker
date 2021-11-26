package com.example.termtracker.app.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.termtracker.R;

import Database.Repository;

public class AssessmentList extends AppCompatActivity {
    String courseName, startDate, endDate, status, insName, insPhone, insEmail, note;
    int courseId, termId;
    TextView textName, textStart, textEnd, textStatus, textInsName, textInsPhone, textInsEmail, textNote;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

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
        repository = new Repository(getApplication());
        repository.getAssociatedAssessments(courseId);
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessment(repository.getAssociatedAssessments(courseId));
    }

    public void addAssessment(View view) {
    }
}