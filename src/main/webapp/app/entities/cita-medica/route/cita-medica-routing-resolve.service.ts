import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICitaMedica, CitaMedica } from '../cita-medica.model';
import { CitaMedicaService } from '../service/cita-medica.service';

@Injectable({ providedIn: 'root' })
export class CitaMedicaRoutingResolveService implements Resolve<ICitaMedica> {
  constructor(protected service: CitaMedicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICitaMedica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((citaMedica: HttpResponse<CitaMedica>) => {
          if (citaMedica.body) {
            return of(citaMedica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CitaMedica());
  }
}
