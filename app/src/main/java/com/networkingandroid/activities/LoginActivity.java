package com.networkingandroid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.networkingandroid.NetworkingApplication;
import com.networkingandroid.R;
import com.networkingandroid.network.BusProvider;
import com.networkingandroid.network.model.LoginRequest;
import com.networkingandroid.network.events.SuccessLoginResponseEvent;
import com.networkingandroid.util.Constants;
import com.networkingandroid.util.PrefsUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Usuario on 16/09/2016.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.buttonEntrar)
    Button buttonEntrar;
    @BindView(R.id.linkedinLogoTextView)
    TextView linkedinLogoTextView;
    private Bus mBus = BusProvider.getBus();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.buttonEntrar, R.id.linkedinLogoTextView})
    public void onButtonEntrarClick(){
        setUpLinkedinLogin();
    }

    private void setUpLinkedinLogin(){
        final LISessionManager sessionManager = LISessionManager.getInstance(getApplicationContext());
        sessionManager.init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
                apiHelper.getRequest(NetworkingApplication.getInstance(), getString(R.string.linkedin_url), new ApiListener() {
                    @Override
                    public void onApiSuccess(ApiResponse apiResponse) {
                        PrefsUtil.getInstance().setIsLogged(true);

                        JSONObject responseJsonObject = apiResponse.getResponseDataAsJson();
                        String emailAddress = "", firstName = "", lastName = "", pictureUrl = "", publicProfileUrl = "", id = "", location = "", company = "", companyTitle = "";

                        try { emailAddress = responseJsonObject.getString(Constants.EMAIL_ADDRESS_TAG); } catch (JSONException e){}
                        try { firstName = responseJsonObject.getString(Constants.FIRST_NAME_TAG); } catch (JSONException e){}
                        try { lastName = responseJsonObject.getString(Constants.LAST_NAME_TAG); } catch (JSONException e){}
                        try { pictureUrl = responseJsonObject.getString(Constants.PICTURE_URL_TAG); } catch (JSONException e){}
                        try { publicProfileUrl = responseJsonObject.getString(Constants.PUBLIC_PROFILE_URL); } catch (JSONException e){}
                        try { id = responseJsonObject.getString(Constants.ID_TAG); } catch (JSONException e){}
                        try { location = responseJsonObject.getJSONObject(Constants.LOCATION_TAG).getString(Constants.NAME_LOCATION_TAG); } catch (JSONException e){}
                        try { company = responseJsonObject.getJSONObject(Constants.POSITIONS_TAG).getJSONArray(Constants.POSITIONS_TAG_VALUES).getJSONObject(0).getJSONObject(Constants.COMPANY_TAG).getString(Constants.COMPANY_NAME_TAG); } catch (JSONException e){}
                        try { companyTitle = responseJsonObject.getJSONObject(Constants.POSITIONS_TAG).getJSONArray(Constants.POSITIONS_TAG_VALUES).getJSONObject(0).getString(Constants.COMPANY_TITLE_NAME); } catch (JSONException e){}

                        prepareProgressDialog();
                        PrefsUtil.getInstance().setActiveAccount(sessionManager.getSession().getAccessToken().getValue(), emailAddress, firstName+" "+lastName, pictureUrl, company, companyTitle, location);
                        mBus.post(new LoginRequest(emailAddress, firstName, lastName, pictureUrl, publicProfileUrl, id));
                    }

                    @Override
                    public void onApiError(LIApiError liApiError) {
                        Toast.makeText(NetworkingApplication.getInstance(), getString(R.string.error_login), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onAuthError(LIAuthError error) {
                Toast.makeText(NetworkingApplication.getInstance(), getString(R.string.error_login), Toast.LENGTH_SHORT).show();
            }
        }, true);
    }

    private Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance(this).onActivityResult(this,requestCode,resultCode,data);
    }

    @Subscribe
    public void onSuccessLoginResponse(SuccessLoginResponseEvent successLoginResponseEvent){
        progressDialog.dismiss();
        Intent intent = new Intent(LoginActivity.this, SelectInterestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PrefsUtil.getInstance().setUserIDLogged(successLoginResponseEvent.getResponse().getId());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBus.unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBus.register(this);
    }

    private void prepareProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_data));
        progressDialog.show();
    }
}