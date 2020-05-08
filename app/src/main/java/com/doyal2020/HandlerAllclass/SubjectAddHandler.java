package com.doyal2020.HandlerAllclass;

import com.doyal2020.presentapps.SubjectAddActivity;

public class SubjectAddHandler {

    String department;
    String subject;
    String semester;


    public SubjectAddHandler(){

    }

    public SubjectAddHandler(String department, String subject, String semester) {
        this.department = department;
        this.subject = subject;
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
