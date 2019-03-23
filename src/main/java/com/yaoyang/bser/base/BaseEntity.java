package com.yaoyang.bser.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 *
 * @author jimlaren
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    private Long id;

    private Date createDate = new Date();

    private Date updateDate = new Date();

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(final Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj instanceof BaseEntity) {
            final BaseEntity other = (BaseEntity) obj;
            if (other.getId() == null) {
                return false;
            } else {
                return other.getId().equals(this.getId());
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return super.hashCode();
        } else {
            return getId().hashCode();
        }
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }

}
