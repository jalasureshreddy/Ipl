package com.krazynutz.iplt20info.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krazynutz.iplt20info.R;

import java.util.ArrayList;
import java.util.List;


public class Players_Adapter extends RecyclerView.Adapter<Players_Adapter.MyViewHolder>
{
    ArrayList<String> playerList;
    private Context mContext;

    public Players_Adapter(Context context, ArrayList<String> playerList)
    {
        this.mContext = context;
        this.playerList = playerList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView player;

        public MyViewHolder(View view) {
            super(view);
            player = (TextView) view.findViewById(R.id.player);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_row, parent, false);
        MyViewHolder vh =new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.player.setText(playerList.get(position));
        holder.player.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Semibold.ttf"));

    }

    @Override
    public int getItemCount()
    {
        return playerList.size();
    }



}
