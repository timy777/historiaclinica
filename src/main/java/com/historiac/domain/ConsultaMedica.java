package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ConsultaMedica.
 */
@Entity
@Table(name = "consulta_medica")
public class ConsultaMedica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "diagnostico", nullable = false)
    private String diagnostico;

    @Column(name = "tratamiento_sugerido")
    private String tratamientoSugerido;

    @Column(name = "observaciones")
    private String observaciones;

    @NotNull
    @Column(name = "fecha_consulta", nullable = false)
    private Instant fechaConsulta;

    @OneToMany(mappedBy = "consultaMedica")
    @JsonIgnoreProperties(value = { "consultaMedica" }, allowSetters = true)
    private Set<EvaluacionFisica> evaluacionFisicas = new HashSet<>();

    @OneToMany(mappedBy = "consultaMedica")
    @JsonIgnoreProperties(value = { "consultaMedica" }, allowSetters = true)
    private Set<Medicacion> medicacions = new HashSet<>();

    @OneToMany(mappedBy = "consultaMedica")
    @JsonIgnoreProperties(value = { "consultaMedica" }, allowSetters = true)
    private Set<Tratamiento> tratamientos = new HashSet<>();

    @OneToMany(mappedBy = "consultaMedica")
    @JsonIgnoreProperties(value = { "consultaMedica" }, allowSetters = true)
    private Set<EstudioMedico> estudioMedicos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "consultaMedicas" }, allowSetters = true)
    private PersonalMedico personalMedico;

    @ManyToOne
    @JsonIgnoreProperties(value = { "citaMedicas", "consultaMedicas" }, allowSetters = true)
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConsultaMedica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public ConsultaMedica diagnostico(String diagnostico) {
        this.setDiagnostico(diagnostico);
        return this;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamientoSugerido() {
        return this.tratamientoSugerido;
    }

    public ConsultaMedica tratamientoSugerido(String tratamientoSugerido) {
        this.setTratamientoSugerido(tratamientoSugerido);
        return this;
    }

    public void setTratamientoSugerido(String tratamientoSugerido) {
        this.tratamientoSugerido = tratamientoSugerido;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public ConsultaMedica observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Instant getFechaConsulta() {
        return this.fechaConsulta;
    }

    public ConsultaMedica fechaConsulta(Instant fechaConsulta) {
        this.setFechaConsulta(fechaConsulta);
        return this;
    }

    public void setFechaConsulta(Instant fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public Set<EvaluacionFisica> getEvaluacionFisicas() {
        return this.evaluacionFisicas;
    }

    public void setEvaluacionFisicas(Set<EvaluacionFisica> evaluacionFisicas) {
        if (this.evaluacionFisicas != null) {
            this.evaluacionFisicas.forEach(i -> i.setConsultaMedica(null));
        }
        if (evaluacionFisicas != null) {
            evaluacionFisicas.forEach(i -> i.setConsultaMedica(this));
        }
        this.evaluacionFisicas = evaluacionFisicas;
    }

    public ConsultaMedica evaluacionFisicas(Set<EvaluacionFisica> evaluacionFisicas) {
        this.setEvaluacionFisicas(evaluacionFisicas);
        return this;
    }

    public ConsultaMedica addEvaluacionFisica(EvaluacionFisica evaluacionFisica) {
        this.evaluacionFisicas.add(evaluacionFisica);
        evaluacionFisica.setConsultaMedica(this);
        return this;
    }

    public ConsultaMedica removeEvaluacionFisica(EvaluacionFisica evaluacionFisica) {
        this.evaluacionFisicas.remove(evaluacionFisica);
        evaluacionFisica.setConsultaMedica(null);
        return this;
    }

    public Set<Medicacion> getMedicacions() {
        return this.medicacions;
    }

    public void setMedicacions(Set<Medicacion> medicacions) {
        if (this.medicacions != null) {
            this.medicacions.forEach(i -> i.setConsultaMedica(null));
        }
        if (medicacions != null) {
            medicacions.forEach(i -> i.setConsultaMedica(this));
        }
        this.medicacions = medicacions;
    }

    public ConsultaMedica medicacions(Set<Medicacion> medicacions) {
        this.setMedicacions(medicacions);
        return this;
    }

    public ConsultaMedica addMedicacion(Medicacion medicacion) {
        this.medicacions.add(medicacion);
        medicacion.setConsultaMedica(this);
        return this;
    }

    public ConsultaMedica removeMedicacion(Medicacion medicacion) {
        this.medicacions.remove(medicacion);
        medicacion.setConsultaMedica(null);
        return this;
    }

    public Set<Tratamiento> getTratamientos() {
        return this.tratamientos;
    }

    public void setTratamientos(Set<Tratamiento> tratamientos) {
        if (this.tratamientos != null) {
            this.tratamientos.forEach(i -> i.setConsultaMedica(null));
        }
        if (tratamientos != null) {
            tratamientos.forEach(i -> i.setConsultaMedica(this));
        }
        this.tratamientos = tratamientos;
    }

    public ConsultaMedica tratamientos(Set<Tratamiento> tratamientos) {
        this.setTratamientos(tratamientos);
        return this;
    }

    public ConsultaMedica addTratamiento(Tratamiento tratamiento) {
        this.tratamientos.add(tratamiento);
        tratamiento.setConsultaMedica(this);
        return this;
    }

    public ConsultaMedica removeTratamiento(Tratamiento tratamiento) {
        this.tratamientos.remove(tratamiento);
        tratamiento.setConsultaMedica(null);
        return this;
    }

    public Set<EstudioMedico> getEstudioMedicos() {
        return this.estudioMedicos;
    }

    public void setEstudioMedicos(Set<EstudioMedico> estudioMedicos) {
        if (this.estudioMedicos != null) {
            this.estudioMedicos.forEach(i -> i.setConsultaMedica(null));
        }
        if (estudioMedicos != null) {
            estudioMedicos.forEach(i -> i.setConsultaMedica(this));
        }
        this.estudioMedicos = estudioMedicos;
    }

    public ConsultaMedica estudioMedicos(Set<EstudioMedico> estudioMedicos) {
        this.setEstudioMedicos(estudioMedicos);
        return this;
    }

    public ConsultaMedica addEstudioMedico(EstudioMedico estudioMedico) {
        this.estudioMedicos.add(estudioMedico);
        estudioMedico.setConsultaMedica(this);
        return this;
    }

    public ConsultaMedica removeEstudioMedico(EstudioMedico estudioMedico) {
        this.estudioMedicos.remove(estudioMedico);
        estudioMedico.setConsultaMedica(null);
        return this;
    }

    public PersonalMedico getPersonalMedico() {
        return this.personalMedico;
    }

    public void setPersonalMedico(PersonalMedico personalMedico) {
        this.personalMedico = personalMedico;
    }

    public ConsultaMedica personalMedico(PersonalMedico personalMedico) {
        this.setPersonalMedico(personalMedico);
        return this;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public ConsultaMedica paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultaMedica)) {
            return false;
        }
        return id != null && id.equals(((ConsultaMedica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultaMedica{" +
            "id=" + getId() +
            ", diagnostico='" + getDiagnostico() + "'" +
            ", tratamientoSugerido='" + getTratamientoSugerido() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", fechaConsulta='" + getFechaConsulta() + "'" +
            "}";
    }
}
