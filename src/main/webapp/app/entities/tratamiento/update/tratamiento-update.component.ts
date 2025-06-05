import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITratamiento, Tratamiento } from '../tratamiento.model';
import { TratamientoService } from '../service/tratamiento.service';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { ConsultaMedicaService } from 'app/entities/consulta-medica/service/consulta-medica.service';

@Component({
  selector: 'jhi-tratamiento-update',
  templateUrl: './tratamiento-update.component.html',
})
export class TratamientoUpdateComponent implements OnInit {
  isSaving = false;

  consultaMedicasSharedCollection: IConsultaMedica[] = [];

  editForm = this.fb.group({
    id: [],
    tipoTratamiento: [null, [Validators.required]],
    duracion: [],
    objetivo: [],
    consultaMedica: [],
  });

  constructor(
    protected tratamientoService: TratamientoService,
    protected consultaMedicaService: ConsultaMedicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tratamiento }) => {
      this.updateForm(tratamiento);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tratamiento = this.createFromForm();
    if (tratamiento.id !== undefined) {
      this.subscribeToSaveResponse(this.tratamientoService.update(tratamiento));
    } else {
      this.subscribeToSaveResponse(this.tratamientoService.create(tratamiento));
    }
  }

  trackConsultaMedicaById(_index: number, item: IConsultaMedica): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITratamiento>>): void {
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

  protected updateForm(tratamiento: ITratamiento): void {
    this.editForm.patchValue({
      id: tratamiento.id,
      tipoTratamiento: tratamiento.tipoTratamiento,
      duracion: tratamiento.duracion,
      objetivo: tratamiento.objetivo,
      consultaMedica: tratamiento.consultaMedica,
    });

    this.consultaMedicasSharedCollection = this.consultaMedicaService.addConsultaMedicaToCollectionIfMissing(
      this.consultaMedicasSharedCollection,
      tratamiento.consultaMedica
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

  protected createFromForm(): ITratamiento {
    return {
      ...new Tratamiento(),
      id: this.editForm.get(['id'])!.value,
      tipoTratamiento: this.editForm.get(['tipoTratamiento'])!.value,
      duracion: this.editForm.get(['duracion'])!.value,
      objetivo: this.editForm.get(['objetivo'])!.value,
      consultaMedica: this.editForm.get(['consultaMedica'])!.value,
    };
  }
}
