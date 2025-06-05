import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMedicacion, getMedicacionIdentifier } from '../medicacion.model';

export type EntityResponseType = HttpResponse<IMedicacion>;
export type EntityArrayResponseType = HttpResponse<IMedicacion[]>;

@Injectable({ providedIn: 'root' })
export class MedicacionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/medicacions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(medicacion: IMedicacion): Observable<EntityResponseType> {
    return this.http.post<IMedicacion>(this.resourceUrl, medicacion, { observe: 'response' });
  }

  update(medicacion: IMedicacion): Observable<EntityResponseType> {
    return this.http.put<IMedicacion>(`${this.resourceUrl}/${getMedicacionIdentifier(medicacion) as number}`, medicacion, {
      observe: 'response',
    });
  }

  partialUpdate(medicacion: IMedicacion): Observable<EntityResponseType> {
    return this.http.patch<IMedicacion>(`${this.resourceUrl}/${getMedicacionIdentifier(medicacion) as number}`, medicacion, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMedicacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMedicacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMedicacionToCollectionIfMissing(
    medicacionCollection: IMedicacion[],
    ...medicacionsToCheck: (IMedicacion | null | undefined)[]
  ): IMedicacion[] {
    const medicacions: IMedicacion[] = medicacionsToCheck.filter(isPresent);
    if (medicacions.length > 0) {
      const medicacionCollectionIdentifiers = medicacionCollection.map(medicacionItem => getMedicacionIdentifier(medicacionItem)!);
      const medicacionsToAdd = medicacions.filter(medicacionItem => {
        const medicacionIdentifier = getMedicacionIdentifier(medicacionItem);
        if (medicacionIdentifier == null || medicacionCollectionIdentifiers.includes(medicacionIdentifier)) {
          return false;
        }
        medicacionCollectionIdentifiers.push(medicacionIdentifier);
        return true;
      });
      return [...medicacionsToAdd, ...medicacionCollection];
    }
    return medicacionCollection;
  }
}
