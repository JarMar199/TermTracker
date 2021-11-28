package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Entities.Assessment;

@Dao
public interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessment_table WHERE courseId = :courseId")
    List<Assessment> getAssociatedAssessments(int courseId);

    @Query("SELECT COUNT(*) FROM assessment_table WHERE courseId = :courseId")
    int getAssessmentCount(int courseId);

    @Query("SELECT * FROM assessment_table WHERE assessmentId = :assessmentId")
    Assessment getAssessment(int assessmentId);

    @Query("DELETE FROM assessment_table where courseId = :courseId ")
    void deleteAssociatedAssessments(int courseId);
}
