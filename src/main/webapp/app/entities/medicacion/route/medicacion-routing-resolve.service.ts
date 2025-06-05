import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMedicacion, Medicacion } from '../medicacion.model';
import { MedicacionService } from '../service/medicacion.service';

@Injectable({ providedIn: 'root' })
export class MedicacionRoutingResolveService implements Resolve<IMedicacion> {
  constructor(protected service: MedicacionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((medicacion: HttpResponse<Medicacion>) => {
          if (medicacion.body) {
            return of(medicacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Medicacion());
  }
}
