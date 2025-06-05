import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConsultaMedica, ConsultaMedica } from '../consulta-medica.model';
import { ConsultaMedicaService } from '../service/consulta-medica.service';

@Injectable({ providedIn: 'root' })
export class ConsultaMedicaRoutingResolveService implements Resolve<IConsultaMedica> {
  constructor(protected service: ConsultaMedicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConsultaMedica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((consultaMedica: HttpResponse<ConsultaMedica>) => {
          if (consultaMedica.body) {
            return of(consultaMedica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConsultaMedica());
  }
}
