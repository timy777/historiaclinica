import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEstudioMedico, EstudioMedico } from '../estudio-medico.model';
import { EstudioMedicoService } from '../service/estudio-medico.service';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { ConsultaMedicaService } from 'app/entities/consulta-medica/service/consulta-medica.service';

@Component({
  selector: 'jhi-estudio-medico-update',
  templateUrl: './estudio-medico-update.component.html',
})
export class EstudioMedicoUpdateComponent implements OnInit {
  isSaving = false;

  consultaMedicasSharedCollection: IConsultaMedica[] = [];

  editForm = this.fb.group({
    id: [],
    tipoEstudio: [null, [Validators.required]],
    resultado: [],
    fechaRealizacion: [null, [Validators.required]],
    consultaMedica: [],
  });

  constructor(
    protected estudioMedicoService: EstudioMedicoService,
    protected consultaMedicaService: ConsultaMedicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estudioMedico }) => {
      if (estudioMedico.id === undefined) {
        const today = dayjs().startOf('day');
        estudioMedico.fechaRealizacion = today;
      }

      this.updateForm(estudioMedico);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estudioMedico = this.createFromForm();
    if (estudioMedico.id !== undefined) {
      this.subscribeToSaveResponse(this.estudioMedicoService.update(estudioMedico));
    } else {
      this.subscribeToSaveResponse(this.estudioMedicoService.create(estudioMedico));
    }
  }

  trackConsultaMedicaById(_index: number, item: IConsultaMedica): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstudioMedico>>): void {
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

  protected updateForm(estudioMedico: IEstudioMedico): void {
    this.editForm.patchValue({
      id: estudioMedico.id,
      tipoEstudio: estudioMedico.tipoEstudio,
      resultado: estudioMedico.resultado,
      fechaRealizacion: estudioMedico.fechaRealizacion ? estudioMedico.fechaRealizacion.format(DATE_TIME_FORMAT) : null,
      consultaMedica: estudioMedico.consultaMedica,
    });

    this.consultaMedicasSharedCollection = this.consultaMedicaService.addConsultaMedicaToCollectionIfMissing(
      this.consultaMedicasSharedCollection,
      estudioMedico.consultaMedica
    );
  }

  protected loadRelationshipsOptions(): void {
    this.consultaMedicaService
      .query()
      .pipe(map((res: HttpResponse<IConsultaMedica[]>) => res.body ?? []))
      .pipe(
        map((consultaMedicas: IConsultaMedica[]) =>
          this.consultaMedicaService.addConsultaMedicaToCollectionIfMissing(consultaMedicas, this.editForm.get('consultaMedica')!.value)
        )
      )
      .subscribe((consultaMedicas: IConsultaMedica[]) => (this.consultaMedicasSharedCollection = consultaMedicas));
  }

  protected createFromForm(): IEstudioMedico {
    return {
      ...new EstudioMedico(),
      id: this.editForm.get(['id'])!.value,
      tipoEstudio: this.editForm.get(['tipoEstudio'])!.value,
      resultado: this.editForm.get(['resultado'])!.value,
      fechaRealizacion: this.editForm.get(['fechaRealizacion'])!.value
        ? dayjs(this.editForm.get(['fechaRealizacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      consultaMedica: this.editForm.get(['consultaMedica'])!.value,
    };
  }
}
