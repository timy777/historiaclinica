import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConsultaMedicaComponent } from '../list/consulta-medica.component';
import { ConsultaMedicaDetailComponent } from '../detail/consulta-medica-detail.component';
import { ConsultaMedicaUpdateComponent } from '../update/consulta-medica-update.component';
import { ConsultaMedicaRoutingResolveService } from './consulta-medica-routing-resolve.service';

const consultaMedicaRoute: Routes = [
  {
    path: '',
    component: ConsultaMedicaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConsultaMedicaDetailComponent,
    resolve: {
      consultaMedica: ConsultaMedicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConsultaMedicaUpdateComponent,
    resolve: {
      consultaMedica: ConsultaMedicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConsultaMedicaUpdateComponent,
    resolve: {
      consultaMedica: ConsultaMedicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(consultaMedicaRoute)],
  exports: [RouterModule],
})
export class ConsultaMedicaRoutingModule {}
