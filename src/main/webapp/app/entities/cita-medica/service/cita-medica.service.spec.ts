import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICitaMedica, CitaMedica } from '../cita-medica.model';

import { CitaMedicaService } from './cita-medica.service';

describe('CitaMedica Service', () => {
  let service: CitaMedicaService;
  let httpMock: HttpTestingController;
  let elemDefault: ICitaMedica;
  let expectedResult: ICitaMedica | ICitaMedica[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CitaMedicaService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      fechaCita: currentDate,
      horaCita: 'AAAAAAA',
      motivo: 'AAAAAAA',
      estado: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          fechaCita: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a CitaMedica', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          fechaCita: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaCita: currentDate,
        },
        returnedFromService
      );

      service.create(new CitaMedica()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CitaMedica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fechaCita: currentDate.format(DATE_FORMAT),
          horaCita: 'BBBBBB',
          motivo: 'BBBBBB',
          estado: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaCita: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CitaMedica', () => {
      const patchObject = Object.assign(
        {
          fechaCita: currentDate.format(DATE_FORMAT),
          motivo: 'BBBBBB',
        },
        new CitaMedica()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          fechaCita: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CitaMedica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fechaCita: currentDate.format(DATE_FORMAT),
          horaCita: 'BBBBBB',
          motivo: 'BBBBBB',
          estado: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaCita: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a CitaMedica', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCitaMedicaToCollectionIfMissing', () => {
      it('should add a CitaMedica to an empty array', () => {
        const citaMedica: ICitaMedica = { id: 123 };
        expectedResult = service.addCitaMedicaToCollectionIfMissing([], citaMedica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(citaMedica);
      });

      it('should not add a CitaMedica to an array that contains it', () => {
        const citaMedica: ICitaMedica = { id: 123 };
        const citaMedicaCollection: ICitaMedica[] = [
          {
            ...citaMedica,
          },
          { id: 456 },
        ];
        expectedResult = service.addCitaMedicaToCollectionIfMissing(citaMedicaCollection, citaMedica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CitaMedica to an array that doesn't contain it", () => {
        const citaMedica: ICitaMedica = { id: 123 };
        const citaMedicaCollection: ICitaMedica[] = [{ id: 456 }];
        expectedResult = service.addCitaMedicaToCollectionIfMissing(citaMedicaCollection, citaMedica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(citaMedica);
      });

      it('should add only unique CitaMedica to an array', () => {
        const citaMedicaArray: ICitaMedica[] = [{ id: 123 }, { id: 456 }, { id: 64565 }];
        const citaMedicaCollection: ICitaMedica[] = [{ id: 123 }];
        expectedResult = service.addCitaMedicaToCollectionIfMissing(citaMedicaCollection, ...citaMedicaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const citaMedica: ICitaMedica = { id: 123 };
        const citaMedica2: ICitaMedica = { id: 456 };
        expectedResult = service.addCitaMedicaToCollectionIfMissing([], citaMedica, citaMedica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(citaMedica);
        expect(expectedResult).toContain(citaMedica2);
      });

      it('should accept null and undefined values', () => {
        const citaMedica: ICitaMedica = { id: 123 };
        expectedResult = service.addCitaMedicaToCollectionIfMissing([], null, citaMedica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(citaMedica);
      });

      it('should return initial array if no CitaMedica is added', () => {
        const citaMedicaCollection: ICitaMedica[] = [{ id: 123 }];
        expectedResult = service.addCitaMedicaToCollectionIfMissing(citaMedicaCollection, undefined, null);
        expect(expectedResult).toEqual(citaMedicaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
