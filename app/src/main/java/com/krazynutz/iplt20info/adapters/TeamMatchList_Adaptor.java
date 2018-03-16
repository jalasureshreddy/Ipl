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

import butterknife.BindView;
import butterknife.ButterKnife;


public class TeamMatchList_Adaptor extends RecyclerView.Adapter<TeamMatchList_Adaptor.MyViewHolder> {

    private ArrayList<String> m_date, m_play, m_place, m_result;
    private Context mcontext;

    public TeamMatchList_Adaptor(Context context, ArrayList<String> date, ArrayList<String> play, ArrayList<String> place,
                                 ArrayList<String> result) {
        mcontext = context;
        m_date = date;
        m_play = play;
        m_place = place;
        m_result = result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        TextView txt_date;
        @BindView(R.id.no)
        TextView txt_no;
        @BindView(R.id.play)
        TextView txt_play;
        @BindView(R.id.place)
        TextView txt_place;
        @BindView(R.id.result)
        TextView txt_result;


        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txt_date.setText(m_date.get(position));
        //holder.txt_time.setText(m_time.get(position));
        holder.txt_play.setText(m_play.get(position));
        holder.txt_place.setText(m_place.get(position));
        holder.txt_result.setText(m_result.get(position));

        holder.txt_date.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "JosefinSans-SemiBold.ttf"));
        holder.txt_play.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "JosefinSans-SemiBold.ttf"));
        holder.txt_place.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "JosefinSans-Regular.ttf"));
        holder.txt_result.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "JosefinSans-SemiBold.ttf"));
        holder.txt_no.setText("Match - " + ++position);
        holder.txt_no.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "JosefinSans-Regular.ttf"));


    }

    @Override
    public int getItemCount() {
        return m_date.size();
    }
}
