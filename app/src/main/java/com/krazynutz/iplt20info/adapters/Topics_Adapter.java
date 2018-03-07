package com.krazynutz.iplt20info.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.krazynutz.iplt20info.R;
import com.krazynutz.iplt20info.activites.PlayersActivity;
import com.krazynutz.iplt20info.activites.TeamMatches;
import com.krazynutz.iplt20info.activites.TeamsActivity;

import java.util.List;

public class Topics_Adapter extends RecyclerView.Adapter<Topics_Adapter.ViewHolder>
{
    private List<String> contents;
    private Context mContext;
    int pageNo;
    String selected;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cardview_top, parent, false);
        ViewHolder  vh =new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.title.setText(contents.get(position));
        holder.title.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Oxygen.otf"));

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                System.out.println("page no:"+ TeamsActivity.pageNo+"title:"+contents.get(position));
                pageNo=TeamsActivity.pageNo;
                selected=contents.get(position);
                if(position==0)
                {
                    Intent i = new Intent(mContext, PlayersActivity.class);
                    i.putExtra("pageno", pageNo);
                    mContext.startActivity(i);
                }
                else if(position==1)
                {
                    Intent i = new Intent(mContext, TeamMatches.class);
                    i.putExtra("pageno", pageNo);
                    mContext.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public Topics_Adapter(Context mContext, List<String> contents)
    {
        this.mContext=mContext;
        this.contents = contents;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public RelativeLayout rl,rll;
        public LinearLayout ll;

        public ViewHolder(View v)
        {
            super(v);

            title = (TextView) v.findViewById(R.id.tvTitle);
            //sub_title = (TextView)v.findViewById(R.id.tvSubtitle);
            rl = (RelativeLayout)v.findViewById(R.id.rl);
            rll = (RelativeLayout)v.findViewById(R.id.rlTitle);
            ll=(LinearLayout)v.findViewById(R.id.ll);

        }
    }
}
