/*
PRJ666 Sen-Fit
init date: April 6th 2021
Author Mitchell Culligan
Version 1.0
EnrollTrainingPlanViewModel
This is the viewmodel for the enroll training plan use case
 */
package com.example.senfit.trainingPlan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.senfit.dataContext.DatabaseClient;
import com.example.senfit.dataContext.entities.FitnessPortfolio;

import java.util.List;

public class EnrollTrainingPlanViewModel extends ViewModel {

    private int memberId;
    private int fitnessPortfolioId;
    private int gymLocationId;
    private int trainerId;
    private DatabaseClient dbClient;
    private LiveData<List<FitnessPortfolio>> livePortfolioData;


    public EnrollTrainingPlanViewModel(int memberId){
        this.memberId=memberId;
        this.dbClient = DatabaseClient.getInstance();
        this.livePortfolioData= dbClient.getAppDatabase()
                .getFitnessPortfolioDAO()
                .getFitnessPortfolioFromMember(this.memberId);


    }

    public LiveData<List<FitnessPortfolio>> getLivePortfolioData(){
        return this.livePortfolioData;
    }

    public int getMemberId(){
        return this.memberId;
    }
    public void setFitnessPortfolioId(int fitnessPortfolioId){
        this.fitnessPortfolioId=fitnessPortfolioId;
    }
}
