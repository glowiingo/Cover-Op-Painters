package ca.bcit.coveropspainters;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

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
        // to add each individual marker to the map
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

        // cluster the markers instead of adding individual markers
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

        // Add cluster items (markers) to the cluster manager.
        addItems();

        mGraffitiCluster.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Graffiti_Item>() {
            @Override
            public boolean onClusterClick(Cluster<Graffiti_Item> cluster) {
                String clusterText = "There are " + String.valueOf(cluster.getSize()) + " known graffiti locations in this cluster. " +
                        "Please zoom in and select a specified location to create an event.";
                Toast.makeText(getApplicationContext(), clusterText,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mGraffitiCluster.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Graffiti_Item>() {
            @Override
            public boolean onClusterItemClick(Graffiti_Item item) {
                GraffitiData graffitiItem = new GraffitiData();
                graffitiItem.setLat(String.valueOf(item.getPosition().latitude));
                graffitiItem.setLng(String.valueOf(item.getPosition().longitude));
                Intent intent = new Intent(GMapsMarkerActivity.this, CreateEventActivity.class);
                intent.putExtra("area", graffitiItem.getLocation());
                intent.putExtra("lat", graffitiItem.getlng());
                intent.putExtra("lng", graffitiItem.getlat());
                startActivity(intent);
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

}
