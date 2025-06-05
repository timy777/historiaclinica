import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IConsultaMedica, ConsultaMedica } from '../consulta-medica.model';

import { ConsultaMedicaService } from './consulta-medica.service';

describe('ConsultaMedica Service', () => {
  let service: ConsultaMedicaService;
  let httpMock: HttpTestingController;
  let elemDefault: IConsultaMedica;
  let expectedResult: IConsultaMedica | IConsultaMedica[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConsultaMedicaService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      diagnostico: 'AAAAAAA',
      tratamientoSugerido: 'AAAAAAA',
      observaciones: 'AAAAAAA',
      fechaConsulta: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          fechaConsulta: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ConsultaMedica', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          fechaConsulta: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaConsulta: currentDate,
        },
        returnedFromService
      );

      service.create(new ConsultaMedica()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ConsultaMedica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          diagnostico: 'BBBBBB',
          tratamientoSugerido: 'BBBBBB',
          observaciones: 'BBBBBB',
          fechaConsulta: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaConsulta: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ConsultaMedica', () => {
      const patchObject = Object.assign(
        {
          tratamientoSugerido: 'BBBBBB',
          observaciones: 'BBBBBB',
        },
        new ConsultaMedica()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          fechaConsulta: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ConsultaMedica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          diagnostico: 'BBBBBB',
          tratamientoSugerido: 'BBBBBB',
          observaciones: 'BBBBBB',
          fechaConsulta: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaConsulta: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ConsultaMedica', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addConsultaMedicaToCollectionIfMissing', () => {
      it('should add a ConsultaMedica to an empty array', () => {
        const consultaMedica: IConsultaMedica = { id: 123 };
        expectedResult = service.addConsultaMedicaToCollectionIfMissing([], consultaMedica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(consultaMedica);
      });

      it('should not add a ConsultaMedica to an array that contains it', () => {
        const consultaMedica: IConsultaMedica = { id: 123 };
        const consultaMedicaCollection: IConsultaMedica[] = [
          {
            ...consultaMedica,
          },
          { id: 456 },
        ];
        expectedResult = service.addConsultaMedicaToCollectionIfMissing(consultaMedicaCollection, consultaMedica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ConsultaMedica to an array that doesn't contain it", () => {
        const consultaMedica: IConsultaMedica = { id: 123 };
        const consultaMedicaCollection: IConsultaMedica[] = [{ id: 456 }];
        expectedResult = service.addConsultaMedicaToCollectionIfMissing(consultaMedicaCollection, consultaMedica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(consultaMedica);
      });

      it('should add only unique ConsultaMedica to an array', () => {
        const consultaMedicaArray: IConsultaMedica[] = [{ id: 123 }, { id: 456 }, { id: 83717 }];
        const consultaMedicaCollection: IConsultaMedica[] = [{ id: 123 }];
        expectedResult = service.addConsultaMedicaToCollectionIfMissing(consultaMedicaCollection, ...consultaMedicaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const consultaMedica: IConsultaMedica = { id: 123 };
        const consultaMedica2: IConsultaMedica = { id: 456 };
        expectedResult = service.addConsultaMedicaToCollectionIfMissing([], consultaMedica, consultaMedica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(consultaMedica);
        expect(expectedResult).toContain(consultaMedica2);
      });

      it('should accept null and undefined values', () => {
        const consultaMedica: IConsultaMedica = { id: 123 };
        expectedResult = service.addConsultaMedicaToCollectionIfMissing([], null, consultaMedica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(consultaMedica);
      });

      it('should return initial array if no ConsultaMedica is added', () => {
        const consultaMedicaCollection: IConsultaMedica[] = [{ id: 123 }];
        expectedResult = service.addConsultaMedicaToCollectionIfMissing(consultaMedicaCollection, undefined, null);
        expect(expectedResult).toEqual(consultaMedicaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
