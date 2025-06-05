import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MedicacionComponent } from './list/medicacion.component';
import { MedicacionDetailComponent } from './detail/medicacion-detail.component';
import { MedicacionUpdateComponent } from './update/medicacion-update.component';
import { MedicacionDeleteDialogComponent } from './delete/medicacion-delete-dialog.component';
import { MedicacionRoutingModule } from './route/medicacion-routing.module';

@NgModule({
  imports: [SharedModule, MedicacionRoutingModule],
  declarations: [MedicacionComponent, MedicacionDetailComponent, MedicacionUpdateComponent, MedicacionDeleteDialogComponent],
  entryComponents: [MedicacionDeleteDialogComponent],
})
export class MedicacionModule {}
