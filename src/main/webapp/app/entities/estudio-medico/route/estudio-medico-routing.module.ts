import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EstudioMedicoComponent } from '../list/estudio-medico.component';
import { EstudioMedicoDetailComponent } from '../detail/estudio-medico-detail.component';
import { EstudioMedicoUpdateComponent } from '../update/estudio-medico-update.component';
import { EstudioMedicoRoutingResolveService } from './estudio-medico-routing-resolve.service';

const estudioMedicoRoute: Routes = [
  {
    path: '',
    component: EstudioMedicoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstudioMedicoDetailComponent,
    resolve: {
      estudioMedico: EstudioMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstudioMedicoUpdateComponent,
    resolve: {
      estudioMedico: EstudioMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstudioMedicoUpdateComponent,
    resolve: {
      estudioMedico: EstudioMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(estudioMedicoRoute)],
  exports: [RouterModule],
})
export class EstudioMedicoRoutingModule {}
