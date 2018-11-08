package com.propsy.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Translations.
 */
@Entity
@Table(name = "translations")
public class Translations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "translation")
    private String translation;

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

    public Translations translation(String translation) {
        this.translation = translation;
        return this;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
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
        Translations translations = (Translations) o;
        if (translations.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), translations.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Translations{" +
            "id=" + getId() +
            ", translation='" + getTranslation() + "'" +
            "}";
    }
}
