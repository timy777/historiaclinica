package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Medicacion.
 */
@Entity
@Table(name = "medicacion")
public class Medicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_medicamento", nullable = false)
    private String nombreMedicamento;

    @Column(name = "dosis")
    private String dosis;

    @Column(name = "frecuencia")
    private String frecuencia;

    @Column(name = "via_administracion")
    private String viaAdministracion;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "evaluacionFisicas", "medicacions", "tratamientos", "estudioMedicos", "personalMedico", "paciente" },
        allowSetters = true
    )
    private ConsultaMedica consultaMedica;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Medicacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreMedicamento() {
        return this.nombreMedicamento;
    }

    public Medicacion nombreMedicamento(String nombreMedicamento) {
        this.setNombreMedicamento(nombreMedicamento);
        return this;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getDosis() {
        return this.dosis;
    }

    public Medicacion dosis(String dosis) {
        this.setDosis(dosis);
        return this;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return this.frecuencia;
    }

    public Medicacion frecuencia(String frecuencia) {
        this.setFrecuencia(frecuencia);
        return this;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getViaAdministracion() {
        return this.viaAdministracion;
    }

    public Medicacion viaAdministracion(String viaAdministracion) {
        this.setViaAdministracion(viaAdministracion);
        return this;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public ConsultaMedica getConsultaMedica() {
        return this.consultaMedica;
    }

    public void setConsultaMedica(ConsultaMedica consultaMedica) {
        this.consultaMedica = consultaMedica;
    }

    public Medicacion consultaMedica(ConsultaMedica consultaMedica) {
        this.setConsultaMedica(consultaMedica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicacion)) {
            return false;
        }
        return id != null && id.equals(((Medicacion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medicacion{" +
            "id=" + getId() +
            ", nombreMedicamento='" + getNombreMedicamento() + "'" +
            ", dosis='" + getDosis() + "'" +
            ", frecuencia='" + getFrecuencia() + "'" +
            ", viaAdministracion='" + getViaAdministracion() + "'" +
            "}";
    }
}
