package ca.bcit.coveropspainters;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class GlobalFunctions {

    public String loadJSONFromAsset(AssetManager assetManager, String fileName) {
        String json = null;
        try {
            InputStream is = assetManager.open(fileName);
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
