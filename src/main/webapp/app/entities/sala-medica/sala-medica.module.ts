import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SalaMedicaComponent } from './list/sala-medica.component';
import { SalaMedicaDetailComponent } from './detail/sala-medica-detail.component';
import { SalaMedicaUpdateComponent } from './update/sala-medica-update.component';
import { SalaMedicaDeleteDialogComponent } from './delete/sala-medica-delete-dialog.component';
import { SalaMedicaRoutingModule } from './route/sala-medica-routing.module';

@NgModule({
  imports: [SharedModule, SalaMedicaRoutingModule],
  declarations: [SalaMedicaComponent, SalaMedicaDetailComponent, SalaMedicaUpdateComponent, SalaMedicaDeleteDialogComponent],
  entryComponents: [SalaMedicaDeleteDialogComponent],
})
export class SalaMedicaModule {}
