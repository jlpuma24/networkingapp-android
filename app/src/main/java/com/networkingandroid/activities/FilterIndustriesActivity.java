package com.networkingandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.networkingandroid.R;
import com.networkingandroid.network.BusProvider;
import com.networkingandroid.network.events.AreasResultEvents;
import com.networkingandroid.network.events.IndustriesResultsFilter;
import com.networkingandroid.network.model.ApplicationData;
import com.networkingandroid.network.model.Area;
import com.networkingandroid.network.model.Industry;
import com.networkingandroid.util.OnFilterEvents;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Usuario on 11/10/2016.
 */
public class FilterIndustriesActivity extends BaseActivity {

    @BindView(R.id.listViewData)
    ListView listViewData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.headerBar)
    TextView headerBar;
    @BindView(R.id.confirmButton)
    Button confirmButton;
    @BindView(R.id.editTextSearch)
    EditText editTextSearch;
    private ApplicationData applicationData;
    private boolean searchShowed = false;
    private boolean isIndustries;
    private Bus mBus = BusProvider.getBus();
    private ArrayAdapter<String> adapter;
    private OnFilterEvents onFilterEvents;
    private final int RESULT_INDUSTRIES = 2;
    private final int RESULT_AREAS = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);
        ButterKnife.bind(this);
        isIndustries = getIntent().getBooleanExtra("isIndustries", false);
        applicationData = (ApplicationData) getIntent().getSerializableExtra("applicationData");
        onFilterEvents = (OnFilterEvents) getIntent().getSerializableExtra("interface");
        prepareListView();
        prepareToolbar();
        prepareTextWatcher();
    }

    private void prepareListView() {
        listViewData.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = isIndustries ? buildStringIndustriesAdapter(applicationData.getIndustryArrayList())
                : buildStringAreasAdapter(applicationData.getAreaArrayList());
        listViewData.setAdapter(adapter);
    }

    private void prepareTextWatcher(){
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int position, int i1, int i2) {
                ArrayList<String> results = new ArrayList<String>();
                ArrayList<Industry> industries = applicationData.getIndustryArrayList();
                ArrayList<Area> areas = applicationData.getAreaArrayList();
                if (isIndustries) {
                    for (int i = 0; i < industries.size();i++){
                        if (industries.get(i).getName().toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                            results.add(industries.get(i).getName());
                        }
                    }
                }
                else {
                    for (int i = 0; i < areas.size();i++){
                        if (areas.get(i).getName().toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                            results.add(areas.get(i).getName());
                        }
                    }
                }
                String[] arrayAdapter = new String[results.size()];
                arrayAdapter = results.toArray(arrayAdapter);

                listViewData.setAdapter(new ArrayAdapter<String>(FilterIndustriesActivity.this,
                        android.R.layout.simple_list_item_multiple_choice, arrayAdapter));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void prepareToolbar() {
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
        headerBar.setText(isIndustries ? getString(R.string.industries) : getString(R.string.areas));
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

    @OnClick(R.id.confirmButton)
    public void onConfirmButtonClicked(){
        SparseBooleanArray checked = listViewData.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }
        if (isIndustries){
            Intent intent = new Intent();
            intent.putExtra("data", new IndustriesResultsFilter(selectedItems));
            setResult(RESULT_INDUSTRIES, intent);
            finish();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("data", new AreasResultEvents(selectedItems));
            setResult(RESULT_AREAS, intent);
            finish();
        }
    }

    private ArrayAdapter<String> buildStringIndustriesAdapter(ArrayList<Industry> industryArrayList) {
        String[] industriesStringArray = new String[industryArrayList.size()];
        int i = 0;
        for (Industry industry : industryArrayList) {
            industriesStringArray[i] = industry.getName();
            i++;
        }
        return new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, industriesStringArray);
    }

    private ArrayAdapter<String> buildStringAreasAdapter(ArrayList<Area> areaArrayList) {
        String[] areasStringArray = new String[areaArrayList.size()];
        int i = 0;
        for (Area area : areaArrayList) {
            areasStringArray[i] = area.getName();
            i++;
        }
        return new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, areasStringArray);
    }
}