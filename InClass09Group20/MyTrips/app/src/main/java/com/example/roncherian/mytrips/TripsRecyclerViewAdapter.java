package com.example.roncherian.mytrips;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by roncherian on 04/12/17.
 */

public class TripsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    TripsRecyclerViewAdapter.OnClick onClick;

    static ArrayList<TripAndPlaces> mData;
    static String userId;
    // Context context;

    static MainActivity mainActivity;

    public TripsRecyclerViewAdapter(ArrayList<TripAndPlaces> mData, TripsRecyclerViewAdapter.OnClick context) {
        this.mData = mData;
        this.onClick = context;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if (mData.get(position).isTripObj()){
            return 1;
        } else {
            return 2;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        if (viewType == 1){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trip_layout, parent, false);
            viewHolder = new TripsRecyclerViewAdapter.ViewHolder(v);
            return viewHolder;
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.place_layout, parent, false);
            TripsRecyclerViewAdapter.SecondViewHolder vh = new TripsRecyclerViewAdapter.SecondViewHolder(v);
            return vh;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        TripAndPlaces userObj = mData.get(position);
        if (holder.getItemViewType()==1){

            ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.tripAndPlaces = userObj;
            viewHolder.position = position;
        } else if (holder.getItemViewType()==2){

            SecondViewHolder secondViewHolder = (SecondViewHolder)holder;
            secondViewHolder.tripAndPlaces = userObj;
            secondViewHolder.position = position;
        }

        //holder.name.setText(userObj);



    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView placeName, cityName, tripName;

        ImageButton addPlaces;
        int position;
        TripAndPlaces tripAndPlaces;

        public ViewHolder(View itemView) {
            super(itemView);
            if (null == tripAndPlaces)
                tripAndPlaces = mData.get(position);
            if (tripAndPlaces.isTripObj()){
                cityName = (TextView)itemView.findViewById(R.id.textViewCityName);
                tripName = (TextView)itemView.findViewById(R.id.textViewTripName);

                addPlaces = (ImageButton)itemView.findViewById(R.id.imageButtonAddPlaces);
                addPlaces.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        onClick.addPlacesClicked(position, tripAndPlaces);

                    }
                });
                cityName.setText(tripAndPlaces.getCityName());
                tripName.setText(tripAndPlaces.getTripName());

            }


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onClick.OnItemClicked(position, tripAndPlaces);
                    return false;
                }
            });



        }
    }

    public  class SecondViewHolder extends RecyclerView.ViewHolder {

        TextView placeName, cityName, tripName;

        int position;
        TripAndPlaces tripAndPlaces;

        public SecondViewHolder(View itemView) {
            super(itemView);
            if (null == tripAndPlaces)
                tripAndPlaces = mData.get(position);

                placeName = (TextView)itemView.findViewById(R.id.textViewPlaceName);
                placeName.setText(tripAndPlaces.getPlaceName());



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onClick.OnItemClicked(position, tripAndPlaces);
                    return false;
                }
            });



        }
    }

    public interface OnClick{
        void OnItemClicked(int position, TripAndPlaces place);
        void addPlacesClicked(int position, TripAndPlaces place);

    }


}