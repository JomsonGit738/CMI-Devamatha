package com.brocodes.cmidevamatha.ui.search;

public class FOject {
    private String houseimage;
    private String housedetails;
    private String houseaddress;
    private String housemap;
    private String housenumber;

    public FOject() {
    }

    public FOject(String houseimage, String housedetails, String houseaddress, String housemap, String housenumber) {
        this.houseimage = houseimage;
        this.housedetails = housedetails;
        this.houseaddress = houseaddress;
        this.housemap = housemap;
        this.housenumber = housenumber;
    }

    public String getHouseimage() {
        return houseimage;
    }

    public void setHouseimage(String houseimage) {
        this.houseimage = houseimage;
    }

    public String getHousedetails() {
        return housedetails;
    }

    public void setHousedetails(String housedetails) {
        this.housedetails = housedetails;
    }

    public String getHouseaddress() {
        return houseaddress;
    }

    public void setHouseaddress(String houseaddress) {
        this.houseaddress = houseaddress;
    }

    public String getHousemap() {
        return housemap;
    }

    public void setHousemap(String housemap) {
        this.housemap = housemap;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }
}
