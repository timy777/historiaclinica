import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonalMedico } from '../personal-medico.model';
import { PersonalMedicoService } from '../service/personal-medico.service';

@Component({
  templateUrl: './personal-medico-delete-dialog.component.html',
})
export class PersonalMedicoDeleteDialogComponent {
  personalMedico?: IPersonalMedico;

  constructor(protected personalMedicoService: PersonalMedicoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personalMedicoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
