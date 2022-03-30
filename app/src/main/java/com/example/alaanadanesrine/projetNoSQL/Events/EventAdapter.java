package com.example.alaanadanesrine.projetNoSQL.Events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alaanadanesrine.projetNoSQL.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

        private Context context;
        private List<Event> list;
        private int lastPosition = -1;
        private DatabaseHelper db;

        public EventAdapter(Context context, List<Event> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.event_item_row, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Event event = list.get(position);

            holder.eventName.setText(event.getEventName());
            holder.eventBeginDate.setText(String.valueOf(event.getBeginDate()));
            holder.eventEndDate.setText(String.valueOf(event.getEndDate()));
            holder.eventPlace.setText(String.valueOf(event.getEventPlace()));
            holder.eventDescription.setText(String.valueOf(event.getEventDescription()));
            db = new DatabaseHelper(this.context);
            String name=holder.eventName.getText().toString();
            if(db.getEvent(name)) {holder.ButtonLike.setLiked(true);}
            holder.ButtonLike.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    try {
                        db.insertEvent(event.getEventName(),event.getBeginDate(),event.getEndDate()
                                ,event.getEventPlace(),event.getEventDescription());
                    }
                    catch (Exception e){
                        Log.e("errror", "message", e);
                    }


                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    try {
                    db.deleteEvent(event);
                    }
                    catch (Exception e){
                        Log.e("errror", "message", e);
                    }
                }
            });

            setAnimation(holder.itemView, position);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView eventName,eventBeginDate,eventEndDate,eventPlace,eventDescription;
            private LikeButton ButtonLike;

            public ViewHolder(final View itemView) {
                super(itemView);

                eventName = itemView.findViewById(R.id.name_event);
                eventBeginDate = itemView.findViewById(R.id.begin_date_event);
                eventEndDate = itemView.findViewById(R.id.end_date_event);
                eventPlace = itemView.findViewById(R.id.place_event);
                eventDescription = itemView.findViewById(R.id.description_event);
                ButtonLike=itemView.findViewById(R.id.ButtonLike);

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
