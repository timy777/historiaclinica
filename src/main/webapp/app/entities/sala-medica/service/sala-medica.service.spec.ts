import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISalaMedica, SalaMedica } from '../sala-medica.model';

import { SalaMedicaService } from './sala-medica.service';

describe('SalaMedica Service', () => {
  let service: SalaMedicaService;
  let httpMock: HttpTestingController;
  let elemDefault: ISalaMedica;
  let expectedResult: ISalaMedica | ISalaMedica[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SalaMedicaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nroSala: 0,
      nombre: 'AAAAAAA',
      ubicacion: 'AAAAAAA',
      equipamiento: 'AAAAAAA',
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

    it('should create a SalaMedica', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SalaMedica()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalaMedica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nroSala: 1,
          nombre: 'BBBBBB',
          ubicacion: 'BBBBBB',
          equipamiento: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalaMedica', () => {
      const patchObject = Object.assign(
        {
          nombre: 'BBBBBB',
          ubicacion: 'BBBBBB',
          equipamiento: 'BBBBBB',
        },
        new SalaMedica()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalaMedica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nroSala: 1,
          nombre: 'BBBBBB',
          ubicacion: 'BBBBBB',
          equipamiento: 'BBBBBB',
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

    it('should delete a SalaMedica', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSalaMedicaToCollectionIfMissing', () => {
      it('should add a SalaMedica to an empty array', () => {
        const salaMedica: ISalaMedica = { id: 123 };
        expectedResult = service.addSalaMedicaToCollectionIfMissing([], salaMedica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salaMedica);
      });

      it('should not add a SalaMedica to an array that contains it', () => {
        const salaMedica: ISalaMedica = { id: 123 };
        const salaMedicaCollection: ISalaMedica[] = [
          {
            ...salaMedica,
          },
          { id: 456 },
        ];
        expectedResult = service.addSalaMedicaToCollectionIfMissing(salaMedicaCollection, salaMedica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalaMedica to an array that doesn't contain it", () => {
        const salaMedica: ISalaMedica = { id: 123 };
        const salaMedicaCollection: ISalaMedica[] = [{ id: 456 }];
        expectedResult = service.addSalaMedicaToCollectionIfMissing(salaMedicaCollection, salaMedica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salaMedica);
      });

      it('should add only unique SalaMedica to an array', () => {
        const salaMedicaArray: ISalaMedica[] = [{ id: 123 }, { id: 456 }, { id: 51707 }];
        const salaMedicaCollection: ISalaMedica[] = [{ id: 123 }];
        expectedResult = service.addSalaMedicaToCollectionIfMissing(salaMedicaCollection, ...salaMedicaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salaMedica: ISalaMedica = { id: 123 };
        const salaMedica2: ISalaMedica = { id: 456 };
        expectedResult = service.addSalaMedicaToCollectionIfMissing([], salaMedica, salaMedica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salaMedica);
        expect(expectedResult).toContain(salaMedica2);
      });

      it('should accept null and undefined values', () => {
        const salaMedica: ISalaMedica = { id: 123 };
        expectedResult = service.addSalaMedicaToCollectionIfMissing([], null, salaMedica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salaMedica);
      });

      it('should return initial array if no SalaMedica is added', () => {
        const salaMedicaCollection: ISalaMedica[] = [{ id: 123 }];
        expectedResult = service.addSalaMedicaToCollectionIfMissing(salaMedicaCollection, undefined, null);
        expect(expectedResult).toEqual(salaMedicaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
