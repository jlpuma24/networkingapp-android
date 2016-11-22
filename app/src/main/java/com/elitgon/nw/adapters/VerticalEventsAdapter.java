package com.elitgon.nw.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elitgon.nw.NetworkingApplication;
import com.elitgon.R;
import com.elitgon.nw.activities.HomeActivity;
import com.elitgon.nw.network.events.RequestAttendanceEvent;
import com.elitgon.nw.network.model.Event;
import com.elitgon.nw.network.model.UserAttending;
import com.elitgon.nw.util.PrefsUtil;
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

    public VerticalEventsAdapter(Context mContext, Bus mBus){
        this.eventsArrayList = new ArrayList<Event>();
        this.mContext = mContext;
        this.mBus = mBus;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event, parent, false);
        return new EventsViewHolder(itemView);
    }

    public ArrayList<Event> getItems(){
        return eventsArrayList;
    }

    @Override
    public void onBindViewHolder(final EventsViewHolder holder, final int position) {
        Picasso.with(NetworkingApplication.getInstance())
                .load(String.format(mContext.getString(R.string.format_image_url),
                        mContext.getString(R.string.url_base), eventsArrayList.get(position).getCover()))
                .into(holder.imageViewEvent);
        holder.textViewCityEvent.setText(eventsArrayList.get(position).getPlace().getName()+", "+eventsArrayList.get(position).getPlace().getAddress());
        holder.textViewDescriptionEvent.setText(eventsArrayList.get(position).getDescription());
        holder.textViewEventName.setText(eventsArrayList.get(position).getName());
        holder.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextUrl();
            }
        });

        if (validateAssist(eventsArrayList.get(position).getUsers_attending())){
            holder.buttonAsistir.setBackgroundResource(R.drawable.attend);
            holder.buttonAsistir.setTextColor(Color.parseColor("#FFFFFF"));
            holder.buttonAsistir.setText(mContext.getString(R.string.attending));
            holder.buttonAsistir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder
                            .setMessage(mContext.getString(R.string.unattend_message))
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mBus.post(new RequestAttendanceEvent(
                                            PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED,0), eventsArrayList.get(position).getId()));
                                    Intent intent = new Intent(mContext, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    mContext.startActivity(intent);
                                    ((Activity)mContext).finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }
        else {
            holder.buttonAsistir.setBackgroundResource(R.drawable.assist);
            holder.buttonAsistir.setTextColor(Color.parseColor("#3048A7"));
            holder.buttonAsistir.setText(mContext.getString(R.string.attend));
            holder.buttonAsistir.setPadding(0,0,0,0);
            holder.buttonAsistir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBus.post(new RequestAttendanceEvent(
                            PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED,0), eventsArrayList.get(position).getId()));
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                    ((Activity)mContext).finish();
                }
            });
        }
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.share_text));
        mContext.startActivity(Intent.createChooser(share, mContext.getString(R.string.share)));
    }

    public void setItems(ArrayList<Event> eventsArrayList){
        this.eventsArrayList = eventsArrayList;
        notifyDataSetChanged();
    }

    private boolean validateAssist(ArrayList<UserAttending> attendings){
        if (attendings != null && !attendings.isEmpty()) {
            for (UserAttending userAttending : attendings) {
                try {
                    if (userAttending.getId() == PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED, 0))
                        return true;
                } catch (NullPointerException e){

                }
            }
            return false;
        }
        return false;
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
