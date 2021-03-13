package com.example.senfit.dataContext.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.senfit.dataContext.entities.Exercise;

import java.util.List;

@Dao
public interface ExerciseDAO {

    @Query("select * from exercises")
    public LiveData<List<Exercise>> getExercises();

    @Query("select * from exercises LIMIT :lim")
    public LiveData<List<Exercise>> getExercisesWithLim(int lim);

    @Insert
    public void insertExercises(Exercise...exercises);
}
