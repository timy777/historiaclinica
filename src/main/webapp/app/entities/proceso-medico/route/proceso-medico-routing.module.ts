import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProcesoMedicoComponent } from '../list/proceso-medico.component';
import { ProcesoMedicoDetailComponent } from '../detail/proceso-medico-detail.component';
import { ProcesoMedicoUpdateComponent } from '../update/proceso-medico-update.component';
import { ProcesoMedicoRoutingResolveService } from './proceso-medico-routing-resolve.service';

const procesoMedicoRoute: Routes = [
  {
    path: '',
    component: ProcesoMedicoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProcesoMedicoDetailComponent,
    resolve: {
      procesoMedico: ProcesoMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProcesoMedicoUpdateComponent,
    resolve: {
      procesoMedico: ProcesoMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProcesoMedicoUpdateComponent,
    resolve: {
      procesoMedico: ProcesoMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(procesoMedicoRoute)],
  exports: [RouterModule],
})
export class ProcesoMedicoRoutingModule {}
