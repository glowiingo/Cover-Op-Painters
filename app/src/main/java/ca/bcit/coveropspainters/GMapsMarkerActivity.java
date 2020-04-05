package ca.bcit.coveropspainters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.res.Resources;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class GMapsMarkerActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private ArrayList<LatLng> listOfLatLng = new ArrayList<>();
    private ClusterManager<Graffiti_Item> mGraffitiCluster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_maps_marker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(13.5f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        JSONObject graffitiGeoJsonObject = null;

        // use geo json instead
        try {
            graffitiGeoJsonObject = new JSONObject(new GlobalFunctions().loadJSONFromAsset(getAssets(), "graffiti.geojson"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assert graffitiGeoJsonObject != null;
        GeoJsonLayer layer = new GeoJsonLayer(mMap, graffitiGeoJsonObject);
        // layer.addLayerToMap();

        // auto focus to downtown Vancouver
        LatLng downtownVancouver = new LatLng(49.2820, -123.1171);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(downtownVancouver));

        // iterate through features (markers)
        for (GeoJsonFeature feature : layer.getFeatures()) {
            LatLng latlng = (LatLng) feature.getGeometry().getGeometryObject();
            listOfLatLng.add(latlng);
        }
        Log.e("List: ", String.valueOf(listOfLatLng.size()));
        layer.setOnFeatureClickListener(new GeoJsonLayer.OnFeatureClickListener() {
            @Override
            public void onFeatureClick(Feature feature) {
                Log.e("Feature: ", feature.toString());
                Log.e("GeoJsonClick", "Feature clicked: " + feature.getGeometry().getGeometryObject());
                Log.e("LatLon", feature.getGeometry().getGeometryObject().toString().split(" ")[1]);
                Log.e("Geo Local: ", feature.getProperty("geo_local_area"));
            }
        });


        setUpClusterer();

    }

    private void setUpClusterer() {
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mGraffitiCluster = new ClusterManager<Graffiti_Item>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mGraffitiCluster);
        mMap.setOnMarkerClickListener(mGraffitiCluster);
//        mMap.setInfoWindowAdapter(mGraffitiCluster.getMarkerManager());
//
//        mMap.setOnInfoWindowClickListener(mGraffitiCluster); //added


        // Add cluster items (markers) to the cluster manager.
        addItems();

        mGraffitiCluster.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Graffiti_Item>() {
            @Override
            public boolean onClusterClick(Cluster<Graffiti_Item> cluster) {
                Log.e("cluster","clicked");
                Log.e("Size: ", String.valueOf(cluster.getSize()));
                String clusterText = "There are " + String.valueOf(cluster.getSize()) + " known graffiti locations in this cluster. " +
                        "Please zoom in and select a specified location to create an event.";
                Toast.makeText(getApplicationContext(), clusterText,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mGraffitiCluster.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Graffiti_Item>() {
            @Override
            public boolean onClusterItemClick(Graffiti_Item item) {
                Log.e("cluster item","clicked");
                Log.e("Coordinates: ", item.getPosition().toString());
                return true;
            }
        });

        mGraffitiCluster.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<Graffiti_Item>() {
            @Override
            public void onClusterItemInfoWindowClick(Graffiti_Item item) {

            }
        }); //added
    }

    private void addItems() {
        for (int i = 0; i < listOfLatLng.size(); i++) {
            LatLng graffitiCoordinates = listOfLatLng.get(i);
            Graffiti_Item graffiti_marker = new Graffiti_Item(graffitiCoordinates, graffitiCoordinates.toString(), graffitiCoordinates.toString());
            mGraffitiCluster.addItem(graffiti_marker);
        }
    }

    /*
     * In case this is needed.
     */
//    public void iterateMarkers() {
//
//        JSONObject graffitiJsonObject = null;
//        JSONArray graffitiArray = null;
//        try {
//            graffitiJsonObject = new JSONObject(new GlobalFunctions().loadJSONFromAsset(getAssets(), "graffiti.json"));
//        } catch (JSONException e) {
//            Log.e("", "Couldn't get json.");
//            e.printStackTrace();
//        }
//        if (graffitiJsonObject != null) {
//            try {
//                graffitiArray = graffitiJsonObject.getJSONArray("graffiti");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        assert graffitiArray != null;
//        for (int i = 0; i < graffitiArray.length(); i++) {
//
//            JSONObject singleGraffitiJsonObject = null;
//            try {
//                singleGraffitiJsonObject = graffitiArray.getJSONObject(i);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            JSONObject fields = null;
//            try {
//                assert singleGraffitiJsonObject != null;
//                fields = singleGraffitiJsonObject.getJSONObject("fields");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            JSONObject geom = null;
//            try {
//                assert fields != null;
//                geom = fields.getJSONObject("geom");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            JSONArray coordinates = null;
//            try {
//                assert geom != null;
//                coordinates = geom.getJSONArray("coordinates");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            assert coordinates != null;
//            double lat = 0;
//            double lon = 0;
//            try {
//                lat = coordinates.getDouble(0);
//                lon = coordinates.getDouble(1);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            LatLng mark = new LatLng(lat, lon);
//            mMap.addMarker(new MarkerOptions().position(mark));
//
//
//        }

//    }

}
