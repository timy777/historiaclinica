import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonalMedicoComponent } from './list/personal-medico.component';
import { PersonalMedicoDetailComponent } from './detail/personal-medico-detail.component';
import { PersonalMedicoUpdateComponent } from './update/personal-medico-update.component';
import { PersonalMedicoDeleteDialogComponent } from './delete/personal-medico-delete-dialog.component';
import { PersonalMedicoRoutingModule } from './route/personal-medico-routing.module';

@NgModule({
  imports: [SharedModule, PersonalMedicoRoutingModule],
  declarations: [
    PersonalMedicoComponent,
    PersonalMedicoDetailComponent,
    PersonalMedicoUpdateComponent,
    PersonalMedicoDeleteDialogComponent,
  ],
  entryComponents: [PersonalMedicoDeleteDialogComponent],
})
export class PersonalMedicoModule {}
