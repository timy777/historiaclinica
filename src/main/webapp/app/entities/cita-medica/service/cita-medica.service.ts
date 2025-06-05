import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICitaMedica, getCitaMedicaIdentifier } from '../cita-medica.model';

export type EntityResponseType = HttpResponse<ICitaMedica>;
export type EntityArrayResponseType = HttpResponse<ICitaMedica[]>;

@Injectable({ providedIn: 'root' })
export class CitaMedicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cita-medicas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(citaMedica: ICitaMedica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(citaMedica);
    return this.http
      .post<ICitaMedica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(citaMedica: ICitaMedica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(citaMedica);
    return this.http
      .put<ICitaMedica>(`${this.resourceUrl}/${getCitaMedicaIdentifier(citaMedica) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(citaMedica: ICitaMedica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(citaMedica);
    return this.http
      .patch<ICitaMedica>(`${this.resourceUrl}/${getCitaMedicaIdentifier(citaMedica) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICitaMedica>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICitaMedica[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCitaMedicaToCollectionIfMissing(
    citaMedicaCollection: ICitaMedica[],
    ...citaMedicasToCheck: (ICitaMedica | null | undefined)[]
  ): ICitaMedica[] {
    const citaMedicas: ICitaMedica[] = citaMedicasToCheck.filter(isPresent);
    if (citaMedicas.length > 0) {
      const citaMedicaCollectionIdentifiers = citaMedicaCollection.map(citaMedicaItem => getCitaMedicaIdentifier(citaMedicaItem)!);
      const citaMedicasToAdd = citaMedicas.filter(citaMedicaItem => {
        const citaMedicaIdentifier = getCitaMedicaIdentifier(citaMedicaItem);
        if (citaMedicaIdentifier == null || citaMedicaCollectionIdentifiers.includes(citaMedicaIdentifier)) {
          return false;
        }
        citaMedicaCollectionIdentifiers.push(citaMedicaIdentifier);
        return true;
      });
      return [...citaMedicasToAdd, ...citaMedicaCollection];
    }
    return citaMedicaCollection;
  }

  protected convertDateFromClient(citaMedica: ICitaMedica): ICitaMedica {
    return Object.assign({}, citaMedica, {
      fechaCita: citaMedica.fechaCita?.isValid() ? citaMedica.fechaCita.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCita = res.body.fechaCita ? dayjs(res.body.fechaCita) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((citaMedica: ICitaMedica) => {
        citaMedica.fechaCita = citaMedica.fechaCita ? dayjs(citaMedica.fechaCita) : undefined;
      });
    }
    return res;
  }
}
