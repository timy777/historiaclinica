import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstudioMedico } from '../estudio-medico.model';
import { EstudioMedicoService } from '../service/estudio-medico.service';

@Component({
  templateUrl: './estudio-medico-delete-dialog.component.html',
})
export class EstudioMedicoDeleteDialogComponent {
  estudioMedico?: IEstudioMedico;

  constructor(protected estudioMedicoService: EstudioMedicoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estudioMedicoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
