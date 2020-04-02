package ca.bcit.coveropspainters;

import androidx.fragment.app.FragmentActivity;

import android.content.res.Resources;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class GMapsMarkerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> listOfLatLng = new ArrayList<>();

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

        JSONObject graffitiGeoJsonObject = null;

        // use geo json instead
        try {
            graffitiGeoJsonObject = new JSONObject(new GlobalFunctions().loadJSONFromAsset(getAssets(), "graffiti.geojson"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assert graffitiGeoJsonObject != null;
        GeoJsonLayer layer = new GeoJsonLayer(mMap, graffitiGeoJsonObject);
        layer.addLayerToMap();

        LatLng downtownVancouver = new LatLng(49.2820, -123.1171);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(downtownVancouver));
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        for (GeoJsonFeature feature : layer.getFeatures()) {
            LatLng latlng = (LatLng) feature.getGeometry().getGeometryObject();
            listOfLatLng.add(latlng);
        }
        Log.e("List: ", String.valueOf(listOfLatLng.size()));
        //Log.e("First", listOfLatLng.get(0).toString());
        layer.setOnFeatureClickListener(new GeoJsonLayer.OnFeatureClickListener() {
            @Override
            public void onFeatureClick(Feature feature) {
                Log.e("Feature: ", feature.toString());
                Log.e("GeoJsonClick", "Feature clicked: " + feature.getGeometry().getGeometryObject());
                Log.e("LatLon", feature.getGeometry().getGeometryObject().toString().split(" ")[1]);
                Log.e("Test", feature.getProperty("geo_local_area"));
            }
        });

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
