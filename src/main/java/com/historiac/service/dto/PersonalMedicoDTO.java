package com.historiac.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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

    private String hashBlockchain;

    private Set<PacienteDTO> pacientes = new HashSet<>();

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

    public String getHashBlockchain() {
        return hashBlockchain;
    }

    public void setHashBlockchain(String hashBlockchain) {
        this.hashBlockchain = hashBlockchain;
    }

    public Set<PacienteDTO> getPacientes() {
        return pacientes;
    }

    public void setPacientes(Set<PacienteDTO> pacientes) {
        this.pacientes = pacientes;
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
            ", hashBlockchain='" + getHashBlockchain() + "'" +
            ", pacientes=" + getPacientes() +
            "}";
    }
}
