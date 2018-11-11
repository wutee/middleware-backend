package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "jhi_role")
    private Integer role;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @OneToMany(mappedBy = "orderee")
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

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public Customer mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getRole() {
        return role;
    }

    public Customer role(Integer role) {
        this.role = role;
        return this;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Customer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public Customer loyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
        return this;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Set<FoodOrder> getOrders() {
        return orders;
    }

    public Customer orders(Set<FoodOrder> foodOrders) {
        this.orders = foodOrders;
        return this;
    }

    public Customer addOrder(FoodOrder foodOrder) {
        this.orders.add(foodOrder);
        foodOrder.setOrderee(this);
        return this;
    }

    public Customer removeOrder(FoodOrder foodOrder) {
        this.orders.remove(foodOrder);
        foodOrder.setOrderee(null);
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
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mail='" + getMail() + "'" +
            ", role=" + getRole() +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", phone='" + getPhone() + "'" +
            ", loyaltyPoints=" + getLoyaltyPoints() +
            "}";
    }
}
