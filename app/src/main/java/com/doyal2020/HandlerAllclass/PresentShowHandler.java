package com.doyal2020.HandlerAllclass;

public class PresentShowHandler {


    String time;
    String present;
    String roll;


    public PresentShowHandler(){



    }

    public PresentShowHandler(String time, String present, String roll) {
        this.time = time;
        this.present = present;
        this.roll = roll;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
