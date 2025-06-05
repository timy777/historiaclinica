import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EstudioMedicoService } from '../service/estudio-medico.service';
import { IEstudioMedico, EstudioMedico } from '../estudio-medico.model';
import { IConsultaMedica } from 'app/entities/consulta-medica/consulta-medica.model';
import { ConsultaMedicaService } from 'app/entities/consulta-medica/service/consulta-medica.service';

import { EstudioMedicoUpdateComponent } from './estudio-medico-update.component';

describe('EstudioMedico Management Update Component', () => {
  let comp: EstudioMedicoUpdateComponent;
  let fixture: ComponentFixture<EstudioMedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let estudioMedicoService: EstudioMedicoService;
  let consultaMedicaService: ConsultaMedicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EstudioMedicoUpdateComponent],
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
      .overrideTemplate(EstudioMedicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstudioMedicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    estudioMedicoService = TestBed.inject(EstudioMedicoService);
    consultaMedicaService = TestBed.inject(ConsultaMedicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ConsultaMedica query and add missing value', () => {
      const estudioMedico: IEstudioMedico = { id: 456 };
      const consultaMedica: IConsultaMedica = { id: 92846 };
      estudioMedico.consultaMedica = consultaMedica;

      const consultaMedicaCollection: IConsultaMedica[] = [{ id: 59295 }];
      jest.spyOn(consultaMedicaService, 'query').mockReturnValue(of(new HttpResponse({ body: consultaMedicaCollection })));
      const additionalConsultaMedicas = [consultaMedica];
      const expectedCollection: IConsultaMedica[] = [...additionalConsultaMedicas, ...consultaMedicaCollection];
      jest.spyOn(consultaMedicaService, 'addConsultaMedicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ estudioMedico });
      comp.ngOnInit();

      expect(consultaMedicaService.query).toHaveBeenCalled();
      expect(consultaMedicaService.addConsultaMedicaToCollectionIfMissing).toHaveBeenCalledWith(
        consultaMedicaCollection,
        ...additionalConsultaMedicas
      );
      expect(comp.consultaMedicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const estudioMedico: IEstudioMedico = { id: 456 };
      const consultaMedica: IConsultaMedica = { id: 40134 };
      estudioMedico.consultaMedica = consultaMedica;

      activatedRoute.data = of({ estudioMedico });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(estudioMedico));
      expect(comp.consultaMedicasSharedCollection).toContain(consultaMedica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EstudioMedico>>();
      const estudioMedico = { id: 123 };
      jest.spyOn(estudioMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estudioMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estudioMedico }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(estudioMedicoService.update).toHaveBeenCalledWith(estudioMedico);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EstudioMedico>>();
      const estudioMedico = new EstudioMedico();
      jest.spyOn(estudioMedicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estudioMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estudioMedico }));
      saveSubject.complete();

      // THEN
      expect(estudioMedicoService.create).toHaveBeenCalledWith(estudioMedico);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EstudioMedico>>();
      const estudioMedico = { id: 123 };
      jest.spyOn(estudioMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estudioMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(estudioMedicoService.update).toHaveBeenCalledWith(estudioMedico);
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
