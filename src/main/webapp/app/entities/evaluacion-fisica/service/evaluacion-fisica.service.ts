import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEvaluacionFisica, getEvaluacionFisicaIdentifier } from '../evaluacion-fisica.model';

export type EntityResponseType = HttpResponse<IEvaluacionFisica>;
export type EntityArrayResponseType = HttpResponse<IEvaluacionFisica[]>;

@Injectable({ providedIn: 'root' })
export class EvaluacionFisicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/evaluacion-fisicas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(evaluacionFisica: IEvaluacionFisica): Observable<EntityResponseType> {
    return this.http.post<IEvaluacionFisica>(this.resourceUrl, evaluacionFisica, { observe: 'response' });
  }

  update(evaluacionFisica: IEvaluacionFisica): Observable<EntityResponseType> {
    return this.http.put<IEvaluacionFisica>(
      `${this.resourceUrl}/${getEvaluacionFisicaIdentifier(evaluacionFisica) as number}`,
      evaluacionFisica,
      { observe: 'response' }
    );
  }

  partialUpdate(evaluacionFisica: IEvaluacionFisica): Observable<EntityResponseType> {
    return this.http.patch<IEvaluacionFisica>(
      `${this.resourceUrl}/${getEvaluacionFisicaIdentifier(evaluacionFisica) as number}`,
      evaluacionFisica,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEvaluacionFisica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvaluacionFisica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEvaluacionFisicaToCollectionIfMissing(
    evaluacionFisicaCollection: IEvaluacionFisica[],
    ...evaluacionFisicasToCheck: (IEvaluacionFisica | null | undefined)[]
  ): IEvaluacionFisica[] {
    const evaluacionFisicas: IEvaluacionFisica[] = evaluacionFisicasToCheck.filter(isPresent);
    if (evaluacionFisicas.length > 0) {
      const evaluacionFisicaCollectionIdentifiers = evaluacionFisicaCollection.map(
        evaluacionFisicaItem => getEvaluacionFisicaIdentifier(evaluacionFisicaItem)!
      );
      const evaluacionFisicasToAdd = evaluacionFisicas.filter(evaluacionFisicaItem => {
        const evaluacionFisicaIdentifier = getEvaluacionFisicaIdentifier(evaluacionFisicaItem);
        if (evaluacionFisicaIdentifier == null || evaluacionFisicaCollectionIdentifiers.includes(evaluacionFisicaIdentifier)) {
          return false;
        }
        evaluacionFisicaCollectionIdentifiers.push(evaluacionFisicaIdentifier);
        return true;
      });
      return [...evaluacionFisicasToAdd, ...evaluacionFisicaCollection];
    }
    return evaluacionFisicaCollection;
  }
}
