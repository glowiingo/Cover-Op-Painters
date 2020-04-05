package ca.bcit.coveropspainters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {
    private static final String TAG = "CreateEventActivity";
    private DatabaseReference mDatabase;
    TextView addressData, postalData, cityData, inputTime, inputDate;
    Geocoder geocoder;
    List<Address> addresses;
    Button dateBtn, timeBtn;
    String address;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        addressData = (TextView)findViewById(R.id.addressData);
        cityData = (TextView)findViewById(R.id.cityData);
        postalData = (TextView)findViewById(R.id.postalCodeData);
        geocoder = new Geocoder(this, Locale.getDefault());
        mDatabase = FirebaseDatabase.getInstance().getReference("CurrentEvents");
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
                address = addresses.get(0).getAddressLine(0);
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


        inputDate = findViewById(R.id.inputDate);
        dateBtn = findViewById(R.id.setDateBtn);
        inputTime = findViewById(R.id.inputTime);
        timeBtn = findViewById(R.id.setTimeBtn);

        timeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                setTime();
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                setDate();
            }
        });
    }

    private void setTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int min) {
                String timeString = "Hour: " + hour + " Minute: " + min;
                inputTime.setText(timeString);
            }
        }, hour, min, false);
        timePickerDialog.show();
    }

    private void setDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mnth = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int mnth, int date) {
                String dateString = year + "/" + mnth + "/" + date;
                inputDate.setText(dateString);
            }
        }, year, mnth, date);

        datePickerDialog.show();
    }

    public void onBtnClick(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        if (user != null) {
            String eventName = "Event " + count;
            String time = inputTime.getText().toString();
            String date = inputDate.getText().toString();
            String name = user.getDisplayName();
            String email = user.getEmail();
            String key = db.getReference("CurrentEvents").push().getKey();
            Events e = new Events(eventName, name, email, address, time, date);
            mDatabase.child(key).setValue(e);
            count++;
        }
        Toast.makeText(CreateEventActivity.this, "Added to the Database!!",
                Toast.LENGTH_LONG).show();
    }

}
