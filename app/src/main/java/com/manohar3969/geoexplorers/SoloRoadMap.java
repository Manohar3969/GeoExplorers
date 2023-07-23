package com.manohar3969.geoexplorers;

public class SoloRoadMap {

    private String EndDate;
    private String EndDest;
    private String RoadMapID;
    private String StartDate;
    private String StartDest;
    private String UserID;

    public SoloRoadMap(){

    }

    public SoloRoadMap(String endDate, String endDest, String roadMapID, String startDate, String startDest, String userID) {
        EndDate = endDate;
        EndDest = endDest;
        RoadMapID = roadMapID;
        StartDate = startDate;
        StartDest = startDest;
        UserID = userID;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndDest() {
        return EndDest;
    }

    public void setEndDest(String endDest) {
        EndDest = endDest;
    }

    public String getRoadMapID() {
        return RoadMapID;
    }

    public void setRoadMapID(String roadMapID) {
        RoadMapID = roadMapID;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStartDest() {
        return StartDest;
    }

    public void setStartDest(String startDest) {
        StartDest = startDest;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
