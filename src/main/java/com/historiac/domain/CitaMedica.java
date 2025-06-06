package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CitaMedica.
 */
@Entity
@Table(name = "cita_medica")
public class CitaMedica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;

    @NotNull
    @Column(name = "hora_cita", nullable = false)
    private String horaCita;

    @Column(name = "motivo")
    private String motivo;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @ManyToOne
    @JsonIgnoreProperties(value = { "consultaMedicas", "citaMedicas", "procesoMedicos", "personalMedicos" }, allowSetters = true)
    private Paciente paciente;

    @ManyToOne
    @JsonIgnoreProperties(value = { "consultaMedicas", "pacientes", "citaMedicas", "procesoMedicos" }, allowSetters = true)
    private PersonalMedico personalMedico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CitaMedica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCita() {
        return this.fechaCita;
    }

    public CitaMedica fechaCita(LocalDate fechaCita) {
        this.setFechaCita(fechaCita);
        return this;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraCita() {
        return this.horaCita;
    }

    public CitaMedica horaCita(String horaCita) {
        this.setHoraCita(horaCita);
        return this;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public String getMotivo() {
        return this.motivo;
    }

    public CitaMedica motivo(String motivo) {
        this.setMotivo(motivo);
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return this.estado;
    }

    public CitaMedica estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public CitaMedica paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    public PersonalMedico getPersonalMedico() {
        return this.personalMedico;
    }

    public void setPersonalMedico(PersonalMedico personalMedico) {
        this.personalMedico = personalMedico;
    }

    public CitaMedica personalMedico(PersonalMedico personalMedico) {
        this.setPersonalMedico(personalMedico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitaMedica)) {
            return false;
        }
        return id != null && id.equals(((CitaMedica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitaMedica{" +
            "id=" + getId() +
            ", fechaCita='" + getFechaCita() + "'" +
            ", horaCita='" + getHoraCita() + "'" +
            ", motivo='" + getMotivo() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
