/*
PRJ666 Sen-Fit
init date: Feb 22nd 2021
Author Mitchell Culligan
Version 1.0
Add fitness portfolio activity
This activity allows the user to add a fitness portfolio to their account
 */
package com.example.senfit.fitnessPortfolio.addFitnessPortfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.senfit.R;
import com.example.senfit.fitnessResult.addFitnessResults.AddFitnessResultsActivity;
import com.example.senfit.ui.inperson.SenFitActivity;
import com.example.senfit.uiHelpers.DialogBoxHelper;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddFitnessPortfolioActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String MEMBER_TAG="member__tag";
    private AddFitnessPortfolioViewModel portfolioViewModel;
    private EditText heightIn,weigthIn, healthConcerns;
    private Button startBtn;
    private int memberId;
    private long portfolioId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fitness_portfolio);
        if(savedInstanceState==null) {
            Intent args = getIntent();
            this.memberId=args.getIntExtra(MEMBER_TAG,-1);

        }else{
            this.memberId=savedInstanceState.getInt(MEMBER_TAG,-1);
        }

        this.portfolioViewModel = new ViewModelProvider(this).get(AddFitnessPortfolioViewModel.class);

        this.heightIn=findViewById(R.id.height_input);
        this.weigthIn=findViewById(R.id.weight_input);
        this.startBtn=findViewById(R.id.start_buttonId);
        this.healthConcerns=findViewById(R.id.health_concerns_input);

        this.startBtn.setOnClickListener(this);

       /* this.portfolioViewModel.getRowNumData().observe(this,(row)->{// observe weather portfolio has been inserted
            if(row!=null){
                portfolioId=row;
                Intent args = new Intent(this, AddFitnessResultsActivity.class);
                Bundle extras =new Bundle();
                extras.putLong(AddFitnessResultsActivity.ADD_RESULT_TAG,portfolioId);
                args.putExtras(extras);
                startActivity(args);
                finish();
                //TODO:Start exercise result activity
            }

        });*/

    }

    @Override
    public void onClick(View v) {
        startBtn.setEnabled(false);
        String heightStr=null, weightStr=null,healthStr;
        heightStr=this.heightIn.getText().toString();
        weightStr=this.weigthIn.getText().toString();
        healthStr=this.healthConcerns.getText().toString();
        if(heightStr.trim().length()>0 && TextUtils.isDigitsOnly(heightStr)){
            if(weightStr.trim().length()>0 && TextUtils.isDigitsOnly(weightStr)){
                this.portfolioViewModel.getPortfolio().setHeight(Integer.parseInt(heightStr));
                this.portfolioViewModel.getPortfolio().setWeight(Integer.parseInt(weightStr));
                this.portfolioViewModel.getPortfolio().setHealthConcerns(healthStr.isEmpty()?"N/A":healthStr);
                this.portfolioViewModel.getPortfolio().setMemberId(this.memberId);
                this.portfolioViewModel.insertPortfolio()
                .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Integer>() {
                    private Disposable disposable;


                    @Override
                    public void onSubscribe(io.reactivex.rxjava3.disposables.@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposable=d;
                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull Integer row) {
                        portfolioId=row;
                        Intent args = new Intent(AddFitnessPortfolioActivity.this, AddFitnessResultsActivity.class);
                        Bundle extras =new Bundle();
                        extras.putLong(AddFitnessResultsActivity.ADD_RESULT_TAG,portfolioId);
                        args.putExtras(extras);
                        startActivity(args);
                        finish();
                        if(!disposable.isDisposed())
                            disposable.dispose();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        if(!disposable.isDisposed())
                            disposable.dispose();
                        startBtn.setEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getApplicationContext(),"Completed",Toast.LENGTH_LONG).show();
                        if(!disposable.isDisposed())
                            disposable.dispose();

                    }
                });//insert portfolio into database
        /*        Intent intent = new Intent(this, SenFitActivity.class);//go back to main class for now
              //  args.putExtra(AddFitnessResultsActivity.ADD_RESULT_TAG,portfolioId);
                Toast.makeText(this,R.string.portfolio_added,Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
         */
            }
            else
            {
                DialogBoxHelper.createPositiveDialog(this,R.string.fitness_portfolio,
                        R.string.weight_errMssg,null)
                        .show();
                startBtn.setEnabled(true);
            }
        }
        else{
            DialogBoxHelper.createPositiveDialog(this,R.string.fitness_portfolio,R.string.height_errMssg,null)
                    .show();
            startBtn.setEnabled(true);
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MEMBER_TAG,this.memberId);
    }
}