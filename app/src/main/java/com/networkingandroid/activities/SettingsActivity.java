package com.networkingandroid.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.networkingandroid.R;
import com.networkingandroid.network.BusProvider;
import com.networkingandroid.network.UserUpdateRequest;
import com.networkingandroid.network.events.AreasResultEvents;
import com.networkingandroid.network.events.IndustriesResultsFilter;
import com.networkingandroid.network.events.RequestAreas;
import com.networkingandroid.network.events.RequestIndustries;
import com.networkingandroid.network.events.SuccessAreasResponseEvent;
import com.networkingandroid.network.events.SuccessIndustriesResponseEvent;
import com.networkingandroid.network.model.ApplicationData;
import com.networkingandroid.network.model.Area;
import com.networkingandroid.network.model.Industry;
import com.networkingandroid.network.model.IndustryAreaUser;
import com.networkingandroid.network.model.UserUpdateObjectRequest;
import com.networkingandroid.network.model.UserUpdateResponse;
import com.networkingandroid.util.Constants;
import com.networkingandroid.util.PrefsUtil;
import com.networkingandroid.util.RoundedCornersTransform;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Usuario on 11/10/2016.
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.headerBar)
    TextView headerBar;
    @BindView(R.id.imageViewUser)
    ImageView imageViewUser;
    @BindView(R.id.editTextUserName)
    EditText editTextUserName;
    @BindView(R.id.editTextUserMail)
    EditText editTextUserMail;
    @BindView(R.id.editTextIntereses)
    EditText editTextIntereses;
    @BindView(R.id.editTextArea)
    EditText editTextArea;
    @BindView(R.id.containerAreas)
    LinearLayout containerAreas;
    @BindView(R.id.containerIndustries)
    LinearLayout containerIndustries;
    @BindView(R.id.buttonEntrar)
    Button buttonLogout;
    private Bus mBus = BusProvider.getBus();
    private ArrayList<Area> areaArrayList;
    private ArrayList<String> industries = new ArrayList<String>();
    private ArrayList<String> areas = new ArrayList<String>();
    private ArrayList<Industry> industryArrayList;
    private final int RESULT_INDUSTRIES = 2;
    private final int RESULT_AREAS = 3;
    private ApplicationData applicationData = new ApplicationData();
    private ProgressDialog mAlertDialog;
    private UserUpdateRequest userUpdateRequest = new UserUpdateRequest();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        prepareToolbar();
        setUpInfoUser();
        prepareRequestData();
    }

    private void prepareRequestData() {
        mAlertDialog = new ProgressDialog(this);
        mAlertDialog.setMessage(getString(R.string.loading_data));
        mAlertDialog.setCancelable(false);
        containerIndustries.removeAllViews();
        containerAreas.removeAllViews();
        mAlertDialog.show();
        mBus.post(new RequestIndustries());
    }

    private void prepareToolbar() {
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
            }
        });
        toolbar.inflateMenu(R.menu.save_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mSearch:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                        alertDialogBuilder
                                .setMessage(getString(R.string.save_user_data))
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        prepareUpdateData();
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
                return false;
            }
        });
        headerBar.setText(getString(R.string.settings));
    }

    private void prepareUpdateData(){
        mAlertDialog.show();
        ArrayList<IndustryAreaUser> industryAreaUser = new ArrayList<IndustryAreaUser>();
        for (String area: areas){
            Area areaSelected = getSpecificArea(area);
            if (areaSelected != null){
                industryAreaUser.add(new IndustryAreaUser(areaSelected.getIndustry_id(), areaSelected.getId(), false));
            }
        }
        userUpdateRequest.setIndustry_areas(industryAreaUser);
        mBus.post(new UserUpdateObjectRequest(userUpdateRequest));
    }

    @Subscribe
    public void onUserUpdateResponse(UserUpdateResponse userUpdateResponse){
        mAlertDialog.dismiss();
        Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    public Area getSpecificArea(String areaName){
        for (Area area: applicationData.getAreaArrayList()){
            if (area.getName().equals(areaName))
                return area;
        }
        return null;
    }

    private void setUpInfoUser() {
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

        editTextUserName.setText(PrefsUtil.getInstance().getPrefs().getString(PrefsUtil.NAME_USER_DATA, ""));
        editTextUserMail.setText(PrefsUtil.getInstance().getPrefs().getString(PrefsUtil.EMAIL_ACTIVE_ACCOUNT_ID, ""));
        buttonLogout.setPaintFlags(buttonLogout.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        editTextUserMail.setEnabled(false);
    }

    @OnClick(R.id.editTextIntereses)
    public void onEditTextInteresesClicked() {
        Intent intent = new Intent(this, FilterIndustriesActivity.class);
        intent.putExtra("isIndustries", true);
        intent.putExtra("applicationData", applicationData);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.editTextArea)
    public void onEditTextAreaClicked() {
        Intent intent = new Intent(this, FilterIndustriesActivity.class);
        intent.putExtra("isIndustries", false);
        intent.putExtra("applicationData", applicationData);
        startActivityForResult(intent, 1);
    }

    private TextView inflateInterestArea(final String value, final boolean isIndustry, boolean isFirst) {
        View child = getLayoutInflater().inflate(R.layout.word, null);
        TextView word = (TextView) child.findViewById(R.id.textViewWord);
        if (isFirst)
            word.setText(value);
        else
            word.setText(" |  " + value);
        word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(value, isIndustry);
            }
        });
        return word;
    }

    private void removeItem(String value, boolean isIndustry) {
        if (isIndustry) {
            industries.remove(value);
            containerIndustries.removeAllViews();
            for (int i = 0; i < industries.size(); i++) {
                containerIndustries.addView(inflateInterestArea(industries.get(i), true, i == 0));
            }
        } else {
            areas.remove(value);
            containerAreas.removeAllViews();
            for (int i = 0; i < areas.size(); i++) {
                containerAreas.addView(inflateInterestArea(areas.get(i), false, i == 0));
            }
        }
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

    @OnClick(R.id.buttonEntrar)
    public void onLogoutButtonClicked() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(getString(R.string.logout_message))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        PrefsUtil.getInstance().setIsLogged(false);
                        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
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

    @Subscribe
    public void onSuccessIndustriesEvent(SuccessIndustriesResponseEvent successIndustriesResponseEvent) {
        industryArrayList = successIndustriesResponseEvent.getResponse();
        applicationData.setIndustryArrayList(industryArrayList);
        mBus.post(new RequestAreas());
    }

    @Subscribe
    public void onSuccessAreaEvent(SuccessAreasResponseEvent successAreasResponseEvent) {
        applicationData.setAreaArrayList(successAreasResponseEvent.getResponse());
        mAlertDialog.dismiss();
    }

    public void onFilterIndustryEvent(IndustriesResultsFilter industriesResultsFilter) {
        containerIndustries.removeAllViews();
        industries = industriesResultsFilter.getResults();
        for (int i = 0; i < industries.size(); i++) {
            containerIndustries.addView(inflateInterestArea(industries.get(i), true, i == 0));
        }

    }

    public void onFilterEventsEvent(AreasResultEvents areasResultEvents) {
        containerAreas.removeAllViews();
        areas = areasResultEvents.getResults();
        for (int i = 0; i < areas.size(); i++) {
            containerAreas.addView(inflateInterestArea(areas.get(i), false, i == 0));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_INDUSTRIES) {
                onFilterIndustryEvent((IndustriesResultsFilter) data.getSerializableExtra("data"));
            } else if (resultCode == RESULT_AREAS) {
                onFilterEventsEvent((AreasResultEvents) data.getSerializableExtra("data"));
            }
        }
    }
}
