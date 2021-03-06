/*
PRJ666 Sen-Fit
init date: January 24th 2021
Author Mitchell Culligan
Version 1.0
MemberDAO class
This interface repersents the data access object of the member class
 */
package com.example.senfit.dataContext.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.senfit.dataContext.entities.Member;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MemberDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertMember(Member member);//returns new rownumber

    //use maybe
    @Query("Select * from members where member_id=:id")
    public Member getMember(int id);//IMPORTANT: Methods that retrieve data from database should
    // never be run from main thread

    @Query("Select * from members where email=:email")
    public List<Member> getMembersFromEmail(String email);
    //This method retrieves all members that have the email of the parameter
    //used for user validation


    @Update(onConflict = OnConflictStrategy.ABORT)
    public Single<Integer> updateMember(Member member);

    @Query("Select first_name || ' ' || last_name from members where member_id=:id")
    public Single<String> getMemberName(int id);
   //TODO: ADD methods for retrieving associated data
}
