import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPersonalMedico, PersonalMedico } from '../personal-medico.model';
import { PersonalMedicoService } from '../service/personal-medico.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';

@Component({
  selector: 'jhi-personal-medico-update',
  templateUrl: './personal-medico-update.component.html',
})
export class PersonalMedicoUpdateComponent implements OnInit {
  isSaving = false;

  pacientesSharedCollection: IPaciente[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    especialidad: [],
    telefonoContacto: [],
    correo: [],
    licenciaMedica: [],
    pacientes: [],
  });

  constructor(
    protected personalMedicoService: PersonalMedicoService,
    protected pacienteService: PacienteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personalMedico }) => {
      this.updateForm(personalMedico);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personalMedico = this.createFromForm();
    if (personalMedico.id !== undefined) {
      this.subscribeToSaveResponse(this.personalMedicoService.update(personalMedico));
    } else {
      this.subscribeToSaveResponse(this.personalMedicoService.create(personalMedico));
    }
  }

  trackPacienteById(_index: number, item: IPaciente): number {
    return item.id!;
  }

  getSelectedPaciente(option: IPaciente, selectedVals?: IPaciente[]): IPaciente {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonalMedico>>): void {
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

  protected updateForm(personalMedico: IPersonalMedico): void {
    this.editForm.patchValue({
      id: personalMedico.id,
      nombre: personalMedico.nombre,
      especialidad: personalMedico.especialidad,
      telefonoContacto: personalMedico.telefonoContacto,
      correo: personalMedico.correo,
      licenciaMedica: personalMedico.licenciaMedica,
      pacientes: personalMedico.pacientes,
    });

    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing(
      this.pacientesSharedCollection,
      ...(personalMedico.pacientes ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) =>
          this.pacienteService.addPacienteToCollectionIfMissing(pacientes, ...(this.editForm.get('pacientes')!.value ?? []))
        )
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));
  }

  protected createFromForm(): IPersonalMedico {
    return {
      ...new PersonalMedico(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      especialidad: this.editForm.get(['especialidad'])!.value,
      telefonoContacto: this.editForm.get(['telefonoContacto'])!.value,
      correo: this.editForm.get(['correo'])!.value,
      licenciaMedica: this.editForm.get(['licenciaMedica'])!.value,
      pacientes: this.editForm.get(['pacientes'])!.value,
    };
  }
}
