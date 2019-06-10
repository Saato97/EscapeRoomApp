package org.jhipster.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Wlasciciel.
 */
@Entity
@Table(name = "wlasciciel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wlasciciel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("wlasciciels")
    private Osoba osoba;

    @ManyToMany(mappedBy = "wlasciciels")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<EscapeRoom> escaperooms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public Wlasciciel osoba(Osoba osoba) {
        this.osoba = osoba;
        return this;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public Set<EscapeRoom> getEscaperooms() {
        return escaperooms;
    }

    public Wlasciciel escaperooms(Set<EscapeRoom> escapeRooms) {
        this.escaperooms = escapeRooms;
        return this;
    }

    public Wlasciciel addEscaperoom(EscapeRoom escapeRoom) {
        this.escaperooms.add(escapeRoom);
        escapeRoom.getWlasciciels().add(this);
        return this;
    }

    public Wlasciciel removeEscaperoom(EscapeRoom escapeRoom) {
        this.escaperooms.remove(escapeRoom);
        escapeRoom.getWlasciciels().remove(this);
        return this;
    }

    public void setEscaperooms(Set<EscapeRoom> escapeRooms) {
        this.escaperooms = escapeRooms;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wlasciciel)) {
            return false;
        }
        return id != null && id.equals(((Wlasciciel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Wlasciciel{" +
            "id=" + getId() +
            "}";
    }
}
