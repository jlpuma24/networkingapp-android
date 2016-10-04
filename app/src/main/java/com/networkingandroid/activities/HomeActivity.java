package com.networkingandroid.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.networkingandroid.R;
import com.networkingandroid.adapters.VerticalEventsAdapter;
import com.networkingandroid.network.ApiError;
import com.networkingandroid.network.BusProvider;
import com.networkingandroid.network.events.AttendanceEventResponse;
import com.networkingandroid.network.events.EventsResponse;
import com.networkingandroid.network.events.RequestEvents;
import com.networkingandroid.util.PrefsUtil;
import com.networkingandroid.util.RoundedCornersTransform;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Usuario on 16/09/2016.
 */
public class HomeActivity extends BaseActivity {

    private Bus mBus = BusProvider.getBus();
    @BindView(R.id.recyclerViewEvents)
    RecyclerView recyclerViewEvents;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.imageViewUser)
    ImageView imageViewUser;
    @BindView(R.id.textViewUserName)
    TextView textViewUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupToolbar();
        setUpMenu();
        mBus.post(new RequestEvents());
    }

    private void setUpMenu(){
        Picasso.with(this)
               .load(PrefsUtil.getInstance().getPrefs().getString(PrefsUtil.PICTURE_USER_DATA,""))
               .transform(new RoundedCornersTransform())
               .into(imageViewUser, new Callback() {
                   @Override
                   public void onSuccess() {

                   }

                   @Override
                   public void onError() {

                   }
               });
        textViewUsername.setText(PrefsUtil.getInstance().getPrefs().getString(PrefsUtil.NAME_USER_DATA, ""));
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

    @Subscribe
    public void onEventsResponse(EventsResponse eventsResponse){
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewEvents.setLayoutManager(verticalLayoutmanager);
        recyclerViewEvents.setAdapter(new VerticalEventsAdapter(eventsResponse.getResponse(), this, mBus));
    }

    @Subscribe
    public void onEventsResponseError(ApiError error){
        Toast.makeText(this, error.message(), Toast.LENGTH_SHORT).show();
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.menu));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigationView);
            }
        });
    }

    /**
     * Close Drawer with on backPressed
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Subscribe
    public void onSuccessAttendanceResponse(AttendanceEventResponse attendanceEventResponse){
        Toast.makeText(this, getString(R.string.success_event_request), Toast.LENGTH_SHORT).show();
    }
}
