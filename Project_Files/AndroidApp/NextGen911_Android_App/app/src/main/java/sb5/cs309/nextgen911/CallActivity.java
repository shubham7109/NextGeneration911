package sb5.cs309.nextgen911;

import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.text.ParseException;

public class CallActivity extends AppCompatActivity {

    public SipManager mSipManager = null;
    public SipProfile mSipProfile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        register();
        String address = getAddress();
        call(address);
    }

    protected void register() {
        if (mSipManager == null) {
            mSipManager = SipManager.newInstance(this);
        }
        try {
            SipProfile.Builder builder = new SipProfile.Builder(getResources().getString(R.string.SIP_username), getResources().getString(R.string.SIP_domain));
            builder.setPassword(getResources().getString(R.string.SIP_password));
            mSipProfile = builder.build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void call(String sipAddress) {
        SipAudioCall.Listener listener = new SipAudioCall.Listener() {

            @Override
            public void onCallEstablished(SipAudioCall call) {
                call.startAudio();
                call.setSpeakerMode(true);
                call.toggleMute();
            }

            @Override
            public void onCallEnded(SipAudioCall call) {
                // Do something.
            }
        };

        try {
            mSipManager.makeAudioCall(mSipProfile.getUriString(), sipAddress, listener, 30);
        } catch (SipException e) {
            e.printStackTrace();
        }
    }


    // Returns the address that the caller should call
    // TODO
    protected String getAddress() {
        return "user@nextgen911.onsip.com";
    }

}
