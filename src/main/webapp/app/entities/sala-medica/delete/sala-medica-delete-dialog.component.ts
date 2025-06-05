import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalaMedica } from '../sala-medica.model';
import { SalaMedicaService } from '../service/sala-medica.service';

@Component({
  templateUrl: './sala-medica-delete-dialog.component.html',
})
export class SalaMedicaDeleteDialogComponent {
  salaMedica?: ISalaMedica;

  constructor(protected salaMedicaService: SalaMedicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salaMedicaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
