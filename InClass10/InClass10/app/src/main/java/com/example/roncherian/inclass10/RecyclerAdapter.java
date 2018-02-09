package com.example.roncherian.inclass10;

/**
 * Created by roncherian on 13/11/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arun on 11/13/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    OnClick onClick;

    static ArrayList<Contacts> mData;
    // Context context;

    static MainActivity mainActivity;

    public RecyclerAdapter(ArrayList<Contacts> mData,OnClick context) {
        this.mData = mData;
        this.onClick = context;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {


        Contacts contactObj = mData.get(position);
        holder.contactObj = contactObj;
        holder.name.setText(contactObj.getName());
        holder.email.setText(contactObj.getEmail());
        holder.phone.setText(contactObj.getPhone());
        holder.department.setText(contactObj.getDepartment());
        holder.position = position;
        switch (contactObj.getImageId()){
            case 1001: holder.profileImageView.setImageResource(R.drawable.avatar_f_1);
                break;

            case 1002:holder.profileImageView.setImageResource(R.drawable.avatar_m_3);
                break;
            case 1003:holder.profileImageView.setImageResource(R.drawable.avatar_f_2);
                break;
            case 1004:holder.profileImageView.setImageResource(R.drawable.avatar_m_2);
                break;
            case 1005:holder.profileImageView.setImageResource(R.drawable.avatar_f_3);
                break;
            case 1006:holder.profileImageView.setImageResource(R.drawable.avatar_m_1);
                break;
        }

    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView email;
        TextView phone;
        TextView department;
        ImageView profileImageView;

        int position;
        Contacts contactObj;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.textViewName);
            email = (TextView)itemView.findViewById(R.id.textViewEmail);
            phone = (TextView)itemView.findViewById(R.id.textViewPhone);
            department = (TextView)itemView.findViewById(R.id.textViewDept);
            profileImageView = (ImageView)itemView.findViewById(R.id.imageViewProfilePicture);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onClick.onItemClicked(position,contactObj);
                    return false;
                }
            });



        }
    }

    public interface OnClick{
        public void onItemClicked(int position, Contacts contact);
    }

}
