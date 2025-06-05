import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProcesoMedicoComponent } from './list/proceso-medico.component';
import { ProcesoMedicoDetailComponent } from './detail/proceso-medico-detail.component';
import { ProcesoMedicoUpdateComponent } from './update/proceso-medico-update.component';
import { ProcesoMedicoDeleteDialogComponent } from './delete/proceso-medico-delete-dialog.component';
import { ProcesoMedicoRoutingModule } from './route/proceso-medico-routing.module';

@NgModule({
  imports: [SharedModule, ProcesoMedicoRoutingModule],
  declarations: [ProcesoMedicoComponent, ProcesoMedicoDetailComponent, ProcesoMedicoUpdateComponent, ProcesoMedicoDeleteDialogComponent],
  entryComponents: [ProcesoMedicoDeleteDialogComponent],
})
export class ProcesoMedicoModule {}
