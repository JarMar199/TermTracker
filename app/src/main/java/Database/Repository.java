package Database;


import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import DAO.CourseDao;
import DAO.TermDao;
import Entities.Course;
import Entities.Term;

public class Repository {
    private TermDao mTermDao;
    private CourseDao mCourseDao;
    private List<Term> mAllTerms;
    private List<Course> mAssociatedCourses;
    private int mSelectedTermId;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        ScheduleDatabaseBuilder db = ScheduleDatabaseBuilder.getDatabase(application);
        mTermDao= db.termDAO();
        mCourseDao = db.courseDao();

    }
    public List<Term> getAllTerms() {
        databaseExecutor.execute(()->{
            mAllTerms=mTermDao.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }
    public void insert(Term term) {
        databaseExecutor.execute(()->{
            mTermDao.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAssociatedCourses(int termId) {
        databaseExecutor.execute(()->{
            mAssociatedCourses = mCourseDao.getAssociatedCourses(termId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssociatedCourses;
    }

    public void insert(Course course) {
        databaseExecutor.execute(()-> mCourseDao.insert(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getSelectedTermId(String termName) {
        databaseExecutor.execute(()-> mSelectedTermId = mTermDao.selectTermId(termName));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mSelectedTermId;
    }

}
