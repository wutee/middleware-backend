package com.propsy.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RestaurantWorker.
 */
@Entity
@Table(name = "restaurant_worker")
public class RestaurantWorker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "working_status")
    private Integer workingStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RestaurantWorker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public RestaurantWorker surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getWorkingStatus() {
        return workingStatus;
    }

    public RestaurantWorker workingStatus(Integer workingStatus) {
        this.workingStatus = workingStatus;
        return this;
    }

    public void setWorkingStatus(Integer workingStatus) {
        this.workingStatus = workingStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantWorker restaurantWorker = (RestaurantWorker) o;
        if (restaurantWorker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurantWorker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestaurantWorker{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", workingStatus=" + getWorkingStatus() +
            "}";
    }
}
