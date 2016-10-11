package com.networkingandroid.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.networkingandroid.NetworkingApplication;
import com.networkingandroid.R;
import com.networkingandroid.network.events.RequestAttendanceEvent;
import com.networkingandroid.network.model.Event;
import com.networkingandroid.util.PrefsUtil;
import com.networkingandroid.util.RoundedCornersTransform;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Usuario on 17/09/2016.
 */
public class VerticalEventsAdapter extends RecyclerView.Adapter<VerticalEventsAdapter.EventsViewHolder>{

    private ArrayList<Event> eventsArrayList;
    private Context mContext;
    private Bus mBus;

    public VerticalEventsAdapter(ArrayList<Event> eventsArrayList, Context mContext, Bus mBus){
        this.eventsArrayList = eventsArrayList;
        this.mContext = mContext;
        this.mBus = mBus;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event, parent, false);
        return new EventsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, final int position) {
        Picasso.with(NetworkingApplication.getInstance())
                .load(String.format(mContext.getString(R.string.format_image_url),
                        mContext.getString(R.string.url_base), eventsArrayList.get(position).getCover()))
                .into(holder.imageViewEvent);
        holder.textViewCityEvent.setText(eventsArrayList.get(position).getPlace().getName()+", "+eventsArrayList.get(position).getPlace().getAddress());
        holder.textViewDescriptionEvent.setText(eventsArrayList.get(position).getDescription());
        holder.textViewEventName.setText(eventsArrayList.get(position).getName());
        holder.buttonAsistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBus.post(new RequestAttendanceEvent(
                    PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED,0), eventsArrayList.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.buttonAsistir)
        ImageView buttonAsistir;
        @BindView(R.id.imageViewEvent)
        ImageView imageViewEvent;
        @BindView(R.id.imageViewShare)
        ImageView imageViewShare;
        @BindView(R.id.textViewCityEvent)
        TextView textViewCityEvent;
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
