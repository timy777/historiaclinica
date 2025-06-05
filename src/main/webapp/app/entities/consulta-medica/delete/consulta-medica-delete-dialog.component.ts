import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConsultaMedica } from '../consulta-medica.model';
import { ConsultaMedicaService } from '../service/consulta-medica.service';

@Component({
  templateUrl: './consulta-medica-delete-dialog.component.html',
})
export class ConsultaMedicaDeleteDialogComponent {
  consultaMedica?: IConsultaMedica;

  constructor(protected consultaMedicaService: ConsultaMedicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.consultaMedicaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
