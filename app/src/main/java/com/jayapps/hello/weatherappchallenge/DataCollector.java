package com.jayapps.hello.weatherappchallenge;

/**
 * Created by Jay-PC on 11-08-2018.
 */

public class DataCollector {

    private int ctemp;
    private String cday;
    private int ctempp;
    int cid;
    private String cDATe;
    private String ctime;
    private int chumidity;
    private double cpressur;
    private double cwind;
    private int ctemper;


    public DataCollector(String day, int tempmax, int tempmin, String DATe, int idd) {
        cid = idd;
        cday = day;
        ctemp = tempmax;
        ctempp = tempmin;
        cDATe = DATe;
    }


    public DataCollector(String time, int humidity, double pressur, double wind, int temper, int idd) {

        ctime = time;
        chumidity = humidity;
        cpressur = pressur;
        cwind = wind;
        ctemper = temper - 273;
        cid = idd;


    }

    public String gettime() {
        return ctime;
    }

    public String getChumidity() {
        return String.valueOf(chumidity);
    }

    public String getCpressur() {
        return String.valueOf(cpressur);
    }

    public String getCtemper() {
        return String.valueOf(ctemper);
    }

    public String getCwind() {

        return String.valueOf(cwind);
    }

    public String getday() {


        return cday;
    }

    public String getDATe() {


        return cDATe;
    }

    public int getimageid() {
        if (cid >= 200 && cid < 300) {
            return (R.drawable.thunder);
        } else if (cid >= 300 && cid < 600) {
            return R.drawable.rainn;
        } else if (cid >= 600 && cid < 700) {
            return R.drawable.snowflake;
        } else if (cid >= 700 && cid < 800) {
            return R.drawable.storm;
        } else if (cid == 800) {
            return R.drawable.sunww;
        } else {
            return R.drawable.clouds;
        }
    }

    public String gettempmax() {
        return String.format("%d°C", ctemp);
    }

    public String gettemppmin() {
        return String.format("%d°C", ctempp);
    }
}
