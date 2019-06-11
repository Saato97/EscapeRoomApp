package org.jhipster.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

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

    @ManyToOne
    @JsonIgnoreProperties("wizyties")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("wizyties")
    private EscapeRoom escapeRoom;

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

    public User getUser() {
        return user;
    }

    public Wizyty user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EscapeRoom getEscapeRoom() {
        return escapeRoom;
    }

    public Wizyty escapeRoom(EscapeRoom escapeRoom) {
        this.escapeRoom = escapeRoom;
        return this;
    }

    public void setEscapeRoom(EscapeRoom escapeRoom) {
        this.escapeRoom = escapeRoom;
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
