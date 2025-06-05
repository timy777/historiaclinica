import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITratamiento, Tratamiento } from '../tratamiento.model';
import { TratamientoService } from '../service/tratamiento.service';

@Injectable({ providedIn: 'root' })
export class TratamientoRoutingResolveService implements Resolve<ITratamiento> {
  constructor(protected service: TratamientoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITratamiento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tratamiento: HttpResponse<Tratamiento>) => {
          if (tratamiento.body) {
            return of(tratamiento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tratamiento());
  }
}
