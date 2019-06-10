package org.jhipster.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Osoba.
 */
@Entity
@Table(name = "osoba")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Osoba implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @NotNull
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @NotNull
    @Column(name = "haslo", nullable = false)
    private String haslo;

    @OneToMany(mappedBy = "osoba")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Klient> klients = new HashSet<>();

    @OneToMany(mappedBy = "osoba")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wlasciciel> wlasciciels = new HashSet<>();

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

    public Osoba imie(String imie) {
        this.imie = imie;
        return this;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public Osoba nazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
        return this;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getLogin() {
        return login;
    }

    public Osoba login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public Osoba haslo(String haslo) {
        this.haslo = haslo;
        return this;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public Set<Klient> getKlients() {
        return klients;
    }

    public Osoba klients(Set<Klient> klients) {
        this.klients = klients;
        return this;
    }

    public Osoba addKlient(Klient klient) {
        this.klients.add(klient);
        klient.setOsoba(this);
        return this;
    }

    public Osoba removeKlient(Klient klient) {
        this.klients.remove(klient);
        klient.setOsoba(null);
        return this;
    }

    public void setKlients(Set<Klient> klients) {
        this.klients = klients;
    }

    public Set<Wlasciciel> getWlasciciels() {
        return wlasciciels;
    }

    public Osoba wlasciciels(Set<Wlasciciel> wlasciciels) {
        this.wlasciciels = wlasciciels;
        return this;
    }

    public Osoba addWlasciciel(Wlasciciel wlasciciel) {
        this.wlasciciels.add(wlasciciel);
        wlasciciel.setOsoba(this);
        return this;
    }

    public Osoba removeWlasciciel(Wlasciciel wlasciciel) {
        this.wlasciciels.remove(wlasciciel);
        wlasciciel.setOsoba(null);
        return this;
    }

    public void setWlasciciels(Set<Wlasciciel> wlasciciels) {
        this.wlasciciels = wlasciciels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Osoba)) {
            return false;
        }
        return id != null && id.equals(((Osoba) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Osoba{" +
            "id=" + getId() +
            ", imie='" + getImie() + "'" +
            ", nazwisko='" + getNazwisko() + "'" +
            ", login='" + getLogin() + "'" +
            ", haslo='" + getHaslo() + "'" +
            "}";
    }
}
