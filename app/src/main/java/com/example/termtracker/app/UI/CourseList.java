package com.example.termtracker.app.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;
import java.util.Locale;

import Database.Repository;
import Entities.Course;
import Entities.Term;

public class CourseList extends AppCompatActivity {
    String termName, startDate, endDate;
    int termId;
    TextView textName, textStart, textEnd;
    Repository repository;;
    Term currentTerm;
    int courseCount;
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
        currentTerm = repository.getTerm(termId);
        courseCount = adapter.getItemCount();

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
                Intent intent= new Intent(CourseList.this,MyReceiver.class);
                intent.putExtra("key",termName + " Starts Today");
                PendingIntent sender=PendingIntent.getBroadcast(CourseList.this, ++MainActivity.numAlert,intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender);

                intent= new Intent(CourseList.this,MyReceiver.class);
                intent.putExtra("key",termName + " Ends Today");
                sender=PendingIntent.getBroadcast(CourseList.this, ++MainActivity.numAlert,intent,0);
                alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, sender);
                Toast.makeText(this, "Term Notifications Set", Toast.LENGTH_LONG).show();
                return true;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseList.this);

                builder.setTitle("WARNING");
                builder.setMessage("Deleting Term will delete all courses and assessments associated with term. \n" + courseCount + " Course(s) associated with term");

                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        List<Course> associatedCourses = repository.getAssociatedCourses(termId);
                        for(Course course : associatedCourses){
                            repository.deleteAssociatedAssessment(course.getCourseId());
                        }
                        repository.deleteAssociatedCourses(termId);
                        repository.delete(currentTerm);
                        Intent intent = new Intent(CourseList.this, TermList.class);
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

    public void addCourse(View view) {
        Intent intent = new Intent(CourseList.this, AddCourse.class);
        intent.putExtra("termName", termName);
        intent.putExtra("termId", termId);
        intent.putExtra("termStart",startDate);
        intent.putExtra("termEnd", endDate);
        startActivity(intent);
    }

    public void editTerm(View view) {
        Intent intent = new Intent(CourseList.this, AddTerm.class);
        intent.putExtra("termName", termName);
        intent.putExtra("termId", termId);
        intent.putExtra("termStart",startDate);
        intent.putExtra("termEnd", endDate);

        startActivity(intent);
    }
}