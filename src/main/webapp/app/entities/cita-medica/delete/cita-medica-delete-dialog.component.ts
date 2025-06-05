import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICitaMedica } from '../cita-medica.model';
import { CitaMedicaService } from '../service/cita-medica.service';

@Component({
  templateUrl: './cita-medica-delete-dialog.component.html',
})
export class CitaMedicaDeleteDialogComponent {
  citaMedica?: ICitaMedica;

  constructor(protected citaMedicaService: CitaMedicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.citaMedicaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
