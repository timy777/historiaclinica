import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConsultaMedica, getConsultaMedicaIdentifier } from '../consulta-medica.model';

export type EntityResponseType = HttpResponse<IConsultaMedica>;
export type EntityArrayResponseType = HttpResponse<IConsultaMedica[]>;

@Injectable({ providedIn: 'root' })
export class ConsultaMedicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/consulta-medicas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(consultaMedica: IConsultaMedica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consultaMedica);
    return this.http
      .post<IConsultaMedica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(consultaMedica: IConsultaMedica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consultaMedica);
    return this.http
      .put<IConsultaMedica>(`${this.resourceUrl}/${getConsultaMedicaIdentifier(consultaMedica) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(consultaMedica: IConsultaMedica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consultaMedica);
    return this.http
      .patch<IConsultaMedica>(`${this.resourceUrl}/${getConsultaMedicaIdentifier(consultaMedica) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConsultaMedica>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConsultaMedica[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addConsultaMedicaToCollectionIfMissing(
    consultaMedicaCollection: IConsultaMedica[],
    ...consultaMedicasToCheck: (IConsultaMedica | null | undefined)[]
  ): IConsultaMedica[] {
    const consultaMedicas: IConsultaMedica[] = consultaMedicasToCheck.filter(isPresent);
    if (consultaMedicas.length > 0) {
      const consultaMedicaCollectionIdentifiers = consultaMedicaCollection.map(
        consultaMedicaItem => getConsultaMedicaIdentifier(consultaMedicaItem)!
      );
      const consultaMedicasToAdd = consultaMedicas.filter(consultaMedicaItem => {
        const consultaMedicaIdentifier = getConsultaMedicaIdentifier(consultaMedicaItem);
        if (consultaMedicaIdentifier == null || consultaMedicaCollectionIdentifiers.includes(consultaMedicaIdentifier)) {
          return false;
        }
        consultaMedicaCollectionIdentifiers.push(consultaMedicaIdentifier);
        return true;
      });
      return [...consultaMedicasToAdd, ...consultaMedicaCollection];
    }
    return consultaMedicaCollection;
  }

  protected convertDateFromClient(consultaMedica: IConsultaMedica): IConsultaMedica {
    return Object.assign({}, consultaMedica, {
      fechaConsulta: consultaMedica.fechaConsulta?.isValid() ? consultaMedica.fechaConsulta.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaConsulta = res.body.fechaConsulta ? dayjs(res.body.fechaConsulta) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((consultaMedica: IConsultaMedica) => {
        consultaMedica.fechaConsulta = consultaMedica.fechaConsulta ? dayjs(consultaMedica.fechaConsulta) : undefined;
      });
    }
    return res;
  }
}
