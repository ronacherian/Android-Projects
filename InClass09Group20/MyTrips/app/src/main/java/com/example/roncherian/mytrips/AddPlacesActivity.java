package com.example.roncherian.mytrips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddPlacesActivity extends AppCompatActivity {

    Trip trip = new Trip();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        trip = (Trip) getIntent().getExtras().get("trip");
        getPlaces();
    }

    private void getPlaces(){

        String key = "AIzaSyB2ApzDSEvFxS9P-iEWwdwhEsTMhURtOR4";
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("place")
                .addPathSegment("nearbysearch")
                .addPathSegment("json")
                .addQueryParameter("radius", "1000")
                .addQueryParameter("location", trip.getLat()+","+trip.getLng())
                .addQueryParameter("key", key)
                .build();
        Request request = new Request.Builder()
                .url(url).build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String result = response.body().string();
                //places.removeAll(places);

                try {
                    JSONObject obj = new JSONObject(result);
                    String lat = ((JSONObject) obj).getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat");
                    String lng = ((JSONObject) obj).getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lng");


                } catch (JSONException e) {

                }

            }
        });
    }
}
