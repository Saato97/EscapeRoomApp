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

import org.jhipster.domain.enumeration.Poziom;

/**
 * A EscapeRoom.
 */
@Entity
@Table(name = "escape_room")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EscapeRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "zdjecie")
    private byte[] zdjecie;

    @Column(name = "zdjecie_content_type")
    private String zdjecieContentType;

    @Column(name = "ulica")
    private String ulica;

    @Column(name = "miasto")
    private String miasto;

    @Column(name = "kod_pocztowy")
    private String kodPocztowy;

    @Column(name = "email")
    private String email;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "strona_www")
    private String stronaWWW;

    @Column(name = "nazwa")
    private String nazwa;

    
    @Lob
    @Column(name = "opis", nullable = false)
    private String opis;

    @Column(name = "ilosc_osob")
    private String iloscOsob;

    @Column(name = "cena")
    private Integer cena;

    @Column(name = "pkt_do_zdobycia")
    private Integer pktDoZdobycia;

    @Column(name = "wymagana_ilosc_pkt")
    private Integer wymaganaIloscPkt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "poziom_trudnosci", nullable = false)
    private Poziom poziomTrudnosci;

    @Column(name = "czas_na_przejscie")
    private String czasNaPrzejscie;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "escape_room_wlasciciel",
               joinColumns = @JoinColumn(name = "escape_room_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "wlasciciel_id", referencedColumnName = "id"))
    private Set<Wlasciciel> wlasciciels = new HashSet<>();

    @OneToMany(mappedBy = "escapeRoom")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wizyty> wizyties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getZdjecie() {
        return zdjecie;
    }

    public EscapeRoom zdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
        return this;
    }

    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }

    public String getZdjecieContentType() {
        return zdjecieContentType;
    }

    public EscapeRoom zdjecieContentType(String zdjecieContentType) {
        this.zdjecieContentType = zdjecieContentType;
        return this;
    }

    public void setZdjecieContentType(String zdjecieContentType) {
        this.zdjecieContentType = zdjecieContentType;
    }

    public String getUlica() {
        return ulica;
    }

    public EscapeRoom ulica(String ulica) {
        this.ulica = ulica;
        return this;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getMiasto() {
        return miasto;
    }

    public EscapeRoom miasto(String miasto) {
        this.miasto = miasto;
        return this;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public EscapeRoom kodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
        return this;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getEmail() {
        return email;
    }

    public EscapeRoom email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public EscapeRoom telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getStronaWWW() {
        return stronaWWW;
    }

    public EscapeRoom stronaWWW(String stronaWWW) {
        this.stronaWWW = stronaWWW;
        return this;
    }

    public void setStronaWWW(String stronaWWW) {
        this.stronaWWW = stronaWWW;
    }

    public String getNazwa() {
        return nazwa;
    }

    public EscapeRoom nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public EscapeRoom opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getIloscOsob() {
        return iloscOsob;
    }

    public EscapeRoom iloscOsob(String iloscOsob) {
        this.iloscOsob = iloscOsob;
        return this;
    }

    public void setIloscOsob(String iloscOsob) {
        this.iloscOsob = iloscOsob;
    }

    public Integer getCena() {
        return cena;
    }

    public EscapeRoom cena(Integer cena) {
        this.cena = cena;
        return this;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Integer getPktDoZdobycia() {
        return pktDoZdobycia;
    }

    public EscapeRoom pktDoZdobycia(Integer pktDoZdobycia) {
        this.pktDoZdobycia = pktDoZdobycia;
        return this;
    }

    public void setPktDoZdobycia(Integer pktDoZdobycia) {
        this.pktDoZdobycia = pktDoZdobycia;
    }

    public Integer getWymaganaIloscPkt() {
        return wymaganaIloscPkt;
    }

    public EscapeRoom wymaganaIloscPkt(Integer wymaganaIloscPkt) {
        this.wymaganaIloscPkt = wymaganaIloscPkt;
        return this;
    }

    public void setWymaganaIloscPkt(Integer wymaganaIloscPkt) {
        this.wymaganaIloscPkt = wymaganaIloscPkt;
    }

    public Poziom getPoziomTrudnosci() {
        return poziomTrudnosci;
    }

    public EscapeRoom poziomTrudnosci(Poziom poziomTrudnosci) {
        this.poziomTrudnosci = poziomTrudnosci;
        return this;
    }

    public void setPoziomTrudnosci(Poziom poziomTrudnosci) {
        this.poziomTrudnosci = poziomTrudnosci;
    }

    public String getCzasNaPrzejscie() {
        return czasNaPrzejscie;
    }

    public EscapeRoom czasNaPrzejscie(String czasNaPrzejscie) {
        this.czasNaPrzejscie = czasNaPrzejscie;
        return this;
    }

    public void setCzasNaPrzejscie(String czasNaPrzejscie) {
        this.czasNaPrzejscie = czasNaPrzejscie;
    }

    public Set<Wlasciciel> getWlasciciels() {
        return wlasciciels;
    }

    public EscapeRoom wlasciciels(Set<Wlasciciel> wlasciciels) {
        this.wlasciciels = wlasciciels;
        return this;
    }

    public EscapeRoom addWlasciciel(Wlasciciel wlasciciel) {
        this.wlasciciels.add(wlasciciel);
        wlasciciel.getEscapeRooms().add(this);
        return this;
    }

    public EscapeRoom removeWlasciciel(Wlasciciel wlasciciel) {
        this.wlasciciels.remove(wlasciciel);
        wlasciciel.getEscapeRooms().remove(this);
        return this;
    }

    public void setWlasciciels(Set<Wlasciciel> wlasciciels) {
        this.wlasciciels = wlasciciels;
    }

    public Set<Wizyty> getWizyties() {
        return wizyties;
    }

    public EscapeRoom wizyties(Set<Wizyty> wizyties) {
        this.wizyties = wizyties;
        return this;
    }

    public EscapeRoom addWizyty(Wizyty wizyty) {
        this.wizyties.add(wizyty);
        wizyty.setEscapeRoom(this);
        return this;
    }

    public EscapeRoom removeWizyty(Wizyty wizyty) {
        this.wizyties.remove(wizyty);
        wizyty.setEscapeRoom(null);
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
        if (!(o instanceof EscapeRoom)) {
            return false;
        }
        return id != null && id.equals(((EscapeRoom) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EscapeRoom{" +
            "id=" + getId() +
            ", zdjecie='" + getZdjecie() + "'" +
            ", zdjecieContentType='" + getZdjecieContentType() + "'" +
            ", ulica='" + getUlica() + "'" +
            ", miasto='" + getMiasto() + "'" +
            ", kodPocztowy='" + getKodPocztowy() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", stronaWWW='" + getStronaWWW() + "'" +
            ", nazwa='" + getNazwa() + "'" +
            ", opis='" + getOpis() + "'" +
            ", iloscOsob='" + getIloscOsob() + "'" +
            ", cena=" + getCena() +
            ", pktDoZdobycia=" + getPktDoZdobycia() +
            ", wymaganaIloscPkt=" + getWymaganaIloscPkt() +
            ", poziomTrudnosci='" + getPoziomTrudnosci() + "'" +
            ", czasNaPrzejscie='" + getCzasNaPrzejscie() + "'" +
            "}";
    }
}
