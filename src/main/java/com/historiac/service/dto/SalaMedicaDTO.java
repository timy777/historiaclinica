package com.historiac.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.historiac.domain.SalaMedica} entity.
 */
public class SalaMedicaDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer nroSala;

    @NotNull
    private String nombre;

    private String ubicacion;

    private String equipamiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNroSala() {
        return nroSala;
    }

    public void setNroSala(Integer nroSala) {
        this.nroSala = nroSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalaMedicaDTO)) {
            return false;
        }

        SalaMedicaDTO salaMedicaDTO = (SalaMedicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salaMedicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalaMedicaDTO{" +
            "id=" + getId() +
            ", nroSala=" + getNroSala() +
            ", nombre='" + getNombre() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", equipamiento='" + getEquipamiento() + "'" +
            "}";
    }
}
