import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonalMedicoComponent } from '../list/personal-medico.component';
import { PersonalMedicoDetailComponent } from '../detail/personal-medico-detail.component';
import { PersonalMedicoUpdateComponent } from '../update/personal-medico-update.component';
import { PersonalMedicoRoutingResolveService } from './personal-medico-routing-resolve.service';

const personalMedicoRoute: Routes = [
  {
    path: '',
    component: PersonalMedicoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonalMedicoDetailComponent,
    resolve: {
      personalMedico: PersonalMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonalMedicoUpdateComponent,
    resolve: {
      personalMedico: PersonalMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonalMedicoUpdateComponent,
    resolve: {
      personalMedico: PersonalMedicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personalMedicoRoute)],
  exports: [RouterModule],
})
export class PersonalMedicoRoutingModule {}
