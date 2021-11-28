package com.example.termtracker.app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termtracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Database.Repository;
import Entities.Term;

public class AddTerm extends AppCompatActivity {
    String termName, termStart, termEnd;
    int selectedTermId;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText startText;
    EditText endText;
    private EditText mEditName;
    private TextView mEditID;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    private Repository repository;
    Term currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());
        mEditName = findViewById(R.id.termName);
        startText = findViewById(R.id.termStartDate);
        endText = findViewById(R.id.termEndDate);
        mEditID = findViewById(R.id.termID);

        termName = getIntent().getStringExtra("termName");
        selectedTermId = getIntent().getIntExtra("termId",-1);
        termStart = getIntent().getStringExtra("termStart");
        termEnd = getIntent().getStringExtra("termEnd");
        currentTerm = repository.getTerm(selectedTermId);



        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
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
                new DatePickerDialog(AddTerm.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTerm.this, endDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if(selectedTermId != -1){
            mEditID.setText(String.valueOf(selectedTermId));
            mEditName.setText(termName);
            startText.setText(termStart);
            endText.setText(termEnd);
        }



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


    public void saveTerm(View view) {
        String name = mEditName.getText().toString();
        String start = startText.getText().toString();
        String end = endText.getText().toString();

        if(TextUtils.isEmpty(name)){
            mEditName.setError("Enter Assessment Name");
            return;
        }
        if(TextUtils.isEmpty(start)) {
            Toast.makeText(this, "Enter Start Date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(end)) {
            Toast.makeText(this, "Enter End Date", Toast.LENGTH_SHORT).show();
            return;
        }

        Term newTerm = new Term(name,start,end);
        if(selectedTermId != -1){
            newTerm.setTermId(selectedTermId);
            repository.update(newTerm);
        } else
            repository.insert(newTerm);
        Intent intent = new Intent(AddTerm.this, TermList.class);
        startActivity(intent);
    }
}