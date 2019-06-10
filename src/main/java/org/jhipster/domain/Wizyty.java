package org.jhipster.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Wizyty.
 */
@Entity
@Table(name = "wizyty")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wizyty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_wizyty")
    private LocalDate dataWizyty;

    @OneToOne
    @JoinColumn(unique = true)
    private Opinie opinie;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("wizyties")
    private Klient klient;

    @ManyToOne
    @JsonIgnoreProperties("wizyties")
    private EscapeRoom escaperoom;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataWizyty() {
        return dataWizyty;
    }

    public Wizyty dataWizyty(LocalDate dataWizyty) {
        this.dataWizyty = dataWizyty;
        return this;
    }

    public void setDataWizyty(LocalDate dataWizyty) {
        this.dataWizyty = dataWizyty;
    }

    public Opinie getOpinie() {
        return opinie;
    }

    public Wizyty opinie(Opinie opinie) {
        this.opinie = opinie;
        return this;
    }

    public void setOpinie(Opinie opinie) {
        this.opinie = opinie;
    }

    public Klient getKlient() {
        return klient;
    }

    public Wizyty klient(Klient klient) {
        this.klient = klient;
        return this;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public EscapeRoom getEscaperoom() {
        return escaperoom;
    }

    public Wizyty escaperoom(EscapeRoom escapeRoom) {
        this.escaperoom = escapeRoom;
        return this;
    }

    public void setEscaperoom(EscapeRoom escapeRoom) {
        this.escaperoom = escapeRoom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wizyty)) {
            return false;
        }
        return id != null && id.equals(((Wizyty) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Wizyty{" +
            "id=" + getId() +
            ", dataWizyty='" + getDataWizyty() + "'" +
            "}";
    }
}
