package com.yaoyang.bser.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "city_server")
public class CityServer implements Serializable {

    @Id
    private Long cid;
    private String cityName;//开通的城市名称
    private Date createTime;
    private Long cityID;//开通的城市ID
    private Long stid;//服务商ID
    private int sortID;
    private int staus;//O 关闭 1 开通中

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCityID() {
        return cityID;
    }

    public void setCityID(Long cityID) {
        this.cityID = cityID;
    }

    public Long getStid() {
        return stid;
    }

    public void setStid(Long stid) {
        this.stid = stid;
    }

    public int getSortID() {
        return sortID;
    }

    public void setSortID(int sortID) {
        this.sortID = sortID;
    }

    public int getStaus() {
        return staus;
    }

    public void setStaus(int staus) {
        this.staus = staus;
    }
}
