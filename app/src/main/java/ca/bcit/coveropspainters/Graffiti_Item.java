package ca.bcit.coveropspainters;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Graffiti_Item implements ClusterItem {
    private LatLng position;
    private String title;
    private String snippet;

    public Graffiti_Item(LatLng position) {
        this.position = position;
        this.title = "";
        this.snippet = "";
    }

    public Graffiti_Item(LatLng position, String title, String snippet) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return this.position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSnippet() {
        return this.snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
