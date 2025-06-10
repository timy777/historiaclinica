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

    @Column(name = "hash_blockchain")
    private String hashBlockchain;

    @OneToMany(mappedBy = "personalMedico")
    @JsonIgnoreProperties(
        value = { "evaluacionFisicas", "medicacions", "tratamientos", "estudioMedicos", "personalMedico", "paciente" },
        allowSetters = true
    )
    private Set<ConsultaMedica> consultaMedicas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_personal_medico__paciente",
        joinColumns = @JoinColumn(name = "personal_medico_id"),
        inverseJoinColumns = @JoinColumn(name = "paciente_id")
    )
    @JsonIgnoreProperties(value = { "consultaMedicas", "citaMedicas", "procesoMedicos", "personalMedicos" }, allowSetters = true)
    private Set<Paciente> pacientes = new HashSet<>();

    @OneToMany(mappedBy = "personalMedico")
    @JsonIgnoreProperties(value = { "paciente", "personalMedico" }, allowSetters = true)
    private Set<CitaMedica> citaMedicas = new HashSet<>();

    @OneToMany(mappedBy = "personalMedico")
    @JsonIgnoreProperties(value = { "paciente", "personalMedico", "salaMedica" }, allowSetters = true)
    private Set<ProcesoMedico> procesoMedicos = new HashSet<>();

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

    public String getHashBlockchain() {
        return this.hashBlockchain;
    }

    public PersonalMedico hashBlockchain(String hashBlockchain) {
        this.setHashBlockchain(hashBlockchain);
        return this;
    }

    public void setHashBlockchain(String hashBlockchain) {
        this.hashBlockchain = hashBlockchain;
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

    public Set<Paciente> getPacientes() {
        return this.pacientes;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public PersonalMedico pacientes(Set<Paciente> pacientes) {
        this.setPacientes(pacientes);
        return this;
    }

    public PersonalMedico addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.getPersonalMedicos().add(this);
        return this;
    }

    public PersonalMedico removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.getPersonalMedicos().remove(this);
        return this;
    }

    public Set<CitaMedica> getCitaMedicas() {
        return this.citaMedicas;
    }

    public void setCitaMedicas(Set<CitaMedica> citaMedicas) {
        if (this.citaMedicas != null) {
            this.citaMedicas.forEach(i -> i.setPersonalMedico(null));
        }
        if (citaMedicas != null) {
            citaMedicas.forEach(i -> i.setPersonalMedico(this));
        }
        this.citaMedicas = citaMedicas;
    }

    public PersonalMedico citaMedicas(Set<CitaMedica> citaMedicas) {
        this.setCitaMedicas(citaMedicas);
        return this;
    }

    public PersonalMedico addCitaMedica(CitaMedica citaMedica) {
        this.citaMedicas.add(citaMedica);
        citaMedica.setPersonalMedico(this);
        return this;
    }

    public PersonalMedico removeCitaMedica(CitaMedica citaMedica) {
        this.citaMedicas.remove(citaMedica);
        citaMedica.setPersonalMedico(null);
        return this;
    }

    public Set<ProcesoMedico> getProcesoMedicos() {
        return this.procesoMedicos;
    }

    public void setProcesoMedicos(Set<ProcesoMedico> procesoMedicos) {
        if (this.procesoMedicos != null) {
            this.procesoMedicos.forEach(i -> i.setPersonalMedico(null));
        }
        if (procesoMedicos != null) {
            procesoMedicos.forEach(i -> i.setPersonalMedico(this));
        }
        this.procesoMedicos = procesoMedicos;
    }

    public PersonalMedico procesoMedicos(Set<ProcesoMedico> procesoMedicos) {
        this.setProcesoMedicos(procesoMedicos);
        return this;
    }

    public PersonalMedico addProcesoMedico(ProcesoMedico procesoMedico) {
        this.procesoMedicos.add(procesoMedico);
        procesoMedico.setPersonalMedico(this);
        return this;
    }

    public PersonalMedico removeProcesoMedico(ProcesoMedico procesoMedico) {
        this.procesoMedicos.remove(procesoMedico);
        procesoMedico.setPersonalMedico(null);
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
            ", hashBlockchain='" + getHashBlockchain() + "'" +
            "}";
    }
}
