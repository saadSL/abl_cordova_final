package com.unikrew.faceoff.ABLPlugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.mobile_number.MobileNumberActivity;
import com.unikrew.faceoff.ABLPlugin.ui.aasan_account.personal_details.TaxResidentActivity;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.nationality.NationalityActivity;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.organizational_details.OrganizationDetailsActivity;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.personal_details.PersonalDetailsActivity;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.setup_transaction.SetupTransactionActivity;
import com.unikrew.faceoff.ABLPlugin.ui.current_account.setup_transaction.SetupTransactionActivity2;


/**
 * This class echoes a string called from JavaScript.
 */
public class CustomPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("startFlow")) {

            // String message = args.getString(0);

            this.startFlow(callbackContext);
            return true;
        }
        return false;
    }

    private void startFlow(CallbackContext callbackContext) {
        Context context = cordova.getActivity().getApplicationContext();
        Intent intent = new Intent(context, PersonalDetailsActivity.class);
        cordova.setActivityResultCallback(this);
        this.cordova.getActivity().startActivityForResult(intent, 22);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 22 && resultCode == 200 && data != null) {
            System.out.println(data);
        }
    }
}
