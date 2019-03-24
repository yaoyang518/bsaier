package com.yaoyang.bser.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_severytype")
public class ServerType implements Serializable {

    @Id
    private Long stid;
    private String severyname;
    private int sortID;

    public Long getStid() {
        return stid;
    }

    public void setStid(Long stid) {
        this.stid = stid;
    }

    public String getSeveryname() {
        return severyname;
    }

    public void setSeveryname(String severyname) {
        this.severyname = severyname;
    }

    public int getSortID() {
        return sortID;
    }

    public void setSortID(int sortID) {
        this.sortID = sortID;
    }
}
