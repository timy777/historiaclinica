import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITratamiento, Tratamiento } from '../tratamiento.model';

import { TratamientoService } from './tratamiento.service';

describe('Tratamiento Service', () => {
  let service: TratamientoService;
  let httpMock: HttpTestingController;
  let elemDefault: ITratamiento;
  let expectedResult: ITratamiento | ITratamiento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TratamientoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      tipoTratamiento: 'AAAAAAA',
      duracion: 'AAAAAAA',
      objetivo: 'AAAAAAA',
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

    it('should create a Tratamiento', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Tratamiento()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Tratamiento', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tipoTratamiento: 'BBBBBB',
          duracion: 'BBBBBB',
          objetivo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Tratamiento', () => {
      const patchObject = Object.assign(
        {
          objetivo: 'BBBBBB',
        },
        new Tratamiento()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Tratamiento', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tipoTratamiento: 'BBBBBB',
          duracion: 'BBBBBB',
          objetivo: 'BBBBBB',
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

    it('should delete a Tratamiento', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTratamientoToCollectionIfMissing', () => {
      it('should add a Tratamiento to an empty array', () => {
        const tratamiento: ITratamiento = { id: 123 };
        expectedResult = service.addTratamientoToCollectionIfMissing([], tratamiento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tratamiento);
      });

      it('should not add a Tratamiento to an array that contains it', () => {
        const tratamiento: ITratamiento = { id: 123 };
        const tratamientoCollection: ITratamiento[] = [
          {
            ...tratamiento,
          },
          { id: 456 },
        ];
        expectedResult = service.addTratamientoToCollectionIfMissing(tratamientoCollection, tratamiento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Tratamiento to an array that doesn't contain it", () => {
        const tratamiento: ITratamiento = { id: 123 };
        const tratamientoCollection: ITratamiento[] = [{ id: 456 }];
        expectedResult = service.addTratamientoToCollectionIfMissing(tratamientoCollection, tratamiento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tratamiento);
      });

      it('should add only unique Tratamiento to an array', () => {
        const tratamientoArray: ITratamiento[] = [{ id: 123 }, { id: 456 }, { id: 87652 }];
        const tratamientoCollection: ITratamiento[] = [{ id: 123 }];
        expectedResult = service.addTratamientoToCollectionIfMissing(tratamientoCollection, ...tratamientoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tratamiento: ITratamiento = { id: 123 };
        const tratamiento2: ITratamiento = { id: 456 };
        expectedResult = service.addTratamientoToCollectionIfMissing([], tratamiento, tratamiento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tratamiento);
        expect(expectedResult).toContain(tratamiento2);
      });

      it('should accept null and undefined values', () => {
        const tratamiento: ITratamiento = { id: 123 };
        expectedResult = service.addTratamientoToCollectionIfMissing([], null, tratamiento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tratamiento);
      });

      it('should return initial array if no Tratamiento is added', () => {
        const tratamientoCollection: ITratamiento[] = [{ id: 123 }];
        expectedResult = service.addTratamientoToCollectionIfMissing(tratamientoCollection, undefined, null);
        expect(expectedResult).toEqual(tratamientoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
