package com.example.roncherian.inclass07;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by roncherian on 23/10/17.
 */

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> {

    ArrayList<Email> mData;

    public MyRVAdapter(ArrayList<Email> mData) {
        this.mData = mData;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View emailView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.email_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(emailView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Email email = mData.get(position);
        holder.textViewEmail.setText(email.getEmail());
        holder.textViewSubject.setText(email.getSubject());
        holder.textViewSummary.setText(email.getSummary());
        holder.email = email;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSubject, textViewEmail, textViewSummary;
        Email email;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewSubject = (TextView)itemView.findViewById(R.id.textViewSubject);
            textViewEmail = (TextView)itemView.findViewById(R.id.textViewEmail);
            textViewSummary = (TextView)itemView.findViewById(R.id.textViewSummary);

        /*itemView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("demo","clicked button"+email.getEmail());
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("demo","CLicked item "+email.getEmail());
            }
        });*/
        }
    }
}