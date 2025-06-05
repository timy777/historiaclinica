import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicacion } from '../medicacion.model';
import { MedicacionService } from '../service/medicacion.service';

@Component({
  templateUrl: './medicacion-delete-dialog.component.html',
})
export class MedicacionDeleteDialogComponent {
  medicacion?: IMedicacion;

  constructor(protected medicacionService: MedicacionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medicacionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
