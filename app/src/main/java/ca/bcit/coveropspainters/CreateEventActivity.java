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

public class CreateEvent extends AppCompatActivity {

    TextView eventData;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

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
        mDatabase = FirebaseDatabase.getInstance().getReference("CurrentEvents");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            User userModel = new User(name, email);
            String Useruid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mDatabase.child(Useruid).setValue(userModel);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
        Toast.makeText(CreateEvent.this, "Added to the Database!!",
                Toast.LENGTH_LONG).show();
    }
}
