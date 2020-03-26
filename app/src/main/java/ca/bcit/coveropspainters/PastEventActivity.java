package ca.bcit.coveropspainters;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PastEventActivity extends AppCompatActivity {

    private String TAG = "PastEventActivity";

    private ListView mListView;
    private List<GraffitiData> graffiti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_event);
        Log.e(TAG, "ERROR");

        mListView = findViewById(R.id.past_events);
        graffiti = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(loadJSONFromAsset());
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

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("graffiti.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

}
