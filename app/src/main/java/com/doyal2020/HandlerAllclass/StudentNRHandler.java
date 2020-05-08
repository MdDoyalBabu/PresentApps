package com.doyal2020.HandlerAllclass;

public class StudentNRHandler {

  String student;
    String roll;
    String shift;
    String group;

    public  StudentNRHandler(){

    }

    public StudentNRHandler(String student, String roll, String shift, String group) {
        this.student = student;
        this.roll = roll;
        this.shift = shift;
        this.group = group;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
