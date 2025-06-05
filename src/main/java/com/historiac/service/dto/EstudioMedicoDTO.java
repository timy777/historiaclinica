package com.historiac.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.historiac.domain.EstudioMedico} entity.
 */
public class EstudioMedicoDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoEstudio;

    private String resultado;

    @NotNull
    private Instant fechaRealizacion;

    private ConsultaMedicaDTO consultaMedica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEstudio() {
        return tipoEstudio;
    }

    public void setTipoEstudio(String tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Instant getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Instant fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public ConsultaMedicaDTO getConsultaMedica() {
        return consultaMedica;
    }

    public void setConsultaMedica(ConsultaMedicaDTO consultaMedica) {
        this.consultaMedica = consultaMedica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstudioMedicoDTO)) {
            return false;
        }

        EstudioMedicoDTO estudioMedicoDTO = (EstudioMedicoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estudioMedicoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstudioMedicoDTO{" +
            "id=" + getId() +
            ", tipoEstudio='" + getTipoEstudio() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", fechaRealizacion='" + getFechaRealizacion() + "'" +
            ", consultaMedica=" + getConsultaMedica() +
            "}";
    }
}
