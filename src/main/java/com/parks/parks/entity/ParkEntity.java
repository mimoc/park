package com.parks.parks.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="Park")
public class ParkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="park_id", columnDefinition = "BINARY(16)",nullable=false,unique = true)
    private UUID id;
    String url;
    String fullName;
    String parkCode;
    @Column(name="activity")
    @OneToMany(mappedBy="park",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<ActivityEntity> activities;

    public ParkEntity() {
    }

    public ParkEntity(UUID id, String url, String fullName, String parkCode, Set<ActivityEntity> activities) {
        this.id = id;
        this.url = url;
        this.fullName = fullName;
        this.parkCode = parkCode;
        this.activities = activities;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public Set<ActivityEntity> getActivities() {
        if (activities == null) activities = new HashSet<>();
        return activities;
    }

    public void setActivities(Set<ActivityEntity> activities) {
        this.activities = activities;
    }
}
