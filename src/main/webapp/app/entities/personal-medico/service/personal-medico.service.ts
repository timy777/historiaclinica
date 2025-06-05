import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonalMedico, getPersonalMedicoIdentifier } from '../personal-medico.model';

export type EntityResponseType = HttpResponse<IPersonalMedico>;
export type EntityArrayResponseType = HttpResponse<IPersonalMedico[]>;

@Injectable({ providedIn: 'root' })
export class PersonalMedicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/personal-medicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(personalMedico: IPersonalMedico): Observable<EntityResponseType> {
    return this.http.post<IPersonalMedico>(this.resourceUrl, personalMedico, { observe: 'response' });
  }

  update(personalMedico: IPersonalMedico): Observable<EntityResponseType> {
    return this.http.put<IPersonalMedico>(`${this.resourceUrl}/${getPersonalMedicoIdentifier(personalMedico) as number}`, personalMedico, {
      observe: 'response',
    });
  }

  partialUpdate(personalMedico: IPersonalMedico): Observable<EntityResponseType> {
    return this.http.patch<IPersonalMedico>(
      `${this.resourceUrl}/${getPersonalMedicoIdentifier(personalMedico) as number}`,
      personalMedico,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonalMedico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonalMedico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPersonalMedicoToCollectionIfMissing(
    personalMedicoCollection: IPersonalMedico[],
    ...personalMedicosToCheck: (IPersonalMedico | null | undefined)[]
  ): IPersonalMedico[] {
    const personalMedicos: IPersonalMedico[] = personalMedicosToCheck.filter(isPresent);
    if (personalMedicos.length > 0) {
      const personalMedicoCollectionIdentifiers = personalMedicoCollection.map(
        personalMedicoItem => getPersonalMedicoIdentifier(personalMedicoItem)!
      );
      const personalMedicosToAdd = personalMedicos.filter(personalMedicoItem => {
        const personalMedicoIdentifier = getPersonalMedicoIdentifier(personalMedicoItem);
        if (personalMedicoIdentifier == null || personalMedicoCollectionIdentifiers.includes(personalMedicoIdentifier)) {
          return false;
        }
        personalMedicoCollectionIdentifiers.push(personalMedicoIdentifier);
        return true;
      });
      return [...personalMedicosToAdd, ...personalMedicoCollection];
    }
    return personalMedicoCollection;
  }
}
