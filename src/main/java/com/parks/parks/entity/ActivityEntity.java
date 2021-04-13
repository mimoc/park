package com.parks.parks.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="Activity")
public class ActivityEntity {

    @Id
    @GeneratedValue
    @Column(name="activity_id" ,columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name="park_id")
    private ParkEntity park;

    public UUID getId() {
        return id;
    }

    public ActivityEntity() {
    }

    public ActivityEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParkEntity getPark() {
        return park;
    }

    public void setPark(ParkEntity park) {
        this.park = park;
    }
}
