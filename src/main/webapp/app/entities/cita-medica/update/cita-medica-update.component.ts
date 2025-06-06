import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICitaMedica, CitaMedica } from '../cita-medica.model';
import { CitaMedicaService } from '../service/cita-medica.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IPersonalMedico } from 'app/entities/personal-medico/personal-medico.model';
import { PersonalMedicoService } from 'app/entities/personal-medico/service/personal-medico.service';

@Component({
  selector: 'jhi-cita-medica-update',
  templateUrl: './cita-medica-update.component.html',
})
export class CitaMedicaUpdateComponent implements OnInit {
  isSaving = false;

  pacientesSharedCollection: IPaciente[] = [];
  personalMedicosSharedCollection: IPersonalMedico[] = [];

  editForm = this.fb.group({
    id: [],
    fechaCita: [null, [Validators.required]],
    horaCita: [null, [Validators.required]],
    motivo: [],
    estado: [null, [Validators.required]],
    paciente: [],
    personalMedico: [],
  });

  constructor(
    protected citaMedicaService: CitaMedicaService,
    protected pacienteService: PacienteService,
    protected personalMedicoService: PersonalMedicoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ citaMedica }) => {
      this.updateForm(citaMedica);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const citaMedica = this.createFromForm();
    if (citaMedica.id !== undefined) {
      this.subscribeToSaveResponse(this.citaMedicaService.update(citaMedica));
    } else {
      this.subscribeToSaveResponse(this.citaMedicaService.create(citaMedica));
    }
  }

  trackPacienteById(_index: number, item: IPaciente): number {
    return item.id!;
  }

  trackPersonalMedicoById(_index: number, item: IPersonalMedico): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICitaMedica>>): void {
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

  protected updateForm(citaMedica: ICitaMedica): void {
    this.editForm.patchValue({
      id: citaMedica.id,
      fechaCita: citaMedica.fechaCita,
      horaCita: citaMedica.horaCita,
      motivo: citaMedica.motivo,
      estado: citaMedica.estado,
      paciente: citaMedica.paciente,
      personalMedico: citaMedica.personalMedico,
    });

    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing(
      this.pacientesSharedCollection,
      citaMedica.paciente
    );
    this.personalMedicosSharedCollection = this.personalMedicoService.addPersonalMedicoToCollectionIfMissing(
      this.personalMedicosSharedCollection,
      citaMedica.personalMedico
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
  }

  protected createFromForm(): ICitaMedica {
    return {
      ...new CitaMedica(),
      id: this.editForm.get(['id'])!.value,
      fechaCita: this.editForm.get(['fechaCita'])!.value,
      horaCita: this.editForm.get(['horaCita'])!.value,
      motivo: this.editForm.get(['motivo'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      paciente: this.editForm.get(['paciente'])!.value,
      personalMedico: this.editForm.get(['personalMedico'])!.value,
    };
  }
}
