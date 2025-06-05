package com.historiac.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.historiac.domain.Medicacion} entity.
 */
public class MedicacionDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreMedicamento;

    private String dosis;

    private String frecuencia;

    private String viaAdministracion;

    private ConsultaMedicaDTO consultaMedica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
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
        if (!(o instanceof MedicacionDTO)) {
            return false;
        }

        MedicacionDTO medicacionDTO = (MedicacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, medicacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicacionDTO{" +
            "id=" + getId() +
            ", nombreMedicamento='" + getNombreMedicamento() + "'" +
            ", dosis='" + getDosis() + "'" +
            ", frecuencia='" + getFrecuencia() + "'" +
            ", viaAdministracion='" + getViaAdministracion() + "'" +
            ", consultaMedica=" + getConsultaMedica() +
            "}";
    }
}
