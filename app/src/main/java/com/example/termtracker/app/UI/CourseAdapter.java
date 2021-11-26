package com.example.termtracker.app.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termtracker.R;

import java.util.List;

import Entities.Course;
import Entities.Term;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourse.get(position);

                    Intent intent = new Intent(context, AssessmentList.class);
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("courseStart", current.getStartDate());
                    intent.putExtra("courseEnd", current.getEndDate());
                    intent.putExtra("courseStatus", current.getStatus());
                    intent.putExtra("courseInsName", current.getInstructorName());
                    intent.putExtra("courseInsPhone", current.getInstructorPhone());
                    intent.putExtra("courseInsEmail", current.getInstructorEmail());
                    intent.putExtra("courseNote", current.getNote());
                    intent.putExtra("courseTermId", current.getTermId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mCourse;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item,parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (mCourse != null) {
            final Course current = mCourse.get(position);
            holder.courseItemView.setText(current.getCourseName());
        }
    }

    @Override
    public int getItemCount() {
        if (mCourse != null)
            return mCourse.size();
        else
            return 0;
    }


    public void setCourse(List<Course> courses) {
        mCourse = courses;
        notifyDataSetChanged();
    }


}
