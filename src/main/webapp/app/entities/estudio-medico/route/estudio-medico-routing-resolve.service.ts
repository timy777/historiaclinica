import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEstudioMedico, EstudioMedico } from '../estudio-medico.model';
import { EstudioMedicoService } from '../service/estudio-medico.service';

@Injectable({ providedIn: 'root' })
export class EstudioMedicoRoutingResolveService implements Resolve<IEstudioMedico> {
  constructor(protected service: EstudioMedicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstudioMedico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((estudioMedico: HttpResponse<EstudioMedico>) => {
          if (estudioMedico.body) {
            return of(estudioMedico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstudioMedico());
  }
}
