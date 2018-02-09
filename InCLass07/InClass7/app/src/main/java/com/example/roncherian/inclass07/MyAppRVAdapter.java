package com.example.roncherian.inclass07;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by roncherian on 23/10/17.
 */

public class MyAppRVAdapter extends RecyclerView.Adapter<MyAppRVAdapter.ViewHolder>{



    ArrayList<Email> mData;

    public MyAppRVAdapter(ArrayList<Email> mData) {
        this.mData = mData;
    }


    @Override
    public MyAppRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View emailView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.email_item, parent, false);
        MyAppRVAdapter.ViewHolder viewHolder = new MyAppRVAdapter.ViewHolder(emailView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAppRVAdapter.ViewHolder holder, int position) {

        Email ituneApp = mData.get(position);
        holder.textViewEmail.setText(ituneApp.getEmail());
        holder.textViewSubject.setText(ituneApp.getSubject());

        holder.ituneApp = ituneApp;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSubject, textViewEmail, textViewSummary;
        Email ituneApp;

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
