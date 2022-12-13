package com.milsondev.milsondev.location;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.milsondev.milsondev.db.entities.Visitor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Paths;

@Service
public class LocationService {
    private DatabaseReader dbReader;

    public Visitor getIPLocation() throws Exception {

        String ip = getIp();

        URL res = getClass().getClassLoader().getResource("GeoLite2-City.mmdb");
        File file = Paths.get(res.toURI()).toFile();

        File database = new File(file.getAbsolutePath());
        dbReader = new DatabaseReader.Builder(database).build();

        CityResponse response = dbReader.city(InetAddress.getByName(ip));

        Visitor visitor = new Visitor();

        visitor.setCity(formatCountryAndCity(response.getCity().toString()));
        visitor.setZipCode(response.getPostal().getCode());
        visitor.setCountry(formatCountryAndCity(response.getCountry().toString()));
        visitor.setLatitude(response.getLocation().getLatitude().toString());
        visitor.setLongitude(response.getLocation().getLongitude().toString());
        visitor.setIp(ip);
        return visitor;

    }



    private String formatCountryAndCity (String str) {
        String [] strArray = str.split("pt-BR");
        String [] strArray2 = strArray[1].split(":");
        return strArray2[3].substring(1, strArray2[3].length()-6);
    }





    private String getIp() throws IOException {
        URL whatIsMyIp = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatIsMyIp.openStream()));
        return in.readLine();
    }


}