package com.elitgon.nw.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.elitgon.nw.NetworkingApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Usuario on 16/09/2016.
 */
public class UtilsMethods {

    public static void getPackageId(){
        try {
            PackageInfo info = NetworkingApplication.getInstance().getPackageManager().
                    getPackageInfo(NetworkingApplication.getInstance().getPackageName(),
                            PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("====Hash Key===", Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException ex) {

        }
    }
}
