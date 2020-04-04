package ca.bcit.coveropspainters;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateEventActivity extends AppCompatActivity {

    TextView eventData;
    TextView eventDescription;
    private DatabaseReference mDatabase;
    private List<GraffitiData> graffiti;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mDatabase = FirebaseDatabase.getInstance().getReference("CurrentEvents");

        eventData = (TextView)findViewById(R.id.eventDescription);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String coordinate = bundle.getString("geo_local_area");
            eventData.setText(coordinate);
            //eventData.setText(bundle.getString("graffiti").toString());
        }
        else{
            eventData.setText("No Data Found");
        }
    }

    public void onBtnClick(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        if (user != null) {
            String eventName = "Event" + count;
            String name = user.getDisplayName();
            String email = user.getEmail();
            String key = db.getReference("CurrentEvents").push().getKey();
            Events e = new Events(eventName, name, email);
            mDatabase.child(key).setValue(e);
        }
        count++;
        Toast.makeText(CreateEventActivity.this, "Added to the Database!!",
                Toast.LENGTH_LONG).show();
    }
}
