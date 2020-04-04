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
    ArrayList<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        addressData = (TextView)findViewById(R.id.addressData);
        cityData = (TextView)findViewById(R.id.cityData);
        postalData = (TextView)findViewById(R.id.postalCodeData);

        geocoder = new Geocoder(this, Locale.getDefault());

        Bundle bundle = getIntent().getExtras();
        String dataType =  bundle.getString("type");
        String lat =  bundle.getString("lat");
        String lng =  bundle.getString("lng");
        Log.d(TAG, "onCreate longitude: " + lng);
        Log.d(TAG, "onCreate latitude: " + lat);


//        try {
//            double dataLat = Double.parseDouble(lat);
//            double dataLng = Double.parseDouble(lng);
//            Log.d(TAG, "onCreate latitude: " + dataLat);
//            Log.d(TAG, "onCreate longitude: " + dataLat);
//            addresses = (ArrayList<Address>) geocoder.getFromLocation(dataLat, dataLng, 1);
//            if (addresses.size()>0){
//                String address = addresses.get(0).getAddressLine(0);
//                String city = addresses.get(0).getLocality();
//                String postalCode = addresses.get(0).getPostalCode();
//
//                addressData.setText(address);
//                cityData.setText(city);
//                postalData.setText(postalCode);
//            }
//        }
//        catch (NullPointerException | IOException e){
//            e.getStackTrace();
//        }
    }
}
