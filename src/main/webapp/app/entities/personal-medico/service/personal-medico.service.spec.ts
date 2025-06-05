import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPersonalMedico, PersonalMedico } from '../personal-medico.model';

import { PersonalMedicoService } from './personal-medico.service';

describe('PersonalMedico Service', () => {
  let service: PersonalMedicoService;
  let httpMock: HttpTestingController;
  let elemDefault: IPersonalMedico;
  let expectedResult: IPersonalMedico | IPersonalMedico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonalMedicoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nombre: 'AAAAAAA',
      especialidad: 'AAAAAAA',
      telefonoContacto: 'AAAAAAA',
      correo: 'AAAAAAA',
      licenciaMedica: 'AAAAAAA',
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

    it('should create a PersonalMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PersonalMedico()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonalMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombre: 'BBBBBB',
          especialidad: 'BBBBBB',
          telefonoContacto: 'BBBBBB',
          correo: 'BBBBBB',
          licenciaMedica: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonalMedico', () => {
      const patchObject = Object.assign(
        {
          especialidad: 'BBBBBB',
          correo: 'BBBBBB',
          licenciaMedica: 'BBBBBB',
        },
        new PersonalMedico()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonalMedico', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombre: 'BBBBBB',
          especialidad: 'BBBBBB',
          telefonoContacto: 'BBBBBB',
          correo: 'BBBBBB',
          licenciaMedica: 'BBBBBB',
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

    it('should delete a PersonalMedico', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPersonalMedicoToCollectionIfMissing', () => {
      it('should add a PersonalMedico to an empty array', () => {
        const personalMedico: IPersonalMedico = { id: 123 };
        expectedResult = service.addPersonalMedicoToCollectionIfMissing([], personalMedico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personalMedico);
      });

      it('should not add a PersonalMedico to an array that contains it', () => {
        const personalMedico: IPersonalMedico = { id: 123 };
        const personalMedicoCollection: IPersonalMedico[] = [
          {
            ...personalMedico,
          },
          { id: 456 },
        ];
        expectedResult = service.addPersonalMedicoToCollectionIfMissing(personalMedicoCollection, personalMedico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonalMedico to an array that doesn't contain it", () => {
        const personalMedico: IPersonalMedico = { id: 123 };
        const personalMedicoCollection: IPersonalMedico[] = [{ id: 456 }];
        expectedResult = service.addPersonalMedicoToCollectionIfMissing(personalMedicoCollection, personalMedico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personalMedico);
      });

      it('should add only unique PersonalMedico to an array', () => {
        const personalMedicoArray: IPersonalMedico[] = [{ id: 123 }, { id: 456 }, { id: 28136 }];
        const personalMedicoCollection: IPersonalMedico[] = [{ id: 123 }];
        expectedResult = service.addPersonalMedicoToCollectionIfMissing(personalMedicoCollection, ...personalMedicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personalMedico: IPersonalMedico = { id: 123 };
        const personalMedico2: IPersonalMedico = { id: 456 };
        expectedResult = service.addPersonalMedicoToCollectionIfMissing([], personalMedico, personalMedico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personalMedico);
        expect(expectedResult).toContain(personalMedico2);
      });

      it('should accept null and undefined values', () => {
        const personalMedico: IPersonalMedico = { id: 123 };
        expectedResult = service.addPersonalMedicoToCollectionIfMissing([], null, personalMedico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personalMedico);
      });

      it('should return initial array if no PersonalMedico is added', () => {
        const personalMedicoCollection: IPersonalMedico[] = [{ id: 123 }];
        expectedResult = service.addPersonalMedicoToCollectionIfMissing(personalMedicoCollection, undefined, null);
        expect(expectedResult).toEqual(personalMedicoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
