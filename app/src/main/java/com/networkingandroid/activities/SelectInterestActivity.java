package com.networkingandroid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.networkingandroid.R;
import com.networkingandroid.adapters.CustomAlertAdapter;
import com.networkingandroid.network.BusProvider;
import com.networkingandroid.network.events.AreasResultEvents;
import com.networkingandroid.network.events.IndustriesResultsFilter;
import com.networkingandroid.network.events.RequestAreas;
import com.networkingandroid.network.events.RequestIndustries;
import com.networkingandroid.network.events.SuccessAreasResponseEvent;
import com.networkingandroid.network.events.SuccessIndustriesResponseEvent;
import com.networkingandroid.network.model.ApplicationData;
import com.networkingandroid.network.model.Area;
import com.networkingandroid.network.model.Industry;
import com.networkingandroid.util.OnFilterEvents;
import com.networkingandroid.util.PrefsUtil;
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
    @BindView(R.id.editTextIntereses)
    EditText editTextIntereses;
    @BindView(R.id.editTextArea)
    EditText editTextArea;
    @BindView(R.id.containerAreas)
    LinearLayout containerAreas;
    @BindView(R.id.containerIndustries)
    LinearLayout containerIndustries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_areas);
        ButterKnife.bind(this);
        mAlertDialog = new ProgressDialog(this);
        mAlertDialog.setMessage(getString(R.string.loading_data));
        mAlertDialog.setCancelable(false);
        containerIndustries.removeAllViews();
        containerAreas.removeAllViews();
        mBus.post(new RequestIndustries());
    }

    @OnClick(R.id.editTextIntereses)
    public void onEditTextInteresesClicked(){
        Intent intent = new Intent(this, FilterIndustriesActivity.class);
        intent.putExtra("isIndustries", true);
        intent.putExtra("applicationData", applicationData);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.editTextArea)
    public void onEditTextAreaClicked(){
        Intent intent = new Intent(this, FilterIndustriesActivity.class);
        intent.putExtra("isIndustries", false);
        intent.putExtra("applicationData", applicationData);
        startActivityForResult(intent, 1);
    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
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