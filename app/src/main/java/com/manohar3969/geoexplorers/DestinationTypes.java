package com.manohar3969.geoexplorers;

public class DestinationTypes {
    String DestTypeID;
    String DestType;
    String DestTypeImage;

    public DestinationTypes(){

    }

    public DestinationTypes(String destTypeID, String destType, String destTypeImage) {
        DestTypeID = destTypeID;
        DestType = destType;
        DestTypeImage = destTypeImage;
    }

    public String getDestTypeID() {
        return DestTypeID;
    }

    public void setDestTypeID(String destTypeID) {
        DestTypeID = destTypeID;
    }

    public String getDestType() {
        return DestType;
    }

    public void setDestType(String destType) {
        DestType = destType;
    }

    public String getDestTypeImage() {
        return DestTypeImage;
    }

    public void setDestTypeImage(String destTypeImage) {
        DestTypeImage = destTypeImage;
    }
}
