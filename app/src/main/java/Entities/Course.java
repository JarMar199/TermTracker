package Entities;

import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity (tableName = "course_table")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int termId;
    private String courseName, startDate, endDate, status, note, instructorName, instructorPhone,instructorEmail;
    //List<Assessment> assessmentList;



    public Course(String courseName, String startDate, String endDate, String status, String note, String instructorName, String instructorPhone, String instructorEmail, int termId) {

        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.note = note;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.termId = termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
    /*
    public List<Assessment> getAssessmentList() {
        return assessmentList;
    }

    public void addAssessment(Assessment assessment) {
        assessmentList.add(assessment);
    }

     */
}
