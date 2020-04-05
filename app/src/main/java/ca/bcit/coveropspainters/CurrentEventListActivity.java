package ca.bcit.coveropspainters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrentEventListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private String TAG = "CurrentEventListActivity";

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    View header;

    private ListView mListView;
    private DatabaseReference mDatabase;
    private List<Events> events;
    private ArrayList<String> arrayOfKeys;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_event_list);

        mListView = findViewById(R.id.current_events_list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBarTop);
        navigationView = findViewById(R.id.naviagtionView);
        setSupportActionBar(toolbar);
        header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.firebase_userName);
        TextView email = header.findViewById(R.id.firebase_userEmail);

        if(user != null) {
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
        } else {
            name.setText("Not-Logged In");
            email.setText("Not-Logged In");
        }

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        setNavigationViewListener();
        toggle.syncState();

        getData();
        arrayOfKeys = new ArrayList<>();
    }

    public void getData() {
        events = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("CurrentEvents");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    arrayOfKeys.add(key);
                    String name = ds.child("createdBy").getValue(String.class);
                    String email = ds.child("email").getValue(String.class);
                    String eventName = ds.child("eventName").getValue(String.class);
                    String address = ds.child("address").getValue(String.class);
                    String time = ds.child("time").getValue(String.class);
                    String date = ds.child("date").getValue(String.class);
                    Events event = new Events(eventName, name, email, address, time, date);
                    events.add(event);
                    Log.d(TAG, "Inside OnData");
                }
                CurrentEventListAdapter adapter = new CurrentEventListAdapter(getApplicationContext(), events);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            String useruid = user.getUid();
                            String name = user.getDisplayName();
                            String email = user.getEmail();
                            User userModel = new User(name, email);
                            for(int i = 0; i < arrayOfKeys.size(); i++) {
                                Log.d(TAG, arrayOfKeys.get(i));
                                if(i == position)
                                    mDatabase.child(arrayOfKeys.get(i)).child("Registered Users").child(useruid).setValue(userModel);
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Registered To The Event!", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error");
            }
        });
        Log.d(TAG, "After");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.firebase_userName);
        TextView email = header.findViewById(R.id.firebase_userEmail);
        switch (item.getItemId()) {
            case R.id.google_maps_menu:
                Intent i = new Intent(CurrentEventListActivity.this, GMapsMarkerActivity.class);
                startActivity(i);
                break;
            case R.id.current_list_menu:
                Intent i2 = new Intent(CurrentEventListActivity.this, CurrentEventListActivity.class);
                startActivity(i2);
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                if(user == null) {
                    name.setText("Not-Logged In");
                    email.setText("Not-Logged In");
                    Toast.makeText(CurrentEventListActivity.this, "Successfully Logged Out", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.naviagtionView);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
