package Database;


import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import DAO.AssessmentDao;
import DAO.CourseDao;
import DAO.TermDao;
import Entities.Assessment;
import Entities.Course;
import Entities.Term;

public class Repository {
    private TermDao mTermDao;
    private CourseDao mCourseDao;
    private AssessmentDao mAssessmentDao;
    private List<Term> mAllTerms;
    private List<Course> mAssociatedCourses;
    private List<Assessment> mAssociatedAssessments;
    private Course selectedCourse;
    private Assessment selectedAssessment;
    private Term selectedTerm;
    private int mSelectedCourseId;
    private int mSelectedTermId;
    private int mAssessmentCount;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        ScheduleDatabaseBuilder db = ScheduleDatabaseBuilder.getDatabase(application);
        mTermDao= db.termDAO();
        mCourseDao = db.courseDao();
        mAssessmentDao = db.assessmentDao();

    }
    public List<Term> getAllTerms() {
        databaseExecutor.execute(()-> mAllTerms=mTermDao.getAllTerms());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }
    public void insert(Term term) {
        databaseExecutor.execute(()-> mTermDao.insert(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(()-> mTermDao.update(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        databaseExecutor.execute(()-> mTermDao.delete(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAssociatedCourses(int termId) {
        databaseExecutor.execute(()-> mAssociatedCourses = mCourseDao.getAssociatedCourses(termId));
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

    public void update(Course course) {
        databaseExecutor.execute(()-> mCourseDao.update(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute(()-> mCourseDao.delete(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Course getCourse(int courseId){
        databaseExecutor.execute(()-> selectedCourse = mCourseDao.getCourse(courseId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return selectedCourse;
    }

    public void deleteAssociatedCourses(int termId) {
        databaseExecutor.execute(()->  mCourseDao.deleteAssociatedCourses(termId));
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

    public Term getTerm(int termId) {
        databaseExecutor.execute(()-> selectedTerm = mTermDao.getTerm(termId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return selectedTerm;
    }

    public void insert(Assessment assessment) {
        databaseExecutor.execute(()-> mAssessmentDao.insert(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(()-> mAssessmentDao.update(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute(()-> mAssessmentDao.delete(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Assessment> getAssociatedAssessments(int courseId) {
        databaseExecutor.execute(()-> mAssociatedAssessments = mAssessmentDao.getAssociatedAssessments(courseId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssociatedAssessments;
    }

    public Assessment getAssessment(int assessmentid){
        databaseExecutor.execute(()-> selectedAssessment = mAssessmentDao.getAssessment(assessmentid));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return selectedAssessment;
    }

    public void deleteAssociatedAssessment(int courseId){
        databaseExecutor.execute(()->  mAssessmentDao.deleteAssociatedAssessments(courseId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
