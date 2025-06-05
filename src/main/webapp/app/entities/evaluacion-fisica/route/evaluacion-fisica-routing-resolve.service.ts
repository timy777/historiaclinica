import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEvaluacionFisica, EvaluacionFisica } from '../evaluacion-fisica.model';
import { EvaluacionFisicaService } from '../service/evaluacion-fisica.service';

@Injectable({ providedIn: 'root' })
export class EvaluacionFisicaRoutingResolveService implements Resolve<IEvaluacionFisica> {
  constructor(protected service: EvaluacionFisicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEvaluacionFisica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((evaluacionFisica: HttpResponse<EvaluacionFisica>) => {
          if (evaluacionFisica.body) {
            return of(evaluacionFisica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EvaluacionFisica());
  }
}
