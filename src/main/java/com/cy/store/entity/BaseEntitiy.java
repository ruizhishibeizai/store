package com.cy.store.entity;

import java.util.Date;
import java.util.Objects;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-15 15:19
 **/
//作为实体类的基类
public class BaseEntitiy {
    private String createdUser ;
    private Date createdTime ;
    private String modifiedUser;
    private Date modifiedTime;

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntitiy that = (BaseEntitiy) o;
        return Objects.equals(createdUser, that.createdUser) &&
                Objects.equals(createdTime, that.createdTime) &&
                Objects.equals(modifiedUser, that.modifiedUser) &&
                Objects.equals(modifiedTime, that.modifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdUser, createdTime, modifiedUser, modifiedTime);
    }

    @Override
    public String toString() {
        return "BaseEntitiy{" +
                "createdUser='" + createdUser + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedUser='" + modifiedUser + '\'' +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
