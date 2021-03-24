package com.Venti.MyTime.entity;

//事务实体类
public class Affair {

    private String ID;
    private String startTime;
    private String endTime;
    private String place;
    private String thing;
    private int state;

    public Affair() { }

    public Affair(String startTime, String endTime, String place, String thing) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.thing = thing;
    }

    public Affair(String ID, String startTime, String endTime, String place, String thing) {
        this.ID = ID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.thing = thing;
    }

    public Affair(String ID, String startTime, String endTime, String place, String thing, int state) {
        this.ID = ID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.thing = thing;
        this.state = state;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "theTime='" + ID + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", place='" + place + '\'' +
                ", thing='" + thing + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
