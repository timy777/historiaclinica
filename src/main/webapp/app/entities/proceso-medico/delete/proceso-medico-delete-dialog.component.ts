import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProcesoMedico } from '../proceso-medico.model';
import { ProcesoMedicoService } from '../service/proceso-medico.service';

@Component({
  templateUrl: './proceso-medico-delete-dialog.component.html',
})
export class ProcesoMedicoDeleteDialogComponent {
  procesoMedico?: IProcesoMedico;

  constructor(protected procesoMedicoService: ProcesoMedicoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.procesoMedicoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
