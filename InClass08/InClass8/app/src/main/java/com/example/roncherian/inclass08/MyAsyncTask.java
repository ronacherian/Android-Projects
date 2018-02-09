package com.example.roncherian.inclass08;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roncherian on 30/10/17.
 */

public class MyAsyncTask extends AsyncTask<String,Void,ArrayList<ItunesApp>> {
    IMovie iMovie;

    public MyAsyncTask(IMovie iMovie){
        this.iMovie = iMovie;
    }

    @Override
    protected void onPostExecute(ArrayList<ItunesApp> itunesApps) {

        iMovie.showResults(itunesApps);
    }

    @Override
    protected ArrayList<ItunesApp> doInBackground(String... strings) {
        ArrayList<ItunesApp>movies = new ArrayList<ItunesApp>();
        String urlString = "";
        String searchString = "";
        try {

            List<String>recipeList = strings[0];
            String dish = recipeList.get(0);
            StringBuilder ingredients = new StringBuilder();
            recipeList.remove(0);
            for(String recipe: recipeList){
                if (ingredients.length() != 0)
                    ingredients.append(",").append(recipe);
                else{
                    ingredients.append(recipe);
                }
            }
            Log.d("demoFound",ingredients.toString() + dish);


            RequestParams requestParams = new RequestParams("GET","http://www.recipepuppy.com/api");
            requestParams.addParam("q",dish);
            requestParams.addParam("i",ingredients.toString());

            HttpURLConnection connection = (HttpURLConnection) requestParams.setupConnection();
            connection.connect();
            if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }

                movies = (ArrayList<ItunesApp>) MyUtils.MyJSONParser.parseMovieTracks(stringBuilder.toString());

                return movies;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<ItunesApp>();

    }


    public  interface IMovie
    {
        void showResults(ArrayList<ItunesApp> results);
        void startProgressbar();
    }
}
