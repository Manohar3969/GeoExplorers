package com.manohar3969.geoexplorers;

public class Destinations {

    private String DestID;
    private String DestName;
    private String DestType;
    private String DestImage;

    public Destinations(){

    }

    public String getDestID() {
        return DestID;
    }

    public void setDestID(String destID) {
        DestID = destID;
    }

    public String getDestName() {
        return DestName;
    }

    public void setDestName(String destName) {
        DestName = destName;
    }

    public String getDestType() {
        return DestType;
    }

    public void setDestType(String destType) {
        DestType = destType;
    }

    public String getDestImage() {
        return DestImage;
    }

    public void setDestImage(String destImage) {
        DestImage = destImage;
    }

    public Destinations(String destID, String destName, String destType, String destImage) {
        DestID = destID;
        DestName = destName;
        DestType = destType;
        DestImage = destImage;
    }
}
