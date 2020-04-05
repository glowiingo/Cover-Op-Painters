package ca.bcit.coveropspainters;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainActivity";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    View header;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        Button signIn = findViewById(R.id.signIn);
        Button current = findViewById(R.id.current);
        Button gmaps = findViewById(R.id.gmaps_button);
        Button currentList = findViewById(R.id.currentList);

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

    public void currentList_intent(View v) {
        Intent i = new Intent(this, CurrentEventListActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Profile:
                Toast.makeText(MainActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Logout:
                Toast.makeText(MainActivity.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Successfully Logged Out", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.naviagtionView);
        navigationView.setNavigationItemSelectedListener(this);
    }

}
