import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEstudioMedico, EstudioMedico } from '../estudio-medico.model';

import { EstudioMedicoService } from './estudio-medico.service';

describe('EstudioMedico Service', () => {
  let service: EstudioMedicoService;
  let httpMock: HttpTestingController;
  let elemDefault: IEstudioMedico;
  let expectedResult: IEstudioMedico | IEstudioMedico[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EstudioMedicoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      tipoEstudio: 'AAAAAAA',
      resultado: 'AAAAAAA',
      fechaRealizacion: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          fechaRealizacion: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a EstudioMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          fechaRealizacion: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaRealizacion: currentDate,
        },
        returnedFromService
      );

      service.create(new EstudioMedico()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EstudioMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tipoEstudio: 'BBBBBB',
          resultado: 'BBBBBB',
          fechaRealizacion: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaRealizacion: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EstudioMedico', () => {
      const patchObject = Object.assign(
        {
          tipoEstudio: 'BBBBBB',
          resultado: 'BBBBBB',
          fechaRealizacion: currentDate.format(DATE_TIME_FORMAT),
        },
        new EstudioMedico()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          fechaRealizacion: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EstudioMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tipoEstudio: 'BBBBBB',
          resultado: 'BBBBBB',
          fechaRealizacion: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaRealizacion: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a EstudioMedico', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEstudioMedicoToCollectionIfMissing', () => {
      it('should add a EstudioMedico to an empty array', () => {
        const estudioMedico: IEstudioMedico = { id: 123 };
        expectedResult = service.addEstudioMedicoToCollectionIfMissing([], estudioMedico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estudioMedico);
      });

      it('should not add a EstudioMedico to an array that contains it', () => {
        const estudioMedico: IEstudioMedico = { id: 123 };
        const estudioMedicoCollection: IEstudioMedico[] = [
          {
            ...estudioMedico,
          },
          { id: 456 },
        ];
        expectedResult = service.addEstudioMedicoToCollectionIfMissing(estudioMedicoCollection, estudioMedico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EstudioMedico to an array that doesn't contain it", () => {
        const estudioMedico: IEstudioMedico = { id: 123 };
        const estudioMedicoCollection: IEstudioMedico[] = [{ id: 456 }];
        expectedResult = service.addEstudioMedicoToCollectionIfMissing(estudioMedicoCollection, estudioMedico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estudioMedico);
      });

      it('should add only unique EstudioMedico to an array', () => {
        const estudioMedicoArray: IEstudioMedico[] = [{ id: 123 }, { id: 456 }, { id: 78400 }];
        const estudioMedicoCollection: IEstudioMedico[] = [{ id: 123 }];
        expectedResult = service.addEstudioMedicoToCollectionIfMissing(estudioMedicoCollection, ...estudioMedicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const estudioMedico: IEstudioMedico = { id: 123 };
        const estudioMedico2: IEstudioMedico = { id: 456 };
        expectedResult = service.addEstudioMedicoToCollectionIfMissing([], estudioMedico, estudioMedico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estudioMedico);
        expect(expectedResult).toContain(estudioMedico2);
      });

      it('should accept null and undefined values', () => {
        const estudioMedico: IEstudioMedico = { id: 123 };
        expectedResult = service.addEstudioMedicoToCollectionIfMissing([], null, estudioMedico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estudioMedico);
      });

      it('should return initial array if no EstudioMedico is added', () => {
        const estudioMedicoCollection: IEstudioMedico[] = [{ id: 123 }];
        expectedResult = service.addEstudioMedicoToCollectionIfMissing(estudioMedicoCollection, undefined, null);
        expect(expectedResult).toEqual(estudioMedicoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
