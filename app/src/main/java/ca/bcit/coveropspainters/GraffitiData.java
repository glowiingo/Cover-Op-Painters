package ca.bcit.coveropspainters;

import java.util.HashMap;
import java.util.Map;

public class GraffitiData {

    private String lat;
    private String lng;
    private String location;
    private String datasetid;
    private String recordid;
    private Fields fields;
    private int logo;
    private String recordTimestamp;
    private Map<String, String> additionalProperties = new HashMap<>();
    public void setLogo(int l){this.logo=l;}
    public int getLogo(){return this.logo;}
    public void setLat(String lat){ this.lat = lat; }
    public String getlat(){return this.lat;}
    public void setLng(String lng){ this.lng = lng; }
    public String getlng(){return this.lng;}
    public void setLocation(String t){ this.location = t; }
    public String getLocation(){return this.location;}

    public String getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(String datasetid) {
        this.datasetid = datasetid;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }


    public Fields getFields() {
        return fields;
    }


    public void setFields(Fields fields) {
        this.fields = fields;
    }


    public String getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(String recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public Map<String, String> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, String value) {
        this.additionalProperties.put(name, value);
    }

}
