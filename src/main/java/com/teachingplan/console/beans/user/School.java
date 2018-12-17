package com.teachingplan.console.beans.user;

/**
 * Created by v_yanfzhang on 2018/2/8.
 */
public class School extends User {

    private int schoolId;

    private int accountId;

    private int accountName;

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountName() {
        return accountName;
    }

    public void setAccountName(int accountName) {
        this.accountName = accountName;
    }
}
