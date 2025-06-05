import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EvaluacionFisicaComponent } from '../list/evaluacion-fisica.component';
import { EvaluacionFisicaDetailComponent } from '../detail/evaluacion-fisica-detail.component';
import { EvaluacionFisicaUpdateComponent } from '../update/evaluacion-fisica-update.component';
import { EvaluacionFisicaRoutingResolveService } from './evaluacion-fisica-routing-resolve.service';

const evaluacionFisicaRoute: Routes = [
  {
    path: '',
    component: EvaluacionFisicaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EvaluacionFisicaDetailComponent,
    resolve: {
      evaluacionFisica: EvaluacionFisicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EvaluacionFisicaUpdateComponent,
    resolve: {
      evaluacionFisica: EvaluacionFisicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EvaluacionFisicaUpdateComponent,
    resolve: {
      evaluacionFisica: EvaluacionFisicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(evaluacionFisicaRoute)],
  exports: [RouterModule],
})
export class EvaluacionFisicaRoutingModule {}
