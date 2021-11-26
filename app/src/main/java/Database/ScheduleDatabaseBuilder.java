package Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import DAO.AssessmentDao;
import DAO.CourseDao;
import DAO.TermDao;
import Entities.Assessment;
import Entities.Course;
import Entities.Term;

@Database(entities = {Term.class, Assessment.class, Course.class}, version = 3, exportSchema = false)
public abstract class ScheduleDatabaseBuilder extends RoomDatabase {
    public abstract TermDao termDAO();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

    private static volatile ScheduleDatabaseBuilder INSTANCE;

    static ScheduleDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (ScheduleDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleDatabaseBuilder.class, "MyDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
