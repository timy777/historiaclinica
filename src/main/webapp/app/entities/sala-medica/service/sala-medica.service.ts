import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalaMedica, getSalaMedicaIdentifier } from '../sala-medica.model';

export type EntityResponseType = HttpResponse<ISalaMedica>;
export type EntityArrayResponseType = HttpResponse<ISalaMedica[]>;

@Injectable({ providedIn: 'root' })
export class SalaMedicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sala-medicas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(salaMedica: ISalaMedica): Observable<EntityResponseType> {
    return this.http.post<ISalaMedica>(this.resourceUrl, salaMedica, { observe: 'response' });
  }

  update(salaMedica: ISalaMedica): Observable<EntityResponseType> {
    return this.http.put<ISalaMedica>(`${this.resourceUrl}/${getSalaMedicaIdentifier(salaMedica) as number}`, salaMedica, {
      observe: 'response',
    });
  }

  partialUpdate(salaMedica: ISalaMedica): Observable<EntityResponseType> {
    return this.http.patch<ISalaMedica>(`${this.resourceUrl}/${getSalaMedicaIdentifier(salaMedica) as number}`, salaMedica, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISalaMedica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISalaMedica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSalaMedicaToCollectionIfMissing(
    salaMedicaCollection: ISalaMedica[],
    ...salaMedicasToCheck: (ISalaMedica | null | undefined)[]
  ): ISalaMedica[] {
    const salaMedicas: ISalaMedica[] = salaMedicasToCheck.filter(isPresent);
    if (salaMedicas.length > 0) {
      const salaMedicaCollectionIdentifiers = salaMedicaCollection.map(salaMedicaItem => getSalaMedicaIdentifier(salaMedicaItem)!);
      const salaMedicasToAdd = salaMedicas.filter(salaMedicaItem => {
        const salaMedicaIdentifier = getSalaMedicaIdentifier(salaMedicaItem);
        if (salaMedicaIdentifier == null || salaMedicaCollectionIdentifiers.includes(salaMedicaIdentifier)) {
          return false;
        }
        salaMedicaCollectionIdentifiers.push(salaMedicaIdentifier);
        return true;
      });
      return [...salaMedicasToAdd, ...salaMedicaCollection];
    }
    return salaMedicaCollection;
  }
}
