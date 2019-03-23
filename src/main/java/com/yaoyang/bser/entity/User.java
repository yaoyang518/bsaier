package com.yaoyang.bser.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_user")
public class User implements Serializable {

    @Id
    private Long userid;

    private Date createTime = new Date();

    private Date updateTime = new Date();
    private String mobile;
    private String username;
    private String realname;
    private String password;
    private String mail;
    private Long cid;
    private Long dotid;
    private String areaadree;
    private int level;
    private int userstaus;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getDotid() {
        return dotid;
    }

    public void setDotid(Long dotid) {
        this.dotid = dotid;
    }

    public String getAreaadree() {
        return areaadree;
    }

    public void setAreaadree(String areaadree) {
        this.areaadree = areaadree;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUserstaus() {
        return userstaus;
    }

    public void setUserstaus(int userstaus) {
        this.userstaus = userstaus;
    }
}
