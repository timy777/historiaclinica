import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CitaMedicaComponent } from './list/cita-medica.component';
import { CitaMedicaDetailComponent } from './detail/cita-medica-detail.component';
import { CitaMedicaUpdateComponent } from './update/cita-medica-update.component';
import { CitaMedicaDeleteDialogComponent } from './delete/cita-medica-delete-dialog.component';
import { CitaMedicaRoutingModule } from './route/cita-medica-routing.module';

@NgModule({
  imports: [SharedModule, CitaMedicaRoutingModule],
  declarations: [CitaMedicaComponent, CitaMedicaDetailComponent, CitaMedicaUpdateComponent, CitaMedicaDeleteDialogComponent],
  entryComponents: [CitaMedicaDeleteDialogComponent],
})
export class CitaMedicaModule {}
