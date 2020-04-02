package ca.bcit.coveropspainters;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrentEventActivity extends AppCompatActivity {

    private String TAG = "CurrentEventActivity";

    private ListView mListView;
    private List<GraffitiData> graffiti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_event);
        Log.e(TAG, "ERROR");

        mListView = findViewById(R.id.current_events);
        graffiti = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(new GlobalFunctions().loadJSONFromAsset(getAssets(), "graffiti.json"));
            JSONArray array = object.getJSONArray("graffiti");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);
                String dataID = jsonObject.getString("datasetid");
                String recordID = jsonObject.getString("recordid");
//                Fields fields = jsonObject.getString("fields");
//                String location = jsonObject.getString("geo_local_area");

                GraffitiData g = new GraffitiData();
                g.setDatasetid(dataID);
                g.setRecordid(recordID);
                graffiti.add(g);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CurrentAdapter adapter = new CurrentAdapter(this, graffiti);
        mListView.setAdapter(adapter);
    }

}
