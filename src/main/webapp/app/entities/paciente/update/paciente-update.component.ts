import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPaciente, Paciente } from '../paciente.model';
import { PacienteService } from '../service/paciente.service';

@Component({
  selector: 'jhi-paciente-update',
  templateUrl: './paciente-update.component.html',
})
export class PacienteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    fechaNacimiento: [null, [Validators.required]],
    genero: [],
    direccion: [],
    carnetidentidad: [null, [Validators.required]],
    email: [null, [Validators.required]],
    password: [null, [Validators.required]],
    telefonoContacto: [],
    historialMedico: [],
  });

  constructor(protected pacienteService: PacienteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paciente }) => {
      this.updateForm(paciente);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paciente = this.createFromForm();
    if (paciente.id !== undefined) {
      this.subscribeToSaveResponse(this.pacienteService.update(paciente));
    } else {
      this.subscribeToSaveResponse(this.pacienteService.create(paciente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaciente>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(paciente: IPaciente): void {
    this.editForm.patchValue({
      id: paciente.id,
      nombre: paciente.nombre,
      fechaNacimiento: paciente.fechaNacimiento,
      genero: paciente.genero,
      direccion: paciente.direccion,
      carnetidentidad: paciente.carnetidentidad,
      email: paciente.email,
      password: paciente.password,
      telefonoContacto: paciente.telefonoContacto,
      historialMedico: paciente.historialMedico,
    });
  }

  protected createFromForm(): IPaciente {
    return {
      ...new Paciente(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento'])!.value,
      genero: this.editForm.get(['genero'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      carnetidentidad: this.editForm.get(['carnetidentidad'])!.value,
      email: this.editForm.get(['email'])!.value,
      password: this.editForm.get(['password'])!.value,
      telefonoContacto: this.editForm.get(['telefonoContacto'])!.value,
      historialMedico: this.editForm.get(['historialMedico'])!.value,
    };
  }
}
