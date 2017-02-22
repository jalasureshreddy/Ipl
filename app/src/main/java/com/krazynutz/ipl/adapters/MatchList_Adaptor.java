package com.krazynutz.ipl.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krazynutz.ipl.R;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchList_Adaptor extends  RecyclerView.Adapter<MatchList_Adaptor.MyViewHolder> implements FastScroller.SectionIndexer
{
    private List<String> m_date, m_play, m_place;
    private Context mcontext;

    public MatchList_Adaptor(Context context, List<String> date,  List<String> play, List<String> place) {
        mcontext = context;
        m_date = date;
        m_play = play;
        m_place = place;
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


        public MyViewHolder(View v)
        {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public MatchList_Adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.redesign_first_item, parent, false);
        MyViewHolder vh =new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MatchList_Adaptor.MyViewHolder holder, int position) {

        holder.txt_date.setText(m_date.get(position));
        holder.txt_play.setText(m_play.get(position));
        holder.txt_place.setText(m_place.get(position));

        holder.txt_date.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "JosefinSans-SemiBold.ttf"));
        holder.txt_play.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "JosefinSans-SemiBold.ttf"));
        holder.txt_place.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "JosefinSans-SemiBold.ttf"));
        holder.txt_no.setText("Match - " +  ++position);
        holder.txt_no.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "JosefinSans-SemiBold.ttf"));


    }

    @Override
    public int getItemCount() {
        return m_date.size();
    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(m_play.get(position).charAt(0));
    }

}
