package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A EvaluacionFisica.
 */
@Entity
@Table(name = "evaluacion_fisica")
public class EvaluacionFisica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "presion_arterial")
    private String presionArterial;

    @Column(name = "temperatura")
    private Float temperatura;

    @Column(name = "ritmo_cardiaco")
    private Integer ritmoCardiaco;

    @Column(name = "frecuencia_respiratoria")
    private Integer frecuenciaRespiratoria;

    @Column(name = "peso")
    private Float peso;

    @Column(name = "altura")
    private Float altura;

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

    public EvaluacionFisica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresionArterial() {
        return this.presionArterial;
    }

    public EvaluacionFisica presionArterial(String presionArterial) {
        this.setPresionArterial(presionArterial);
        return this;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public Float getTemperatura() {
        return this.temperatura;
    }

    public EvaluacionFisica temperatura(Float temperatura) {
        this.setTemperatura(temperatura);
        return this;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getRitmoCardiaco() {
        return this.ritmoCardiaco;
    }

    public EvaluacionFisica ritmoCardiaco(Integer ritmoCardiaco) {
        this.setRitmoCardiaco(ritmoCardiaco);
        return this;
    }

    public void setRitmoCardiaco(Integer ritmoCardiaco) {
        this.ritmoCardiaco = ritmoCardiaco;
    }

    public Integer getFrecuenciaRespiratoria() {
        return this.frecuenciaRespiratoria;
    }

    public EvaluacionFisica frecuenciaRespiratoria(Integer frecuenciaRespiratoria) {
        this.setFrecuenciaRespiratoria(frecuenciaRespiratoria);
        return this;
    }

    public void setFrecuenciaRespiratoria(Integer frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public Float getPeso() {
        return this.peso;
    }

    public EvaluacionFisica peso(Float peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return this.altura;
    }

    public EvaluacionFisica altura(Float altura) {
        this.setAltura(altura);
        return this;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public ConsultaMedica getConsultaMedica() {
        return this.consultaMedica;
    }

    public void setConsultaMedica(ConsultaMedica consultaMedica) {
        this.consultaMedica = consultaMedica;
    }

    public EvaluacionFisica consultaMedica(ConsultaMedica consultaMedica) {
        this.setConsultaMedica(consultaMedica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluacionFisica)) {
            return false;
        }
        return id != null && id.equals(((EvaluacionFisica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluacionFisica{" +
            "id=" + getId() +
            ", presionArterial='" + getPresionArterial() + "'" +
            ", temperatura=" + getTemperatura() +
            ", ritmoCardiaco=" + getRitmoCardiaco() +
            ", frecuenciaRespiratoria=" + getFrecuenciaRespiratoria() +
            ", peso=" + getPeso() +
            ", altura=" + getAltura() +
            "}";
    }
}
