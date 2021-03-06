/*
PRJ666 Sen-Fit
init date: January 24th 2021
Author Mitchell Culligan
Version 1.0
TrainerDAO class
This interface repersents the data access object of the trainer class
 */
package com.example.senfit.dataContext.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.senfit.dataContext.entities.Trainer;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface TrainerDAO {

    @Insert
    public Completable insertTrainers(Trainer...trainers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTrainers(List<Trainer> trainers);

    @Query("Select * from trainers where trainerId=:id")
    public Trainer getTrainer(long id);//IMPORTANT: Methods that retrieve data from database should
    // never be run from main thread

    @Query("Select * from trainers")
    public List<Trainer> getAllTrainers();//TODO: update return data to Flowable or LiveData if possible

    //TODO: ADD methods for retrieving associated data

    @Query("Select * from trainers where gymLocationId=:gymId")
    public LiveData<List<Trainer>> getTrainersFromGym(int gymId);
}
