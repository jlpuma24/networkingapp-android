package com.networkingandroid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.networkingandroid.network.events.RequestEventsByNameEvent;
import com.networkingandroid.network.events.RequestFilterEvents;
import com.networkingandroid.network.model.Event;
import com.networkingandroid.util.PrefsUtil;
import com.networkingandroid.util.RoundedCornersTransform;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    @BindView(R.id.textViewCity)
    TextView textViewCity;
    //Menu items
    @BindView(R.id.textViewHome)
    TextView textViewHome;
    @BindView(R.id.textViewAttendance)
    TextView textViewAttendance;
    @BindView(R.id.textViewSettings)
    TextView textViewSettings;
    @BindView(R.id.editTextSearch)
    EditText editTextSearch;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    private boolean searchShowed = false;
    private ProgressDialog progressDialog;
    private long page = 1;
    private long offset = 10;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private ArrayList<Event> searchResponseArrayList = new ArrayList<Event>();
    private boolean isMoreData = true;
    private VerticalEventsAdapter verticalEventsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupToolbar();
        setUpMenu();
        prepareEditTextSearch();
        prepareProgressDialog();
        prepareSwipeLayout();
        LinearLayoutManager verticalLayoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewEvents.setLayoutManager(verticalLayoutmanager);
        verticalEventsAdapter = new VerticalEventsAdapter(this, mBus);
        recyclerViewEvents.setAdapter(verticalEventsAdapter);
        mBus.post(new RequestEvents(page, offset));
    }

    private void prepareProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_data));
        progressDialog.show();
    }

    private void prepareSwipeLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                offset = 2;
                verticalEventsAdapter = new VerticalEventsAdapter(HomeActivity.this, mBus);
                mBus.post(new RequestEvents(page, offset));
            }
        });
    }

    private void setUpMenu() {
        Picasso.with(this)
                .load(PrefsUtil.getInstance().getPrefs().getString(PrefsUtil.PICTURE_USER_DATA, ""))
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
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                finish();
            }
        });
        textViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AttendanceActivity.class));
                finish();
            }
        });
        textViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
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
    public void onEventsResponse(EventsResponse eventsResponse) {
        progressDialog.dismiss();
        if (!eventsResponse.getResponse().isEmpty()) {
            swipeRefreshLayout.setRefreshing(false);
            addItemsToAdapter(eventsResponse.getResponse());
            RecyclerItemClickSupport.addTo(recyclerViewEvents).setOnItemClickListener(new RecyclerItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(HomeActivity.this, DetailEventActivity.class);
                    intent.putExtra("event", verticalEventsAdapter.getItems().get(position));
                    startActivity(intent);
                }
            });

            recyclerViewEvents.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) {
                        visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                        totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findFirstVisibleItemPosition();

                        if (isMoreData && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            onPaging(totalItemCount);
                        }
                    }
                }
            });
        }
    }

    public void addItemsToAdapter(ArrayList<Event> searchResponses) {
        if (!searchResponses.isEmpty()) {
            searchResponseArrayList.addAll(searchResponses);
            verticalEventsAdapter.setItems(searchResponseArrayList);
        } else {
            isMoreData = false;
        }
    }

    @Subscribe
    public void onEventsResponseError(ApiError error) {
        Toast.makeText(this, error.message(), Toast.LENGTH_SHORT).show();
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.menu));
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigationView);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mSearch:
                        if (!searchShowed) {
                            editTextSearch.setVisibility(View.VISIBLE);
                            searchShowed = true;
                        } else {
                            editTextSearch.setVisibility(View.GONE);
                            searchShowed = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void prepareEditTextSearch() {
        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideKeyboard();
                    prepareProgressDialog();
                    mBus.post(new RequestEventsByNameEvent(editTextSearch.getText().toString()));
                    editTextSearch.setText("");
                    return true;
                }
                return false;
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
    public void onSuccessAttendanceResponse(AttendanceEventResponse attendanceEventResponse) {
        Toast.makeText(this, getString(R.string.success_event_request), Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void onPaging(int totalItemCount) {
        if (totalItemCount >= offset) {
            page++;
            mBus.post(new RequestFilterEvents(page, offset));
        }
    }
}
