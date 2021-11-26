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

import Entities.Assessment;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessment.get(position);

                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("assessmentStart", current.getStartDate());
                    intent.putExtra("assessmentEnd", current.getEndDate());
                    intent.putExtra("assessmentCourseId", current.getCourseId());
                    intent.putExtra("assessmentType", current.getType());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessment;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (mAssessment!= null) {
            final Assessment current = mAssessment.get(position);
            holder.assessmentItemView.setText(current.getAssessmentName());
        }
    }

    @Override
    public int getItemCount() {
        if (mAssessment != null)
            return mAssessment.size();
        else
            return 0;
    }

    public void setAssessment(List<Assessment> assessments) {
        mAssessment = assessments;
        notifyDataSetChanged();
    }
}
