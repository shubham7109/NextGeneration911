package sb5.cs309.nextgen911;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class HomeFragment extends Fragment {
    final String mypreference = "911UserPrefs";
    final String regKey = "Registered";
    final int requestCode = 911;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        requestPermissions();

        /* Handle User Registration Should not block, access to 911
           currently just a dummy registration
         */


        if (!preferences.contains(regKey)) {
            // TODO Handle Registration steps


        } else {
            Button button = (Button) view.findViewById(R.id.reg_status);
            button.setVisibility(View.INVISIBLE);
        }


        View myFragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        return myFragmentView;
    }



    public void requestPermissions() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,Manifest.permission.READ_SMS}, requestCode);
    }
}
