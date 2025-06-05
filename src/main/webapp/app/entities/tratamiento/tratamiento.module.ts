import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TratamientoComponent } from './list/tratamiento.component';
import { TratamientoDetailComponent } from './detail/tratamiento-detail.component';
import { TratamientoUpdateComponent } from './update/tratamiento-update.component';
import { TratamientoDeleteDialogComponent } from './delete/tratamiento-delete-dialog.component';
import { TratamientoRoutingModule } from './route/tratamiento-routing.module';

@NgModule({
  imports: [SharedModule, TratamientoRoutingModule],
  declarations: [TratamientoComponent, TratamientoDetailComponent, TratamientoUpdateComponent, TratamientoDeleteDialogComponent],
  entryComponents: [TratamientoDeleteDialogComponent],
})
export class TratamientoModule {}
