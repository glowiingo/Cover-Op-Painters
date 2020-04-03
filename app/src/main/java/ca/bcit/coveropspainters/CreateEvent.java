package ca.bcit.coveropspainters;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CreateEvent extends AppCompatActivity {

    TextView eventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventData = (TextView)findViewById(R.id.eventDescription);
        Bundle bundle = getIntent().getExtras();
        
        eventData.
    }
}
