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
 * A Klient.
 */
@Entity
@Table(name = "klient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Klient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("klients")
    private Osoba osoba;

    @OneToMany(mappedBy = "klient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wizyty> wizyties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefon() {
        return telefon;
    }

    public Klient telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public Klient email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public Klient osoba(Osoba osoba) {
        this.osoba = osoba;
        return this;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public Set<Wizyty> getWizyties() {
        return wizyties;
    }

    public Klient wizyties(Set<Wizyty> wizyties) {
        this.wizyties = wizyties;
        return this;
    }

    public Klient addWizyty(Wizyty wizyty) {
        this.wizyties.add(wizyty);
        wizyty.setKlient(this);
        return this;
    }

    public Klient removeWizyty(Wizyty wizyty) {
        this.wizyties.remove(wizyty);
        wizyty.setKlient(null);
        return this;
    }

    public void setWizyties(Set<Wizyty> wizyties) {
        this.wizyties = wizyties;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Klient)) {
            return false;
        }
        return id != null && id.equals(((Klient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Klient{" +
            "id=" + getId() +
            ", telefon='" + getTelefon() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
