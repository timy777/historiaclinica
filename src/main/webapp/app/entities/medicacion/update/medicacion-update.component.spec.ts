import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MedicacionService } from '../service/medicacion.service';
import { IMedicacion, Medicacion } from '../medicacion.model';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { ConsultaMedicaService } from 'app/entities/consulta-medica/service/consulta-medica.service';

import { MedicacionUpdateComponent } from './medicacion-update.component';

describe('Medicacion Management Update Component', () => {
  let comp: MedicacionUpdateComponent;
  let fixture: ComponentFixture<MedicacionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let medicacionService: MedicacionService;
  let consultaMedicaService: ConsultaMedicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MedicacionUpdateComponent],
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
      .overrideTemplate(MedicacionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MedicacionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    medicacionService = TestBed.inject(MedicacionService);
    consultaMedicaService = TestBed.inject(ConsultaMedicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ConsultaMedica query and add missing value', () => {
      const medicacion: IMedicacion = { id: 456 };
      const consultaMedica: IConsultaMedica = { id: 97622 };
      medicacion.consultaMedica = consultaMedica;

      const consultaMedicaCollection: IConsultaMedica[] = [{ id: 74761 }];
      jest.spyOn(consultaMedicaService, 'query').mockReturnValue(of(new HttpResponse({ body: consultaMedicaCollection })));
      const additionalConsultaMedicas = [consultaMedica];
      const expectedCollection: IConsultaMedica[] = [...additionalConsultaMedicas, ...consultaMedicaCollection];
      jest.spyOn(consultaMedicaService, 'addConsultaMedicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ medicacion });
      comp.ngOnInit();

      expect(consultaMedicaService.query).toHaveBeenCalled();
      expect(consultaMedicaService.addConsultaMedicaToCollectionIfMissing).toHaveBeenCalledWith(
        consultaMedicaCollection,
        ...additionalConsultaMedicas
      );
      expect(comp.consultaMedicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const medicacion: IMedicacion = { id: 456 };
      const consultaMedica: IConsultaMedica = { id: 37394 };
      medicacion.consultaMedica = consultaMedica;

      activatedRoute.data = of({ medicacion });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(medicacion));
      expect(comp.consultaMedicasSharedCollection).toContain(consultaMedica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Medicacion>>();
      const medicacion = { id: 123 };
      jest.spyOn(medicacionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medicacion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: medicacion }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(medicacionService.update).toHaveBeenCalledWith(medicacion);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Medicacion>>();
      const medicacion = new Medicacion();
      jest.spyOn(medicacionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medicacion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: medicacion }));
      saveSubject.complete();

      // THEN
      expect(medicacionService.create).toHaveBeenCalledWith(medicacion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Medicacion>>();
      const medicacion = { id: 123 };
      jest.spyOn(medicacionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medicacion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(medicacionService.update).toHaveBeenCalledWith(medicacion);
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
