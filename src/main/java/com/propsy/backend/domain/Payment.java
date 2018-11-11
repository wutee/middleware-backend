package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "payment")
    private Set<PaymentMethod> methods = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public Payment status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<PaymentMethod> getMethods() {
        return methods;
    }

    public Payment methods(Set<PaymentMethod> paymentMethods) {
        this.methods = paymentMethods;
        return this;
    }

    public Payment addMethod(PaymentMethod paymentMethod) {
        this.methods.add(paymentMethod);
        paymentMethod.setPayment(this);
        return this;
    }

    public Payment removeMethod(PaymentMethod paymentMethod) {
        this.methods.remove(paymentMethod);
        paymentMethod.setPayment(null);
        return this;
    }

    public void setMethods(Set<PaymentMethod> paymentMethods) {
        this.methods = paymentMethods;
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
        Payment payment = (Payment) o;
        if (payment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", status=" + getStatus() +
            "}";
    }
}
