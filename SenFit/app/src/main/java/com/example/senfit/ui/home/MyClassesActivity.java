/*Portia Siddiqua 107741175*/

package com.example.senfit.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senfit.R;
import com.example.senfit.dataContext.DatabaseClient;
import com.example.senfit.dataContext.entities.FitnessClass;
import com.example.senfit.dataContext.entities.GymClass;
import com.example.senfit.dataContext.entities.OnlineClass;
import com.example.senfit.dataContext.entities.Trainer;
import com.example.senfit.ui.inperson.InpersonClassData;
import com.example.senfit.ui.online.OnlineClassAdapter;
import com.example.senfit.uiHelpers.DateTimeFormatHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MyClassesActivity extends AppCompatActivity {


    protected RecyclerView mRecyclerView;

    protected MyClassesAdapter mAdapter;

    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclasses);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMyClasses);

        mLayoutManager = new LinearLayoutManager(this);

        loadOnlineClass();


    }


    private void loadOnlineClass() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<OnlineClass> onlineClasses = DatabaseClient.getInstance()
                        .getAppDatabase()
                        .getOnlineClassDao()
                        .getOnlineClasses();

                List<InpersonClassData> dataSet = new ArrayList<>();

                for(OnlineClass onlineClass: onlineClasses) {

                    if (!onlineClass.isEnrolled()) continue;

                    InpersonClassData data = new InpersonClassData();
                    data.setGymClassId(onlineClass.getOnlineClassId());
                    FitnessClass fClass = DatabaseClient
                            .getInstance()
                            .getAppDatabase()
                            .getFitnessClassDao()
                            .getFitnessClass(onlineClass.getFitnessClassId());
                    data.setClasName(fClass.getFitnessClassName());
                    data.setDate(DateTimeFormatHelper.formatDate(onlineClass.getClassDate()));
                    data.setEnrolled(false);

                    String startDate = DateTimeFormatHelper.formatTime(onlineClass.getStartTime());


                    String endDate = DateTimeFormatHelper.formatTime(onlineClass.getEndTime());


                    data.setTime(startDate+ " - "
                            +endDate);

                    Trainer trainer = DatabaseClient
                            .getInstance()
                            .getAppDatabase()
                            .getTrainerDao()
                            .getTrainer(onlineClass.getTrainerId());
                    data.setInstructorName(trainer.getFirstName() + " " + trainer.getLastName());
                    dataSet.add(data);
                }


                List<GymClass> gymClasses = DatabaseClient.getInstance()
                        .getAppDatabase()
                        .getGymClassDao()
                        .getGymClasses();

                for(GymClass gymClass: gymClasses) {

                    if (!gymClass.getEnrolled()) continue;

                    InpersonClassData data = new InpersonClassData();
                    data.setGymClassId(gymClass.getGymClassId());
                    FitnessClass fClass = DatabaseClient
                            .getInstance()
                            .getAppDatabase()
                            .getFitnessClassDao()
                            .getFitnessClass(gymClass.getFitnessClassId());
                    data.setClasName(fClass.getFitnessClassName());
                    data.setDate(DateTimeFormatHelper.formatDate(gymClass.getClassDate()));
                    data.setEnrolled(gymClass.getEnrolled());



                    String startDate = DateTimeFormatHelper.formatTime(gymClass.getStartTime());


                    String endDate = DateTimeFormatHelper.formatTime(gymClass.getEndTime());


                    data.setTime(startDate+ " - "
                            +endDate);



                    Trainer trainer = DatabaseClient
                            .getInstance()
                            .getAppDatabase()
                            .getTrainerDao()
                            .getTrainer(gymClass.getTrainerId());
                    data.setInstructorName(trainer.getFirstName() + " " + trainer.getLastName());
                    dataSet.add(data);






                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new MyClassesAdapter(dataSet);

                        mRecyclerView.setAdapter(mAdapter);

                        mRecyclerView.setAdapter(mAdapter);

                        mRecyclerView.setLayoutManager(mLayoutManager);
                    }
                });

                return null;
            }
        }.execute();
    }

}
