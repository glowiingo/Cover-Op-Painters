package ca.bcit.coveropspainters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signIn = findViewById(R.id.signIn);
        Button current = findViewById(R.id.current);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_in(v);
            }
        });
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_intent(v);
            }
        });
    }

    public void sign_in(View v) {
        Intent i = new Intent(this, FirebaseUIActivity.class);
        startActivity(i);
    }

    public void current_intent(View v) {
        Intent i = new Intent(this, CurrentEventActivity.class);
        startActivity(i);
    }

    public void gmap(View v) {
        Intent i = new Intent(this, GMapsMarkerActivity.class);
        startActivity(i);
    }
}
