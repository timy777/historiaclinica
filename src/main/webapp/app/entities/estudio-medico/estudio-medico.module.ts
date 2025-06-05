import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EstudioMedicoComponent } from './list/estudio-medico.component';
import { EstudioMedicoDetailComponent } from './detail/estudio-medico-detail.component';
import { EstudioMedicoUpdateComponent } from './update/estudio-medico-update.component';
import { EstudioMedicoDeleteDialogComponent } from './delete/estudio-medico-delete-dialog.component';
import { EstudioMedicoRoutingModule } from './route/estudio-medico-routing.module';

@NgModule({
  imports: [SharedModule, EstudioMedicoRoutingModule],
  declarations: [EstudioMedicoComponent, EstudioMedicoDetailComponent, EstudioMedicoUpdateComponent, EstudioMedicoDeleteDialogComponent],
  entryComponents: [EstudioMedicoDeleteDialogComponent],
})
export class EstudioMedicoModule {}
