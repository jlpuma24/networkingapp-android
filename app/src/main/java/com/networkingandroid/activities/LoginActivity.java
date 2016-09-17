package com.networkingandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.networkingandroid.NetworkingApplication;
import com.networkingandroid.R;
import com.networkingandroid.util.Constants;
import com.networkingandroid.util.PrefsUtil;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Usuario on 16/09/2016.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.buttonEntrar)
    Button buttonEntrar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonEntrar)
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
                        String emailAddress;
                        try {
                            emailAddress = apiResponse.getResponseDataAsJson().getString(Constants.EMAIL_ADDRESS_TAG);
                        } catch (JSONException e){
                            emailAddress = "";
                        }
                        PrefsUtil.getInstance().setActiveAccount(sessionManager.getSession().getAccessToken().getValue(), emailAddress);
                        startActivity(new Intent(LoginActivity.this, SelectInterestActivity.class));
                        finish();
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
}