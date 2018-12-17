package com.teachingplan.console.beans.user;

import java.util.Date;

/**
 * Created by v_yanfzhang on 2018/2/12.
 */
public class Code {

    private int id;

    private String account;

    private String code;

    private Date expireTime;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
