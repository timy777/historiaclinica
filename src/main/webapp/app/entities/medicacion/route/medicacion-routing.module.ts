import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MedicacionComponent } from '../list/medicacion.component';
import { MedicacionDetailComponent } from '../detail/medicacion-detail.component';
import { MedicacionUpdateComponent } from '../update/medicacion-update.component';
import { MedicacionRoutingResolveService } from './medicacion-routing-resolve.service';

const medicacionRoute: Routes = [
  {
    path: '',
    component: MedicacionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MedicacionDetailComponent,
    resolve: {
      medicacion: MedicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MedicacionUpdateComponent,
    resolve: {
      medicacion: MedicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MedicacionUpdateComponent,
    resolve: {
      medicacion: MedicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(medicacionRoute)],
  exports: [RouterModule],
})
export class MedicacionRoutingModule {}
