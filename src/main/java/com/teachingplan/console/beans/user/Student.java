package com.teachingplan.console.beans.user;

/**
 * Created by LQW on 2018/1/27.
 */
public class Student extends User {

    private String parentPhone;

    private String birthday;

    private int schoolId;

    private String schoolName;

    private String classesId;

    private String classes;

    private int sign;

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getClassesId() {
        return classesId;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int signIn) {
        this.sign = signIn;
    }

    public void setClassesId(String classesId) {
        this.classesId = classesId;

    }
}

