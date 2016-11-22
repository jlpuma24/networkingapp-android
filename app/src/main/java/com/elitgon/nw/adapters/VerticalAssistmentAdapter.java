package com.elitgon.nw.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elitgon.nw.NetworkingApplication;
import com.elitgon.R;
import com.elitgon.nw.network.model.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Usuario on 17/09/2016.
 */
public class VerticalAssistmentAdapter extends RecyclerView.Adapter<VerticalAssistmentAdapter.EventsViewHolder>{

    private ArrayList<Event> eventsArrayList;
    private Context mContext;

    public VerticalAssistmentAdapter(ArrayList<Event> eventsArrayList, Context mContext){
        this.eventsArrayList = eventsArrayList;
        this.mContext = mContext;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_assist, parent, false);
        return new EventsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        Picasso.with(NetworkingApplication.getInstance())
                .load(String.format(mContext.getString(R.string.format_image_url),
                        mContext.getString(R.string.url_base), eventsArrayList.get(position).getCover()))
                .into(holder.imageViewEvent);
        holder.textViewDescriptionEvent.setText(eventsArrayList.get(position).getDescription());
        holder.textViewEventName.setText(eventsArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.buttonAsistir)
        Button buttonAsistir;
        @BindView(R.id.imageViewEvent)
        ImageView imageViewEvent;
        @BindView(R.id.textViewNameReferent)
        TextView textViewNameReferent;
        @BindView(R.id.textViewDescriptionEvent)
        TextView textViewDescriptionEvent;
        @BindView(R.id.textViewEventName)
        TextView textViewEventName;

        public EventsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
