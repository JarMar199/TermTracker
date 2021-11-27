package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.zip.CheckedOutputStream;

import Entities.Course;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM course_table")
    List<Course> getAllCourses();

    @Query("SELECT * FROM course_table WHERE termId = :termId")
    List<Course> getAssociatedCourses(int termId);

    @Query("SELECT * FROM course_table WHERE courseId = :courseId")
    Course getCourse(int courseId);
}
