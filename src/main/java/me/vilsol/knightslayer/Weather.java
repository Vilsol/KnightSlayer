package me.vilsol.knightslayer;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Weather {

    NORMAL("NMR"), STORM("SRO"), HEAVY_RAIN("HVA"), LONG_DRY("T E"), FOG("FUNDEFINEDG");

    private String abbreviation;

    Weather(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    private static Pattern pattern = Pattern.compile("<code>(.*)</code>");

    public static Weather getWeather(int id){
        try {
            String data = Unirest.get("http://www.dragonsofmugloar.com/weather/api/report/" + id).asString().getBody();

            Matcher matcher = pattern.matcher(data);
            matcher.find();
            String code = matcher.group(1);

            for (Weather weather : values()) {
                if(weather.abbreviation.equals(code)){
                    return weather;
                }
            }

            System.out.println(code + " : " + data);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

}
