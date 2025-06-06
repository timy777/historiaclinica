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

    @NotNull
    @Column(name = "carnetidentidad", nullable = false)
    private String carnetidentidad;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    @Column(name = "historial_medico")
    private String historialMedico;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnoreProperties(
        value = { "evaluacionFisicas", "medicacions", "tratamientos", "estudioMedicos", "personalMedico", "paciente" },
        allowSetters = true
    )
    private Set<ConsultaMedica> consultaMedicas = new HashSet<>();

    @OneToMany(mappedBy = "paciente")
    @JsonIgnoreProperties(value = { "paciente", "personalMedico" }, allowSetters = true)
    private Set<CitaMedica> citaMedicas = new HashSet<>();

    @OneToMany(mappedBy = "paciente")
    @JsonIgnoreProperties(value = { "paciente", "personalMedico", "salaMedica" }, allowSetters = true)
    private Set<ProcesoMedico> procesoMedicos = new HashSet<>();

    @ManyToMany(mappedBy = "pacientes")
    @JsonIgnoreProperties(value = { "consultaMedicas", "pacientes", "citaMedicas", "procesoMedicos" }, allowSetters = true)
    private Set<PersonalMedico> personalMedicos = new HashSet<>();

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

    public String getCarnetidentidad() {
        return this.carnetidentidad;
    }

    public Paciente carnetidentidad(String carnetidentidad) {
        this.setCarnetidentidad(carnetidentidad);
        return this;
    }

    public void setCarnetidentidad(String carnetidentidad) {
        this.carnetidentidad = carnetidentidad;
    }

    public String getEmail() {
        return this.email;
    }

    public Paciente email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Paciente password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<ProcesoMedico> getProcesoMedicos() {
        return this.procesoMedicos;
    }

    public void setProcesoMedicos(Set<ProcesoMedico> procesoMedicos) {
        if (this.procesoMedicos != null) {
            this.procesoMedicos.forEach(i -> i.setPaciente(null));
        }
        if (procesoMedicos != null) {
            procesoMedicos.forEach(i -> i.setPaciente(this));
        }
        this.procesoMedicos = procesoMedicos;
    }

    public Paciente procesoMedicos(Set<ProcesoMedico> procesoMedicos) {
        this.setProcesoMedicos(procesoMedicos);
        return this;
    }

    public Paciente addProcesoMedico(ProcesoMedico procesoMedico) {
        this.procesoMedicos.add(procesoMedico);
        procesoMedico.setPaciente(this);
        return this;
    }

    public Paciente removeProcesoMedico(ProcesoMedico procesoMedico) {
        this.procesoMedicos.remove(procesoMedico);
        procesoMedico.setPaciente(null);
        return this;
    }

    public Set<PersonalMedico> getPersonalMedicos() {
        return this.personalMedicos;
    }

    public void setPersonalMedicos(Set<PersonalMedico> personalMedicos) {
        if (this.personalMedicos != null) {
            this.personalMedicos.forEach(i -> i.removePaciente(this));
        }
        if (personalMedicos != null) {
            personalMedicos.forEach(i -> i.addPaciente(this));
        }
        this.personalMedicos = personalMedicos;
    }

    public Paciente personalMedicos(Set<PersonalMedico> personalMedicos) {
        this.setPersonalMedicos(personalMedicos);
        return this;
    }

    public Paciente addPersonalMedico(PersonalMedico personalMedico) {
        this.personalMedicos.add(personalMedico);
        personalMedico.getPacientes().add(this);
        return this;
    }

    public Paciente removePersonalMedico(PersonalMedico personalMedico) {
        this.personalMedicos.remove(personalMedico);
        personalMedico.getPacientes().remove(this);
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
            ", carnetidentidad='" + getCarnetidentidad() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            ", historialMedico='" + getHistorialMedico() + "'" +
            "}";
    }
}
