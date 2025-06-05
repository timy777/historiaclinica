import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEvaluacionFisica, EvaluacionFisica } from '../evaluacion-fisica.model';
import { EvaluacionFisicaService } from '../service/evaluacion-fisica.service';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { ConsultaMedicaService } from 'app/entities/consulta-medica/service/consulta-medica.service';

@Component({
  selector: 'jhi-evaluacion-fisica-update',
  templateUrl: './evaluacion-fisica-update.component.html',
})
export class EvaluacionFisicaUpdateComponent implements OnInit {
  isSaving = false;

  consultaMedicasSharedCollection: IConsultaMedica[] = [];

  editForm = this.fb.group({
    id: [],
    presionArterial: [],
    temperatura: [],
    ritmoCardiaco: [],
    frecuenciaRespiratoria: [],
    peso: [],
    altura: [],
    consultaMedica: [],
  });

  constructor(
    protected evaluacionFisicaService: EvaluacionFisicaService,
    protected consultaMedicaService: ConsultaMedicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluacionFisica }) => {
      this.updateForm(evaluacionFisica);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evaluacionFisica = this.createFromForm();
    if (evaluacionFisica.id !== undefined) {
      this.subscribeToSaveResponse(this.evaluacionFisicaService.update(evaluacionFisica));
    } else {
      this.subscribeToSaveResponse(this.evaluacionFisicaService.create(evaluacionFisica));
    }
  }

  trackConsultaMedicaById(_index: number, item: IConsultaMedica): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluacionFisica>>): void {
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

  protected updateForm(evaluacionFisica: IEvaluacionFisica): void {
    this.editForm.patchValue({
      id: evaluacionFisica.id,
      presionArterial: evaluacionFisica.presionArterial,
      temperatura: evaluacionFisica.temperatura,
      ritmoCardiaco: evaluacionFisica.ritmoCardiaco,
      frecuenciaRespiratoria: evaluacionFisica.frecuenciaRespiratoria,
      peso: evaluacionFisica.peso,
      altura: evaluacionFisica.altura,
      consultaMedica: evaluacionFisica.consultaMedica,
    });

    this.consultaMedicasSharedCollection = this.consultaMedicaService.addConsultaMedicaToCollectionIfMissing(
      this.consultaMedicasSharedCollection,
      evaluacionFisica.consultaMedica
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

  protected createFromForm(): IEvaluacionFisica {
    return {
      ...new EvaluacionFisica(),
      id: this.editForm.get(['id'])!.value,
      presionArterial: this.editForm.get(['presionArterial'])!.value,
      temperatura: this.editForm.get(['temperatura'])!.value,
      ritmoCardiaco: this.editForm.get(['ritmoCardiaco'])!.value,
      frecuenciaRespiratoria: this.editForm.get(['frecuenciaRespiratoria'])!.value,
      peso: this.editForm.get(['peso'])!.value,
      altura: this.editForm.get(['altura'])!.value,
      consultaMedica: this.editForm.get(['consultaMedica'])!.value,
    };
  }
}
