package com.propsy.backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @NotNull
    @Column(name = "worker_name", nullable = false)
    private String workerName;

    @NotNull
    @Column(name = "worker_surname", nullable = false)
    private String workerSurname;

    @Column(name = "working_status")
    private Integer workingStatus;

    @ManyToMany
    @JoinTable(name = "restaurant_worker_employer",
               joinColumns = @JoinColumn(name = "restaurant_workers_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "employers_id", referencedColumnName = "id"))
    private Set<Restaurant> employers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkerName() {
        return workerName;
    }

    public RestaurantWorker workerName(String workerName) {
        this.workerName = workerName;
        return this;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerSurname() {
        return workerSurname;
    }

    public RestaurantWorker workerSurname(String workerSurname) {
        this.workerSurname = workerSurname;
        return this;
    }

    public void setWorkerSurname(String workerSurname) {
        this.workerSurname = workerSurname;
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

    public Set<Restaurant> getEmployers() {
        return employers;
    }

    public RestaurantWorker employers(Set<Restaurant> restaurants) {
        this.employers = restaurants;
        return this;
    }

    public RestaurantWorker addEmployer(Restaurant restaurant) {
        this.employers.add(restaurant);
        restaurant.getEmployees().add(this);
        return this;
    }

    public RestaurantWorker removeEmployer(Restaurant restaurant) {
        this.employers.remove(restaurant);
        restaurant.getEmployees().remove(this);
        return this;
    }

    public void setEmployers(Set<Restaurant> restaurants) {
        this.employers = restaurants;
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
            ", workerName='" + getWorkerName() + "'" +
            ", workerSurname='" + getWorkerSurname() + "'" +
            ", workingStatus=" + getWorkingStatus() +
            "}";
    }
}
