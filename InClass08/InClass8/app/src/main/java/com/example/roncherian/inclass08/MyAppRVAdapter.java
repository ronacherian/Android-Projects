package com.example.roncherian.inclass08;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by roncherian on 30/10/17.
 */

public class MyAppRVAdapter extends RecyclerView.Adapter<MyAppRVAdapter.ViewHolder> {


    ArrayList<ItunesApp> mData;
    static DatabaseDataManager dm;
    static ICallBack callback;
    static MainActivity mainActivity;

    public MyAppRVAdapter(ArrayList<ItunesApp> mData, DatabaseDataManager dm, MainActivity mainActivity) {
        this.mData = mData;
        this.dm=dm;
        this.mainActivity=mainActivity;
        callback = (ICallBack) mainActivity;

    }



    @Override
    public MyAppRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View emailView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_recycler_view_item, parent, false);
        MyAppRVAdapter.ViewHolder viewHolder = new MyAppRVAdapter.ViewHolder(emailView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAppRVAdapter.ViewHolder holder, int position) {

        ItunesApp ituneApp = mData.get(position);
        holder.ituneApp = ituneApp;
        holder.appName.setText(ituneApp.getAppName());

        if(Double.parseDouble(ituneApp.getAppPrice())>0)
        {
            holder.priceVal.setText("Price: "+ituneApp.getAppPrice());

            Double priceVal=Double.parseDouble(mData.get(position).getAppPrice());
            /*if(priceVal >=0 && priceVal <=1.99)
            {
                holder.priceIcon.setImageResource(R.drawable.price_low);

            }
            else if(priceVal >=2 && priceVal <=5.99)
            {
                holder.priceIcon.setImageResource(R.drawable.price_medium);
            }
            else
            {
                holder.priceIcon.setImageResource(R.drawable.price_high);
            }*/

        }


        if(ituneApp.getLargeImageUrl() != null && ituneApp.getLargeImageUrl().length()>0)
            Picasso.with(mainActivity).load(mData.get(position).getLargeImageUrl()).into(holder.largeImage);
    }

    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView appName;
        TextView priceVal;

        ImageView largeImage;
        ImageView priceIcon;
        ImageButton deletebutton;
        ItunesApp ituneApp;

        public ViewHolder(View itemView) {
            super(itemView);
            /*appName = (TextView)itemView.findViewById(R.id.textViewFavAppName);
            priceVal = (TextView)itemView.findViewById(R.id.textViewFavAppPrice);
            largeImage=(ImageView) itemView.findViewById(R.id.imageViewFavAppImage);
            priceIcon=(ImageView) itemView.findViewById(R.id.imageViewFavPrice);

            deletebutton=(ImageButton)itemView.findViewById(R.id.imageButtonFavDelete);

            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    dm.deleteNote(ituneApp);


                    RecyclerView recyclerView = (RecyclerView) mainActivity.findViewById(R.id.recyclerView);

                    ArrayList<ItunesApp> dblist= (ArrayList<ItunesApp>) dm.getAll();
                    // ArrayAdapter<Color> adapter= new ArrayAdapter<Color>(this,android.R.layout.simple_list_item_1,colors);
                    MyAppRVAdapter adapter = new MyAppRVAdapter(dblist,dm,mainActivity);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    callback.addsortItems(ituneApp);

//                    mainActivity.musicResults.add(ituneApp);
//                    ListView listview = (ListView)mainActivity.findViewById(R.id.listView);
//                    ItunesAdapter adapter1 = new ItunesAdapter(mainActivity, R.layout.list_layout,mainActivity.musicResults,dm);
//                    listview.setAdapter(adapter1);
//                    adapter1.setNotifyOnChange(true);



                }
            });*/

        }
    }

    public interface ICallBack {
        void addsortItems(ItunesApp s);
    }
}
