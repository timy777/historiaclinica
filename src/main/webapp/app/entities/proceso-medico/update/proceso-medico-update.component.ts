import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProcesoMedico, ProcesoMedico } from '../proceso-medico.model';
import { ProcesoMedicoService } from '../service/proceso-medico.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';
import { PersonalMedicoService } from 'app/entities/personal-medico/service/personal-medico.service';
import { ISalaMedica } from 'app/entities/sala-medica/sala-medica.model';
import { SalaMedicaService } from 'app/entities/sala-medica/service/sala-medica.service';

@Component({
  selector: 'jhi-proceso-medico-update',
  templateUrl: './proceso-medico-update.component.html',
})
export class ProcesoMedicoUpdateComponent implements OnInit {
  isSaving = false;

  pacientesSharedCollection: IPaciente[] = [];
  personalMedicosSharedCollection: IPersonalMedico[] = [];
  salaMedicasSharedCollection: ISalaMedica[] = [];

  editForm = this.fb.group({
    id: [],
    tipoProceso: [null, [Validators.required]],
    fechaInicio: [null, [Validators.required]],
    fechaFin: [],
    estado: [null, [Validators.required]],
    hashBlockchain: [],
    paciente: [],
    personalMedico: [],
    salaMedica: [],
  });

  constructor(
    protected procesoMedicoService: ProcesoMedicoService,
    protected pacienteService: PacienteService,
    protected personalMedicoService: PersonalMedicoService,
    protected salaMedicaService: SalaMedicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ procesoMedico }) => {
      if (procesoMedico.id === undefined) {
        const today = dayjs().startOf('day');
        procesoMedico.fechaInicio = today;
        procesoMedico.fechaFin = today;
      }

      this.updateForm(procesoMedico);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const procesoMedico = this.createFromForm();
    if (procesoMedico.id !== undefined) {
      this.subscribeToSaveResponse(this.procesoMedicoService.update(procesoMedico));
    } else {
      this.subscribeToSaveResponse(this.procesoMedicoService.create(procesoMedico));
    }
  }

  trackPacienteById(_index: number, item: IPaciente): number {
    return item.id!;
  }

  trackPersonalMedicoById(_index: number, item: IPersonalMedico): number {
    return item.id!;
  }

  trackSalaMedicaById(_index: number, item: ISalaMedica): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcesoMedico>>): void {
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

  protected updateForm(procesoMedico: IProcesoMedico): void {
    this.editForm.patchValue({
      id: procesoMedico.id,
      tipoProceso: procesoMedico.tipoProceso,
      fechaInicio: procesoMedico.fechaInicio ? procesoMedico.fechaInicio.format(DATE_TIME_FORMAT) : null,
      fechaFin: procesoMedico.fechaFin ? procesoMedico.fechaFin.format(DATE_TIME_FORMAT) : null,
      estado: procesoMedico.estado,
      hashBlockchain: procesoMedico.hashBlockchain,
      paciente: procesoMedico.paciente,
      personalMedico: procesoMedico.personalMedico,
      salaMedica: procesoMedico.salaMedica,
    });

    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing(
      this.pacientesSharedCollection,
      procesoMedico.paciente
    );
    this.personalMedicosSharedCollection = this.personalMedicoService.addPersonalMedicoToCollectionIfMissing(
      this.personalMedicosSharedCollection,
      procesoMedico.personalMedico
    );
    this.salaMedicasSharedCollection = this.salaMedicaService.addSalaMedicaToCollectionIfMissing(
      this.salaMedicasSharedCollection,
      procesoMedico.salaMedica
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) =>
          this.pacienteService.addPacienteToCollectionIfMissing(pacientes, this.editForm.get('paciente')!.value)
        )
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));

    this.personalMedicoService
      .query()
      .pipe(map((res: HttpResponse<IPersonalMedico[]>) => res.body ?? []))
      .pipe(
        map((personalMedicos: IPersonalMedico[]) =>
          this.personalMedicoService.addPersonalMedicoToCollectionIfMissing(personalMedicos, this.editForm.get('personalMedico')!.value)
        )
      )
      .subscribe((personalMedicos: IPersonalMedico[]) => (this.personalMedicosSharedCollection = personalMedicos));

    this.salaMedicaService
      .query()
      .pipe(map((res: HttpResponse<ISalaMedica[]>) => res.body ?? []))
      .pipe(
        map((salaMedicas: ISalaMedica[]) =>
          this.salaMedicaService.addSalaMedicaToCollectionIfMissing(salaMedicas, this.editForm.get('salaMedica')!.value)
        )
      )
      .subscribe((salaMedicas: ISalaMedica[]) => (this.salaMedicasSharedCollection = salaMedicas));
  }

  protected createFromForm(): IProcesoMedico {
    return {
      ...new ProcesoMedico(),
      id: this.editForm.get(['id'])!.value,
      tipoProceso: this.editForm.get(['tipoProceso'])!.value,
      fechaInicio: this.editForm.get(['fechaInicio'])!.value
        ? dayjs(this.editForm.get(['fechaInicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaFin: this.editForm.get(['fechaFin'])!.value ? dayjs(this.editForm.get(['fechaFin'])!.value, DATE_TIME_FORMAT) : undefined,
      estado: this.editForm.get(['estado'])!.value,
      hashBlockchain: this.editForm.get(['hashBlockchain'])!.value,
      paciente: this.editForm.get(['paciente'])!.value,
      personalMedico: this.editForm.get(['personalMedico'])!.value,
      salaMedica: this.editForm.get(['salaMedica'])!.value,
    };
  }
}
