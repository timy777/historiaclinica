import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonalMedico, PersonalMedico } from '../personal-medico.model';
import { PersonalMedicoService } from '../service/personal-medico.service';

@Injectable({ providedIn: 'root' })
export class PersonalMedicoRoutingResolveService implements Resolve<IPersonalMedico> {
  constructor(protected service: PersonalMedicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonalMedico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((personalMedico: HttpResponse<PersonalMedico>) => {
          if (personalMedico.body) {
            return of(personalMedico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PersonalMedico());
  }
}
