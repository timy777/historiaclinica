import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProcesoMedico, ProcesoMedico } from '../proceso-medico.model';
import { ProcesoMedicoService } from '../service/proceso-medico.service';

@Component({
  selector: 'jhi-proceso-medico-update',
  templateUrl: './proceso-medico-update.component.html',
})
export class ProcesoMedicoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipoProceso: [null, [Validators.required]],
    fechaInicio: [null, [Validators.required]],
    fechaFin: [],
    estado: [null, [Validators.required]],
  });

  constructor(protected procesoMedicoService: ProcesoMedicoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ procesoMedico }) => {
      if (procesoMedico.id === undefined) {
        const today = dayjs().startOf('day');
        procesoMedico.fechaInicio = today;
        procesoMedico.fechaFin = today;
      }

      this.updateForm(procesoMedico);
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
    });
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
    };
  }
}
