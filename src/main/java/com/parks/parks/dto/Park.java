package com.parks.parks.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Park {

    private String id;
    String url;
    @NotEmpty(message = "Park name cannot be empty")
    String fullName;
    @NotEmpty(message = "Park code cannot be empty")
    @Size(min=4, max=10,message="Park code must have between 4 and 10 characters")
    String parkCode;
    Set<Activity> activities;

    public Park() {
    }

    public Park(String id, String url, String fullName, String parkCode, Set<Activity> activities) {
        this.id = id;
        this.url = url;
        this.fullName = fullName;
        this.parkCode = parkCode;
        this.activities = activities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public Set<Activity> getActivities() {
        if (activities == null) activities = new HashSet<>();
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Park)) return false;
        Park park = (Park) o;
        return Objects.equals(getId(), park.getId()) && Objects.equals(getUrl(), park.getUrl()) && Objects.equals(getFullName(), park.getFullName()) && Objects.equals(getParkCode(), park.getParkCode()) && Objects.equals(getActivities(), park.getActivities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUrl(), getFullName(), getParkCode(), getActivities());
    }
}
