package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PersonalMedico.
 */
@Entity
@Table(name = "personal_medico")
public class PersonalMedico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    @Column(name = "correo")
    private String correo;

    @Column(name = "licencia_medica")
    private String licenciaMedica;

    @OneToMany(mappedBy = "personalMedico")
    @JsonIgnoreProperties(
        value = { "evaluacionFisicas", "medicacions", "tratamientos", "estudioMedicos", "personalMedico", "paciente" },
        allowSetters = true
    )
    private Set<ConsultaMedica> consultaMedicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonalMedico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public PersonalMedico nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public PersonalMedico especialidad(String especialidad) {
        this.setEspecialidad(especialidad);
        return this;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefonoContacto() {
        return this.telefonoContacto;
    }

    public PersonalMedico telefonoContacto(String telefonoContacto) {
        this.setTelefonoContacto(telefonoContacto);
        return this;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreo() {
        return this.correo;
    }

    public PersonalMedico correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLicenciaMedica() {
        return this.licenciaMedica;
    }

    public PersonalMedico licenciaMedica(String licenciaMedica) {
        this.setLicenciaMedica(licenciaMedica);
        return this;
    }

    public void setLicenciaMedica(String licenciaMedica) {
        this.licenciaMedica = licenciaMedica;
    }

    public Set<ConsultaMedica> getConsultaMedicas() {
        return this.consultaMedicas;
    }

    public void setConsultaMedicas(Set<ConsultaMedica> consultaMedicas) {
        if (this.consultaMedicas != null) {
            this.consultaMedicas.forEach(i -> i.setPersonalMedico(null));
        }
        if (consultaMedicas != null) {
            consultaMedicas.forEach(i -> i.setPersonalMedico(this));
        }
        this.consultaMedicas = consultaMedicas;
    }

    public PersonalMedico consultaMedicas(Set<ConsultaMedica> consultaMedicas) {
        this.setConsultaMedicas(consultaMedicas);
        return this;
    }

    public PersonalMedico addConsultaMedica(ConsultaMedica consultaMedica) {
        this.consultaMedicas.add(consultaMedica);
        consultaMedica.setPersonalMedico(this);
        return this;
    }

    public PersonalMedico removeConsultaMedica(ConsultaMedica consultaMedica) {
        this.consultaMedicas.remove(consultaMedica);
        consultaMedica.setPersonalMedico(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalMedico)) {
            return false;
        }
        return id != null && id.equals(((PersonalMedico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalMedico{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", licenciaMedica='" + getLicenciaMedica() + "'" +
            "}";
    }
}
