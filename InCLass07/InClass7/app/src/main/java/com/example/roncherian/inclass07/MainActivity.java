package com.example.roncherian.inclass07;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements  MyAsyncTask.IMovie{

    DatabaseDataManager databaseDataManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Email> emailArrayList = new ArrayList<Email>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseDataManager = new DatabaseDataManager(this);
        databaseDataManager.saveNote(new Note("subj1","text1"));
        databaseDataManager.saveNote(new Note("subj2","text2"));
        databaseDataManager.saveNote(new Note("subj3","text3"));

        List<Note> notes = databaseDataManager.getAll();
        Log.d("demo",notes.toString());

        for (int i=0;i<10;i++){
            Email email = new Email("email"+i, "summary"+i, "subject"+i);
            emailArrayList.add(email);
        }
        mRecyclerView  = (RecyclerView)findViewById(R.id.my_recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyRVAdapter(emailArrayList);
        mRecyclerView.setAdapter(mAdapter);
        new MyAsyncTask(MainActivity.this).execute("");

        /*
        if (null != movie && null != movie.getPosterPath() && movie.getPosterPath().length()>0){
            Picasso.with(this.mContext).load("http://image.tmdb.org/t/p/w154"+movie.getPosterPath()).into(musicTrackImageView);
        }
         */
    }

    @Override
    public void showResults(ArrayList<Email> results) {

        databaseDataManager = new DatabaseDataManager(this);
        databaseDataManager.saveNote(new Note("subj1","text1"));
        databaseDataManager.saveNote(new Note("subj2","text2"));
        databaseDataManager.saveNote(new Note("subj3","text3"));
    }

    @Override
    public void startProgressbar() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {


//            case  R.id.showFavorites:
//
//
//                Intent intent = new Intent(this,FavoritesActivity.class);
//                //intent.putExtra("favorites",);
//                startActivity(intent);
//                return true;

            case R.id.quitItem:

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkInternetPermission(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null){
            Toast.makeText(MainActivity.this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
