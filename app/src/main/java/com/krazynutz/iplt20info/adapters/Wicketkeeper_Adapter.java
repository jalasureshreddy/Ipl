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

/**
 * Created by jalas on 07-03-2018.
 */

public class Wicketkeeper_Adapter extends RecyclerView.Adapter<Wicketkeeper_Adapter.ViewHolder>
{

    ArrayList<String> wicketKeeperList;
    private Context mContext;

    public Wicketkeeper_Adapter(Context mContext ,ArrayList<String> wicketKeeperList) {
        this.wicketKeeperList = wicketKeeperList;
        this.mContext = mContext;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_row, parent, false);
        ViewHolder vh =new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.player.setText(wicketKeeperList.get(position));
        holder.player.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Semibold.ttf"));
    }

    @Override
    public int getItemCount() {
        return wicketKeeperList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView player;
        public ViewHolder(View itemView) {
            super(itemView);
            player = (TextView) itemView.findViewById(R.id.player);
        }
    }
}
