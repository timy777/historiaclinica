package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProcesoMedico.
 */
@Entity
@Table(name = "proceso_medico")
public class ProcesoMedico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tipo_proceso", nullable = false)
    private String tipoProceso;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private Instant fechaInicio;

    @Column(name = "fecha_fin")
    private Instant fechaFin;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "hash_blockchain")
    private String hashBlockchain;

    @ManyToOne
    @JsonIgnoreProperties(value = { "consultaMedicas", "citaMedicas", "procesoMedicos", "personalMedicos" }, allowSetters = true)
    private Paciente paciente;

    @ManyToOne
    @JsonIgnoreProperties(value = { "consultaMedicas", "pacientes", "citaMedicas", "procesoMedicos" }, allowSetters = true)
    private PersonalMedico personalMedico;

    @ManyToOne
    @JsonIgnoreProperties(value = { "procesoMedicos" }, allowSetters = true)
    private SalaMedica salaMedica;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProcesoMedico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoProceso() {
        return this.tipoProceso;
    }

    public ProcesoMedico tipoProceso(String tipoProceso) {
        this.setTipoProceso(tipoProceso);
        return this;
    }

    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public Instant getFechaInicio() {
        return this.fechaInicio;
    }

    public ProcesoMedico fechaInicio(Instant fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return this.fechaFin;
    }

    public ProcesoMedico fechaFin(Instant fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return this.estado;
    }

    public ProcesoMedico estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHashBlockchain() {
        return this.hashBlockchain;
    }

    public ProcesoMedico hashBlockchain(String hashBlockchain) {
        this.setHashBlockchain(hashBlockchain);
        return this;
    }

    public void setHashBlockchain(String hashBlockchain) {
        this.hashBlockchain = hashBlockchain;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public ProcesoMedico paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    public PersonalMedico getPersonalMedico() {
        return this.personalMedico;
    }

    public void setPersonalMedico(PersonalMedico personalMedico) {
        this.personalMedico = personalMedico;
    }

    public ProcesoMedico personalMedico(PersonalMedico personalMedico) {
        this.setPersonalMedico(personalMedico);
        return this;
    }

    public SalaMedica getSalaMedica() {
        return this.salaMedica;
    }

    public void setSalaMedica(SalaMedica salaMedica) {
        this.salaMedica = salaMedica;
    }

    public ProcesoMedico salaMedica(SalaMedica salaMedica) {
        this.setSalaMedica(salaMedica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcesoMedico)) {
            return false;
        }
        return id != null && id.equals(((ProcesoMedico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcesoMedico{" +
            "id=" + getId() +
            ", tipoProceso='" + getTipoProceso() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", estado='" + getEstado() + "'" +
            ", hashBlockchain='" + getHashBlockchain() + "'" +
            "}";
    }
}
