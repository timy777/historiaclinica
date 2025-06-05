import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMedicacion, Medicacion } from '../medicacion.model';
import { MedicacionService } from '../service/medicacion.service';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { ConsultaMedicaService } from 'app/entities/consulta-medica/service/consulta-medica.service';

@Component({
  selector: 'jhi-medicacion-update',
  templateUrl: './medicacion-update.component.html',
})
export class MedicacionUpdateComponent implements OnInit {
  isSaving = false;

  consultaMedicasSharedCollection: IConsultaMedica[] = [];

  editForm = this.fb.group({
    id: [],
    nombreMedicamento: [null, [Validators.required]],
    dosis: [],
    frecuencia: [],
    viaAdministracion: [],
    consultaMedica: [],
  });

  constructor(
    protected medicacionService: MedicacionService,
    protected consultaMedicaService: ConsultaMedicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicacion }) => {
      this.updateForm(medicacion);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicacion = this.createFromForm();
    if (medicacion.id !== undefined) {
      this.subscribeToSaveResponse(this.medicacionService.update(medicacion));
    } else {
      this.subscribeToSaveResponse(this.medicacionService.create(medicacion));
    }
  }

  trackConsultaMedicaById(_index: number, item: IConsultaMedica): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicacion>>): void {
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

  protected updateForm(medicacion: IMedicacion): void {
    this.editForm.patchValue({
      id: medicacion.id,
      nombreMedicamento: medicacion.nombreMedicamento,
      dosis: medicacion.dosis,
      frecuencia: medicacion.frecuencia,
      viaAdministracion: medicacion.viaAdministracion,
      consultaMedica: medicacion.consultaMedica,
    });

    this.consultaMedicasSharedCollection = this.consultaMedicaService.addConsultaMedicaToCollectionIfMissing(
      this.consultaMedicasSharedCollection,
      medicacion.consultaMedica
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

  protected createFromForm(): IMedicacion {
    return {
      ...new Medicacion(),
      id: this.editForm.get(['id'])!.value,
      nombreMedicamento: this.editForm.get(['nombreMedicamento'])!.value,
      dosis: this.editForm.get(['dosis'])!.value,
      frecuencia: this.editForm.get(['frecuencia'])!.value,
      viaAdministracion: this.editForm.get(['viaAdministracion'])!.value,
      consultaMedica: this.editForm.get(['consultaMedica'])!.value,
    };
  }
}
