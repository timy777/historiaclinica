import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISalaMedica, SalaMedica } from '../sala-medica.model';
import { SalaMedicaService } from '../service/sala-medica.service';

@Component({
  selector: 'jhi-sala-medica-update',
  templateUrl: './sala-medica-update.component.html',
})
export class SalaMedicaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nroSala: [null, [Validators.required]],
    nombre: [null, [Validators.required]],
    ubicacion: [],
    equipamiento: [],
  });

  constructor(protected salaMedicaService: SalaMedicaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salaMedica }) => {
      this.updateForm(salaMedica);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salaMedica = this.createFromForm();
    if (salaMedica.id !== undefined) {
      this.subscribeToSaveResponse(this.salaMedicaService.update(salaMedica));
    } else {
      this.subscribeToSaveResponse(this.salaMedicaService.create(salaMedica));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalaMedica>>): void {
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

  protected updateForm(salaMedica: ISalaMedica): void {
    this.editForm.patchValue({
      id: salaMedica.id,
      nroSala: salaMedica.nroSala,
      nombre: salaMedica.nombre,
      ubicacion: salaMedica.ubicacion,
      equipamiento: salaMedica.equipamiento,
    });
  }

  protected createFromForm(): ISalaMedica {
    return {
      ...new SalaMedica(),
      id: this.editForm.get(['id'])!.value,
      nroSala: this.editForm.get(['nroSala'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      ubicacion: this.editForm.get(['ubicacion'])!.value,
      equipamiento: this.editForm.get(['equipamiento'])!.value,
    };
  }
}
