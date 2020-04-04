package ca.bcit.coveropspainters;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateEvent extends AppCompatActivity {

    TextView addressData;
    TextView postalData;
    TextView cityData;
//    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//    List<Address> adList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        addressData = (TextView)findViewById(R.id.addressData);
        cityData = (TextView)findViewById(R.id.cityData);
        postalData = (TextView)findViewById(R.id.postalCodeData);

        Bundle bundle = getIntent().getExtras();
        String dataType =  bundle.getString("type");
        String dataLat =  bundle.getString("lat");
        String dataLng =  bundle.getString("lng");
        addressData.setText(dataLat);
//        try {
//            adList = geocoder.getFromLocation(Double.parseDouble(dataLat), Double.parseDouble(dataLng), 1);
//
//            String address = adList.get(0).getAddressLine(0);
////            String city = adList.get(0).getLocality();
////            String postalCode = adList.get(0).getPostalCode();
//
//            addressData.setText(address);
////            cityData.setText(city);
////            postalData.setText(postalCode);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}
