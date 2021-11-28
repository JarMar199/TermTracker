package com.example.termtracker.app.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import Database.Repository;
import Entities.Term;

public class AssessmentList extends AppCompatActivity {
    String courseName, startDate, endDate, status, insName, insPhone, insEmail, note;
    int courseId, termId, assessmentCount;
    TextView textName, textStart, textEnd, textStatus, textInsName, textInsPhone, textInsEmail, textNote;
    Term selectedTerm;
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
        selectedTerm = repository.getTerm(termId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.notify:
                String format = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                Date startDateNotice = null;
                Date endDateNotice = null;
                try {
                    startDateNotice = sdf.parse(startDate);
                    endDateNotice = sdf.parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = startDateNotice.getTime();
                Long endTrigger = endDateNotice.getTime();
                Intent intent= new Intent(AssessmentList.this,MyReceiver.class);
                intent.putExtra("key",courseName + " Starts Today");
                PendingIntent sender=PendingIntent.getBroadcast(AssessmentList.this, ++MainActivity.numAlert,intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender);

                intent= new Intent(AssessmentList.this,MyReceiver.class);
                intent.putExtra("key",courseName + " Ends Today");
                sender=PendingIntent.getBroadcast(AssessmentList.this, ++MainActivity.numAlert,intent,0);
                alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, sender);
                Toast.makeText(this, "Course Notifications Set", Toast.LENGTH_LONG).show();
                return true;
            case R.id.delete:
        }
        return super.onOptionsItemSelected(item);
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

    public void editCourse(View view) {
        Intent intent = new Intent(AssessmentList.this, AddCourse.class);
        intent.putExtra("termName", selectedTerm.getTermName());
        intent.putExtra("termId", termId);
        intent.putExtra("termStart",selectedTerm.getStartDate());
        intent.putExtra("termEnd", selectedTerm.getEndDate());

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
    }
}