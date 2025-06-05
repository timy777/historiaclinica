import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalaMedica, SalaMedica } from '../sala-medica.model';
import { SalaMedicaService } from '../service/sala-medica.service';

@Injectable({ providedIn: 'root' })
export class SalaMedicaRoutingResolveService implements Resolve<ISalaMedica> {
  constructor(protected service: SalaMedicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISalaMedica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((salaMedica: HttpResponse<SalaMedica>) => {
          if (salaMedica.body) {
            return of(salaMedica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SalaMedica());
  }
}
