import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TratamientoService } from '../service/tratamiento.service';
import { ITratamiento, Tratamiento } from '../tratamiento.model';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { ConsultaMedicaService } from 'app/entities/consulta-medica/service/consulta-medica.service';

import { TratamientoUpdateComponent } from './tratamiento-update.component';

describe('Tratamiento Management Update Component', () => {
  let comp: TratamientoUpdateComponent;
  let fixture: ComponentFixture<TratamientoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tratamientoService: TratamientoService;
  let consultaMedicaService: ConsultaMedicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TratamientoUpdateComponent],
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
      .overrideTemplate(TratamientoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TratamientoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tratamientoService = TestBed.inject(TratamientoService);
    consultaMedicaService = TestBed.inject(ConsultaMedicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ConsultaMedica query and add missing value', () => {
      const tratamiento: ITratamiento = { id: 456 };
      const consultaMedica: IConsultaMedica = { id: 34009 };
      tratamiento.consultaMedica = consultaMedica;

      const consultaMedicaCollection: IConsultaMedica[] = [{ id: 97588 }];
      jest.spyOn(consultaMedicaService, 'query').mockReturnValue(of(new HttpResponse({ body: consultaMedicaCollection })));
      const additionalConsultaMedicas = [consultaMedica];
      const expectedCollection: IConsultaMedica[] = [...additionalConsultaMedicas, ...consultaMedicaCollection];
      jest.spyOn(consultaMedicaService, 'addConsultaMedicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tratamiento });
      comp.ngOnInit();

      expect(consultaMedicaService.query).toHaveBeenCalled();
      expect(consultaMedicaService.addConsultaMedicaToCollectionIfMissing).toHaveBeenCalledWith(
        consultaMedicaCollection,
        ...additionalConsultaMedicas
      );
      expect(comp.consultaMedicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tratamiento: ITratamiento = { id: 456 };
      const consultaMedica: IConsultaMedica = { id: 78921 };
      tratamiento.consultaMedica = consultaMedica;

      activatedRoute.data = of({ tratamiento });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tratamiento));
      expect(comp.consultaMedicasSharedCollection).toContain(consultaMedica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Tratamiento>>();
      const tratamiento = { id: 123 };
      jest.spyOn(tratamientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tratamiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tratamiento }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tratamientoService.update).toHaveBeenCalledWith(tratamiento);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Tratamiento>>();
      const tratamiento = new Tratamiento();
      jest.spyOn(tratamientoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tratamiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tratamiento }));
      saveSubject.complete();

      // THEN
      expect(tratamientoService.create).toHaveBeenCalledWith(tratamiento);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Tratamiento>>();
      const tratamiento = { id: 123 };
      jest.spyOn(tratamientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tratamiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tratamientoService.update).toHaveBeenCalledWith(tratamiento);
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
