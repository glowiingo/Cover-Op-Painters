package ca.bcit.coveropspainters;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateEvent extends AppCompatActivity {

    private static final String TAG = "CreateEventActivity";
    TextView addressData;
    TextView postalData;
    TextView cityData;
    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        addressData = (TextView)findViewById(R.id.addressData);
        cityData = (TextView)findViewById(R.id.cityData);
        postalData = (TextView)findViewById(R.id.postalCodeData);

        geocoder = new Geocoder(this, Locale.getDefault());

        Bundle bundle = getIntent().getExtras();
        String dataArea =  bundle.getString("area");
        String lat =  bundle.getString("lat");
        String lng =  bundle.getString("lng");

        try {
            double dataLat = Double.parseDouble(lng);
            double dataLng = Double.parseDouble(lat);
            Log.d(TAG, "onCreate latitude: " + dataLat);
            Log.d(TAG, "onCreate longitude: " + dataLat);
            addresses = geocoder.getFromLocation(dataLat, dataLng, 1);
            if (addresses.size()>0){
//                String address = addresses.get(0).getAddressLine(0);
                StringBuilder str = new StringBuilder();

                String street = addresses.get(0).getThoroughfare();
                String num = addresses.get(0).getSubThoroughfare();
                str.append(num + " " + street);
                String city = addresses.get(0).getLocality();
                String postalCode = addresses.get(0).getPostalCode();

                addressData.setText(str);
                cityData.setText(city);
                postalData.setText(postalCode);
            }
        }
        catch (NullPointerException | IOException e){
            e.getStackTrace();
        }
    }
}
