import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMedicacion, Medicacion } from '../medicacion.model';

import { MedicacionService } from './medicacion.service';

describe('Medicacion Service', () => {
  let service: MedicacionService;
  let httpMock: HttpTestingController;
  let elemDefault: IMedicacion;
  let expectedResult: IMedicacion | IMedicacion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MedicacionService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nombreMedicamento: 'AAAAAAA',
      dosis: 'AAAAAAA',
      frecuencia: 'AAAAAAA',
      viaAdministracion: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Medicacion', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Medicacion()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Medicacion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombreMedicamento: 'BBBBBB',
          dosis: 'BBBBBB',
          frecuencia: 'BBBBBB',
          viaAdministracion: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Medicacion', () => {
      const patchObject = Object.assign(
        {
          nombreMedicamento: 'BBBBBB',
          dosis: 'BBBBBB',
          frecuencia: 'BBBBBB',
        },
        new Medicacion()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Medicacion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombreMedicamento: 'BBBBBB',
          dosis: 'BBBBBB',
          frecuencia: 'BBBBBB',
          viaAdministracion: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Medicacion', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMedicacionToCollectionIfMissing', () => {
      it('should add a Medicacion to an empty array', () => {
        const medicacion: IMedicacion = { id: 123 };
        expectedResult = service.addMedicacionToCollectionIfMissing([], medicacion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(medicacion);
      });

      it('should not add a Medicacion to an array that contains it', () => {
        const medicacion: IMedicacion = { id: 123 };
        const medicacionCollection: IMedicacion[] = [
          {
            ...medicacion,
          },
          { id: 456 },
        ];
        expectedResult = service.addMedicacionToCollectionIfMissing(medicacionCollection, medicacion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Medicacion to an array that doesn't contain it", () => {
        const medicacion: IMedicacion = { id: 123 };
        const medicacionCollection: IMedicacion[] = [{ id: 456 }];
        expectedResult = service.addMedicacionToCollectionIfMissing(medicacionCollection, medicacion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(medicacion);
      });

      it('should add only unique Medicacion to an array', () => {
        const medicacionArray: IMedicacion[] = [{ id: 123 }, { id: 456 }, { id: 74875 }];
        const medicacionCollection: IMedicacion[] = [{ id: 123 }];
        expectedResult = service.addMedicacionToCollectionIfMissing(medicacionCollection, ...medicacionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const medicacion: IMedicacion = { id: 123 };
        const medicacion2: IMedicacion = { id: 456 };
        expectedResult = service.addMedicacionToCollectionIfMissing([], medicacion, medicacion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(medicacion);
        expect(expectedResult).toContain(medicacion2);
      });

      it('should accept null and undefined values', () => {
        const medicacion: IMedicacion = { id: 123 };
        expectedResult = service.addMedicacionToCollectionIfMissing([], null, medicacion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(medicacion);
      });

      it('should return initial array if no Medicacion is added', () => {
        const medicacionCollection: IMedicacion[] = [{ id: 123 }];
        expectedResult = service.addMedicacionToCollectionIfMissing(medicacionCollection, undefined, null);
        expect(expectedResult).toEqual(medicacionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
