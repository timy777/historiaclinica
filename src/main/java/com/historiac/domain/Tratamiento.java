package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Tratamiento.
 */
@Entity
@Table(name = "tratamiento")
public class Tratamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tipo_tratamiento", nullable = false)
    private String tipoTratamiento;

    @Column(name = "duracion")
    private String duracion;

    @Column(name = "objetivo")
    private String objetivo;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "evaluacionFisicas", "medicacions", "tratamientos", "estudioMedicos", "personalMedico", "paciente" },
        allowSetters = true
    )
    private ConsultaMedica consultaMedica;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tratamiento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoTratamiento() {
        return this.tipoTratamiento;
    }

    public Tratamiento tipoTratamiento(String tipoTratamiento) {
        this.setTipoTratamiento(tipoTratamiento);
        return this;
    }

    public void setTipoTratamiento(String tipoTratamiento) {
        this.tipoTratamiento = tipoTratamiento;
    }

    public String getDuracion() {
        return this.duracion;
    }

    public Tratamiento duracion(String duracion) {
        this.setDuracion(duracion);
        return this;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getObjetivo() {
        return this.objetivo;
    }

    public Tratamiento objetivo(String objetivo) {
        this.setObjetivo(objetivo);
        return this;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public ConsultaMedica getConsultaMedica() {
        return this.consultaMedica;
    }

    public void setConsultaMedica(ConsultaMedica consultaMedica) {
        this.consultaMedica = consultaMedica;
    }

    public Tratamiento consultaMedica(ConsultaMedica consultaMedica) {
        this.setConsultaMedica(consultaMedica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tratamiento)) {
            return false;
        }
        return id != null && id.equals(((Tratamiento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tratamiento{" +
            "id=" + getId() +
            ", tipoTratamiento='" + getTipoTratamiento() + "'" +
            ", duracion='" + getDuracion() + "'" +
            ", objetivo='" + getObjetivo() + "'" +
            "}";
    }
}
