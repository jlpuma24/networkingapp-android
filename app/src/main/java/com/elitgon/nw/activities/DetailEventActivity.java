package com.elitgon.nw.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elitgon.nw.MapsActivity;
import com.elitgon.nw.NetworkingApplication;
import com.elitgon.R;
import com.elitgon.nw.network.BusProvider;
import com.elitgon.nw.network.events.RequestAttendanceEvent;
import com.elitgon.nw.network.model.Event;
import com.elitgon.nw.network.model.UserAttending;
import com.elitgon.nw.util.PrefsUtil;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Usuario on 18/10/2016.
 */
public class DetailEventActivity extends BaseActivity {

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
    @BindView(R.id.textViewHour)
    TextView textViewHour;
    @BindView(R.id.textViewAddress)
    TextView textViewAddress;
    @BindView(R.id.imageViewMaps)
    ImageView imageViewMaps;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textViewPeopleAttend)
    TextView textViewPeopleAttend;
    private Event event;
    private Bus mBus = BusProvider.getBus();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);
        setupToolbar();
        event = (Event) getIntent().getSerializableExtra("event");
        placeEventInfo();
    }

    @OnClick(R.id.imageViewMaps)
    public void onMapsButtonPressed(){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
    }

    @OnClick(R.id.imageViewShare)
    public void onShareClicked(){
        shareTextUrl();
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String getUserAttends(ArrayList<UserAttending> attendings){
        String result = "";
        int i = 0;
        for (UserAttending userAttending: attendings){
            if  (i == 0 || result.isEmpty()){
                if (userAttending.getPosition() != null && userAttending.getCompany() != null)
                    result = userAttending.getCompany()+" "+userAttending.getPosition();
            }
            else {
                if (userAttending.getPosition() != null && userAttending.getCompany() != null)
                    result = result+ "\n"+ userAttending.getCompany()+" "+userAttending.getPosition();
            }
        }
        return result;
    }


    private void placeEventInfo() {
        Picasso.with(NetworkingApplication.getInstance())
                .load(String.format(getString(R.string.format_image_url),
                        getString(R.string.url_base), event.getCover()))
                .into(imageViewEvent);
        textViewCityEvent.setText(event.getPlace().getName());
        textViewHour.setText(String.format(getString(R.string.format_address_hour), getString(R.string.hour), event.getSchedule().split(" ")[1]));
        textViewAddress.setText(String.format(getString(R.string.format_address_hour), getString(R.string.address), event.getPlace().getAddress()));
        textViewDescriptionEvent.setText(event.getDescription());
        textViewEventName.setText(event.getName());
        if (validateAssist(event.getUsers_attending())){
            buttonAsistir.setBackgroundResource(R.drawable.attend);
            buttonAsistir.setTextColor(Color.parseColor("#FFFFFF"));
            buttonAsistir.setText(getString(R.string.attending));
            buttonAsistir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailEventActivity.this);
                    alertDialogBuilder
                            .setMessage(getString(R.string.unattend_message))
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mBus.post(new RequestAttendanceEvent(
                                            PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED,0), event.getId()));
                                    Intent intent = new Intent(DetailEventActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
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
            buttonAsistir.setBackgroundResource(R.drawable.assist);
            buttonAsistir.setTextColor(Color.parseColor("#3048A7"));
            buttonAsistir.setText(getString(R.string.attend));
            buttonAsistir.setPadding(0,0,0,0);
            buttonAsistir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBus.post(new RequestAttendanceEvent(
                            PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED,0), event.getId()));
                    Intent intent = new Intent(DetailEventActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }
        textViewPeopleAttend.setText(getUserAttends(event.getUsers_attending()));
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
        startActivity(Intent.createChooser(share, getString(R.string.share)));
    }

    private boolean validateAssist(ArrayList<UserAttending> attendings){
        for (UserAttending userAttending: attendings){
            if (userAttending.getId() == PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED,0))
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBus.unregister(this);
    }
}