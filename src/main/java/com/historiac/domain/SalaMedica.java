package com.historiac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A SalaMedica.
 */
@Entity
@Table(name = "sala_medica")
public class SalaMedica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nro_sala", nullable = false)
    private Integer nroSala;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "equipamiento")
    private String equipamiento;

    @OneToMany(mappedBy = "salaMedica")
    @JsonIgnoreProperties(value = { "paciente", "personalMedico", "salaMedica" }, allowSetters = true)
    private Set<ProcesoMedico> procesoMedicos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalaMedica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNroSala() {
        return this.nroSala;
    }

    public SalaMedica nroSala(Integer nroSala) {
        this.setNroSala(nroSala);
        return this;
    }

    public void setNroSala(Integer nroSala) {
        this.nroSala = nroSala;
    }

    public String getNombre() {
        return this.nombre;
    }

    public SalaMedica nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return this.ubicacion;
    }

    public SalaMedica ubicacion(String ubicacion) {
        this.setUbicacion(ubicacion);
        return this;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEquipamiento() {
        return this.equipamiento;
    }

    public SalaMedica equipamiento(String equipamiento) {
        this.setEquipamiento(equipamiento);
        return this;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }

    public Set<ProcesoMedico> getProcesoMedicos() {
        return this.procesoMedicos;
    }

    public void setProcesoMedicos(Set<ProcesoMedico> procesoMedicos) {
        if (this.procesoMedicos != null) {
            this.procesoMedicos.forEach(i -> i.setSalaMedica(null));
        }
        if (procesoMedicos != null) {
            procesoMedicos.forEach(i -> i.setSalaMedica(this));
        }
        this.procesoMedicos = procesoMedicos;
    }

    public SalaMedica procesoMedicos(Set<ProcesoMedico> procesoMedicos) {
        this.setProcesoMedicos(procesoMedicos);
        return this;
    }

    public SalaMedica addProcesoMedico(ProcesoMedico procesoMedico) {
        this.procesoMedicos.add(procesoMedico);
        procesoMedico.setSalaMedica(this);
        return this;
    }

    public SalaMedica removeProcesoMedico(ProcesoMedico procesoMedico) {
        this.procesoMedicos.remove(procesoMedico);
        procesoMedico.setSalaMedica(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalaMedica)) {
            return false;
        }
        return id != null && id.equals(((SalaMedica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalaMedica{" +
            "id=" + getId() +
            ", nroSala=" + getNroSala() +
            ", nombre='" + getNombre() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", equipamiento='" + getEquipamiento() + "'" +
            "}";
    }
}
