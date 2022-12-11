package com.milsondev.milsondev.location;

public class Location {
    private String city;
    private String zipcode;
    private String country;
    private String timeZone;

    private Double latitude;
    private Double longitude;


    public Location(String city, String zipcode, String country, String timeZone,
                    Double latitude, Double longitude) {

        this.city = city;
        this.zipcode = zipcode;
        this.country = country;
        this.timeZone = timeZone;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCountry() {
        return country;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }


}
