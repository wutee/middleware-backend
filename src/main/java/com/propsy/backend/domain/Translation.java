package com.propsy.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Translation.
 */
@Entity
@Table(name = "translation")
public class Translation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "translation")
    private String translation;

    @OneToOne    @JoinColumn(unique = true)
    private Food food;

    @ManyToOne
    @JsonIgnoreProperties("translations")
    private Language language;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTranslation() {
        return translation;
    }

    public Translation translation(String translation) {
        this.translation = translation;
        return this;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Food getFood() {
        return food;
    }

    public Translation food(Food food) {
        this.food = food;
        return this;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Language getLanguage() {
        return language;
    }

    public Translation language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
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
        Translation translation = (Translation) o;
        if (translation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), translation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Translation{" +
            "id=" + getId() +
            ", translation='" + getTranslation() + "'" +
            "}";
    }
}
