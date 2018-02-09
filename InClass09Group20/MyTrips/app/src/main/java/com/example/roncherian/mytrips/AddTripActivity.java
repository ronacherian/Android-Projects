package com.example.roncherian.mytrips;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddTripActivity extends AppCompatActivity implements SelectPlacesRecyclerViewAdapter.OnClick{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Places>places = new ArrayList<>();

    DatabaseReference mPlacesRef, mTripsRef;

    Trip trip = new Trip();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        mPlacesRef = FirebaseDatabase.getInstance().getReference("places");
        mTripsRef = FirebaseDatabase.getInstance().getReference("trips");

        final EditText editText = (EditText)findViewById(R.id.editTextSearch);
        final EditText editTextTripName = (EditText)findViewById(R.id.editTextTripName);

        Button searchCity = (Button)findViewById(R.id.buttonSearchCity);
        searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText.getText().length() > 0)
                    getPlaces(editText.getText().toString());
                else
                    Toast.makeText(AddTripActivity.this,"Enter Search Text",Toast.LENGTH_SHORT).show();
            }
        });

        Button addNewTrip = (Button)findViewById(R.id.buttonAddTrip);
        addNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tripId=mTripsRef.push().getKey();

                trip.setTripId(tripId);
                trip.setTripName(editTextTripName.getText().toString());


                ArrayList<Places>placesWithTripId = new ArrayList<>();
                for (Places place: places){
                    String placeId=mPlacesRef.push().getKey();
                    Places places = new Places();
                    places.setTripId(tripId);
                    places.setPlaceId(placeId);
                    places.setPlcaeName(place.getPlcaeName());
                    placesWithTripId.add(places);
                    //mPlacesRef.child(placeId).setValue(places);
                }
                places = placesWithTripId;
                //trip1.setPlaces(places);
                mTripsRef.child(tripId).setValue(trip);
                finish();
                //post.setText("");
            }
        });
    }

    public void getPlaces(String place) {
        OkHttpClient client = new OkHttpClient();
        // String token=sp.getString("token",null);

        String key = "AIzaSyB2ApzDSEvFxS9P-iEWwdwhEsTMhURtOR4";
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("place")
                .addPathSegment("autocomplete")
                .addPathSegment("json")
                .addQueryParameter("types", "(cities)")
                .addQueryParameter("input", place)
                .addQueryParameter("key", key)
                .build();
        Request request = new Request.Builder()
                .url(url).build();
        //
                //.addHeader("key", key).addHeader("types", "(cities)")
                //.addHeader("input", "charlotte,nc")

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String result = response.body().string();
                places.removeAll(places);

                try {
                    JSONObject obj = new JSONObject(result);

                    JSONArray array = obj.getJSONArray("predictions");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject threadobj = array.getJSONObject(i);
                        Places place = new Places();

                        if (threadobj.getString("description").length() > 0) {
                            place.setPlcaeName(threadobj.getString("description"));
                        }

                        if (threadobj.getString("id").length() > 0) {
                            place.setPlaceId(threadobj.getString("id"));
                        }

                        if (threadobj.getString("place_id").length() > 0) {
                            trip.setTripGeoId(threadobj.getString("place_id"));
                        }


//                        if (threadobj.getInt("user_id") > 0) {
//                            messages.setUserId(threadobj.getInt("user_id")+"");
//                        }
//                        if (threadobj.getInt("id") > 0) {
//                            messages.setId(threadobj.getInt("id")+"");
//                        }
//                        if (threadobj.getString("message").length() > 0) {
//                            messages.setMessage(threadobj.getString("message"));
//                        }
//                        if (threadobj.getString("created_at").length() > 0) {
//                            messages.setCreatedAt(threadobj.getString("created_at"));
//
//                        }
                        places.add(place);


                    }
                    //Collections.sort(msgs,Messages.sortById);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewListSearchedPlaces);
                            // use this setting to improve performance if you know that changes
                            // in content do not change the layout size of the RecyclerView
                            mRecyclerView.setHasFixedSize(true);

                            // use a linear layout manager
                            mLayoutManager = new LinearLayoutManager(AddTripActivity.this, LinearLayoutManager.VERTICAL,false);
                            mRecyclerView.setLayoutManager(mLayoutManager);




                            // specify an adapter (see also next example)
                            mAdapter = new SelectPlacesRecyclerViewAdapter(places,AddTripActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                        }
                    });

                } catch (JSONException e) {

                }

            }
        });
    }

    @Override
    public void OnItemClicked(int position, Places place) {

        places.removeAll(places);
        //places.add(place);
        //trip.setTripGeoId(place.getPlaceId());
        trip.setCityName(place.getPlcaeName());
        EditText editText = (EditText)findViewById(R.id.editTextSearch);
        editText.setText(place.getPlcaeName());
    }
}
