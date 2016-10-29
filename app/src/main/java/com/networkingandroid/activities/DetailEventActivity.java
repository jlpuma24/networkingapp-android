package com.networkingandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.networkingandroid.MapsActivity;
import com.networkingandroid.NetworkingApplication;
import com.networkingandroid.R;
import com.networkingandroid.network.model.Event;
import com.networkingandroid.network.model.UserAttending;
import com.networkingandroid.util.PrefsUtil;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Usuario on 18/10/2016.
 */
public class DetailEventActivity extends BaseActivity {

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
            if  (i == 0){
                result = userAttending.getName()+" "+userAttending.getLast_name();
            }
            else {
                result = result+ "\n"+ userAttending.getName()+" "+userAttending.getLast_name();
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
            buttonAsistir.setImageResource(R.drawable.attend);
        }
        else {
            buttonAsistir.setImageResource(R.drawable.assist);
        }
        textViewPeopleAttend.setText(getUserAttends(event.getUsers_attending()));
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

}
