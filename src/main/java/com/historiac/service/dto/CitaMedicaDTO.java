package com.historiac.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.historiac.domain.CitaMedica} entity.
 */
public class CitaMedicaDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fechaCita;

    @NotNull
    private String horaCita;

    private String motivo;

    @NotNull
    private String estado;

    private PacienteDTO paciente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        if (!(o instanceof CitaMedicaDTO)) {
            return false;
        }

        CitaMedicaDTO citaMedicaDTO = (CitaMedicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, citaMedicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitaMedicaDTO{" +
            "id=" + getId() +
            ", fechaCita='" + getFechaCita() + "'" +
            ", horaCita='" + getHoraCita() + "'" +
            ", motivo='" + getMotivo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", paciente=" + getPaciente() +
            "}";
    }
}
