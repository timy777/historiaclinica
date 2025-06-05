import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITratamiento, getTratamientoIdentifier } from '../tratamiento.model';

export type EntityResponseType = HttpResponse<ITratamiento>;
export type EntityArrayResponseType = HttpResponse<ITratamiento[]>;

@Injectable({ providedIn: 'root' })
export class TratamientoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tratamientos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tratamiento: ITratamiento): Observable<EntityResponseType> {
    return this.http.post<ITratamiento>(this.resourceUrl, tratamiento, { observe: 'response' });
  }

  update(tratamiento: ITratamiento): Observable<EntityResponseType> {
    return this.http.put<ITratamiento>(`${this.resourceUrl}/${getTratamientoIdentifier(tratamiento) as number}`, tratamiento, {
      observe: 'response',
    });
  }

  partialUpdate(tratamiento: ITratamiento): Observable<EntityResponseType> {
    return this.http.patch<ITratamiento>(`${this.resourceUrl}/${getTratamientoIdentifier(tratamiento) as number}`, tratamiento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITratamiento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITratamiento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTratamientoToCollectionIfMissing(
    tratamientoCollection: ITratamiento[],
    ...tratamientosToCheck: (ITratamiento | null | undefined)[]
  ): ITratamiento[] {
    const tratamientos: ITratamiento[] = tratamientosToCheck.filter(isPresent);
    if (tratamientos.length > 0) {
      const tratamientoCollectionIdentifiers = tratamientoCollection.map(tratamientoItem => getTratamientoIdentifier(tratamientoItem)!);
      const tratamientosToAdd = tratamientos.filter(tratamientoItem => {
        const tratamientoIdentifier = getTratamientoIdentifier(tratamientoItem);
        if (tratamientoIdentifier == null || tratamientoCollectionIdentifiers.includes(tratamientoIdentifier)) {
          return false;
        }
        tratamientoCollectionIdentifiers.push(tratamientoIdentifier);
        return true;
      });
      return [...tratamientosToAdd, ...tratamientoCollection];
    }
    return tratamientoCollection;
  }
}
