/*author: Portia siddiqua(107741175)*/

package com.example.senfit.dataContext.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.senfit.dataContext.entities.FitnessClass;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FitnessClassDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertFitnessClass(FitnessClass... classes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFitnessClasses(List<FitnessClass> classes);

    @Query("Select * from FitnessClass where fitnessClassId=:id")
    public FitnessClass getFitnessClass(long id);

    @Query("Select * from fitnessClass")
    public List<FitnessClass> getAllClasses();

}
