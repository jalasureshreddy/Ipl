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


public class Stats_Adaptor extends  RecyclerView.Adapter<Stats_Adaptor.MyViewHolder> {

    private ArrayList<String> m_score;
    private ArrayList<String> m_player;
    private ArrayList<String> m_inn;
    private Context mcontext;

    public Stats_Adaptor(Context context,ArrayList<String> score, ArrayList<String> player,
                         ArrayList<String> inn) {
        mcontext = context;
        m_score = score;
        m_player = player;
        m_inn = inn;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.player)
        TextView txt_names;

        @BindView(R.id.inn)
        TextView txt_inn;

        @BindView(R.id.no)
        TextView txt_no;

        public MyViewHolder(View v)
        {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public Stats_Adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_list, parent, false);
        Stats_Adaptor.MyViewHolder vh =new Stats_Adaptor.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Stats_Adaptor.MyViewHolder holder, int position) {

        holder.txt_names.setText(m_player.get(position));
        holder.txt_inn.setText(m_inn.get(position));
        holder.txt_no.setText(m_score.get(position));
    }

    @Override
    public int getItemCount() {
        return m_score.size();
    }
}
