import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EvaluacionFisicaComponent } from './list/evaluacion-fisica.component';
import { EvaluacionFisicaDetailComponent } from './detail/evaluacion-fisica-detail.component';
import { EvaluacionFisicaUpdateComponent } from './update/evaluacion-fisica-update.component';
import { EvaluacionFisicaDeleteDialogComponent } from './delete/evaluacion-fisica-delete-dialog.component';
import { EvaluacionFisicaRoutingModule } from './route/evaluacion-fisica-routing.module';

@NgModule({
  imports: [SharedModule, EvaluacionFisicaRoutingModule],
  declarations: [
    EvaluacionFisicaComponent,
    EvaluacionFisicaDetailComponent,
    EvaluacionFisicaUpdateComponent,
    EvaluacionFisicaDeleteDialogComponent,
  ],
  entryComponents: [EvaluacionFisicaDeleteDialogComponent],
})
export class EvaluacionFisicaModule {}
