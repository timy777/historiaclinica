package com.historiac.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.historiac.domain.Tratamiento} entity.
 */
public class TratamientoDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoTratamiento;

    private String duracion;

    private String objetivo;

    private ConsultaMedicaDTO consultaMedica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoTratamiento() {
        return tipoTratamiento;
    }

    public void setTipoTratamiento(String tipoTratamiento) {
        this.tipoTratamiento = tipoTratamiento;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
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
        if (!(o instanceof TratamientoDTO)) {
            return false;
        }

        TratamientoDTO tratamientoDTO = (TratamientoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tratamientoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TratamientoDTO{" +
            "id=" + getId() +
            ", tipoTratamiento='" + getTipoTratamiento() + "'" +
            ", duracion='" + getDuracion() + "'" +
            ", objetivo='" + getObjetivo() + "'" +
            ", consultaMedica=" + getConsultaMedica() +
            "}";
    }
}
