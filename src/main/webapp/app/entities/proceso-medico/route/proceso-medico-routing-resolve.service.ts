import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProcesoMedico, ProcesoMedico } from '../proceso-medico.model';
import { ProcesoMedicoService } from '../service/proceso-medico.service';

@Injectable({ providedIn: 'root' })
export class ProcesoMedicoRoutingResolveService implements Resolve<IProcesoMedico> {
  constructor(protected service: ProcesoMedicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcesoMedico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((procesoMedico: HttpResponse<ProcesoMedico>) => {
          if (procesoMedico.body) {
            return of(procesoMedico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProcesoMedico());
  }
}
