import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstudioMedico, getEstudioMedicoIdentifier } from '../estudio-medico.model';

export type EntityResponseType = HttpResponse<IEstudioMedico>;
export type EntityArrayResponseType = HttpResponse<IEstudioMedico[]>;

@Injectable({ providedIn: 'root' })
export class EstudioMedicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/estudio-medicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(estudioMedico: IEstudioMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estudioMedico);
    return this.http
      .post<IEstudioMedico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(estudioMedico: IEstudioMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estudioMedico);
    return this.http
      .put<IEstudioMedico>(`${this.resourceUrl}/${getEstudioMedicoIdentifier(estudioMedico) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(estudioMedico: IEstudioMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estudioMedico);
    return this.http
      .patch<IEstudioMedico>(`${this.resourceUrl}/${getEstudioMedicoIdentifier(estudioMedico) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEstudioMedico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEstudioMedico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEstudioMedicoToCollectionIfMissing(
    estudioMedicoCollection: IEstudioMedico[],
    ...estudioMedicosToCheck: (IEstudioMedico | null | undefined)[]
  ): IEstudioMedico[] {
    const estudioMedicos: IEstudioMedico[] = estudioMedicosToCheck.filter(isPresent);
    if (estudioMedicos.length > 0) {
      const estudioMedicoCollectionIdentifiers = estudioMedicoCollection.map(
        estudioMedicoItem => getEstudioMedicoIdentifier(estudioMedicoItem)!
      );
      const estudioMedicosToAdd = estudioMedicos.filter(estudioMedicoItem => {
        const estudioMedicoIdentifier = getEstudioMedicoIdentifier(estudioMedicoItem);
        if (estudioMedicoIdentifier == null || estudioMedicoCollectionIdentifiers.includes(estudioMedicoIdentifier)) {
          return false;
        }
        estudioMedicoCollectionIdentifiers.push(estudioMedicoIdentifier);
        return true;
      });
      return [...estudioMedicosToAdd, ...estudioMedicoCollection];
    }
    return estudioMedicoCollection;
  }

  protected convertDateFromClient(estudioMedico: IEstudioMedico): IEstudioMedico {
    return Object.assign({}, estudioMedico, {
      fechaRealizacion: estudioMedico.fechaRealizacion?.isValid() ? estudioMedico.fechaRealizacion.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaRealizacion = res.body.fechaRealizacion ? dayjs(res.body.fechaRealizacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((estudioMedico: IEstudioMedico) => {
        estudioMedico.fechaRealizacion = estudioMedico.fechaRealizacion ? dayjs(estudioMedico.fechaRealizacion) : undefined;
      });
    }
    return res;
  }
}
