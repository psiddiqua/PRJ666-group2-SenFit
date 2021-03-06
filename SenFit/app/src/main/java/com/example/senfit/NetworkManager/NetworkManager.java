/*
PRJ666 Sen-Fit
init date: March 26th 2021
Author Mitchell Culligan
Version 1.0
NetworkManager
This class is created with the purpose of creating services to interact with the rest api
specifically made for Sen-fit. This class does so through RetroFit. This class implements the singleton deisign pattern
 */
package com.example.senfit.NetworkManager;

import android.app.Application;

import com.example.senfit.NetworkManager.Interceptor.AuthInterceptor;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    public static final String BASE_URL="https://limitless-woodland-96285.herokuapp.com/";
    private  Retrofit retrofit=null;
    private AuthInterceptor authInterceptor;

    private static NetworkManager networkManager=null;

    private NetworkManager(String url){
        this.authInterceptor=new AuthInterceptor(null);
        this.retrofit = new Retrofit.Builder()
                .baseUrl(url)//URL for senfit API
                .addConverterFactory(GsonConverterFactory.create(new
                        GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))//convert JSON to java objects
                .client(new OkHttpClient.Builder().addInterceptor(this.authInterceptor).build())
                .build();
            
    }
    public  <T> T  createNetworkService(Class<T> serviceClass){

        return this.retrofit.create(serviceClass);
    }

    public static NetworkManager getNetworkManager(){
        if(networkManager==null){
            networkManager = new NetworkManager(BASE_URL);
        }

        return networkManager;
    }



    public void addAuthToken(String token){
        this.authInterceptor.setToken(token);
    }
    public void invalidateAuthToken(){
        this.authInterceptor.invlaidateToken();

    }
    /*
    public void addInterceptorToClient(Interceptor interceptor){
        synchronized (this) {
            this.retrofit = this.retrofit.newBuilder()
                    .client(new OkHttpClient.Builder().addInterceptor(interceptor).build())
                    .build();//TODO Lookup add authenticator
        }
>>>>>>> TrainingPlan*/
}
