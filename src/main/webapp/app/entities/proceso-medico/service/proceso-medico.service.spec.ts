import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProcesoMedico, ProcesoMedico } from '../proceso-medico.model';

import { ProcesoMedicoService } from './proceso-medico.service';

describe('ProcesoMedico Service', () => {
  let service: ProcesoMedicoService;
  let httpMock: HttpTestingController;
  let elemDefault: IProcesoMedico;
  let expectedResult: IProcesoMedico | IProcesoMedico[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProcesoMedicoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      tipoProceso: 'AAAAAAA',
      fechaInicio: currentDate,
      fechaFin: currentDate,
      estado: 'AAAAAAA',
      hashBlockchain: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          fechaInicio: currentDate.format(DATE_TIME_FORMAT),
          fechaFin: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ProcesoMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          fechaInicio: currentDate.format(DATE_TIME_FORMAT),
          fechaFin: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaInicio: currentDate,
          fechaFin: currentDate,
        },
        returnedFromService
      );

      service.create(new ProcesoMedico()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProcesoMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tipoProceso: 'BBBBBB',
          fechaInicio: currentDate.format(DATE_TIME_FORMAT),
          fechaFin: currentDate.format(DATE_TIME_FORMAT),
          estado: 'BBBBBB',
          hashBlockchain: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaInicio: currentDate,
          fechaFin: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProcesoMedico', () => {
      const patchObject = Object.assign(
        {
          tipoProceso: 'BBBBBB',
          fechaInicio: currentDate.format(DATE_TIME_FORMAT),
        },
        new ProcesoMedico()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          fechaInicio: currentDate,
          fechaFin: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProcesoMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tipoProceso: 'BBBBBB',
          fechaInicio: currentDate.format(DATE_TIME_FORMAT),
          fechaFin: currentDate.format(DATE_TIME_FORMAT),
          estado: 'BBBBBB',
          hashBlockchain: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaInicio: currentDate,
          fechaFin: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ProcesoMedico', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProcesoMedicoToCollectionIfMissing', () => {
      it('should add a ProcesoMedico to an empty array', () => {
        const procesoMedico: IProcesoMedico = { id: 123 };
        expectedResult = service.addProcesoMedicoToCollectionIfMissing([], procesoMedico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(procesoMedico);
      });

      it('should not add a ProcesoMedico to an array that contains it', () => {
        const procesoMedico: IProcesoMedico = { id: 123 };
        const procesoMedicoCollection: IProcesoMedico[] = [
          {
            ...procesoMedico,
          },
          { id: 456 },
        ];
        expectedResult = service.addProcesoMedicoToCollectionIfMissing(procesoMedicoCollection, procesoMedico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProcesoMedico to an array that doesn't contain it", () => {
        const procesoMedico: IProcesoMedico = { id: 123 };
        const procesoMedicoCollection: IProcesoMedico[] = [{ id: 456 }];
        expectedResult = service.addProcesoMedicoToCollectionIfMissing(procesoMedicoCollection, procesoMedico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(procesoMedico);
      });

      it('should add only unique ProcesoMedico to an array', () => {
        const procesoMedicoArray: IProcesoMedico[] = [{ id: 123 }, { id: 456 }, { id: 55733 }];
        const procesoMedicoCollection: IProcesoMedico[] = [{ id: 123 }];
        expectedResult = service.addProcesoMedicoToCollectionIfMissing(procesoMedicoCollection, ...procesoMedicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const procesoMedico: IProcesoMedico = { id: 123 };
        const procesoMedico2: IProcesoMedico = { id: 456 };
        expectedResult = service.addProcesoMedicoToCollectionIfMissing([], procesoMedico, procesoMedico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(procesoMedico);
        expect(expectedResult).toContain(procesoMedico2);
      });

      it('should accept null and undefined values', () => {
        const procesoMedico: IProcesoMedico = { id: 123 };
        expectedResult = service.addProcesoMedicoToCollectionIfMissing([], null, procesoMedico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(procesoMedico);
      });

      it('should return initial array if no ProcesoMedico is added', () => {
        const procesoMedicoCollection: IProcesoMedico[] = [{ id: 123 }];
        expectedResult = service.addProcesoMedicoToCollectionIfMissing(procesoMedicoCollection, undefined, null);
        expect(expectedResult).toEqual(procesoMedicoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
