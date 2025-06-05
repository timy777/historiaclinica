import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TratamientoComponent } from '../list/tratamiento.component';
import { TratamientoDetailComponent } from '../detail/tratamiento-detail.component';
import { TratamientoUpdateComponent } from '../update/tratamiento-update.component';
import { TratamientoRoutingResolveService } from './tratamiento-routing-resolve.service';

const tratamientoRoute: Routes = [
  {
    path: '',
    component: TratamientoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TratamientoDetailComponent,
    resolve: {
      tratamiento: TratamientoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TratamientoUpdateComponent,
    resolve: {
      tratamiento: TratamientoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TratamientoUpdateComponent,
    resolve: {
      tratamiento: TratamientoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tratamientoRoute)],
  exports: [RouterModule],
})
export class TratamientoRoutingModule {}
