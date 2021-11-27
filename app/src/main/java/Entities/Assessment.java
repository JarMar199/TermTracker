package Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "assessment_table")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;


    private int courseId;
    private String assessmentName, type, startDate, endDate;


    public Assessment(String assessmentName, String type, String startDate, String endDate, int courseId) {
        this.assessmentName = assessmentName;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseId = courseId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
