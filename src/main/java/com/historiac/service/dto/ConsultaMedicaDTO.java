package com.historiac.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.historiac.domain.ConsultaMedica} entity.
 */
public class ConsultaMedicaDTO implements Serializable {

    private Long id;

    @NotNull
    private String diagnostico;

    private String tratamientoSugerido;

    private String observaciones;

    @NotNull
    private Instant fechaConsulta;

    private PersonalMedicoDTO personalMedico;

    private PacienteDTO paciente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamientoSugerido() {
        return tratamientoSugerido;
    }

    public void setTratamientoSugerido(String tratamientoSugerido) {
        this.tratamientoSugerido = tratamientoSugerido;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Instant getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Instant fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public PersonalMedicoDTO getPersonalMedico() {
        return personalMedico;
    }

    public void setPersonalMedico(PersonalMedicoDTO personalMedico) {
        this.personalMedico = personalMedico;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultaMedicaDTO)) {
            return false;
        }

        ConsultaMedicaDTO consultaMedicaDTO = (ConsultaMedicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, consultaMedicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultaMedicaDTO{" +
            "id=" + getId() +
            ", diagnostico='" + getDiagnostico() + "'" +
            ", tratamientoSugerido='" + getTratamientoSugerido() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", fechaConsulta='" + getFechaConsulta() + "'" +
            ", personalMedico=" + getPersonalMedico() +
            ", paciente=" + getPaciente() +
            "}";
    }
}
