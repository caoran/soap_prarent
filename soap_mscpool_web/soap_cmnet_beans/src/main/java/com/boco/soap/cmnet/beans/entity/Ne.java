package com.boco.soap.cmnet.beans.entity;

import java.io.Serializable;

public class Ne implements Serializable {
    private String id;

    private String name;

    private String physicalCity;

    private String vendor;

    private String coverCity;

    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhysicalCity() {
        return physicalCity;
    }

    public void setPhysicalCity(String physicalCity) {
        this.physicalCity = physicalCity == null ? null : physicalCity.trim();
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor == null ? null : vendor.trim();
    }

    public String getCoverCity() {
        return coverCity;
    }

    public void setCoverCity(String coverCity) {
        this.coverCity = coverCity == null ? null : coverCity.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }
}