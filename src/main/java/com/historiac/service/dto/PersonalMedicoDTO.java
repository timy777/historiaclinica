package com.historiac.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.historiac.domain.PersonalMedico} entity.
 */
public class PersonalMedicoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String especialidad;

    private String telefonoContacto;

    private String correo;

    private String licenciaMedica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLicenciaMedica() {
        return licenciaMedica;
    }

    public void setLicenciaMedica(String licenciaMedica) {
        this.licenciaMedica = licenciaMedica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalMedicoDTO)) {
            return false;
        }

        PersonalMedicoDTO personalMedicoDTO = (PersonalMedicoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personalMedicoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalMedicoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", licenciaMedica='" + getLicenciaMedica() + "'" +
            "}";
    }
}
