package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "genero")
    private String genero;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    @Column(name = "historial_medico")
    private String historialMedico;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnoreProperties(value = { "paciente" }, allowSetters = true)
    private Set<CitaMedica> citaMedicas = new HashSet<>();

    @OneToMany(mappedBy = "paciente")
    @JsonIgnoreProperties(
        value = { "evaluacionFisicas", "medicacions", "tratamientos", "estudioMedicos", "personalMedico", "paciente" },
        allowSetters = true
    )
    private Set<ConsultaMedica> consultaMedicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paciente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Paciente nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public Paciente fechaNacimiento(LocalDate fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return this.genero;
    }

    public Paciente genero(String genero) {
        this.setGenero(genero);
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Paciente direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoContacto() {
        return this.telefonoContacto;
    }

    public Paciente telefonoContacto(String telefonoContacto) {
        this.setTelefonoContacto(telefonoContacto);
        return this;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getHistorialMedico() {
        return this.historialMedico;
    }

    public Paciente historialMedico(String historialMedico) {
        this.setHistorialMedico(historialMedico);
        return this;
    }

    public void setHistorialMedico(String historialMedico) {
        this.historialMedico = historialMedico;
    }

    public Set<CitaMedica> getCitaMedicas() {
        return this.citaMedicas;
    }

    public void setCitaMedicas(Set<CitaMedica> citaMedicas) {
        if (this.citaMedicas != null) {
            this.citaMedicas.forEach(i -> i.setPaciente(null));
        }
        if (citaMedicas != null) {
            citaMedicas.forEach(i -> i.setPaciente(this));
        }
        this.citaMedicas = citaMedicas;
    }

    public Paciente citaMedicas(Set<CitaMedica> citaMedicas) {
        this.setCitaMedicas(citaMedicas);
        return this;
    }

    public Paciente addCitaMedica(CitaMedica citaMedica) {
        this.citaMedicas.add(citaMedica);
        citaMedica.setPaciente(this);
        return this;
    }

    public Paciente removeCitaMedica(CitaMedica citaMedica) {
        this.citaMedicas.remove(citaMedica);
        citaMedica.setPaciente(null);
        return this;
    }

    public Set<ConsultaMedica> getConsultaMedicas() {
        return this.consultaMedicas;
    }

    public void setConsultaMedicas(Set<ConsultaMedica> consultaMedicas) {
        if (this.consultaMedicas != null) {
            this.consultaMedicas.forEach(i -> i.setPaciente(null));
        }
        if (consultaMedicas != null) {
            consultaMedicas.forEach(i -> i.setPaciente(this));
        }
        this.consultaMedicas = consultaMedicas;
    }

    public Paciente consultaMedicas(Set<ConsultaMedica> consultaMedicas) {
        this.setConsultaMedicas(consultaMedicas);
        return this;
    }

    public Paciente addConsultaMedica(ConsultaMedica consultaMedica) {
        this.consultaMedicas.add(consultaMedica);
        consultaMedica.setPaciente(this);
        return this;
    }

    public Paciente removeConsultaMedica(ConsultaMedica consultaMedica) {
        this.consultaMedicas.remove(consultaMedica);
        consultaMedica.setPaciente(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return id != null && id.equals(((Paciente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", genero='" + getGenero() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            ", historialMedico='" + getHistorialMedico() + "'" +
            "}";
    }
}
