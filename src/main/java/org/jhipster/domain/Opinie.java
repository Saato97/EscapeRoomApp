package org.jhipster.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Opinie.
 */
@Entity
@Table(name = "opinie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Opinie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Column(name = "opinia", nullable = false)
    private String opinia;

    @OneToOne(mappedBy = "opinie")
    @JsonIgnore
    private Wizyty wizyty;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpinia() {
        return opinia;
    }

    public Opinie opinia(String opinia) {
        this.opinia = opinia;
        return this;
    }

    public void setOpinia(String opinia) {
        this.opinia = opinia;
    }

    public Wizyty getWizyty() {
        return wizyty;
    }

    public Opinie wizyty(Wizyty wizyty) {
        this.wizyty = wizyty;
        return this;
    }

    public void setWizyty(Wizyty wizyty) {
        this.wizyty = wizyty;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opinie)) {
            return false;
        }
        return id != null && id.equals(((Opinie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Opinie{" +
            "id=" + getId() +
            ", opinia='" + getOpinia() + "'" +
            "}";
    }
}
