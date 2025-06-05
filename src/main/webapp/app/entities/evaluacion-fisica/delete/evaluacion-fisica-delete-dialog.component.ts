import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEvaluacionFisica } from '../evaluacion-fisica.model';
import { EvaluacionFisicaService } from '../service/evaluacion-fisica.service';

@Component({
  templateUrl: './evaluacion-fisica-delete-dialog.component.html',
})
export class EvaluacionFisicaDeleteDialogComponent {
  evaluacionFisica?: IEvaluacionFisica;

  constructor(protected evaluacionFisicaService: EvaluacionFisicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evaluacionFisicaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
