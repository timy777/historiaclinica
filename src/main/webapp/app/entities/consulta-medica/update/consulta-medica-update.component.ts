import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IConsultaMedica, ConsultaMedica } from '../consulta-medica.model';
import { ConsultaMedicaService } from '../service/consulta-medica.service';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';
import { PersonalMedicoService } from 'app/entities/personal-medico/service/personal-medico.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';

@Component({
  selector: 'jhi-consulta-medica-update',
  templateUrl: './consulta-medica-update.component.html',
})
export class ConsultaMedicaUpdateComponent implements OnInit {
  isSaving = false;

  personalMedicosSharedCollection: IPersonalMedico[] = [];
  pacientesSharedCollection: IPaciente[] = [];

  editForm = this.fb.group({
    id: [],
    diagnostico: [null, [Validators.required]],
    tratamientoSugerido: [],
    observaciones: [],
    fechaConsulta: [null, [Validators.required]],
    personalMedico: [],
    paciente: [],
  });

  constructor(
    protected consultaMedicaService: ConsultaMedicaService,
    protected personalMedicoService: PersonalMedicoService,
    protected pacienteService: PacienteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consultaMedica }) => {
      if (consultaMedica.id === undefined) {
        const today = dayjs().startOf('day');
        consultaMedica.fechaConsulta = today;
      }

      this.updateForm(consultaMedica);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consultaMedica = this.createFromForm();
    if (consultaMedica.id !== undefined) {
      this.subscribeToSaveResponse(this.consultaMedicaService.update(consultaMedica));
    } else {
      this.subscribeToSaveResponse(this.consultaMedicaService.create(consultaMedica));
    }
  }

  trackPersonalMedicoById(_index: number, item: IPersonalMedico): number {
    return item.id!;
  }

  trackPacienteById(_index: number, item: IPaciente): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsultaMedica>>): void {
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

  protected updateForm(consultaMedica: IConsultaMedica): void {
    this.editForm.patchValue({
      id: consultaMedica.id,
      diagnostico: consultaMedica.diagnostico,
      tratamientoSugerido: consultaMedica.tratamientoSugerido,
      observaciones: consultaMedica.observaciones,
      fechaConsulta: consultaMedica.fechaConsulta ? consultaMedica.fechaConsulta.format(DATE_TIME_FORMAT) : null,
      personalMedico: consultaMedica.personalMedico,
      paciente: consultaMedica.paciente,
    });

    this.personalMedicosSharedCollection = this.personalMedicoService.addPersonalMedicoToCollectionIfMissing(
      this.personalMedicosSharedCollection,
      consultaMedica.personalMedico
    );
    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing(
      this.pacientesSharedCollection,
      consultaMedica.paciente
    );
  }

  protected loadRelationshipsOptions(): void {
    this.personalMedicoService
      .query()
      .pipe(map((res: HttpResponse<IPersonalMedico[]>) => res.body ?? []))
      .pipe(
        map((personalMedicos: IPersonalMedico[]) =>
          this.personalMedicoService.addPersonalMedicoToCollectionIfMissing(personalMedicos, this.editForm.get('personalMedico')!.value)
        )
      )
      .subscribe((personalMedicos: IPersonalMedico[]) => (this.personalMedicosSharedCollection = personalMedicos));

    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) =>
          this.pacienteService.addPacienteToCollectionIfMissing(pacientes, this.editForm.get('paciente')!.value)
        )
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));
  }

  protected createFromForm(): IConsultaMedica {
    return {
      ...new ConsultaMedica(),
      id: this.editForm.get(['id'])!.value,
      diagnostico: this.editForm.get(['diagnostico'])!.value,
      tratamientoSugerido: this.editForm.get(['tratamientoSugerido'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      fechaConsulta: this.editForm.get(['fechaConsulta'])!.value
        ? dayjs(this.editForm.get(['fechaConsulta'])!.value, DATE_TIME_FORMAT)
        : undefined,
      personalMedico: this.editForm.get(['personalMedico'])!.value,
      paciente: this.editForm.get(['paciente'])!.value,
    };
  }
}
