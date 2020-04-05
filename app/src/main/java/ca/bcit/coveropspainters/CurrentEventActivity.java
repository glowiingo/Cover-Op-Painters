package ca.bcit.coveropspainters;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CurrentEventActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private String TAG = "CurrentEventActivity";
    private int count = 0;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Geocoder geocoder;
    List<Address> addresses;

    private ListView mListView;
    private List<GraffitiData> graffiti;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_event);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBarTop);
        navigationView = findViewById(R.id.naviagtionView);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        Log.e(TAG, "ERROR");

        mListView = findViewById(R.id.current_events);
        graffiti = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(new GlobalFunctions().loadJSONFromAsset(getAssets(), "graffiti.json"));
            JSONArray array = object.getJSONArray("graffiti");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);

                String location = jsonObject.getJSONObject("fields").getString("geo_local_area");
                JSONArray coordJson = jsonObject.getJSONObject("fields").getJSONObject("geom").getJSONArray("coordinates");

                ArrayList<String> coord = new ArrayList<>();
                for (int j = 0; j<coordJson.length(); j++){
                    coord.add(coordJson.get(j).toString());
                }
                geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(Double.parseDouble(coord.get(1)), Double.parseDouble(coord.get(0)), 1);
                String dataID = addresses.get(0).getAddressLine(0);
                GraffitiData g = new GraffitiData();
                g.setLat(coord.get(0));
                g.setLng(coord.get(1));
                g.setLocation(location);
                g.setDatasetid(dataID);
                graffiti.add(g);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        CurrentAdapter adapter = new CurrentAdapter(this, graffiti);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent intent = new Intent(CurrentEventActivity.this, CreateEvent.class);
                GraffitiData graffitiItem = (GraffitiData) mListView.getItemAtPosition(position);
                Intent intent = new Intent(CurrentEventActivity.this, CreateEventActivity.class);
                intent.putExtra("area", graffitiItem.getLocation());
                intent.putExtra("lat", graffitiItem.getlat());
                intent.putExtra("lng", graffitiItem.getlng());
                intent.putExtra("graffiti", mListView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Profile:
                Toast.makeText(CurrentEventActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Contact:
                Toast.makeText(CurrentEventActivity.this, "Contact Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.About:
                Toast.makeText(CurrentEventActivity.this, "About Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Logout:
                Toast.makeText(CurrentEventActivity.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
