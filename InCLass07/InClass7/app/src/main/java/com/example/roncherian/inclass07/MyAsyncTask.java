package com.example.roncherian.inclass07;

import android.os.AsyncTask;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by roncherian on 23/10/17.
 */

public class MyAsyncTask extends AsyncTask<String,Void,ArrayList<Email>> {

    IMovie iMovie;

    public MyAsyncTask(IMovie iMovie){
        this.iMovie = iMovie;
    }

    @Override
    protected void onPostExecute(ArrayList<Email> musicTracks) {

        iMovie.showResults(musicTracks);
    }

    @Override
    protected ArrayList<Email> doInBackground(String... strings) {
        ArrayList<Email>movies = new ArrayList<Email>();
        String urlString = "";
        String searchString = "";
        try {
            urlString = strings[0];
            searchString = strings[1];
            URL url = new URL(urlString);
//method=track.search&track=Believe&api_key=b395df93fa2168371596bb1a129d34b1&format=json
            RequestParams requestParams = new RequestParams("GET",urlString);

            requestParams.addParam("query",searchString);
            requestParams.addParam("page","1");



            requestParams.addParam("api_key","f216622c39b3cb0b8ecfadc4d28c3d49");
            //requestParams.addParam("format","json");

            HttpURLConnection connection = (HttpURLConnection) requestParams.setupConnection();
            connection.connect();
            if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                /*if (null != similarSearchAPI && similarSearchAPI.equals(TrackDetailsActivity.SIMILAR_TRACKS)){
                    movies = (ArrayList<MusicTrack>) MusicSearchUtil.MyJSONParser.parseSimilarMusicTracks(stringBuilder.toString());
                } else {
                    movies = (ArrayList<MusicTrack>) MusicSearchUtil.MyJSONParser.parseMovieTracks(stringBuilder.toString());
                }*/
                movies = (ArrayList<Email>) MyUtils.MyJSONParser.parseMovieTracks(stringBuilder.toString());

                //iF XML

                //movies = (ArrayList<Email>) MyUtils.MyXmlParser.parseRecipes("");

                return movies;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<Email>();

    }

    public  interface IMovie
    {
        void showResults(ArrayList<Email> results);
        void startProgressbar();
    }
}
