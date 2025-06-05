import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITratamiento } from '../tratamiento.model';
import { TratamientoService } from '../service/tratamiento.service';

@Component({
  templateUrl: './tratamiento-delete-dialog.component.html',
})
export class TratamientoDeleteDialogComponent {
  tratamiento?: ITratamiento;

  constructor(protected tratamientoService: TratamientoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tratamientoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
