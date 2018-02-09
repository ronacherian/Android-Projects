package com.example.roncherian.mytrips;

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

public class SelectPlacesRecyclerViewAdapter extends RecyclerView.Adapter<SelectPlacesRecyclerViewAdapter.ViewHolder>{

    SelectPlacesRecyclerViewAdapter.OnClick onClick;

    static ArrayList<Places> mData;
    static String userId;
    // Context context;

    static MainActivity mainActivity;

    public SelectPlacesRecyclerViewAdapter(ArrayList<Places> mData, SelectPlacesRecyclerViewAdapter.OnClick context) {
        this.mData = mData;
        this.onClick = context;

    }


    @Override
    public SelectPlacesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_places_layout, parent, false);
        SelectPlacesRecyclerViewAdapter.ViewHolder vh = new SelectPlacesRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SelectPlacesRecyclerViewAdapter.ViewHolder holder, int position) {


        Places userObj = mData.get(position);
        holder.place = userObj;
        holder.name.setText(userObj.getPlcaeName());
        holder.position = position;


    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        ImageButton removeFirend;


        int position;
        Places place;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.textViewPlaceName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.OnItemClicked(position, place);
                }
            });



        }
    }


    public interface OnClick{
        public void OnItemClicked(int position, Places place);

    }


}