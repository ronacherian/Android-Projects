package com.example.roncherian.mytrips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements TripsRecyclerViewAdapter.OnClick{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DatabaseReference mTripsRef;

    ChildEventListener childEventListener;
    ValueEventListener valueEventListener;

    ArrayList<TripAndPlaces> tripAndPlaces = new ArrayList<>();

    ArrayList<Trip> trips = new ArrayList<>();

    static String googleAPIKey = "AIzaSyDbws6AUVoniCqEnwlXE6_pd4P46dBLYew";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTripsRef = FirebaseDatabase.getInstance().getReference("trips");

        ImageButton addTripButton = (ImageButton)findViewById(R.id.imageButtonAddTrip);

        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewTrips);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new TripsRecyclerViewAdapter(tripAndPlaces,MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                trips.add(trip);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Trip post = dataSnapshot.getValue(Trip.class);
                trips.remove(post);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mTripsRef.addChildEventListener(childEventListener);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tripAndPlaces.clear();

                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()) {

                    Trip trip=postSnapshot.getValue(Trip.class);
                    TripAndPlaces tripAndPlaces1 = new TripAndPlaces();
                    tripAndPlaces1.setTripId(trip.getTripId());
                    if (tripAndPlaces.contains(tripAndPlaces1)){
                        tripAndPlaces.remove(tripAndPlaces);
                    }
                    tripAndPlaces1.setTripName(trip.getTripName());
                    tripAndPlaces1.setTripObj(true);
                    tripAndPlaces1.setTripGeoId(trip.getTripGeoId());
                    tripAndPlaces1.setCityName(trip.getCityName());

                    tripAndPlaces.add(tripAndPlaces1);
                    for (Places places: trip.getPlaces()){
                        TripAndPlaces tripAndPlaces2 = new TripAndPlaces();
                        tripAndPlaces2.setTripObj(false);
                        tripAndPlaces2.setPlaceId(places.getPlaceId());
                        tripAndPlaces2.setTripId(trip.getTripId());
                        tripAndPlaces2.setPlaceName(places.getPlcaeName());
                        tripAndPlaces.add(tripAndPlaces2);
                    }

                }

                mRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewTrips);

                mRecyclerView.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false);
                mRecyclerView.setLayoutManager(mLayoutManager);


                // specify an adapter (see also next example)
                mAdapter = new TripsRecyclerViewAdapter(tripAndPlaces,MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mTripsRef.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTripsRef.removeEventListener(childEventListener);
    }

    @Override
    public void OnItemClicked(int position, TripAndPlaces place) {

    }

    @Override
    public void addPlacesClicked(int position, TripAndPlaces place) {
        getGeoCoordinates(position,place);
        //getNearByPlaces(position,place);
    }

    private void getGeoCoordinates(final int position, TripAndPlaces place){
        String key = "AIzaSyB2ApzDSEvFxS9P-iEWwdwhEsTMhURtOR4";
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("place")
                .addPathSegment("details")
                .addPathSegment("json")
                .addQueryParameter("placeid", place.getTripGeoId())
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


                    trips.get(position).setLat(lat);
                    trips.get(position).setLng(lng);
                    //Collections.sort(msgs,Messages.sortById);

                    Intent intent = new Intent(MainActivity.this, AddPlacesActivity.class);
                    intent.putExtra("trip",trips.get(position));
                    startActivity(intent);


                } catch (JSONException e) {

                }

            }
        });

    }

    public void getNearByPlaces(int position, TripAndPlaces place) {
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
                //places.removeAll(places);

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


//                        places.add(place);


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
                            //mLayoutManager = new LinearLayoutManager(AddTripActivity.this, LinearLayoutManager.VERTICAL,false);
                            mRecyclerView.setLayoutManager(mLayoutManager);




                            // specify an adapter (see also next example)
                           // mAdapter = new SelectPlacesRecyclerViewAdapter(places,AddTripActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                        }
                    });

                } catch (JSONException e) {

                }

            }
        });
    }
}
