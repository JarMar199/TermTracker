package Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "assessment_table")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

}
