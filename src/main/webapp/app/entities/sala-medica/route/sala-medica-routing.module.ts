import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SalaMedicaComponent } from '../list/sala-medica.component';
import { SalaMedicaDetailComponent } from '../detail/sala-medica-detail.component';
import { SalaMedicaUpdateComponent } from '../update/sala-medica-update.component';
import { SalaMedicaRoutingResolveService } from './sala-medica-routing-resolve.service';

const salaMedicaRoute: Routes = [
  {
    path: '',
    component: SalaMedicaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SalaMedicaDetailComponent,
    resolve: {
      salaMedica: SalaMedicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SalaMedicaUpdateComponent,
    resolve: {
      salaMedica: SalaMedicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SalaMedicaUpdateComponent,
    resolve: {
      salaMedica: SalaMedicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(salaMedicaRoute)],
  exports: [RouterModule],
})
export class SalaMedicaRoutingModule {}
