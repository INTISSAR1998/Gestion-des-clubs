package com.example.alaanadanesrine.projetNoSQL.Events;

public class Event {

    private String eventName,beginDate,endDate,eventPlace,eventDescription;

    public static final String TABLE_NAME = "event";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BEGINDATE = "begin_date";
    public static final String COLUMN_ENDDATE = "end_date";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"+ COLUMN_BEGINDATE + " TEXT,"+ COLUMN_ENDDATE + " TEXT,"
                    + COLUMN_PLACE + " TEXT,"+ COLUMN_DESCRIPTION + " TEXT" + ")";

    public Event() {
    }

    public Event(String eventName, String beginDate, String endDate, String eventPlace, String eventDescription) {
        this.eventName = eventName;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.eventPlace = eventPlace;
        this.eventDescription = eventDescription;
    }

    public Event(String eventName, String beginDate, String eventPlace) {
        this.eventName = eventName;
        this.beginDate = beginDate;
        this.eventPlace = eventPlace;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
