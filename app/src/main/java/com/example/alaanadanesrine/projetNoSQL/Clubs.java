package com.example.alaanadanesrine.projetNoSQL;

public class Clubs {
    private int clubid;
    private  String clubname;
    private String creationdate;
    private String description;

    public  Clubs (){

    }

    public Clubs(int clubid, String clubname, String creationdate, String description) {
        this.clubid = clubid;
        this.clubname = clubname;
        this.creationdate = creationdate;
        this.description=description;
    }

    public int getClubid() {
        return clubid;
    }

    public void setClubid(int clubid) {
        this.clubid = clubid;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
