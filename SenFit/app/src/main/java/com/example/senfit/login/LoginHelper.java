/*
 * author: Portia siddiqua(107741175)
 *
 * */

package com.example.senfit.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.util.Log;

import com.example.senfit.NetworkManager.Interceptor.AuthInterceptor;
import com.example.senfit.NetworkManager.NetworkManager;
import com.example.senfit.NetworkManager.NetwrokServices.LoginService;
import com.example.senfit.dataContext.DatabaseClient;
import com.example.senfit.dataContext.entities.Member;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * This class fetch login info from database and compare with the user input
 */
public class LoginHelper {

    private static final String SHARED_PREFS = "senfit_sharedPrefs";
    private static final String KEY = "memberId";

    public LoginHelper() {}

    /*
    Compare user credentials
    context - app context
    email -  input email
    password - input password
    comparisonCallback - interface to propagate result
     */
    public static void compareEmailPass(Context context, String email, String password,
                                        LoginActivity.ComparisonCallback comparisonCallback) {
        LoginService loginService = NetworkManager.getNetworkManager().createNetworkService(LoginService.class);
        Call<Member> memberCall = loginService.login(new LoginBody(email,password));
        memberCall.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                    if(!response.isSuccessful()){
                        comparisonCallback.isValid(false, true);
                        return;
                    }
                    Member member = response.body();
                    NetworkManager.getNetworkManager().addInterceptorToClient(new AuthInterceptor(member.getToken()));
                    //retrieve token for member
                    DatabaseClient.dbExecutors.execute(()->{
                        DatabaseClient.initDB(context).getAppDatabase()
                                .getMemberDao()
                                .insertMember(member);
                    });
                comparisonCallback.isValid(true, true);
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                Log.e("login_api_err",t.getMessage());
            }
        });
        /*new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                List<Member> memberList = DatabaseClient.initDB(context)
                        .getAppDatabase().getMemberDao().getMembersFromEmail(email);

                if (memberList == null || memberList.isEmpty()) {
                    comparisonCallback.isValid(false, true);
                    return null;
                }

                int memberId = memberList.get(0).getMember_id();
                setMemberId(context, memberId);

                String emailFromDb = memberList.get(0).getEmail();
                String passWordFromDb = memberList.get(0).getPassword();

                boolean isValidUser = email.equalsIgnoreCase(emailFromDb);
                boolean isValidPass = password.equals(passWordFromDb);

                comparisonCallback.isValid(isValidUser, isValidPass);
                return null;
            }
        }.execute();*/
    }

    public static void setMemberId(Context context, int memberId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, memberId);
        editor.apply();
    }

    public static int getMemberId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY, -1);
    }
}
