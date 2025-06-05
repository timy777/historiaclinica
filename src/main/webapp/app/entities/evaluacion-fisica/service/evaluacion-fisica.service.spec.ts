import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEvaluacionFisica, EvaluacionFisica } from '../evaluacion-fisica.model';

import { EvaluacionFisicaService } from './evaluacion-fisica.service';

describe('EvaluacionFisica Service', () => {
  let service: EvaluacionFisicaService;
  let httpMock: HttpTestingController;
  let elemDefault: IEvaluacionFisica;
  let expectedResult: IEvaluacionFisica | IEvaluacionFisica[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EvaluacionFisicaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      presionArterial: 'AAAAAAA',
      temperatura: 0,
      ritmoCardiaco: 0,
      frecuenciaRespiratoria: 0,
      peso: 0,
      altura: 0,
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

    it('should create a EvaluacionFisica', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new EvaluacionFisica()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EvaluacionFisica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          presionArterial: 'BBBBBB',
          temperatura: 1,
          ritmoCardiaco: 1,
          frecuenciaRespiratoria: 1,
          peso: 1,
          altura: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EvaluacionFisica', () => {
      const patchObject = Object.assign(
        {
          presionArterial: 'BBBBBB',
          temperatura: 1,
          ritmoCardiaco: 1,
        },
        new EvaluacionFisica()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EvaluacionFisica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          presionArterial: 'BBBBBB',
          temperatura: 1,
          ritmoCardiaco: 1,
          frecuenciaRespiratoria: 1,
          peso: 1,
          altura: 1,
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

    it('should delete a EvaluacionFisica', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEvaluacionFisicaToCollectionIfMissing', () => {
      it('should add a EvaluacionFisica to an empty array', () => {
        const evaluacionFisica: IEvaluacionFisica = { id: 123 };
        expectedResult = service.addEvaluacionFisicaToCollectionIfMissing([], evaluacionFisica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(evaluacionFisica);
      });

      it('should not add a EvaluacionFisica to an array that contains it', () => {
        const evaluacionFisica: IEvaluacionFisica = { id: 123 };
        const evaluacionFisicaCollection: IEvaluacionFisica[] = [
          {
            ...evaluacionFisica,
          },
          { id: 456 },
        ];
        expectedResult = service.addEvaluacionFisicaToCollectionIfMissing(evaluacionFisicaCollection, evaluacionFisica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EvaluacionFisica to an array that doesn't contain it", () => {
        const evaluacionFisica: IEvaluacionFisica = { id: 123 };
        const evaluacionFisicaCollection: IEvaluacionFisica[] = [{ id: 456 }];
        expectedResult = service.addEvaluacionFisicaToCollectionIfMissing(evaluacionFisicaCollection, evaluacionFisica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(evaluacionFisica);
      });

      it('should add only unique EvaluacionFisica to an array', () => {
        const evaluacionFisicaArray: IEvaluacionFisica[] = [{ id: 123 }, { id: 456 }, { id: 59498 }];
        const evaluacionFisicaCollection: IEvaluacionFisica[] = [{ id: 123 }];
        expectedResult = service.addEvaluacionFisicaToCollectionIfMissing(evaluacionFisicaCollection, ...evaluacionFisicaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const evaluacionFisica: IEvaluacionFisica = { id: 123 };
        const evaluacionFisica2: IEvaluacionFisica = { id: 456 };
        expectedResult = service.addEvaluacionFisicaToCollectionIfMissing([], evaluacionFisica, evaluacionFisica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(evaluacionFisica);
        expect(expectedResult).toContain(evaluacionFisica2);
      });

      it('should accept null and undefined values', () => {
        const evaluacionFisica: IEvaluacionFisica = { id: 123 };
        expectedResult = service.addEvaluacionFisicaToCollectionIfMissing([], null, evaluacionFisica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(evaluacionFisica);
      });

      it('should return initial array if no EvaluacionFisica is added', () => {
        const evaluacionFisicaCollection: IEvaluacionFisica[] = [{ id: 123 }];
        expectedResult = service.addEvaluacionFisicaToCollectionIfMissing(evaluacionFisicaCollection, undefined, null);
        expect(expectedResult).toEqual(evaluacionFisicaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
