package com.elitgon.nw.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elitgon.R;
import com.elitgon.nw.network.BusProvider;
import com.elitgon.nw.network.UserUpdateRequest;
import com.elitgon.nw.network.events.AreasResultEvents;
import com.elitgon.nw.network.events.IndustriesResultsFilter;
import com.elitgon.nw.network.events.RequestAreas;
import com.elitgon.nw.network.events.RequestIndustries;
import com.elitgon.nw.network.events.SuccessAreasResponseEvent;
import com.elitgon.nw.network.events.SuccessIndustriesResponseEvent;
import com.elitgon.nw.network.model.ApplicationData;
import com.elitgon.nw.network.model.Area;
import com.elitgon.nw.network.model.Industry;
import com.elitgon.nw.network.model.IndustryAreaUser;
import com.elitgon.nw.network.model.UserUpdateObjectRequest;
import com.elitgon.nw.network.model.UserUpdateResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Usuario on 16/09/2016.
 */
public class SelectInterestActivity extends BaseActivity {

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
    @BindView(R.id.editTextIntereses)
    TextView editTextIntereses;
    @BindView(R.id.editTextArea)
    TextView editTextArea;
    @BindView(R.id.containerAreas)
    LinearLayout containerAreas;
    @BindView(R.id.containerIndustries)
    LinearLayout containerIndustries;
    @BindView(R.id.industriesContainerArea)
    RelativeLayout industriesContainerArea;
    @BindView(R.id.areasContainerArea)
    RelativeLayout areasContainerArea;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_areas);
        ButterKnife.bind(this);
        mAlertDialog = new ProgressDialog(this);
        mAlertDialog.setMessage(getString(R.string.loading_data));
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
        containerIndustries.removeAllViews();
        containerAreas.removeAllViews();
        industriesContainerArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditTextInteresesClicked();
            }
        });

        areasContainerArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditTextAreaClicked();
            }
        });
        mBus.post(new RequestIndustries());
    }

    public void onEditTextInteresesClicked(){
        Intent intent = new Intent(this, FilterIndustriesActivity.class);
        intent.putExtra("isIndustries", true);
        intent.putExtra("applicationData", applicationData);
        startActivityForResult(intent, 1);
    }

    public void onEditTextAreaClicked(){
        Intent intent = new Intent(this, FilterIndustriesActivity.class);
        intent.putExtra("isIndustries", false);
        intent.putExtra("applicationData", applicationData);
        startActivityForResult(intent, 1);
    }

    private TextView inflateInterestArea(final String value, final boolean isIndustry, boolean isFirst){
        View child = getLayoutInflater().inflate(R.layout.word, null);
        TextView word = (TextView) child.findViewById(R.id.textViewWord);
        if (isFirst)
            word.setText(value);
        else
            word.setText(" |  "+ value);
        word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(value, isIndustry);
            }
        });
        return word;
    }

    private void removeItem(String value, boolean isIndustry){
        if (isIndustry){
            industries.remove(value);
            containerIndustries.removeAllViews();
            for (int i = 0; i < industries.size(); i++) {
                containerIndustries.addView(inflateInterestArea(industries.get(i), true, i == 0));
            }
        }
        else {
            areas.remove(value);
            containerAreas.removeAllViews();
            for (int i = 0; i < areas.size(); i++) {
                containerAreas.addView(inflateInterestArea(areas.get(i), false, i == 0));
            }
        }
    }

    @OnClick(R.id.buttonContinuar)
    public void onButtonContinuarClicked(){
        mAlertDialog.show();
        ArrayList<IndustryAreaUser> industryAreaUser = new ArrayList<IndustryAreaUser>();
        for (String area: areas){
            Area areaSelected = getSpecificArea(area);
            if (areaSelected != null){
                industryAreaUser.add(new IndustryAreaUser(areaSelected.getIndustry_id(), areaSelected.getId(), false));
            }
        }
        if (!industryAreaUser.isEmpty()) {
            userUpdateRequest.setIndustry_areas(industryAreaUser);
            mBus.post(new UserUpdateObjectRequest(userUpdateRequest));
        }
        else {
            mAlertDialog.dismiss();
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
    }

    public Area getSpecificArea(String areaName){
        for (Area area: applicationData.getAreaArrayList()){
            if (area.getName().equals(areaName))
                return area;
        }
        return null;
    }

    @Subscribe
    public void onUserUpdateResponse(UserUpdateResponse userUpdateResponse){
        mAlertDialog.dismiss();
        startActivity(new Intent(this,HomeActivity.class));
        finish();
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
    public void onSuccessIndustriesEvent(SuccessIndustriesResponseEvent successIndustriesResponseEvent){
        industryArrayList = successIndustriesResponseEvent.getResponse();
        applicationData.setIndustryArrayList(industryArrayList);
        mBus.post(new RequestAreas());
    }

    @Subscribe
    public void onSuccessAreaEvent(SuccessAreasResponseEvent successAreasResponseEvent){
        applicationData.setAreaArrayList(successAreasResponseEvent.getResponse());
        mAlertDialog.dismiss();
    }

    public void onFilterIndustryEvent(IndustriesResultsFilter industriesResultsFilter){
        containerIndustries.removeAllViews();
        industries = industriesResultsFilter.getResults();
        for (int i = 0; i < industries.size(); i++) {
            containerIndustries.addView(inflateInterestArea(industries.get(i), true, i == 0));
        }
    }

    public void onFilterEventsEvent(AreasResultEvents areasResultEvents){
        containerAreas.removeAllViews();
        areas = areasResultEvents.getResults();
        for (int i = 0; i < areas.size(); i++) {
            containerAreas.addView(inflateInterestArea(areas.get(i), false, i == 0));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if (resultCode == RESULT_INDUSTRIES){
                onFilterIndustryEvent((IndustriesResultsFilter) data.getSerializableExtra("data"));
            } else if(resultCode == RESULT_AREAS){
                onFilterEventsEvent((AreasResultEvents) data.getSerializableExtra("data"));
            }
        }
    }
}