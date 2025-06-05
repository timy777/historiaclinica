import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CitaMedicaComponent } from '../list/cita-medica.component';
import { CitaMedicaDetailComponent } from '../detail/cita-medica-detail.component';
import { CitaMedicaUpdateComponent } from '../update/cita-medica-update.component';
import { CitaMedicaRoutingResolveService } from './cita-medica-routing-resolve.service';

const citaMedicaRoute: Routes = [
  {
    path: '',
    component: CitaMedicaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CitaMedicaDetailComponent,
    resolve: {
      citaMedica: CitaMedicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CitaMedicaUpdateComponent,
    resolve: {
      citaMedica: CitaMedicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CitaMedicaUpdateComponent,
    resolve: {
      citaMedica: CitaMedicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(citaMedicaRoute)],
  exports: [RouterModule],
})
export class CitaMedicaRoutingModule {}
