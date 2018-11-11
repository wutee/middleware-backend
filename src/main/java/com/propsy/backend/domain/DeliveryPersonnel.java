package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DeliveryPersonnel.
 */
@Entity
@Table(name = "delivery_personnel")
public class DeliveryPersonnel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "delivery")
    private Set<FoodOrder> orders = new HashSet<>();
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

    public DeliveryPersonnel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public DeliveryPersonnel surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<FoodOrder> getOrders() {
        return orders;
    }

    public DeliveryPersonnel orders(Set<FoodOrder> foodOrders) {
        this.orders = foodOrders;
        return this;
    }

    public DeliveryPersonnel addOrder(FoodOrder foodOrder) {
        this.orders.add(foodOrder);
        foodOrder.setDelivery(this);
        return this;
    }

    public DeliveryPersonnel removeOrder(FoodOrder foodOrder) {
        this.orders.remove(foodOrder);
        foodOrder.setDelivery(null);
        return this;
    }

    public void setOrders(Set<FoodOrder> foodOrders) {
        this.orders = foodOrders;
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
        DeliveryPersonnel deliveryPersonnel = (DeliveryPersonnel) o;
        if (deliveryPersonnel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliveryPersonnel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliveryPersonnel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            "}";
    }
}
