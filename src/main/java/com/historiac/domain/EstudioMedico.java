package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EstudioMedico.
 */
@Entity
@Table(name = "estudio_medico")
public class EstudioMedico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tipo_estudio", nullable = false)
    private String tipoEstudio;

    @Column(name = "resultado")
    private String resultado;

    @NotNull
    @Column(name = "fecha_realizacion", nullable = false)
    private Instant fechaRealizacion;

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

    public EstudioMedico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEstudio() {
        return this.tipoEstudio;
    }

    public EstudioMedico tipoEstudio(String tipoEstudio) {
        this.setTipoEstudio(tipoEstudio);
        return this;
    }

    public void setTipoEstudio(String tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }

    public String getResultado() {
        return this.resultado;
    }

    public EstudioMedico resultado(String resultado) {
        this.setResultado(resultado);
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Instant getFechaRealizacion() {
        return this.fechaRealizacion;
    }

    public EstudioMedico fechaRealizacion(Instant fechaRealizacion) {
        this.setFechaRealizacion(fechaRealizacion);
        return this;
    }

    public void setFechaRealizacion(Instant fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public ConsultaMedica getConsultaMedica() {
        return this.consultaMedica;
    }

    public void setConsultaMedica(ConsultaMedica consultaMedica) {
        this.consultaMedica = consultaMedica;
    }

    public EstudioMedico consultaMedica(ConsultaMedica consultaMedica) {
        this.setConsultaMedica(consultaMedica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstudioMedico)) {
            return false;
        }
        return id != null && id.equals(((EstudioMedico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstudioMedico{" +
            "id=" + getId() +
            ", tipoEstudio='" + getTipoEstudio() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", fechaRealizacion='" + getFechaRealizacion() + "'" +
            "}";
    }
}
