import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EvaluacionFisicaService } from '../service/evaluacion-fisica.service';
import { IEvaluacionFisica, EvaluacionFisica } from '../evaluacion-fisica.model';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { ConsultaMedicaService } from 'app/entities/consulta-medica/service/consulta-medica.service';

import { EvaluacionFisicaUpdateComponent } from './evaluacion-fisica-update.component';

describe('EvaluacionFisica Management Update Component', () => {
  let comp: EvaluacionFisicaUpdateComponent;
  let fixture: ComponentFixture<EvaluacionFisicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let evaluacionFisicaService: EvaluacionFisicaService;
  let consultaMedicaService: ConsultaMedicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EvaluacionFisicaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EvaluacionFisicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EvaluacionFisicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    evaluacionFisicaService = TestBed.inject(EvaluacionFisicaService);
    consultaMedicaService = TestBed.inject(ConsultaMedicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ConsultaMedica query and add missing value', () => {
      const evaluacionFisica: IEvaluacionFisica = { id: 456 };
      const consultaMedica: IConsultaMedica = { id: 92904 };
      evaluacionFisica.consultaMedica = consultaMedica;

      const consultaMedicaCollection: IConsultaMedica[] = [{ id: 86466 }];
      jest.spyOn(consultaMedicaService, 'query').mockReturnValue(of(new HttpResponse({ body: consultaMedicaCollection })));
      const additionalConsultaMedicas = [consultaMedica];
      const expectedCollection: IConsultaMedica[] = [...additionalConsultaMedicas, ...consultaMedicaCollection];
      jest.spyOn(consultaMedicaService, 'addConsultaMedicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ evaluacionFisica });
      comp.ngOnInit();

      expect(consultaMedicaService.query).toHaveBeenCalled();
      expect(consultaMedicaService.addConsultaMedicaToCollectionIfMissing).toHaveBeenCalledWith(
        consultaMedicaCollection,
        ...additionalConsultaMedicas
      );
      expect(comp.consultaMedicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const evaluacionFisica: IEvaluacionFisica = { id: 456 };
      const consultaMedica: IConsultaMedica = { id: 5256 };
      evaluacionFisica.consultaMedica = consultaMedica;

      activatedRoute.data = of({ evaluacionFisica });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(evaluacionFisica));
      expect(comp.consultaMedicasSharedCollection).toContain(consultaMedica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EvaluacionFisica>>();
      const evaluacionFisica = { id: 123 };
      jest.spyOn(evaluacionFisicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ evaluacionFisica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: evaluacionFisica }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(evaluacionFisicaService.update).toHaveBeenCalledWith(evaluacionFisica);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EvaluacionFisica>>();
      const evaluacionFisica = new EvaluacionFisica();
      jest.spyOn(evaluacionFisicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ evaluacionFisica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: evaluacionFisica }));
      saveSubject.complete();

      // THEN
      expect(evaluacionFisicaService.create).toHaveBeenCalledWith(evaluacionFisica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EvaluacionFisica>>();
      const evaluacionFisica = { id: 123 };
      jest.spyOn(evaluacionFisicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ evaluacionFisica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(evaluacionFisicaService.update).toHaveBeenCalledWith(evaluacionFisica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackConsultaMedicaById', () => {
      it('Should return tracked ConsultaMedica primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackConsultaMedicaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
