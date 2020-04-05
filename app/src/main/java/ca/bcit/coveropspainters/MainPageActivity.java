package ca.bcit.coveropspainters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainPageActivity";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    View header;
    CardView gmaps, currentList;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBarTop);
        navigationView = findViewById(R.id.naviagtionView);
        setSupportActionBar(toolbar);
        header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.firebase_userName);
        TextView email = header.findViewById(R.id.firebase_userEmail);


        if (user != null) {
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

        gmaps = findViewById(R.id.gmaps_button);
        currentList = findViewById(R.id.currentList);

        gmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmap(v);
            }
        });
        currentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentList_intent(v);
            }
        });
    }

    public void gmap(View v) {
        Intent i = new Intent(this, GMapsMarkerActivity.class);
        startActivity(i);
    }

    public void currentList_intent(View v) {
        Intent i = new Intent(this, CurrentEventListActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.firebase_userName);
        TextView email = header.findViewById(R.id.firebase_userEmail);
        switch (item.getItemId()) {
            case R.id.google_maps_menu:
                Intent i = new Intent(MainPageActivity.this, GMapsMarkerActivity.class);
                startActivity(i);
                break;
            case R.id.current_list_menu:
                Intent i2 = new Intent(MainPageActivity.this, CurrentEventListActivity.class);
                startActivity(i2);
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                if (user == null) {
                    name.setText("Not-Logged In");
                    email.setText("Not-Logged In");
                    Toast.makeText(MainPageActivity.this, "Successfully Logged Out", Toast.LENGTH_LONG).show();
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
