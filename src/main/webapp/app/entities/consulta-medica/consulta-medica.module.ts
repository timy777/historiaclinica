import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ConsultaMedicaComponent } from './list/consulta-medica.component';
import { ConsultaMedicaDetailComponent } from './detail/consulta-medica-detail.component';
import { ConsultaMedicaUpdateComponent } from './update/consulta-medica-update.component';
import { ConsultaMedicaDeleteDialogComponent } from './delete/consulta-medica-delete-dialog.component';
import { ConsultaMedicaRoutingModule } from './route/consulta-medica-routing.module';

@NgModule({
  imports: [SharedModule, ConsultaMedicaRoutingModule],
  declarations: [
    ConsultaMedicaComponent,
    ConsultaMedicaDetailComponent,
    ConsultaMedicaUpdateComponent,
    ConsultaMedicaDeleteDialogComponent,
  ],
  entryComponents: [ConsultaMedicaDeleteDialogComponent],
})
export class ConsultaMedicaModule {}
