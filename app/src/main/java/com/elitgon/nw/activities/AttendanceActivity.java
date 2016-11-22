package com.elitgon.nw.activities;

import android.content.Intent;
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

import com.elitgon.R;
import com.elitgon.nw.adapters.VerticalAssistmentAdapter;
import com.elitgon.nw.network.ApiError;
import com.elitgon.nw.network.BusProvider;
import com.elitgon.nw.network.events.AttendanceResponse;
import com.elitgon.nw.network.events.RequestAttendances;
import com.elitgon.nw.util.PrefsUtil;
import com.elitgon.nw.util.RoundedCornersTransform;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Usuario on 04/10/2016.
 */
public class AttendanceActivity extends BaseActivity {

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
    @BindView(R.id.headerBar)
    TextView headerBar;
    //Menu items
    @BindView(R.id.textViewHome)
    TextView textViewHome;
    @BindView(R.id.textViewAttendance)
    TextView textViewAttendance;
    @BindView(R.id.textViewCity)
    TextView textViewCity;
    @BindView(R.id.textViewSettings)
    TextView textViewSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupToolbar();
        setUpMenu();
        mBus.post(new RequestAttendances(PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED,0)));
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

        textViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this, HomeActivity.class));
                finish();
            }
        });
        textViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this, AttendanceActivity.class));
                finish();
            }
        });
        textViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this, SettingsActivity.class));
                finish();
            }
        });
        textViewCity.setText(PrefsUtil.getInstance().getPrefs().getString(PrefsUtil.USER_LOCATION, ""));
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
    public void onEventsResponse(AttendanceResponse attendanceResponse){
        if (attendanceResponse.getResponse() != null && !attendanceResponse.getResponse().isEmpty()) {
            LinearLayoutManager verticalLayoutmanager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerViewEvents.setLayoutManager(verticalLayoutmanager);
            recyclerViewEvents.setAdapter(new VerticalAssistmentAdapter(attendanceResponse.getResponse(), this));
        }
        else {
            Toast.makeText(this, getString(R.string.no_attendances),Toast.LENGTH_SHORT).show();
        }
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
        headerBar.setText(getString(R.string.attendances));
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
}