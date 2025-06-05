import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProcesoMedico, getProcesoMedicoIdentifier } from '../proceso-medico.model';

export type EntityResponseType = HttpResponse<IProcesoMedico>;
export type EntityArrayResponseType = HttpResponse<IProcesoMedico[]>;

@Injectable({ providedIn: 'root' })
export class ProcesoMedicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/proceso-medicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(procesoMedico: IProcesoMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(procesoMedico);
    return this.http
      .post<IProcesoMedico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(procesoMedico: IProcesoMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(procesoMedico);
    return this.http
      .put<IProcesoMedico>(`${this.resourceUrl}/${getProcesoMedicoIdentifier(procesoMedico) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(procesoMedico: IProcesoMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(procesoMedico);
    return this.http
      .patch<IProcesoMedico>(`${this.resourceUrl}/${getProcesoMedicoIdentifier(procesoMedico) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProcesoMedico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProcesoMedico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProcesoMedicoToCollectionIfMissing(
    procesoMedicoCollection: IProcesoMedico[],
    ...procesoMedicosToCheck: (IProcesoMedico | null | undefined)[]
  ): IProcesoMedico[] {
    const procesoMedicos: IProcesoMedico[] = procesoMedicosToCheck.filter(isPresent);
    if (procesoMedicos.length > 0) {
      const procesoMedicoCollectionIdentifiers = procesoMedicoCollection.map(
        procesoMedicoItem => getProcesoMedicoIdentifier(procesoMedicoItem)!
      );
      const procesoMedicosToAdd = procesoMedicos.filter(procesoMedicoItem => {
        const procesoMedicoIdentifier = getProcesoMedicoIdentifier(procesoMedicoItem);
        if (procesoMedicoIdentifier == null || procesoMedicoCollectionIdentifiers.includes(procesoMedicoIdentifier)) {
          return false;
        }
        procesoMedicoCollectionIdentifiers.push(procesoMedicoIdentifier);
        return true;
      });
      return [...procesoMedicosToAdd, ...procesoMedicoCollection];
    }
    return procesoMedicoCollection;
  }

  protected convertDateFromClient(procesoMedico: IProcesoMedico): IProcesoMedico {
    return Object.assign({}, procesoMedico, {
      fechaInicio: procesoMedico.fechaInicio?.isValid() ? procesoMedico.fechaInicio.toJSON() : undefined,
      fechaFin: procesoMedico.fechaFin?.isValid() ? procesoMedico.fechaFin.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaInicio = res.body.fechaInicio ? dayjs(res.body.fechaInicio) : undefined;
      res.body.fechaFin = res.body.fechaFin ? dayjs(res.body.fechaFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((procesoMedico: IProcesoMedico) => {
        procesoMedico.fechaInicio = procesoMedico.fechaInicio ? dayjs(procesoMedico.fechaInicio) : undefined;
        procesoMedico.fechaFin = procesoMedico.fechaFin ? dayjs(procesoMedico.fechaFin) : undefined;
      });
    }
    return res;
  }
}
