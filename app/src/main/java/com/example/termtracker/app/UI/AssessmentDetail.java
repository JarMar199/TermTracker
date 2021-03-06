package com.example.termtracker.app.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
import Entities.Assessment;
import Entities.Course;

public class AssessmentDetail extends AppCompatActivity {
    String assessmentName, startDate, endDate, type;
    int courseId, assessmentId;
    Course selectedCourse;
    TextView textName, textStart, textEnd, textType;
    Repository repository;
    Assessment currentAssessment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        currentAssessment = repository.getAssessment(assessmentId);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
            //TODO
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
                Intent intent= new Intent(AssessmentDetail.this,MyReceiver.class);
                intent.putExtra("key",assessmentName + " Starts Today");
                PendingIntent sender=PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert,intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender);

                intent= new Intent(AssessmentDetail.this,MyReceiver.class);
                intent.putExtra("key",assessmentName + " Ends Today");
                sender=PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert,intent,0);
                alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, sender);
                Toast.makeText(this, "Assessment Notifications Set", Toast.LENGTH_LONG).show();
                return true;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentDetail.this);

                builder.setTitle("Confirm");
                builder.setMessage("Delete assessment?");

                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        repository.delete(currentAssessment);
                        Intent intent = new Intent(AssessmentDetail.this, AssessmentList.class);
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
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

        }
        return super.onOptionsItemSelected(item);
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