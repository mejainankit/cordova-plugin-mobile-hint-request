package cordova.plugins;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * This class echoes a string called from JavaScript.
 */
public class CordovaHintRequest extends CordovaPlugin {

    public static final String TAG = "PhoneHintActivity";
    private static final int RC_PHONE_HINT = 22;
    private static final int RESULT_OK = -1;

    private GoogleApiClient client;

    public CallbackContext callback = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("showHintRequest")) {
            callback = callbackContext;
            cordova.setActivityResultCallback(this);
            String message = args.getString(0);
            this.showHintRequest(message, callbackContext);
            return true;
        }
        return false;
    }

    private void showHintRequest(String message, CallbackContext callbackContext) {
        showPhoneAutoCompleteHint();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Activity activity = cordova.getActivity();
        if (requestCode == RC_PHONE_HINT) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    com.google.android.gms.auth.api.credentials.Credential cred = data
                            .getParcelableExtra(com.google.android.gms.auth.api.credentials.Credential.EXTRA_KEY);
                    if (cred != null) {
                        final String unformattedPhone = cred.getId();
                        callback.success(unformattedPhone);
                    } else {
                        callback.error("Error: Unable to detect Phone Number");
                    }
                }
            } else {
                // User clicked outside to close the intent or clicked none of the above
                callback.success(0);
            }
            client.stopAutoManage((FragmentActivity) activity);
            client.disconnect();
        }
    }

    private void showPhoneAutoCompleteHint() {
        try {
            Activity activity = cordova.getActivity();
            ActivityCompat.startIntentSenderForResult(activity, getPhoneHintIntent().getIntentSender(), RC_PHONE_HINT,
                    null, 0, 0, 0, null);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Unable to start hint intent", e);
        }
    }

    private PendingIntent getPhoneHintIntent() {
        Activity activity = cordova.getActivity();
        Context context = cordova.getActivity().getApplicationContext();
        client = new GoogleApiClient.Builder(context)
                .addApi(Auth.CREDENTIALS_API)
                .enableAutoManage(
                        (FragmentActivity) activity,
                        (GoogleApiClient.OnConnectionFailedListener) connectionResult -> Log.e("MAIN ACTIVITY",
                                "Client connection failed: " + connectionResult.getErrorMessage()))
                .build();
        HintRequest hintRequest = new HintRequest.Builder()
                .setHintPickerConfig(
                        new CredentialPickerConfig.Builder().setShowCancelButton(true).build())
                .setPhoneNumberIdentifierSupported(true)
                .setEmailAddressIdentifierSupported(false)
                .build();
        return Auth.CredentialsApi.getHintPickerIntent(client, hintRequest);
    }
}
