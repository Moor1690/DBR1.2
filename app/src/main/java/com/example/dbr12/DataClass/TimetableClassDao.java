package com.example.dbr12.DataClass;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dbr12.Timetable;

import java.util.List;

@Dao
public interface TimetableClassDao {

    @Query("SELECT MAX(id) FROM timetable")
    int getMax();

    @Query("SELECT * FROM timetable")
    List<Timetable> getAll();

    @Query("SELECT * FROM timetable WHERE id = :id")
    Timetable getById(long id);

    @Insert
    void insert(Timetable timetable);

    /*@Insert
    void add(DataClass dataclass);*/

    @Update
    void update(Timetable timetable);

    @Delete
    void delete(Timetable timetable);


/*    @Query("select * from timetable where id ==:id ")
    public Timetable getTimetable(int id);*/

}
