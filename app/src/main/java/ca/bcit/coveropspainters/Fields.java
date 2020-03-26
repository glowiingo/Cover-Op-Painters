package ca.bcit.coveropspainters;

import java.util.HashMap;
import java.util.Map;

public class Fields {

    private Integer count;
    private Geom geom;
    private String geoLocalArea;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Geom getGeom() {
        return geom;
    }

    public void setGeom(Geom geom) {
        this.geom = geom;
    }

    public String getGeoLocalArea() {
        return geoLocalArea;
    }

    public void setGeoLocalArea(String geoLocalArea) {
        this.geoLocalArea = geoLocalArea;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
