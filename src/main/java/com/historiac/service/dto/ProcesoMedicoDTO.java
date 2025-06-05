package com.historiac.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.historiac.domain.ProcesoMedico} entity.
 */
public class ProcesoMedicoDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoProceso;

    @NotNull
    private Instant fechaInicio;

    private Instant fechaFin;

    @NotNull
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcesoMedicoDTO)) {
            return false;
        }

        ProcesoMedicoDTO procesoMedicoDTO = (ProcesoMedicoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, procesoMedicoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcesoMedicoDTO{" +
            "id=" + getId() +
            ", tipoProceso='" + getTipoProceso() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
