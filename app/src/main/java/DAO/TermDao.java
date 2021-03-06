package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Entities.Term;

@Dao
public interface TermDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("Select * FROM term_table ORDER BY termID ASC")
    List<Term> getAllTerms();

    @Query("SELECT termId from term_table WHERE termName = :selectedTerm")
    int selectTermId(String selectedTerm);

    @Query("SELECT * FROM term_table WHERE termId = :termId")
    Term getTerm(int termId);
}
