package com.example.alaanadanesrine.projetNoSQL;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

public class ClubsAdapter extends  RecyclerView.Adapter<ClubsAdapter.ViewHolder> {

    private Context context;
    private List<Clubs> list;
    private int lastPosition = -1;

    public ClubsAdapter(Context context, List<Clubs> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.club_item_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Clubs club = list.get(position);

        holder.clubname.setText(club.getClubname());
        holder.creationdate.setText(String.valueOf(club.getCreationdate()));
        holder.clubdescroption.setText(String.valueOf(club.getDescription()));


        setAnimation(holder.itemView, position);

    }

    @Override
    public int getItemCount() {return  list.size();}
        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView clubname,creationdate,clubdescroption;

            public ViewHolder(View itemView) {
                super(itemView);

                clubname = itemView.findViewById(R.id.name_club);
                creationdate = itemView.findViewById(R.id.creationdate);
                clubdescroption = itemView.findViewById(R.id.description_club);

            }
        }

        private void setAnimation(View viewToAnimate, int position) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }



    }
}
