package com.teachingplan.console.beans.user;

import java.util.List;

/**
 * Created by v_yanfzhang on 2018/1/22.
 */
public class Account extends User {

    private String phone;

    private String officePhone;

    private String sex;

    private String idNumber;

    private String joinRenyingDate;

    private String provinceApproveRenyingDate;

    private String cityApproveRenyingDate;

    private String address;

    private int modDefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getJoinRenyingDate() {
        return joinRenyingDate;
    }

    public void setJoinRenyingDate(String joinRenyingDate) {
        this.joinRenyingDate = joinRenyingDate;
    }

    public String getProvinceApproveRenyingDate() {
        return provinceApproveRenyingDate;
    }

    public void setProvinceApproveRenyingDate(String provinceApproveRenyingDate) {
        this.provinceApproveRenyingDate = provinceApproveRenyingDate;
    }

    public String getCityApproveRenyingDate() {
        return cityApproveRenyingDate;
    }

    public void setCityApproveRenyingDate(String cityApproveRenyingDate) {
        this.cityApproveRenyingDate = cityApproveRenyingDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getModDefault() {
        return modDefault;
    }

    public void setModDefault(int modDefault) {
        this.modDefault = modDefault;
    }
}
