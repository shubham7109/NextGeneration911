package sb5.cs309.nextgen911;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Implementation of Fingerprint manager's authenticalCallback. Required to process fingerprints
 */

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private CancellationSignal cancellationSignal;
    private Context context;

    /**
     * @param mContext Context of the calling activity
     */
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    /**
     * Start authentication of a fingerprint
     *
     * @param manager      Provided by system security services
     * @param cryptoObject Provided by system security services
     */
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    /**
     * Display an error toast on system error
     *
     * @param errMsgId  Unused parameter from override
     * @param errString Unused parameter from override
     */
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }

    /**
     * Display an error toast on authentication failure
     */
    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();
    }


    /**
     * On success open the Personal info activity
     *
     * @param result Unused Override param
     */
    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        //Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        context.startActivity(intent);
    }

}