package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_slug", nullable = false)
    private String nameSlug;

    @Column(name = "address")
    private String address;

    @Column(name = "owner_id")
    private String ownerId;

    @OneToOne(mappedBy = "restaurant")
    @JsonIgnore
    private Menu menu;

    @ManyToMany(mappedBy = "employers")
    @JsonIgnore
    private Set<RestaurantWorker> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSlug() {
        return nameSlug;
    }

    public Restaurant nameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
        return this;
    }

    public void setNameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
    }

    public String getAddress() {
        return address;
    }

    public Restaurant address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Restaurant ownerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Menu getMenu() {
        return menu;
    }

    public Restaurant menu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Set<RestaurantWorker> getEmployees() {
        return employees;
    }

    public Restaurant employees(Set<RestaurantWorker> restaurantWorkers) {
        this.employees = restaurantWorkers;
        return this;
    }

    public Restaurant addEmployee(RestaurantWorker restaurantWorker) {
        this.employees.add(restaurantWorker);
        restaurantWorker.getEmployers().add(this);
        return this;
    }

    public Restaurant removeEmployee(RestaurantWorker restaurantWorker) {
        this.employees.remove(restaurantWorker);
        restaurantWorker.getEmployers().remove(this);
        return this;
    }

    public void setEmployees(Set<RestaurantWorker> restaurantWorkers) {
        this.employees = restaurantWorkers;
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
        Restaurant restaurant = (Restaurant) o;
        if (restaurant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", nameSlug='" + getNameSlug() + "'" +
            ", address='" + getAddress() + "'" +
            ", ownerId='" + getOwnerId() + "'" +
            "}";
    }
}
