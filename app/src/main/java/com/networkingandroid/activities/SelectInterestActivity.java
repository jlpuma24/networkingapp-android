package com.networkingandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.networkingandroid.R;
import com.networkingandroid.adapters.CustomAlertAdapter;
import com.networkingandroid.network.BusProvider;
import com.networkingandroid.network.events.RequestAreas;
import com.networkingandroid.network.events.RequestIndustries;
import com.networkingandroid.network.events.SuccessAreasResponseEvent;
import com.networkingandroid.network.events.SuccessIndustriesResponseEvent;
import com.networkingandroid.network.model.Area;
import com.networkingandroid.network.model.Industry;
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
    private ArrayList<Industry> industryArrayList;
    private ArrayList<Area> areaArrayList;
    private ArrayList<String> industries = new ArrayList<String>();
    private ArrayList<String> areas = new ArrayList<String>();
    private AlertDialog mAlertDialog;
    @BindView(R.id.editTextIntereses)
    EditText editTextIntereses;
    @BindView(R.id.editTextArea)
    EditText editTextArea;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_areas);
        ButterKnife.bind(this);
        mBus.post(new RequestIndustries());
    }

    @OnClick(R.id.editTextIntereses)
    public void onEditTextInteresesClicked(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(SelectInterestActivity.this);
        myDialog.setTitle(getString(R.string.selecciona_industria));
        final ArrayList<String> arrayAdapter = new ArrayList<String>();
        View toplist = getLayoutInflater().inflate(R.layout.search_list, null);
        SearchView searchBar = (SearchView) toplist.findViewById(R.id.search_bar);
        final ListView listitem = (ListView) toplist.findViewById(R.id.list_item_dialog);
        listitem.setAdapter(null);
        searchBar.setFocusable(true);
        searchBar.setIconified(false);
        searchBar.requestFocusFromTouch();
        searchBar.setQueryHint(getString(R.string.buscar));
        for (Industry industry: industryArrayList)
            industries.add(industry.getName());
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.clear();
                for (int i = 0; i < industries.size(); i++) {
                    if(industries.get(i).toLowerCase().contains(newText.toLowerCase().trim())) {
                        arrayAdapter.add(industries.get(i));
                    }
                }
                listitem.setAdapter(new CustomAlertAdapter(SelectInterestActivity.this, arrayAdapter));
                return false;
            }
        });

        listitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> inputParent, View view, int position, long id) {
                editTextIntereses.setText(arrayAdapter.get(position));
                hideKeyboard();
                mBus.post(new RequestAreas(getIndustryId(arrayAdapter.get(position))));
                mAlertDialog.dismiss();
            }
        });

        myDialog.setView(toplist);
        mAlertDialog = myDialog.show();
    }

    private long getIndustryId(String industrySelected){
        for (Industry industry: industryArrayList)
            if (industry.getName().equals(industrySelected))
                return industry.getId();
        return 0;
    }

    @OnClick(R.id.editTextArea)
    public void onEditTextAreaClicked(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(SelectInterestActivity.this);
        myDialog.setTitle(getString(R.string.selecciona_area));
        final ArrayList<String> arrayAdapter = new ArrayList<String>();
        View toplist = getLayoutInflater().inflate(R.layout.search_list, null);
        SearchView searchBar = (SearchView) toplist.findViewById(R.id.search_bar);
        final ListView listitem = (ListView) toplist.findViewById(R.id.list_item_dialog);
        listitem.setAdapter(null);
        searchBar.setFocusable(true);
        searchBar.setIconified(false);
        searchBar.requestFocusFromTouch();
        searchBar.setQueryHint(getString(R.string.buscar));
        for (Area area: areaArrayList)
            areas.add(area.getName());
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.clear();
                for (int i = 0; i < industries.size(); i++) {
                    if(areas.get(i).toLowerCase().contains(newText.toLowerCase().trim())) {
                        arrayAdapter.add(areas.get(i));
                    }
                }
                listitem.setAdapter(new CustomAlertAdapter(SelectInterestActivity.this, arrayAdapter));
                return false;
            }
        });

        listitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> inputParent, View view, int position, long id) {
                editTextArea.setText(arrayAdapter.get(position));
                editTextArea.setText("");
                hideKeyboard();
                mAlertDialog.dismiss();
            }
        });

        myDialog.setView(toplist);
        mAlertDialog = myDialog.show();
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
        industryArrayList = new ArrayList<Industry>();
        industryArrayList = successIndustriesResponseEvent.getResponse();
    }

    @Subscribe
    public void onSuccessAreaEvent(SuccessAreasResponseEvent successAreasResponseEvent){
        areaArrayList = new ArrayList<Area>();
        areaArrayList = successAreasResponseEvent.getResponse();
    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}