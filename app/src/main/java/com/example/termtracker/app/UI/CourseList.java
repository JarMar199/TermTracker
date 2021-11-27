package com.example.termtracker.app.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.termtracker.R;

import Database.Repository;

public class CourseList extends AppCompatActivity {
    String termName, startDate, endDate;
    int termId;
    TextView textName, textStart, textEnd;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        termName = getIntent().getStringExtra("termName");
        termId = getIntent().getIntExtra("termId",-1);
        startDate = getIntent().getStringExtra("termStart");
        endDate = getIntent().getStringExtra("termEnd");
        textName = findViewById(R.id.courseTermText);
        textName.setText(termName);
        textStart = findViewById(R.id.courseTermStartDate);
        textStart.setText("Start Date: " + startDate);
        textEnd = findViewById(R.id.courseTermEndDate);
        textEnd.setText("End Date: " + endDate);
        repository = new Repository(getApplication());
        repository.getAssociatedCourses(termId);
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourse(repository.getAssociatedCourses(termId));

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:

            case R.id.notify:

            case R.id.delete:
        }
        return super.onOptionsItemSelected(item);
    }

    public void addCourse(View view) {
        Intent intent = new Intent(CourseList.this, AddCourse.class);
        intent.putExtra("termName", termName);
        intent.putExtra("termId", termId);
        intent.putExtra("termStart",startDate);
        intent.putExtra("termEnd", endDate);
        startActivity(intent);
    }

    public void editTerm(View view) {
        Intent intent = new Intent(CourseList.this, AddCourse.class);
        intent.putExtra("termName", termName);
        intent.putExtra("termId", termId);
        intent.putExtra("termStart",startDate);
        intent.putExtra("termEnd", endDate);

        startActivity(intent);
    }
}