package org.jhipster.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

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

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @ManyToMany(mappedBy = "wlasciciels")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<EscapeRoom> escapeRooms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public Wlasciciel imie(String imie) {
        this.imie = imie;
        return this;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public Wlasciciel nazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
        return this;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Set<EscapeRoom> getEscapeRooms() {
        return escapeRooms;
    }

    public Wlasciciel escapeRooms(Set<EscapeRoom> escapeRooms) {
        this.escapeRooms = escapeRooms;
        return this;
    }

    public Wlasciciel addEscapeRoom(EscapeRoom escapeRoom) {
        this.escapeRooms.add(escapeRoom);
        escapeRoom.getWlasciciels().add(this);
        return this;
    }

    public Wlasciciel removeEscapeRoom(EscapeRoom escapeRoom) {
        this.escapeRooms.remove(escapeRoom);
        escapeRoom.getWlasciciels().remove(this);
        return this;
    }

    public void setEscapeRooms(Set<EscapeRoom> escapeRooms) {
        this.escapeRooms = escapeRooms;
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
            ", imie='" + getImie() + "'" +
            ", nazwisko='" + getNazwisko() + "'" +
            "}";
    }
}
