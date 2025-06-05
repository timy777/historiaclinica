package com.historiac.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.historiac.domain.EvaluacionFisica} entity.
 */
public class EvaluacionFisicaDTO implements Serializable {

    private Long id;

    private String presionArterial;

    private Float temperatura;

    private Integer ritmoCardiaco;

    private Integer frecuenciaRespiratoria;

    private Float peso;

    private Float altura;

    private ConsultaMedicaDTO consultaMedica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getRitmoCardiaco() {
        return ritmoCardiaco;
    }

    public void setRitmoCardiaco(Integer ritmoCardiaco) {
        this.ritmoCardiaco = ritmoCardiaco;
    }

    public Integer getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(Integer frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public ConsultaMedicaDTO getConsultaMedica() {
        return consultaMedica;
    }

    public void setConsultaMedica(ConsultaMedicaDTO consultaMedica) {
        this.consultaMedica = consultaMedica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluacionFisicaDTO)) {
            return false;
        }

        EvaluacionFisicaDTO evaluacionFisicaDTO = (EvaluacionFisicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evaluacionFisicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluacionFisicaDTO{" +
            "id=" + getId() +
            ", presionArterial='" + getPresionArterial() + "'" +
            ", temperatura=" + getTemperatura() +
            ", ritmoCardiaco=" + getRitmoCardiaco() +
            ", frecuenciaRespiratoria=" + getFrecuenciaRespiratoria() +
            ", peso=" + getPeso() +
            ", altura=" + getAltura() +
            ", consultaMedica=" + getConsultaMedica() +
            "}";
    }
}
